package com.stylefeng.guns.rest.modular.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import com.stylefeng.guns.rest.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.rest.modular.PromoStockStatus;
import com.stylefeng.guns.rest.modular.prefix.PromoPrefix;
import com.stylefeng.guns.rest.modular.producer.PromoProducer;
import com.stylefeng.guns.rest.promoljq.exception.PromoEnum;
import com.stylefeng.guns.rest.promoljq.service.PromoService;
import com.stylefeng.guns.rest.promoljq.vo.PromoInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service(interfaceClass = PromoService.class)
@Component
@Slf4j
public class PromoServiceImpl implements PromoService {

    private final String PROMO_TOKEN_STOCK_LIMIT = "promo_token_stock_limit_";

    private final String PROMO_TOKEN_REDIS_PRE = "promo_token_redis_pre_";


    @Autowired
    MtimePromoMapper mapper;

    @Autowired
    MtimePromoStockMapper stockMapper;

    @Autowired
    MtimeStockLogMapper stockLogMapper;

    @Autowired
    Jedis jedis;

    @Autowired
    PromoProducer promoProducer;

    @Autowired
    MtimePromoOrderMapper promoOrderMapper;

    @Autowired
    MtimePromoMapper promoMapper;

    public boolean createPromoOrder(Integer promoId, Integer userId, Integer amount,String stockLogId) throws ParseException {
        // 获取生成订单所需的信息
        MtimePromoOrder promoOrder = new MtimePromoOrder();
        promoOrder.setAmount(amount);
        promoOrder.setUserId(userId);
        MtimePromo mtimePromo = new MtimePromo();
        mtimePromo.setUuid(promoId);
        mtimePromo = promoMapper.selectOne(mtimePromo);
        promoOrder.setCinemaId(mtimePromo.getCinemaId());
        String uuid = UUID.randomUUID().toString();
        promoOrder.setUuid(uuid);
        BigDecimal price = BigDecimal.valueOf(mtimePromo.getPrice().floatValue() * amount);
        promoOrder.setPrice(price);
        String exchangeCode = UUID.randomUUID().toString();
        promoOrder.setExchangeCode(exchangeCode);
        Date beginTime = new Date();
        String afterDayWeek = DateUtil.getAfterDayDate(34+"");
        Date endTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(afterDayWeek);
        promoOrder.setStartTime(beginTime);
        promoOrder.setEndTime(endTime);
        promoOrder.setCreateTime(beginTime);
        // 插入操作,如果返回值是null或者0，则表示订单插入失败，返回相应信息
        Integer insert = promoOrderMapper.insert(promoOrder);
        if (insert == null || insert == 0) return false;
        // 插入成功则进行redis的库存扣减
        Long stock = jedis.incrBy(PromoPrefix.PROMO_PUBLISH_STOCK + promoId, amount * -1);
        if (stock < 0) {
            jedis.incrBy(PromoPrefix.PROMO_PUBLISH_STOCK + promoId, amount);
            throw new GunsException(GunsExceptionEnum.PROMO_STOCK_ERROR);
        }
        stockLogMapper.updateStatus(stockLogId, PromoStockStatus.STOCK_LOG_SUCCESS.getIndex());
        return true;
    }

    @Override
    public List<PromoInfoVo> getPromo(Integer cinemaId) {
        List<PromoInfoVo> pros = mapper.selectPromos(cinemaId);
        return pros;
    }

    @Override
    public PromoEnum transactionCreateOrder(Integer promoId, Integer amount, Integer userId) {
        boolean isTrue = paramentchecking(promoId,amount);
        if (!isTrue) {
            return PromoEnum.PARAM_ERROR;
        }
        String stockLogId = createStockLog(promoId,amount);
        boolean result = promoProducer.transactionCreateOrder(userId,promoId,amount,stockLogId);
        if (!result) return PromoEnum.CREATE_FAIL;
        return PromoEnum.CREATE_SUCCESS;
    }

    @Override
    public boolean publishPromoStock(Integer cinemaId) {
        MtimePromo mtimePromo = new MtimePromo();
        mtimePromo.setCinemaId(cinemaId);
        HashMap hashMap = new HashMap<>();
        hashMap.put("cinema_id",cinemaId);
        List<MtimePromo> list = promoMapper.selectByMap(hashMap);
        for (MtimePromo promo : list) {
            Integer uuid = promo.getUuid();
            MtimePromoStock mtimePromoStock = new MtimePromoStock();
            mtimePromoStock.setPromoId(uuid);
            mtimePromoStock = stockMapper.selectOne(mtimePromoStock);
            Integer stock = mtimePromoStock.getStock();
            jedis.set(PromoPrefix.PROMO_PUBLISH_STOCK + uuid,stock + "");
        }
        return true;
    }

    @Override
    public String generateToken(Integer promoId, Integer userId) {
        Long tokenStock = jedis.incrBy(PROMO_TOKEN_STOCK_LIMIT + promoId,-1);
        if (tokenStock == null) {
            log.info("该令牌在redis中不存在，即将重新生成");
            boolean result = createPromoTokenStock(promoId);
            if (result) {
                log.info("生成成功");
                tokenStock = jedis.incrBy(PROMO_TOKEN_STOCK_LIMIT + promoId,-1);
            } else {
                log.info("生成失败，该promoId非法");
                return null;
            }
        }
        if (tokenStock < 1) return null;
        String token = UUID.randomUUID().toString().replaceAll("-","");
        // 将生成令牌的信息放到redis里面
        jedis.set(PROMO_TOKEN_REDIS_PRE + "userId_" + userId + "_promoId_" + promoId,token);
        return token;
    }

    private boolean createPromoTokenStock(Integer promoId) {
        MtimePromoStock promoStock = new MtimePromoStock();
        promoStock.setPromoId(promoId);
        promoStock = stockMapper.selectOne(promoStock);
        if (promoStock == null) return false;
        Integer stock = promoStock.getStock();
        try {
            // 设置令牌数为库存数的两倍
            jedis.set(PROMO_TOKEN_STOCK_LIMIT + promoId,stock * 2 + "");
        } catch (Exception e) {
            log.info("在redis中创建token库存信息时出错了");
            e.printStackTrace();
        }
        return true;
    }

    private String createStockLog(Integer promoId, Integer amount) {
        MtimeStockLog mtimeStockLog = new MtimeStockLog();
        mtimeStockLog.setPromoId(promoId);
        mtimeStockLog.setAmount(amount);
        mtimeStockLog.setStatus(PromoStockStatus.STOCK_LOG_INIT.getIndex());
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        mtimeStockLog.setUuid(uuid);
        mtimeStockLog.setCreateTime(new Date());
        Integer result = stockLogMapper.insertLog(mtimeStockLog);
        if (result == 0) return null;
        return mtimeStockLog.getUuid();
    }

    private boolean paramentchecking(Integer promoId, Integer amount) {
        // 判断该活动id是否正确
        Integer remain = mapper.selectAmountByPromoId(promoId);
        if (remain == null) return false;
        // 判断数量是否正确
        if (amount <= 0 || amount > 5) return false;
        return true;
    }

    public Integer selectStockLogStatusById(String stockLogId) {
        if (stockLogId == null) return 0;
        MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
        if (mtimeStockLog != null) return mtimeStockLog.getStatus();
        return 0;
    }
}

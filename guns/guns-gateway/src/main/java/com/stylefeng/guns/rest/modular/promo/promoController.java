package com.stylefeng.guns.rest.modular.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.util.concurrent.RateLimiter;
import com.stylefeng.guns.rest.BaseRespVO;
import com.stylefeng.guns.rest.common.utils.UserUtil;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.promoljq.exception.PromoEnum;
import com.stylefeng.guns.rest.promoljq.service.PromoService;
import com.stylefeng.guns.rest.promoljq.vo.PromoInfoVo;
import com.stylefeng.guns.rest.promoljq.vo.PromoResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("promo")
@Slf4j
public class promoController {

    private final String PROMO_TOKEN_REDIS_PRE = "promo_token_redis_pre_";

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    Jedis jedis;

    private ExecutorService executorService;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(200);

        /*rateLimiter = RateLimiter.create(10);*/
    }

    @Reference(interfaceClass = PromoService.class, check = false)
    private PromoService promoService;

    @RequestMapping("getPromo")
    public BaseRespVO getPromo(Integer cinemaId){
        List<PromoInfoVo> promos = promoService.getPromo(cinemaId);
        return BaseRespVO.ok(promos);
    }

    @RequestMapping("createOrder")
    public PromoResultVO createOrder(Integer promoId, Integer amount, HttpServletRequest request) {
        long beginTime = System.currentTimeMillis();
        Future<PromoResultVO> future = executorService.submit(() -> {
            Integer userId = UserUtil.getUserId(request, jwtProperties, jedis);
            if (userId == null || userId < 1) return PromoResultVO.fail("登录信息有误,请重新登录。");
            PromoEnum result = promoService.transactionCreateOrder(promoId, amount, userId);
            if (0 == result.getIndex()) {
                return PromoResultVO.success("下单成功");
            }
            return PromoResultVO.fail("下单失败: " + result.getMsg());
        });
        PromoResultVO result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return PromoResultVO.fail("下单失败");
        } catch (ExecutionException e) {
            e.printStackTrace();
            return PromoResultVO.fail("下单失败");
        }
        long endTime = System.currentTimeMillis();
        log.info("秒杀一单所花的时间为: {}",endTime - beginTime);
        return result;

    }

    @RequestMapping("publishPromoStock")
    public PromoResultVO publishPromoStock(Integer cinemaId) {
        try {
            promoService.publishPromoStock(cinemaId);
        } catch (Exception e) {
            e.printStackTrace();
            return PromoResultVO.success("发布失败");
        }
        return PromoResultVO.success("发布成功");
    }

    @RequestMapping("generateToken")
    public PromoResultVO generateToken(Integer promoId, HttpServletRequest request) {
        Integer userId = UserUtil.getUserId(request, jwtProperties, jedis);
        if (userId == null || userId < 1) return PromoResultVO.fail("用户信息错误");
        if (promoId == null || promoId < 1) return PromoResultVO.fail("活动参数错误");
        String generateToken = promoService.generateToken(promoId,userId);
        if (generateToken == null) return PromoResultVO.fail("秒杀失败");
        return PromoResultVO.success(generateToken);
    }

}

package com.stylefeng.guns.rest.modular.producer;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.rest.modular.PromoStockStatus;
import com.stylefeng.guns.rest.modular.service.PromoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;

@Component
@Slf4j
public class PromoProducer {

    private DefaultMQProducer defaultMQProducer;

    private TransactionMQProducer transactionMQProducer;

    @Value("${mq.producer.group}")
    private String producerGroup;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.address}")
    private String address;

    @Autowired
    PromoServiceImpl promoService;

    @Value("${mq.transaction.group}")
    private String transactionGroup;
    @PostConstruct
    public void initProducer() {
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(address);
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.info("mqProducer启动失败");
        }
        log.info("mqProducer启动成功");

        transactionMQProducer = new TransactionMQProducer(transactionGroup);
        transactionMQProducer.setNamesrvAddr(address);
        try {
            transactionMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("transactionProducer启动成功");
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            // TODO
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                byte[] body = message.getBody();
                String jsonString = new String(body);
                HashMap<String, Object> hashMap = JSON.parseObject(jsonString, HashMap.class);
                Integer promoId = (Integer) hashMap.get("promoId");
                Integer userId = (Integer) hashMap.get("userId");
                Integer amount = (Integer) hashMap.get("amount");
                String stockLogId = (String) hashMap.get("stockLogId");
                Boolean result = null;
                try {
                    result = promoService.createPromoOrder(promoId,userId,amount,stockLogId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if (result == null || !result) return LocalTransactionState.ROLLBACK_MESSAGE;

                return LocalTransactionState.COMMIT_MESSAGE;
            }

            // TODO
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                byte[] body = messageExt.getBody();
                String jsonString = new String(body);
                HashMap<String,Object> hashMap = JSON.parseObject(jsonString, HashMap.class);

                String stockLogId = (String) hashMap.get("stockLogId");
                Integer result = promoService.selectStockLogStatusById(stockLogId);
                if (PromoStockStatus.STOCK_LOG_INIT.getIndex() == result) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (PromoStockStatus.STOCK_LOG_SUCCESS.getIndex() == result) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
        });
    }

    public boolean transactionCreateOrder (Integer userId, Integer promoId, Integer amount, String stockLogId) {
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("userId",userId);
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);
        hashMap.put("stockLogId",stockLogId);

        HashMap<Object, Object> args = new HashMap<>();

        String jsonString = JSON.toJSONString(hashMap);

        Message message = new Message(topic, jsonString.getBytes(Charset.forName("utf-8")));
        TransactionSendResult transactionSendResult = null;
        try {
            transactionSendResult = transactionMQProducer.sendMessageInTransaction(message, args);
        } catch (MQClientException e) {
            e.printStackTrace();
            log.info("transactionMQProducer发送消息失败");
            return false;
        }
        if (transactionSendResult == null) {
            log.info("transactionMQProducer发送消息失败");
            return false;
        }
        log.info("transactionMQProducer发送消息成功");

        LocalTransactionState localTransactionState = transactionSendResult.getLocalTransactionState();
        if (LocalTransactionState.COMMIT_MESSAGE == localTransactionState) return true;
        return false;
    }

}


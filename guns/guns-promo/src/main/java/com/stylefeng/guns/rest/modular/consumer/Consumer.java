package com.stylefeng.guns.rest.modular.consumer;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class Consumer {

    @Value("${mq.address}")
    private String address;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.consumer.group}")
    private String groupName;

    private DefaultMQPushConsumer mqConsumer;

    @Autowired
    MtimePromoStockMapper stockMapper;

    @PostConstruct
    public void consumerInit() {
        mqConsumer = new DefaultMQPushConsumer(groupName);
        mqConsumer.setNamesrvAddr(address);
        try {
            mqConsumer.subscribe(topic,"*");
        } catch (MQClientException e) {
            log.info("mqConsumer订阅失败");
            e.printStackTrace();
        }
        log.info("mqConsumer订阅成功");

        mqConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                byte[] body = messageExt.getBody();
                String jsonString = new String(body);
                HashMap<String,Integer> hashMap = JSON.parseObject(jsonString, HashMap.class);
                Integer promoId = hashMap.get("promoId");
                Integer amount = hashMap.get("amount");
                Integer result = stockMapper.decreaseStock(promoId,amount);
                if (result < 1) return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            mqConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.info("comsumer启动失败");
        }
    }







}

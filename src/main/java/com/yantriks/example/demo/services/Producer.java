package com.yantriks.example.demo.services;

import com.yantriks.example.demo.beans.Employee;
import com.yantriks.example.demo.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;



@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;


    public void sendMessage(Employee employee){
        logger.info(String.format("$$$ -> Producing message --> %s",employee.toString()));
        ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send(Constants.EMPLOYEE_TOPIC, employee);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error(String.format("## -> Failed messages --> %s",employee.toString()), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                logger.info(String.format("## -> Successfully message delivered  --> %s",employee.toString()));
            }
        });
    }

    public void sendFailuresToDLQ(Employee employee){
        logger.info(String.format("$$$ -> Sending Failed messages DLQ --> %s",employee.toString()));
        ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send(Constants.DLQ_TOPIC,employee);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error(String.format("## -> Failed messages to DLQ --> %s",employee.toString()), throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                logger.info(String.format("## -> Successfully message delivered to DLQ  --> %s",employee.toString()));
            }
        });
    }
}

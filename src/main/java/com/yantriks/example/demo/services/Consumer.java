package com.yantriks.example.demo.services;

import com.yantriks.example.demo.beans.Employee;
import com.yantriks.example.demo.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);


    @Autowired
    private Producer producer;

    @KafkaListener(topics = Constants.EMPLOYEE_TOPIC, groupId = Constants.GROUP_ID)
    public void consume(Employee employee) {
        if (!validate(employee)) {
            producer.sendFailuresToDLQ(employee);
        }
        logger.info(String.format("$$ -> Received Message -> %s",employee));
    }

    private boolean validate(Employee employee)
    {
        return !isAnyNullOrEmpty(employee.getId()
                ,employee.getName()
                , employee.getRole()
                , employee.getOrganization());
    }
    private boolean isAnyNullOrEmpty(String... values) {

        return Arrays.stream(values).anyMatch(this:: isNullOrEmpty);
    }
    private boolean isNullOrEmpty(String value) {
        return value ==null || value.isEmpty();
    }
}

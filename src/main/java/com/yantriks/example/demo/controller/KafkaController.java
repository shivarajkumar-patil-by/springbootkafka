package com.yantriks.example.demo.controller;

import com.yantriks.example.demo.beans.Employee;
import com.yantriks.example.demo.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final Producer producer;

    @Autowired
    public KafkaController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestBody Employee employee) {
        this.producer.sendMessage(employee);
    }
}

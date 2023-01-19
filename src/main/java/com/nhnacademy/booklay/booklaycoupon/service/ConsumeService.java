package com.nhnacademy.booklay.booklaycoupon.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumeService {

    @KafkaListener(topics = "${message.topic.name}")
    public void consume(String message) {
        System.out.println(String.format("Consumed message : %s", message));
    }
}

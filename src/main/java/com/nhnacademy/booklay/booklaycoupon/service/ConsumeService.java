package com.nhnacademy.booklay.booklaycoupon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumeService {

    @KafkaListener(topics = "${message.topic.name}")
    public void consume(String message) {
        log.info(message);
    }
}

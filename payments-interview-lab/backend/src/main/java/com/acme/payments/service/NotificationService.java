package com.acme.payments.service;
import org.slf4j.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {
    private static final Logger log=LoggerFactory.getLogger(NotificationService.class);
    @Async
    public void notifyMerchant(String merchantId, String reference){
        if(reference.contains("FAIL-NOTIFY")) throw new IllegalStateException("SMTP unavailable");
        log.info("Notification sent merchant={} reference={}", merchantId, reference);
    }
}

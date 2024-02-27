package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailSenderServiceTest {

    @Autowired
    private MailSenderService mailService;

    @Test
    public void testSendNewMail() {
        mailService.sendNewMail("abdoujouini48@gmail.com", "Subject right here", "Body right there!");
    }
}
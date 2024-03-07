package com.vichayturen.rag_chatman;

import com.vichayturen.rag_chatman.mail.MyMailSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailTest {
    @Autowired
    private MyMailSender myMailSender;

    @Test
    public void test1() {
        myMailSender.sendVcode("1073931273@qq.com", "123456");
    }
}

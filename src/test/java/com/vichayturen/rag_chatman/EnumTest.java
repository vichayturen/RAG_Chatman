package com.vichayturen.rag_chatman;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {
    private enum E1 {
        A,
        B;
    }
    @Test
    void test1() {
        System.out.println(E1.A.name());
        System.out.println(E1.A.toString());
    }
}

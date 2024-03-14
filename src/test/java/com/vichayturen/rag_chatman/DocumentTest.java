package com.vichayturen.rag_chatman;

import com.vichayturen.rag_chatman.document.DocumentLoader;
import com.vichayturen.rag_chatman.document.MixSearchClient;
import com.vichayturen.rag_chatman.document.TextSpliter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

@SpringBootTest
public class DocumentTest {
    @Autowired
    private MixSearchClient mixSearchClient;

    @Test
    void test1() {
        File file = new File("V:\\模型结构硬件资源和部署方式-韦开意臣(1).docx");
        try (InputStream inputStream = new FileInputStream(file)) {
            String content = DocumentLoader.load(inputStream);
            List<String> splits = TextSpliter.split(content);
            System.out.println(splits);
            System.out.println(splits.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {
        System.out.println(mixSearchClient.getUrl("insert"));
    }
}

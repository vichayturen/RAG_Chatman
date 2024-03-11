package com.vichayturen.rag_chatman.document;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;

public class DocumentLoader {
    public static String load(InputStream inputStream) {
        try {
            XWPFDocument xdoc = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String text = extractor.getText();
            inputStream.close();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

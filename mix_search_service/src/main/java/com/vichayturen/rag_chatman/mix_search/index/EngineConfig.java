package com.vichayturen.rag_chatman.mix_search.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class EngineConfig {

    // 存放所有index的目录
    private String parentDir;

    public String getParentDir() {
        return parentDir;
    }

    private Directory indexDir;

    private Analyzer analyzer;

    private IndexWriterConfig config;

    public EngineConfig(String indexName) {
        String usrHome = System.getProperty("user.home");
        parentDir = new File(usrHome, ".fd").getPath();
        initIndexDir(indexName);
        config = new IndexWriterConfig();
    }

    public EngineConfig(String indexName, Analyzer analyzer) {
        String tmpDir = System.getProperty("user.home");
        parentDir = new File(tmpDir, ".fd").getPath();
        initIndexDir(indexName);
        config = new IndexWriterConfig();
    }

    public EngineConfig(Directory indexDir, Analyzer analyzer) {
        this.indexDir = indexDir;
        this.analyzer = analyzer;
        this.config = new IndexWriterConfig(analyzer);
    }

    public Directory getIndexDir() {
        return indexDir;
    }

    public IndexWriterConfig getConfig() {
        return config;
    }
    private void initIndexDir(String indexName) {

        File dir = new File(parentDir,indexName);
        try {
            if (dir.exists()&&dir.isDirectory()) {
                indexDir = FSDirectory.open(Paths.get(parentDir,indexName));
            } else {
                Path indexPath = Files.createDirectories(Path.of(parentDir,indexName));
                indexDir = FSDirectory.open(indexPath);
            }
        } catch (IOException e) {
            Exception newE = new Exception("获取存储目录失败...");
            newE.printStackTrace();
        }


    }


}

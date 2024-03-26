package com.vichayturen.rag_chatman.mix_search.index;

import com.vichayturen.rag_chatman.mix_search.common.DocumentConstant;
import com.vichayturen.rag_chatman.mix_search.common.TaskConstant;
import lombok.Getter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.QueryBuilder;

import java.io.IOException;
import java.util.List;

public class InternalEngine {

    IndexWriter indexWriter;

    IndexReader indexReader;

    IndexSearcher indexSearcher;

    @Getter
    EngineConfig engineConfig;

    public InternalEngine(EngineConfig config) {

        this.engineConfig = config;

    }

    public InternalEngine(String indexName, String task) throws IOException {
        switch (task) {
            case TaskConstant.INSERT -> {
                engineConfig = new EngineConfig(indexName);
                indexWriter = new IndexWriter(engineConfig.getIndexDir(), engineConfig.getConfig());
            }
            case TaskConstant.SEARCH -> {
                engineConfig = new EngineConfig(indexName, new StandardAnalyzer());
                indexReader = DirectoryReader.open(engineConfig.getIndexDir());
                indexSearcher = new IndexSearcher(indexReader);
            }
            case TaskConstant.CLEAN -> {
                engineConfig = new EngineConfig(indexName);
            }
        }
    }

    public void updateDocs(final Term uid, final List<LuceneDocument> docs, final IndexWriter indexWriter) throws IOException {
        if (docs.size() > 1) {
            indexWriter.updateDocuments(uid, docs);
        } else {
            indexWriter.updateDocument(uid, docs.get(0));
        }
    }

    public void addDocs(final List<LuceneDocument> docs) throws IOException {
        if (docs.size() > 1) {
            indexWriter.addDocuments(docs);
        } else {
            indexWriter.addDocument(docs.get(0));
        }
        indexWriter.close();
    }

    public void searchDocs(Query query, Collector collector) throws IOException {
        indexSearcher.search(query, collector);
    }

    public Query getQuery(String question) {
        QueryBuilder queryBuilder = new QueryBuilder(engineConfig.getConfig().getAnalyzer());
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        // 1. 第一个默认
        BoostQuery query1 = new BoostQuery(queryBuilder.createBooleanQuery(DocumentConstant.CONTENT, question), 1);
        builder.add(query1, BooleanClause.Occur.SHOULD);
        // 2. 第二个乘以3
//        Query q2 = queryBuilder.createBooleanQuery(Constants.FDDocument.PARA_CONTEXT, questionAnal);
//        if (q2 != null) {
//            BoostQuery query2 = new BoostQuery(q2, 3);
//            builder.add(query2, BooleanClause.Occur.SHOULD);
//        }
        BooleanQuery booleanQuery = builder.build();
        return booleanQuery;

    }

    public Document doc(int docId) throws IOException {
        return indexSearcher.doc(docId);
    }
}

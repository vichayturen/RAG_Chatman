package com.vichayturen.rag_chatman.mix_search.template;

import com.vichayturen.rag_chatman.chatman_common.Result;
import com.vichayturen.rag_chatman.chatman_common.entity.DocumentEntity;
import com.vichayturen.rag_chatman.mix_search.common.DocumentConstant;
import com.vichayturen.rag_chatman.mix_search.common.TaskConstant;
import com.vichayturen.rag_chatman.mix_search.index.LuceneDocument;
import com.vichayturen.rag_chatman.mix_search.index.InternalEngine;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class MixSearchTemplate {
    private InternalEngine engine;

    public Result<Boolean> insertDocuments(List<String> chunks, String indexName) {
        Result<Boolean> result = new Result<>();
        List<LuceneDocument> docs = new ArrayList<>();
        try {
            for (String chunk: chunks) {
                LuceneDocument doc = new LuceneDocument();
                doc.add(new Field(DocumentConstant.CONTENT, chunk, TextField.TYPE_STORED));
//                doc.addWithKey(Constants.FDDocument.PARA_VECTOR, new KnnFloatVectorField(Constants.FDDocument.PARA_VECTOR, document.getPara_vector()));
                docs.add(doc);
            }
            engine = new InternalEngine(indexName, TaskConstant.INSERT);
            engine.addDocs(docs);
            result.setCode(0);
            result.setMsg("插入成功");
            result.setData(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMsg("插入失败");
            result.setData(false);
        }
        return result;
    }
    
    public Result searchDocuments(String question, String indexName, int topK) {
        Result<List<DocumentEntity>> result = new Result<>();
        try {
            engine = new InternalEngine(indexName, TaskConstant.SEARCH);
            Query query = engine.getQuery(question);
            TopScoreDocCollector collector = TopScoreDocCollector.create(topK, 1000);
            engine.searchDocs(query, collector);

            List<DocumentEntity> hitDocs = new ArrayList<>();
            for (ScoreDoc scoreDoc : collector.topDocs().scoreDocs) {
                Document document = engine.doc(scoreDoc.doc);
                DocumentEntity hitDoc = new DocumentEntity();
                hitDoc.setContent(document.get(DocumentConstant.CONTENT));
                hitDocs.add(hitDoc);
            }
            result.setData(hitDocs);
            result.setCode(0);
            result.setMsg("搜索成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    public Result<Boolean> cleanDocuments(String indexName) {
        Result<Boolean> result = new Result<>();
        try {
            engine = new InternalEngine(indexName, TaskConstant.CLEAN);
            FileUtils.cleanDirectory(new File(String.valueOf(engine.getEngineConfig().getParentDir()), indexName));
            result.setCode(0);
            result.setMsg("删除成功");
            result.setData(true);
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(1);
            result.setMsg(e.getMessage());
            result.setData(false);
        }
        return result;
    }
}

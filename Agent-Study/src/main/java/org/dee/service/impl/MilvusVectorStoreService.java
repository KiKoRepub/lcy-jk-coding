package org.dee.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.dee.service.RagDocService;
import org.dee.service.VectorStoreService;
import org.dee.utils.LoggerUtils;
import org.dee.utils.VectorStoreUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class MilvusVectorStoreService implements VectorStoreService {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private RagDocService ragDocService;

    private final String DEAFULT_RESOURCE_PATH = "D:\\university\\JAVA\\jingKun\\lcy-jk-coding\\Agent-Study\\src\\main\\resources\\knowledge";
    @Override
    public boolean addVectorStore() {
        try {

            String resourcePath = DEAFULT_RESOURCE_PATH + "\\20251207_k1_01";
            Map<String,List<Document>> documents = VectorStoreUtils.getDocumentList(resourcePath);

            List<String> savedFileNames = new LinkedList<>();
            documents.forEach((k,v)->{

                // 保存进 向量数据库
                vectorStore.add(v);

                log.info("文件:{}，分片数量:{},已存入 milvus",k,v.size());
                // 保存到数据库中
                savedFileNames.add(k);
            });


            ragDocService.saveRagDocRecords(savedFileNames,resourcePath);




            return true;
        }catch (Exception e){
            e.printStackTrace();
            LoggerUtils.error(e,"添加文档数据失败");
            return false;
        }

    }

    @Override
    public List<Document> searchVector(String message, int topK) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(message)
                .topK(topK)
                .build());
    }

    @Override
    public void addVectorStoreFile() {

    }
}

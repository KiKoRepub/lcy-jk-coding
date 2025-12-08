package org.dee.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface VectorStoreService {


    boolean addVectorStore();

    List<Document> searchVector(String message,int topK);

    void addVectorStoreFile();
}

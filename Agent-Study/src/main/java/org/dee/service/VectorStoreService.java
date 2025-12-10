package org.dee.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VectorStoreService {


    boolean addVectorStore();

    Prompt searchVector(String message, int topK);

    void addVectorStoreFile(MultipartFile file);
}

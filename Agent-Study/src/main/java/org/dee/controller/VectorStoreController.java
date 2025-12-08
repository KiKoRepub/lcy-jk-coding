package org.dee.controller;

import org.dee.service.VectorStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vector-store")
public class VectorStoreController {


    @Autowired
    private VectorStoreService vectorStoreService;

    @PostMapping("/add")
    public void addVectorStore() {
        vectorStoreService.addVectorStore();
    }

    @PostMapping("/add-file")
    public void addVectorStoreFile() {
        vectorStoreService.addVectorStoreFile();
    }


}

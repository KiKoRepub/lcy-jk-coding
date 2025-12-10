package org.dee.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.dee.service.UserService;
import org.dee.service.VectorStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vector-store")
public class VectorStoreController {


    @Autowired
    private VectorStoreService vectorStoreService;

    @Autowired
    private UserService userService;

    @GetMapping("/chat")
    public String chatVectorStore(@RequestParam("message") String message,
                                  @RequestParam(value = "topK", defaultValue = "3") int topK) {
        return vectorStoreService.searchVector(message, topK).toString();
    }



    @PostMapping("/add")
    public void addVectorStore() {
        vectorStoreService.addVectorStore();
    }

    @PostMapping(value = "/add-file",produces = "multipart/form-data")
    public void addVectorStoreFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        userService.analyzeUserIdFromToken(request.getHeader("Authorization"));
        vectorStoreService.addVectorStoreFile(file);
    }


}

package org.dee.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dee.service.RagDocService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RagDocServiceImpl implements RagDocService {


    @Override
    public void saveRagDocRecords(List<String> savedFileNames, String resourcePath) {
        log.info("保存RAG文档记录，文件列表：{}，资源路径：{}", savedFileNames, resourcePath);
    }
}

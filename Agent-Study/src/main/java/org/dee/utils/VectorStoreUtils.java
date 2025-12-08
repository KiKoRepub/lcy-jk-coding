package org.dee.utils;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * RAG流程中的向量存储部分
 * 利用 SpringAI 自带的向量存储类，
 * 轻松将文档转换成想了并保存到向量数据库中
 */
public class VectorStoreUtils {

    private static final int CHUNK_SIZE = 10; // 每个块的目标大小
    private static final int MIN_CHUNK_SIZE_CHARS = 100; // 最小块字符数
    private static final int MIN_CHUNK_LENGTH_TO_EMBED = 10;// 嵌入的最小块长度
    private static final int MAX_NUM_CHUNKS = 400;// 最大块数量

    /**
     * 当提供的路径为目录时，读取目录下的所有文件（递归），
     * 使用 Tika + TokenTextSplitter 处理为 Document 列表，
     * 并按文件名分组，返回 Map<文件名, 文档列表>
     *
     * @param directoryPath 目录的本地文件系统路径
     * @return key 为文件名，value 为该文件切分后的 Document 列表
     */
    public static Map<String, List<Document>> getDocumentList(String directoryPath) {
        Map<String, List<Document>> grouped = new HashMap<>();

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return grouped;
        }

        TokenTextSplitter splitter = new TokenTextSplitter(
                CHUNK_SIZE,
                MIN_CHUNK_SIZE_CHARS,
                MIN_CHUNK_LENGTH_TO_EMBED,
                MAX_NUM_CHUNKS,
                true
        );

        // 递归遍历目录并处理文件
        traverseAndProcess(dir, splitter, grouped);

        return grouped;
    }

    /**
     * 递归遍历目录，遇到文件则处理。
     */
    private static void traverseAndProcess(File root, TokenTextSplitter splitter, Map<String, List<Document>> grouped) {
        File[] files = root.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) {
                traverseAndProcess(f, splitter, grouped);
            } else {
                processSingleFile(f, splitter, grouped);
            }
        }
    }

    /**
     * 将单个文件读取为 Document 列表并按文件名加入分组。
     */
    private static void processSingleFile(File file, TokenTextSplitter splitter, Map<String, List<Document>> grouped) {
        try {
            FileSystemResource resource = new FileSystemResource(file);
            TikaDocumentReader reader = new TikaDocumentReader(resource);
            List<Document> docs = splitter.apply(reader.get());
            String fileName = file.getName();
            grouped.computeIfAbsent(fileName, k -> new ArrayList<>()).addAll(docs);
        } catch (Exception ignored) {
            // 忽略无法解析的文件，生产环境可加日志
        }
    }
}

package com.bandomatteo.WebApp.services;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Component
public class EmbeddingService {

    @Value("${document.path}")
    private String documentPath;

    private EmbeddingModel embeddingModel;
    private EmbeddingStore embeddingStore;
    private UserDetailsService userDetailsService;
    private boolean isFileUploaded = false;

    public EmbeddingService(EmbeddingModel embeddingModel, EmbeddingStore embeddingStore) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public boolean loadSingleDocument() {

        String fileName = "/sample.pdf";
        String filePath = documentPath + fileName;

        try {
            if (!Files.exists(Paths.get(filePath))) {
                isFileUploaded = false;
                return false;
            }

            Document document = loadDocument(filePath, new ApachePdfBoxDocumentParser());

            EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                    .documentSplitter(DocumentSplitters.recursive(300, 10))
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();

            embeddingStoreIngestor.ingest(document);

            isFileUploaded = true;
            return true;

        } catch (Exception e) {
            isFileUploaded = false;
            return false;
        }
    }

    public boolean isFileUploaded() {
        return isFileUploaded;
    }
}


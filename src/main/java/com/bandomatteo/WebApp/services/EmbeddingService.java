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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public boolean loadSingleDocument(MultipartFile file) {
        // Imposta la directory di destinazione
        String userDir = System.getProperty("user.dir");
        String saveDir = userDir + "/data/documents";

        // Crea la directory se non esiste
        File directory = new File(saveDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Salva sempre il file come "sample.pdf"
        File savedFile = new File(directory, "sample.pdf");

        try (FileOutputStream fos = new FileOutputStream(savedFile)) {
            // Salva il file caricato nella directory
            fos.write(file.getBytes());

            // Carica il documento utilizzando il percorso del file salvato
            Document document = loadDocument(savedFile.getAbsolutePath(), new ApachePdfBoxDocumentParser());

            EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                    .documentSplitter(DocumentSplitters.recursive(300, 10))
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();

            embeddingStoreIngestor.ingest(document);

            isFileUploaded = true;
            return true;

        } catch (IOException e) {

            isFileUploaded = false;
            return false;

        }
    }

    public boolean isFileUploaded() {
        return isFileUploaded;
    }
}


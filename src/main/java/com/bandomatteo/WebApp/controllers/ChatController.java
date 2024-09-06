package com.bandomatteo.WebApp.controllers;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import com.bandomatteo.WebApp.domain.dto.ChatResponseDTO;
import com.bandomatteo.WebApp.services.EmbeddingService;
import com.bandomatteo.WebApp.services.GenAIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.reflections.Reflections.log;

@RestController
public class ChatController {

    private GenAIService genAIService;
    private EmbeddingService embeddingService;

    public ChatController(GenAIService genAIService, EmbeddingService embeddingService) {
        this.genAIService = genAIService;
        this.embeddingService = embeddingService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/chat")
    public ChatResponseDTO chat(@RequestBody ChatRequestDTO chatRequestDTO) {
        return new ChatResponseDTO(genAIService.getResponse(chatRequestDTO));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/loader/single")
    public ResponseEntity<String> loadSingle(@RequestParam("file") MultipartFile file) {
        log.info("Starting loadSingle document");

        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }

            embeddingService.loadSingleDocument(file);
            return ResponseEntity.ok("File uploaded successfully");

        } catch (Exception e) {
            log.error("Error during file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}

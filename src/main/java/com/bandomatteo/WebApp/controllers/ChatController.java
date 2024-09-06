package com.bandomatteo.WebApp.controllers;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import com.bandomatteo.WebApp.domain.dto.ChatResponseDTO;
import com.bandomatteo.WebApp.services.EmbeddingService;
import com.bandomatteo.WebApp.services.GenAIService;
import org.springframework.web.bind.annotation.*;

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
    public void loadSingle(){
        log.info("Starting loadSingle document");
        embeddingService.loadSingleDocument();

    }
}

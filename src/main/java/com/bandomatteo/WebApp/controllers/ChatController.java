package com.bandomatteo.WebApp.controllers;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import com.bandomatteo.WebApp.domain.dto.ChatResponseDTO;
import com.bandomatteo.WebApp.services.GenAIService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private GenAIService genAIService;

    public ChatController(GenAIService genAIService) {
        this.genAIService = genAIService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/chat")
    public ChatResponseDTO chat(@RequestBody ChatRequestDTO chatRequestDTO) {
        return new ChatResponseDTO(genAIService.getResponse(chatRequestDTO));

    }
}

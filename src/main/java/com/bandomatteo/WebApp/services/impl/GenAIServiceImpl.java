package com.bandomatteo.WebApp.services.impl;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import com.bandomatteo.WebApp.services.Assistant;
import com.bandomatteo.WebApp.services.RAGAssistant;
import com.bandomatteo.WebApp.services.EmbeddingService;
import com.bandomatteo.WebApp.services.GenAIService;
import dev.langchain4j.service.spring.AiService;



@AiService
public class GenAIServiceImpl implements GenAIService {

    private Assistant assistant;
    private final RAGAssistant RAGAssistant;
    private final EmbeddingService embeddingService;

    public GenAIServiceImpl(Assistant assistant, RAGAssistant RAGAssistant, EmbeddingService embeddingService) {
        this.assistant = assistant;
        this.RAGAssistant = RAGAssistant;
        this.embeddingService = embeddingService;
    }

    @Override
    public String getResponse(ChatRequestDTO chatRequestDTO) {
        if (embeddingService.isFileUploaded()) {

            String ragResponse = RAGAssistant.chat(chatRequestDTO.getId(), chatRequestDTO.getMessage());


            if (isResponseRelevant(ragResponse)) {
                return ragResponse;
            } else {
                return assistant.chat(chatRequestDTO.getId(), chatRequestDTO.getMessage());
            }
        } else {
            return assistant.chat(chatRequestDTO.getId(), chatRequestDTO.getMessage());
        }
    }

    private boolean isResponseRelevant(String response) {

        return !(response.contains("Mi dispiace") ||
                response.isEmpty() ||
                response.contains("non contengono") ||
                response.contains("I'm sorry")
        );
    }
}

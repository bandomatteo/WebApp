package com.bandomatteo.WebApp.services.impl;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import com.bandomatteo.WebApp.services.Assistant;
import com.bandomatteo.WebApp.services.GenAIService;
import dev.langchain4j.service.spring.AiService;



@AiService
public class GenAIServiceImpl implements GenAIService {

    private Assistant assistant;

    public GenAIServiceImpl(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public String getResponse(ChatRequestDTO chatRequestDTO) {

        return assistant.chat(chatRequestDTO.getId(), chatRequestDTO.getMessage());
    }
}

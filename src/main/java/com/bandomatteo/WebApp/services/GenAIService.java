package com.bandomatteo.WebApp.services;

import com.bandomatteo.WebApp.domain.dto.ChatRequestDTO;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface GenAIService {

    String getResponse(ChatRequestDTO chatRequestDTO);
}

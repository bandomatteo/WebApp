package com.bandomatteo.WebApp.services;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface RAGAssistant {
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}

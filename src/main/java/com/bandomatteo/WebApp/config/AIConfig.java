package com.bandomatteo.WebApp.config;

import com.bandomatteo.WebApp.services.Assistant;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public Assistant assistant() {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel() {

        Dotenv dotenv = Dotenv.load();

        return OpenAiChatModel.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .modelName(System.getProperty("langchain4j.open-ai.chat-model.model-name"))
                .responseFormat("json_object")
                .build();
    }
}

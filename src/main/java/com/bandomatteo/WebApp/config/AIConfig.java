package com.bandomatteo.WebApp.config;

import com.bandomatteo.WebApp.services.Assistant;
import com.bandomatteo.WebApp.services.RAGAssistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModelName;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;

import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Arrays.asList;

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
    public EmbeddingModel embeddingModel(){

        Dotenv dotenv = Dotenv.load();

        return OpenAiEmbeddingModel.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002)
                .build();
    }
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(){
        return new InMemoryEmbeddingStore<>();

    }

    @Bean
    public RAGAssistant RAGassistant() {

        EmbeddingStoreContentRetriever conetentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore())
                .embeddingModel(embeddingModel())
                .build();

        DefaultContentInjector contentInjector = DefaultContentInjector.builder()
                .metadataKeysToInclude(asList("file_name", "index"))
                .build();

        RetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder()
                .contentRetriever(conetentRetriever)
                .contentInjector(contentInjector)
                .build();

        return AiServices.builder(RAGAssistant.class)
                .chatLanguageModel(chatLanguageModel())
                .retrievalAugmentor(retrievalAugmentor)
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

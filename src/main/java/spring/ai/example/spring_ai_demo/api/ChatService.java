package spring.ai.example.spring_ai_demo.api;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.ai.example.spring_ai_demo.enums.ChatModel;
import spring.ai.example.spring_ai_demo.utils.ChatResponse;
import spring.ai.example.spring_ai_demo.utils.ChatResponseAggregator;
import spring.ai.example.spring_ai_demo.utils.ChatResponseMetadata;
import spring.ai.example.spring_ai_demo.utils.Generation;

@Service
public class ChatService {
	GeminiApi geminiApi = new GeminiApi();
    private final Client client;
    private final ChatResponseAggregator aggregator;

    public ChatService() {
        this.client = new Client(); 
        this.aggregator = new ChatResponseAggregator(); 
    }

    // Method to stream AI chat responses based on prompt
    public Flux<ChatResponse> streamChatResponse(String prompt, Consumer<ChatResponse> onComplete) {
        // Call Gemini API synchronously inside Mono.defer to make reactive
    	String fullPrompt = prompt + geminiApi.RMO_Prompt;
    	ChatModel model = ChatModel.GEMINI_2_0_FLASH;
        Mono<GenerateContentResponse> generateMono = Mono.fromSupplier(() -> 
            client.models.generateContent(model.getId(), fullPrompt, null)
        );

        // Wrap the entire AI response as a single ChatResponse in Flux
        Flux<ChatResponse> responseFlux = generateMono.flatMapMany(response -> {
            String fullText = response.text();

            ChatResponse singleResponse = new ChatResponse(
                List.of(new Generation(fullText, Map.of("chunkIndex", 0))),
                new ChatResponseMetadata("metadata-full-response")
            );

            return Flux.just(singleResponse);
        });

        return aggregator.aggregate(responseFlux, onComplete);
    }
}

package spring.ai.example.spring_ai_demo.controller;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import spring.ai.example.spring_ai_demo.RequestDto;
import spring.ai.example.spring_ai_demo.api.ChatService;
import spring.ai.example.spring_ai_demo.utils.ChatResponse;
import spring.ai.example.spring_ai_demo.utils.ChatResponseAggregator;

@RestController
@RequestMapping("/api/gemini")
public class ChatController {

    private final ChatService chatService;
    private final ChatResponseAggregator aggregator;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
        this.aggregator = new ChatResponseAggregator();
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> streamChat(@RequestBody RequestDto prompt) {
        AtomicReference<ChatResponse> aggregatedResponseRef = new AtomicReference<>();

        return chatService.streamChatResponse(prompt.getDescription(), aggregatedResponse -> {
            // This is called once aggregation is complete
            aggregatedResponseRef.set(aggregatedResponse);
        }).transform(flux -> aggregator.aggregate(flux, aggregatedResponseRef::set));
    }
}

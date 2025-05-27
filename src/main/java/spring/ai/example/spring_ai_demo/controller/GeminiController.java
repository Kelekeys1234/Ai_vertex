package spring.ai.example.spring_ai_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import spring.ai.example.spring_ai_demo.api.GeminiApi;
import spring.ai.example.spring_ai_demo.api.GeminiTextApi;
import spring.ai.example.spring_ai_demo.enums.ChatModel;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {
    @Autowired
    private GeminiApi geminiApi;
    @Autowired
    private GeminiTextApi geminiTextApi;

    @PostMapping("/chat")
    public Mono<ResponseEntity<String>> chat(@RequestBody Map<String, String> request) {
        String prompt = request.get("description");
        if (prompt == null || prompt.isBlank()) {
            return Mono.just(ResponseEntity.badRequest().body("Prompt is required"));
        }

        return geminiApi.chat(ChatModel.GEMINI_2_0_FLASH, prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body("Error calling Gemini API: " + e.getMessage())));
    }
    
    @PostMapping("/chat/text")
    public Mono<ResponseEntity<String>> textChat(@RequestBody Map<String, String> request) {
        String prompt = request.get("description");
        if (prompt == null || prompt.isBlank()) {
            return Mono.just(ResponseEntity.badRequest().body("Prompt is required"));
        }

        return geminiTextApi.chat(ChatModel.GEMINI_2_0_FLASH, prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body("Error calling Gemini API: " + e.getMessage())));
    }
}

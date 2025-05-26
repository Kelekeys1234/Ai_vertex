package spring.ai.example.spring_ai_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import spring.ai.example.spring_ai_demo.api.GeminiApi;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiApi geminiApi;

    public GeminiController(GeminiApi geminiApi) {
        this.geminiApi = geminiApi;
    }

    @PostMapping("/chat")
    public Mono<ResponseEntity<String>> chat(@RequestBody Map<String, String> request) {
        String prompt = request.get("description");
        if (prompt == null || prompt.isBlank()) {
            return Mono.just(ResponseEntity.badRequest().body("Prompt is required"));
        }

        return geminiApi.chat(GeminiApi.ChatModel.GEMINI_2_0_FLASH, prompt)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body("Error calling Gemini API: " + e.getMessage())));
    }
}

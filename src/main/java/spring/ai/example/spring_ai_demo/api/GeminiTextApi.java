package spring.ai.example.spring_ai_demo.api;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import reactor.core.publisher.Mono;
import spring.ai.example.spring_ai_demo.enums.ChatModel;

@Component
public class GeminiTextApi {

	private final Client client;
	
	GeminiApi geminiApi = new GeminiApi();

	public GeminiTextApi() {
		this.client = new Client();
	}

	
	public Mono<String> chat(ChatModel model, String userPrompt) {
		return Mono.fromSupplier(() -> {
			// Call Gemini API
			String fullPropmt = userPrompt + geminiApi.RMO_Prompt;
			GenerateContentResponse response = client.models.generateContent(model.getId(), fullPropmt, null);
			String result = response.text();
			String text = Jsoup.parse(result).wholeText();

			// Just print the plain text response to console
			System.out.println("=== Gemini Response ===");
			System.out.println(text);
			System.out.println("========================");

			return text;
		});
	}
}
package spring.ai.example.spring_ai_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring.ai.example.spring_ai_demo.api.GeminiApi;
import spring.ai.example.spring_ai_demo.api.GeminiApi.ChatModel;

@SpringBootApplication
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiDemoApplication.class, args);
		  GeminiApi api = new GeminiApi();

	        api.chat(ChatModel.GEMINI_1_5_FLASH_LATEST, "Generate RMO quiz in HTML")
	           .subscribe(response -> {
	               System.out.println("Response completed.");
	           });
	    }
	

}

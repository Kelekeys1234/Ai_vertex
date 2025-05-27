package spring.ai.example.spring_ai_demo.enums;

public enum ChatModel {
	GEMINI_1_5_FLASH_LATEST("gemini-1.5-flash-latest"), GEMINI_2_0_FLASH("gemini-2.0-flash"),
	GEMINI_1_5_PRO_LATEST("gemini-1.5-pro-latest");

	final String id;

	ChatModel(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}

package spring.ai.example.spring_ai_demo.utils;

import java.util.Map;

public class Generation {
	private String content;
	private Map<String, Object> metadata;

	public Generation(String content, Map<String, Object> metadata) {
		this.content = content;
		this.metadata = metadata;
	}

	public String getContent() {
		return content;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}
}

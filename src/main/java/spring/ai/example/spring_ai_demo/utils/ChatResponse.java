package spring.ai.example.spring_ai_demo.utils;

import java.util.List;

public class ChatResponse {
    private List<Generation> result;
    private ChatResponseMetadata metadata;

    public ChatResponse(List<Generation> result, ChatResponseMetadata metadata) {
        this.result = result;
        this.metadata = metadata;
    }

    public List<Generation> getResult() {
        return result;
    }

    public ChatResponseMetadata getMetadata() {
        return metadata;
    }
}
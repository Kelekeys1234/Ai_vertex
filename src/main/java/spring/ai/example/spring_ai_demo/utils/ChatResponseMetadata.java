package spring.ai.example.spring_ai_demo.utils;


import java.util.Map;

public class ChatResponseMetadata {
    private String info;

    public ChatResponseMetadata(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
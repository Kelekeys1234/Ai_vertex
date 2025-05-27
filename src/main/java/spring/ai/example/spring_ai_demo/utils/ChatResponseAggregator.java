package spring.ai.example.spring_ai_demo.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class ChatResponseAggregator {
	

    private static final Logger logger = LoggerFactory.getLogger(ChatResponseAggregator.class);

    public Flux<ChatResponse> aggregate(Flux<ChatResponse> fluxChatResponse,
                                       Consumer<ChatResponse> onAggregationComplete) {

        AtomicReference<StringBuilder> stringBufferRef = new AtomicReference<>(new StringBuilder());
        AtomicReference<ChatResponseMetadata> lastMetadataRef = new AtomicReference<>();
        AtomicReference<Map<String, Object>> mapRef = new AtomicReference<>();

        return fluxChatResponse.doOnSubscribe(subscription -> {
            // logger.info("Aggregation Subscribe:" + subscription);
            stringBufferRef.set(new StringBuilder());
            mapRef.set(new HashMap<>());
        }).doOnNext(chatResponse -> {
            // logger.info("Aggregation Next:" + chatResponse);
            if (chatResponse.getResult() != null) {
                chatResponse.getResult().forEach(gen -> {
                    if (gen.getContent() != null) {
                        stringBufferRef.get().append(gen.getContent());
                    }
                    if (gen.getMetadata() != null) {
                        mapRef.get().putAll(gen.getMetadata());
                    }
                });
            }
            if (chatResponse.getMetadata() != null) {
                lastMetadataRef.set(chatResponse.getMetadata());
            }
        }).doOnComplete(() -> {
            // logger.debug("Aggregation Complete");
            onAggregationComplete.accept(
                new ChatResponse(
                    List.of(new Generation(stringBufferRef.get().toString(), mapRef.get())),
                    lastMetadataRef.get()
                )
            );
            stringBufferRef.set(new StringBuilder());
            mapRef.set(new HashMap<>());
        }).doOnError(e -> {
            logger.error("Aggregation Error", e);
        });
    }

}

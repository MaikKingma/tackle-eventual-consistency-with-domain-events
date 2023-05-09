package uk.devoxx.tackle_eventual_consistency.domaininteraction.kafka;

public interface KafkaProducerService {
    public void sendMessage(String topic, String message);
}

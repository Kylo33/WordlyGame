package game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationSystem {
    private final List<Consumer<String>> messageRecievers = new ArrayList<>();

    public void sendMessage(String message) {
        messageRecievers.forEach(c -> c.accept(message));
    }

    public void addMessageReciever(Consumer<String> stringConsumer) {
        messageRecievers.add(stringConsumer);
    }

    public void removeMessageReciever(Consumer<String> stringConsumer) {
        messageRecievers.remove(stringConsumer);
    }

    public void clearMessageRecievers() {
        messageRecievers.clear();
    }
}

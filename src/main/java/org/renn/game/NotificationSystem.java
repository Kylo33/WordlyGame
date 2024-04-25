package org.renn.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationSystem {
    private final List<Consumer<String>> messageReceivers = new ArrayList<>();

    public void sendMessage(String message) {
        messageReceivers.forEach(c -> c.accept(message));
    }

    public void addMessageReceiver(Consumer<String> stringConsumer) {
        messageReceivers.add(stringConsumer);
    }
}

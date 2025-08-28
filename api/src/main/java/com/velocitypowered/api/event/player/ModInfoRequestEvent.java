package com.velocitypowered.api.event.player;

import com.velocitypowered.api.event.annotation.AwaitingEvent;
import com.velocitypowered.api.proxy.Player;

@AwaitingEvent
public final class ModInfoRequestEvent {

    private final Player player;

    public ModInfoRequestEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

package top.mahua_a.gamecore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import top.mahua_a.gamecore.game.Game;

public class PlayerChatInGameEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Game game;
    private String msg;
    private ChatType type;

    public PlayerChatInGameEvent(Player who, Game game, String msg, ChatType type) {
        super(who);
        this.game = game;
        this.msg = msg;
        this.type = type;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Game getGame() {
        return game;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChatType getType() {
        return type;
    }
}

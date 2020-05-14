package top.mahua_a.gamecore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.mahua_a.gamecore.game.GameManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent event){
        GameManager.playerChat(event);
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        if(GameManager.isInGame(event.getPlayer())){
            GameManager.playerQuit(event.getPlayer());
        }
    }
}

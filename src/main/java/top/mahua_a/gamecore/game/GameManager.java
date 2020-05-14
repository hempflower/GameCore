package top.mahua_a.gamecore.game;


import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import top.mahua_a.gamecore.arena.Arena;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager {
    private static Map<Plugin, Set<Game>> gameMap = new HashMap<>();

    public static Game createGame(Plugin plugin, String name, Arena arena) {
        Game game = new Game(plugin, name, arena);
        gameMap.computeIfAbsent(plugin, k -> new HashSet<>()).add(game);
        return game;
    }

    public static void removeGame(Plugin plugin, Game game) {
        game.destroy();
        gameMap.computeIfAbsent(plugin, k -> new HashSet<>()).remove(game);
    }

    public static boolean isInGame(Player player){
        for(Set<Game> gameSet:gameMap.values()){
            for(Game game : gameSet){
               if(game.isInGame(player)){
                    return true;
               }
            }
        }
        return false;
    }

    private static Game getPlayerInGame(Player player){
        for(Set<Game> gameSet:gameMap.values()){
            for(Game game : gameSet){
                if(game.isInGame(player)){
                    return game;
                }
            }
        }
        return null;
    }

    public static void playerChat(AsyncPlayerChatEvent asyncPlayerChatEvent) {
        Game game = getPlayerInGame(asyncPlayerChatEvent.getPlayer());
        if(game == null){
            return;
        }
        if(!game.isChatInGame()){
            return;
        }
        asyncPlayerChatEvent.setCancelled(true);

    }

}

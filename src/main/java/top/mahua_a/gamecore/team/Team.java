package top.mahua_a.gamecore.team;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import sun.plugin2.main.server.Plugin;

import java.util.HashSet;
import java.util.Set;

public class Team {
    private String name;
    private Set<Player> players = new HashSet<>();
    private Color color = Color.WHITE;

    public Team(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void join(Player player) {
        players.add(player);
    }

    public void left(Player player) {
        players.remove(player);
    }

    public void teamMsg(String msg) {
        for(Player p : players){
            p.sendMessage(msg);
        }
    }

    public void teamMsg(String[] msg) {
        for(Player p : players){
            p.sendMessage(msg);
        }
    }

    public Set<Player> getPlayers(){
        return players;
    }

    public int getPlayerNum(){
        return players.size();
    }

}

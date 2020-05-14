package top.mahua_a.gamecore.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import top.mahua_a.gamecore.arena.Arena;
import top.mahua_a.gamecore.arena.ArenaManager;
import top.mahua_a.gamecore.event.ChatType;
import top.mahua_a.gamecore.event.PlayerChatInGameEvent;
import top.mahua_a.gamecore.team.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
    private Plugin plugin;
    private String name;
    private Map<Player, Team> teams = new HashMap<>();
    private Set<Player> players = new HashSet<>();
    private Set<Player> nonTeamPlayers = new HashSet<>();
    private Arena arena;

    //玩家消息是否只在游戏中可见
    private boolean chatInGame = true;
    //玩家消息只在队伍中可见
    private boolean chatInTeam = true;

    private GameState state = GameState.STOP;
    private int maxPlayer = 0;


    private Game() {

    }

    public Game(Plugin plugin, String name, Arena arena) {
        this.plugin = plugin;
        this.name = name;
        this.arena = arena;
    }

    public void join(Player player) {
        if (players.size() == maxPlayer) {

        }
        players.add(player);
        nonTeamPlayers.add(player);
    }

    public void left(Player player) {
        arena.left(player);
        players.remove(player);
        nonTeamPlayers.remove(player);
        for (Team t : teams.values()) {
            t.left(player);
        }
    }

    public void setState(GameState newState) {
        state = newState;
    }

    public void destroy() {
        ArenaManager.removeArena(plugin, arena);
        state = GameState.STOP;
        players.clear();
        nonTeamPlayers.clear();
    }

    public void chat(String msg, Player player) {
        ChatType chatType = isChatInTeam() ? ChatType.TEAM : ChatType.GAME;
        PlayerChatInGameEvent event = new PlayerChatInGameEvent(player, this, msg, chatType);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (event.getType() == ChatType.GAME) {
            gameMsg(event.getMsg());
        }
        if (event.getType() == ChatType.TEAM) {
            getPlayerTeam(event.getPlayer()).teamMsg(event.getMsg());
        }

    }

    public void gameMsg(String msg) {
        for (Player p : players) {
            p.sendMessage(msg);
        }
    }

    public boolean isInGame(Player player) {
        return players.contains(player);
    }

    public Team getPlayerTeam(Player player) {
        return teams.get(player);
    }

    public boolean isChatInGame() {
        return chatInGame;
    }

    public void setChatInGame(boolean chatInGame) {
        this.chatInGame = chatInGame;
    }

    public boolean isChatInTeam() {
        return chatInTeam;
    }

    public void setChatInTeam(boolean chatInTeam) {
        this.chatInTeam = chatInTeam;
    }
}

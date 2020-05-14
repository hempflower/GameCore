package top.mahua_a.gamecore.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
    private Set<Player> watchingPlayers = new HashSet<>();
    private Set<Player> invisiblePlayers = new HashSet<>();
    private Set<Player> leftPlayers = new HashSet<>();

    private Arena arena;

    //玩家消息是否只在游戏中可见
    private boolean chatInGame = true;
    //玩家消息只在队伍中可见
    private boolean chatInTeam = true;

    private boolean allowRejoin = true;

    private GameState state = GameState.STOP;
    private int maxPlayer = 0;


    private Game() {

    }

    public Game(Plugin plugin, String name, Arena arena) {
        this.plugin = plugin;
        this.name = name;
        this.arena = arena;

        Player player;
    }

    public Arena getArena() {
        return arena;
    }

    public void join(Player player) {
        if (players.size() == maxPlayer) {

        }
        players.add(player);
        nonTeamPlayers.add(player);
        arena.join(player);
    }

    public void left(Player player) {
        arena.left(player);
        players.remove(player);
        nonTeamPlayers.remove(player);
        leftPlayers.remove(player);
        for (Team t : teams.values()) {
            t.left(player);
        }
    }

    public void playerOffline(Player player) {
        this.leftPlayers.add(player);
    }

    public boolean isOffline(Player player) {
        return this.leftPlayers.contains(player);
    }

    public void playerOnline(Player player) {
        this.leftPlayers.remove(player);
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
            Team team = getPlayerTeam(event.getPlayer());
            if (team == null) {
                gameMsg(event.getMsg());
            } else {
                team.teamMsg(event.getMsg());
            }

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

    public boolean isAllowRejoin() {
        return allowRejoin;
    }

    public void setAllowRejoin(boolean allowRejoin) {
        this.allowRejoin = allowRejoin;
    }

    public boolean isWatchingPlayer(Player player) {
        return watchingPlayers.contains(player);
    }

    public void addWatchingPlayer(Player player) {
        watchingPlayers.add(player);
    }

    public void removeWatchingPlayer(Player player) {
        watchingPlayers.remove(player);
    }

    public void resetPlayer(Player player) {
        player.setCustomName(player.getName());
        player.setGlowing(false);
        player.setGliding(false);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.getInventory().clear();
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public void invisibleMode(Player player) {
        resetPlayer(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3, false, false));
    }

    public void visibleMode(Player player) {
        resetPlayer(player);
    }
}

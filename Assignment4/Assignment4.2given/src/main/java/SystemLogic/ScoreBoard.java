package SystemLogic;

import Entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoard {
    private final List<Player> players;

    public ScoreBoard() {
        players = new ArrayList<>();
    }

    /**
     * Adds a new player to the scoreboard.
     * If the player already exists, it won't be added again.
     *
     * @param player The player object to add.
     */
    public void addPlayer(Player player) {
        for (Player existingPlayer : players) {
            if (existingPlayer.getName().equals(player.getName())) {
                return; // Do nothing if the player already exists
            }
        }
        players.add(player);
    }

    /**
     * Sorts and ranks players by points in descending order.
     * The method does not modify the list but returns a sorted version of it.
     *
     * @return A sorted list of players by rank.
     */
    public List<Player> getRankedPlayers() {
        // Create a copy of the players list to avoid modifying the original
        List<Player> rankedPlayers = new ArrayList<>(players);

        // Sort players by points in descending order
        rankedPlayers.sort(Comparator.comparingInt(Player::getPoint).reversed());

        return rankedPlayers;
    }
}


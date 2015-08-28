package org.toursys.processor.html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.PlayerUpdateTournamentException;
import org.toursys.repository.model.Player;

public class PlayerHtmlUpdateFactory {

    private static final Logger logger = LoggerFactory.getLogger(PlayerHtmlUpdateFactory.class);

    public static void synchronizedIthfPlayer(List<Player> players) {
        try {
            URL url = new URL("http://stiga.trefik.cz/ithf/ranking/ranking.txt");
            InputStreamReader input = new InputStreamReader(url.openStream());
            BufferedReader bufRead = new BufferedReader(input);
            String myLine = null;

            int lineCount = 0;

            while ((myLine = bufRead.readLine()) != null) {
                if (lineCount > 1) {

                    String[] playerLine = myLine.split("\\t");
                    int rank = Integer.parseInt(playerLine[0]);
                    int playerId = Integer.parseInt(playerLine[1]);
                    String playerName = playerLine[2].trim().toUpperCase();

                    Player foundedPlayer = null;

                    for (Player player : players) {
                        if (player.getIthfId() != null && player.getIthfId().intValue() == playerId) {
                            foundedPlayer = player;
                            // break;
                        } else {
                            String dbName = (konvertujNaAscii(player.getName()) + " "
                                    + konvertujNaAscii(player.getSurname()) + " " + player.getPlayerDiscriminator())
                                    .toUpperCase().trim();
                            if (playerName.equals(dbName)) {
                                foundedPlayer = player;
                                foundedPlayer.setIthfId(playerId);
                                // break;
                            }
                        }
                    }

                    if (foundedPlayer != null) {
                        foundedPlayer.setWorldRanking(rank);
                    }
                }

                lineCount++;
            }

            for (Player player : players) {
                if (player.getIthfId() == null && player.getWorldRanking() == null) {
                    logger.warn("Player: " + player + " not found.");
                }
            }
        } catch (Exception e) {
            logger.error("Error during updating players", e);
            throw new PlayerUpdateTournamentException(e.getMessage());
        }

    }

    private static final String konvertujNaAscii(String value) {
        if (StringUtils.isBlank(value))
            return value;
        String ascii = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[\\W]", "");
        return ascii;
    }
}

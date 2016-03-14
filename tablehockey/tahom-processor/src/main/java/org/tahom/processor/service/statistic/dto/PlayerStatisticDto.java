package org.tahom.processor.service.statistic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tahom.repository.model.Player;

public class PlayerStatisticDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Player player;
	private Map<String, PlayerStatisticInfo> playerInfos;
	private List<Player> players;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map<String, PlayerStatisticInfo> getPlayerInfos() {
		if (playerInfos == null) {
			playerInfos = new HashMap<String, PlayerStatisticInfo>();
		}
		return playerInfos;
	}

	public void setPlayerInfos(Map<String, PlayerStatisticInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public List<PlayerStatisticInfo> getPlayerInfoValues() {
		return new ArrayList<PlayerStatisticInfo>(getPlayerInfos().values());
	}

	public List<Player> getPlayers() {
		if (players == null) {
			players = new ArrayList<Player>();
		}
		return players;
	}

}

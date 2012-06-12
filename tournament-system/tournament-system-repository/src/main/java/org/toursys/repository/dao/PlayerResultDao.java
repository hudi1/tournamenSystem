package org.toursys.repository.dao;

import java.util.List;

import org.toursys.repository.form.PlayerResultForm;
import org.toursys.repository.model.PlayerResult;

public interface PlayerResultDao {

	public void createPlayerResult(PlayerResult playerResult);

	public void updatePlayerResult(PlayerResult playerResult);

	public void deletePlayerResult(PlayerResult playerResult);

	public List<PlayerResult> findPlayerResult(PlayerResultForm playerResultForm);
}

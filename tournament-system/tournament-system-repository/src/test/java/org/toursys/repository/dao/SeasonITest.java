package org.toursys.repository.dao;

import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.toursys.repository.model.Season;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:repositoryContextTest.xml" })
@Transactional()
@TransactionConfiguration(defaultRollback = true)
public class SeasonITest {

	@Autowired
	private SeasonDao seasonDao;

	@Test
	public void testSeason() throws Exception {
		Season season = new Season();
		season.setName("2012");
		season.setSeasonId(16l);
		
		seasonDao.createSeason(season);
		List<Season> seasons = seasonDao.getAllSeason();
		assertNotNull(seasons);
				
		int size = seasons.size();
		assertTrue(size > 0);
		
		Season seasonDb = seasons.get(0);
		seasonDb.setName("2014");
		seasonDao.updateSeason(seasonDb);
		seasonDao.deleteSeason(seasonDb);
		
		int sizeAfterDelete = seasonDao.getAllSeason().size();
		assertTrue(sizeAfterDelete < size);
	}
}

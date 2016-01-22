package org.toursys.processor.service.wch;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.toursys.processor.html.WChHtmlImportFactory;
import org.toursys.processor.service.player.PlayerService;
import org.toursys.repository.dao.WchQualificationDao;
import org.toursys.repository.dao.impl.WchTournamentDaoImpl;
import org.toursys.repository.model.Player;
import org.toursys.repository.model.User;
import org.toursys.repository.model.WchQualification;
import org.toursys.repository.model.WchTournament;
import org.toursys.repository.model.wch.WChSeason;
import org.toursys.repository.model.wch.WChTableRecord;

public class WChService {

    @Inject
    private WchQualificationDao wchQualificationDao;

    @Inject
    private WchTournamentDaoImpl wchTournamentDaoImpl;

    @Inject
    private PlayerService playerService;

    @Inject
    private WChModel wChModel;

    @Transactional
    public WchQualification createWchQualification(WchQualification wchQualification) {
        return wchQualificationDao.insert(wchQualification);
    }

    @Transactional
    public int updateWchQualificationn(WchQualification wchQualification) {
        return wchQualificationDao.update(wchQualification);
    }

    @Transactional
    public int deleteWchQualification(WchQualification wchQualification) {
        return wchQualificationDao.delete(wchQualification);
    }

    @Transactional(readOnly = true)
    public List<WchQualification> listWchQualification(WchQualification wchQualification) {
        return wchQualificationDao.list(wchQualification._setInit(WchQualification.Association.wchTournaments));
    }

    @Transactional(readOnly = true)
    public WchQualification getWchQualification(WchQualification wchQualification) {
        return wchQualificationDao.get(wchQualification._setInit(WchQualification.Association.wchTournaments));
    }

    @Transactional
    public WchTournament createWchTournament(WchTournament wchTournament) {
        return wchTournamentDaoImpl.insert(wchTournament);
    }

    @Transactional
    public int updateWchTournament(WchTournament wchTournament) {
        return wchTournamentDaoImpl.update(wchTournament);
    }

    @Transactional
    public int deleteWchTournament(WchTournament wchTournament) {
        return wchTournamentDaoImpl.delete(wchTournament);
    }

    @Transactional(readOnly = true)
    public WChTableRecord getWchRecords(WChSeason wChSeason) {
        List<WchQualification> wchQualifications = listWchQualification(new WchQualification());
        WChTableRecord wchTableRecord = wChModel.map(wChSeason, wchQualifications);
        return wchTableRecord;
    }

    public void updateWch(User user) {
        List<Player> players = playerService.getUserPlayers(user);
        for (Player player : players) {
            if (player.getIthfId() != null) {
                WchQualification wchQualification = WChHtmlImportFactory.getWchQualification(player.getIthfId());

                boolean newQualification = false;
                if (wchQualification != null) {
                    WchQualification wchQualificationDb = getWchQualification(new WchQualification()
                            ._setIthfId(wchQualification.getIthfId()));
                    if (wchQualificationDb != null) {
                        wchQualificationDb.setLastUpdate(new Date());
                        updateWchQualificationn(wchQualificationDb);
                    } else {
                        String name = player.getName() + " " + player.getSurname() + " "
                                + player.getPlayerDiscriminator();
                        wchQualification.setName(name);
                        wchQualificationDb = createWchQualification(wchQualification);
                        newQualification = true;
                    }
                    for (WchTournament wchTournament : wchQualification.getWchTournaments()) {

                        WchTournament wchTournamentSaved = null;
                        if (newQualification) {
                            wchTournamentSaved = wchTournament;
                        } else {
                            boolean founded = false;
                            for (WchTournament wchTournamentDb : wchQualificationDb.getWchTournaments()) {
                                if (wchTournament.getName().equals(wchTournamentDb.getName())) {
                                    founded = true;
                                    break;
                                }
                            }
                            if (!founded) {
                                wchTournamentSaved = wchTournament;
                            }
                        }

                        if (wchTournamentSaved != null && wchQualificationDb.getId() != null) {
                            wchTournamentSaved.setWchQualification(wchQualificationDb);
                            createWchTournament(wchTournamentSaved);
                        }
                    }
                }
            }
        }
    }
}

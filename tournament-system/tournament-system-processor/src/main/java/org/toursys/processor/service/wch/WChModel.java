package org.toursys.processor.service.wch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.toursys.repository.model.WChRecord;
import org.toursys.repository.model.WchQualification;
import org.toursys.repository.model.WchTournament;
import org.toursys.repository.model.wch.WChSeason;

public class WChModel {

    private static final String NATION_SERIES = "Slovak Championships";

    public List<WChRecord> map(WChSeason wChSeason, List<WchQualification> wchQualifications) {
        List<WChRecord> wChRecords = new ArrayList<WChRecord>();
        for (WchQualification wchQualification : wchQualifications) {
            WChRecord wChRecord = new WChRecord();
            wChRecord.setName(wchQualification.getName());
            int total = 0;
            for (WchTournament wchTournament : wchQualification.getWchTournaments()) {
                if (NATION_SERIES.equals(wchTournament.getSeries())) {
                    if (wchTournament.getDate().after(wChSeason.getStartDateYear1())
                            && wchTournament.getDate().before(wChSeason.getEndDateYear1())) {
                        wChRecord.setSlovakChampionship1points(wchTournament.getPoints());
                        total += wchTournament.getPoints();
                    } else if (wchTournament.getDate().after(wChSeason.getStartDateYear2())
                            && wchTournament.getDate().before(wChSeason.getEndDateYear2())) {
                        wChRecord.setSlovakChampionship2points(wchTournament.getPoints());
                        total += wchTournament.getPoints();
                    }
                } else {
                    if (wchTournament.getDate().after(wChSeason.getStartDateYear1())
                            && wchTournament.getDate().before(wChSeason.getEndDateYear1())) {
                        if (wChRecord.getTournamentPoints1().size() > 2) {
                            continue;
                        }
                        wChRecord.getTournamentPoints1().add(wchTournament.getPoints());
                        total += wchTournament.getPoints();
                    } else if (wchTournament.getDate().after(wChSeason.getStartDateYear2())
                            && wchTournament.getDate().before(wChSeason.getEndDateYear2())) {
                        if (wChRecord.getTournamentPoints2().size() > 2) {
                            continue;
                        }
                        wChRecord.getTournamentPoints2().add(wchTournament.getPoints());
                        total += wchTournament.getPoints();
                    }
                }
            }

            Collections.sort(wChRecord.getTournamentPoints1(), new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });

            Collections.sort(wChRecord.getTournamentPoints2(), new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });

            wChRecord.setTotal(total);
            wChRecords.add(wChRecord);
        }

        Collections.sort(wChRecords, new Comparator<WChRecord>() {

            @Override
            public int compare(WChRecord o1, WChRecord o2) {
                return o2.getTotal().compareTo(o1.getTotal());
            }
        });

        return wChRecords;
    }
}

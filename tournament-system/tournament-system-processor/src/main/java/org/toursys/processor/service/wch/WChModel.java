package org.toursys.processor.service.wch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.toursys.repository.model.WchQualification;
import org.toursys.repository.model.WchTournament;
import org.toursys.repository.model.wch.WChSeason;
import org.toursys.repository.model.wch.WChSeasonRecord;
import org.toursys.repository.model.wch.WChTableHeader;
import org.toursys.repository.model.wch.WChTableRecord;
import org.toursys.repository.model.wch.WChTournamentDetailsRecord;
import org.toursys.repository.model.wch.season.WChRecord;

public class WChModel {

    public WChTableRecord map(WChSeason wChSeason, List<WchQualification> wchQualifications) {
        WChTableRecord wchTableRecord = new WChTableRecord();
        wchTableRecord.setRecords(getWChRecords(wChSeason, wchQualifications));
        wchTableRecord.setTableHeader(getWChTableHeader(wchTableRecord.getRecords(), wChSeason));
        return wchTableRecord;
    }

    private List<WChRecord> getWChRecords(WChSeason wChSeason, List<WchQualification> wchQualifications) {
        List<String> excludedSeries = wChSeason.getExcludedSeries();
        List<WChRecord> wChRecords = new ArrayList<WChRecord>();

        for (WchQualification wchQualification : wchQualifications) {
            WChRecord wChRecord = new WChRecord();
            wChRecord.setPlayerName(wchQualification.getName());
            wChRecords.add(wChRecord);

            for (Map.Entry<String, String> entry : wChSeason.getSeasonsName().entrySet()) {
                wChRecord.getWchSeasonRecords().put(entry.getValue(), new WChSeasonRecord());
            }

            Collections.sort(wchQualification.getWchTournaments(), new Comparator<WchTournament>() {
                @Override
                public int compare(WchTournament o1, WchTournament o2) {
                    return o2.getPoints().compareTo(o1.getPoints());
                };
            });
            int total = 0;
            for (WchTournament wchTournament : wchQualification.getWchTournaments()) {
                if (excludedSeries.contains(wchTournament.getSeries()) || wChSeason.getSeason(wchTournament) == null) {
                    continue;
                } else if (wChSeason.getNationalChampionshipSeriesName().equals(wchTournament.getSeries())) {
                    wChRecord
                            .getWchSeasonRecords()
                            .get(wChSeason.getSeason(wchTournament))
                            .setNationalChampionship(
                                    new WChTournamentDetailsRecord(wchTournament.getPoints().toString(), wchTournament
                                            .getName()));
                    total += wchTournament.getPoints();
                } else if (wChSeason.getNationalSeriesName() != null
                        && wChSeason.getNationalSeriesName().equals(wchTournament.getSeries())) {
                    // TODO zatial nie je potrebne
                } else {
                    if (wChRecord.getWchSeasonRecords().get(wChSeason.getSeason(wchTournament)).getTournamentDetails()
                            .size() >= wChSeason.getOtherSeriesCount()) {
                        continue;
                    }
                    wChRecord
                            .getWchSeasonRecords()
                            .get(wChSeason.getSeason(wchTournament))
                            .getTournamentDetails()
                            .add(new WChTournamentDetailsRecord(wchTournament.getPoints().toString(), wchTournament
                                    .getName()));
                    total += wchTournament.getPoints();
                }
            }

            for (Entry<String, WChSeasonRecord> record : wChRecord.getWchSeasonRecords().entrySet()) {
                if (record.getValue().getNationalChampionship() == null) {
                    record.getValue().setNationalChampionship(new WChTournamentDetailsRecord("", null));
                }

                int size = record.getValue().getTournamentDetails().size();
                while (size < wChSeason.getOtherSeriesCount() + wChSeason.getNationalSeriesCount()) {
                    record.getValue().getTournamentDetails().add(new WChTournamentDetailsRecord("", null));
                    size++;
                }

                Collections.sort(record.getValue().getTournamentDetails(),
                        new Comparator<WChTournamentDetailsRecord>() {
                            @Override
                            public int compare(WChTournamentDetailsRecord o1, WChTournamentDetailsRecord o2) {
                                Integer points1 = o1.getPoints().equals("") ? 0 : Integer.valueOf(o1.getPoints());
                                Integer points2 = o2.getPoints().equals("") ? 0 : Integer.valueOf(o2.getPoints());
                                return points2.compareTo(points1);
                            }
                        });

            }

            wChRecord.setTotalPoints(total);

            Collections.sort(wChRecords, new Comparator<WChRecord>() {

                @Override
                public int compare(WChRecord o1, WChRecord o2) {
                    return o2.getTotalPoints().compareTo(o1.getTotalPoints());
                }
            });

        }
        return wChRecords;
    }

    private WChTableHeader getWChTableHeader(List<WChRecord> wChRecords, WChSeason wChSeason) {
        WChTableHeader wChTableHeader = new WChTableHeader();
        if (!wChRecords.isEmpty()) {
            WChRecord record = wChRecords.get(0);
            for (Entry<String, WChSeasonRecord> entry : record.getWchSeasonRecords().entrySet()) {
                wChTableHeader.getWchSeasonHeaders().add(entry.getKey());
                for (int i = 0; i < entry.getValue().getTournamentDetails().size(); i++) {
                    int name = i + 1;
                    wChTableHeader.getWchTournamentHeaders().add(name + "");
                }

                if (entry.getValue().getNationalChampionship() != null) {
                    wChTableHeader.getWchTournamentHeaders().add(wChSeason.getNationalChampionshipSeriesIndexName());
                }
            }
        }
        return wChTableHeader;
    }
}

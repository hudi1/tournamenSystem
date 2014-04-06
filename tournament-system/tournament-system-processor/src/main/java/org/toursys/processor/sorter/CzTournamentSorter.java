package org.toursys.processor.sorter;

import java.util.List;

import org.toursys.repository.model.Participant;

public class CzTournamentSorter extends TournamentSorter {

    // O pořadí ve všech skupinách turnaje rozhodují následující kritéria (v pořadí, v jakém jsou uvedena):
    // 1) počet získaných bodů,
    // 2) počet bodů ze vzájemných zápasů hráčů se stejným počtem bodů,
    // 3) rozdíl skóre (počet vstřelených mínus počet obdržených branek) ze vzájemných zápasů,
    // 4) počet vstřelených branek ve vzájemných zápasech,
    // 5) pouze pro základní skupiny turnaje a jen v případě rozhodování o postupu do finálové skupiny A - rozdíl skóre
    // (počet vstřelených minus počet obdržených branek) ze zápasů s hráči postupujícími do finálových skupin A a B,
    // 6) pouze pro základní skupiny turnaje a jen v případě rozhodování o postupu do finálové skupiny A - počet
    // vstřelených branek v zápasech s hráči postupujícími do finálových skupin A a B,
    // 7) rozdíl celkového skóre (počet vstřelených minus počet obdržených branek) ve všech zápasech dané skupiny,
    // 8) počet vstřelených branek ve všech zápasech dané skupiny,
    // 9) hra na náhlou smrt.
    // V bodech 1 až 6 platí, že vyšší hodnota daného kritéria znamená pro hráče lepší umístění. Pokud je nutné
    // rozhodnout o pořadí hráčů hrou na náhlou smrt, pak bude výše umístěn hráč, který jako první vstřelí platný gól.
    @Override
    public void sort(List<Participant> participants) {
        // TODO Auto-generated method stub

    }

}

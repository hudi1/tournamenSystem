package org.toursys.processor.util;

import java.security.MessageDigest;

import org.toursys.processor.TournamentException;

public class TournamentUtil {

    public static final String ALGORITHM_SHA = "SHA-512";

    public static String getRoundName(int playerCount, int position) {
        int actualRound = getRound(playerCount, position);
        int roundsCount = binlog(playerCount);

        int differ = roundsCount - actualRound;
        switch (differ) {
        case -1:
            return RoundName._3rd.name();
        case 0:
            return RoundName.FINAL.name();
        case 1:
            return RoundName.SEMI_FINALS.name();
        case 2:
            return RoundName.QUARTER_FINALS.name();
        case 3:
            return RoundName.FINALS_8.name();
        case 4:
            return RoundName.FINALS_16.name();
        }

        throw new TournamentException("Unknown round of playOff");
    }

    /*
     * position zacinat od 1
     */
    public static int getRound(int playerCount, int position) {
        if (position == 0) {
            return 0;
        }

        int rounds = binlog(playerCount);

        if (playerCount == position) {
            return rounds + 1;
        }

        int gamesCount = 0;
        for (int i = 1; i <= rounds; i++) {
            gamesCount += playerCount / Math.pow(2, i);
            if (gamesCount > (position - 1)) {
                return i;
            }
        }
        return rounds;
    }

    public static int binlog(int bits) {
        int log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    public static String encryptUserPassword(String password) {
        try {
            final MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA);
            md.update(password.getBytes("UTF-8"));
            byte[] securePassword = md.digest();
            return bytes2hex(securePassword);
        } catch (Exception e) {
            // logger.error("!! ERROR encrypt password", e);
            throw new RuntimeException(e);
        }
    }

    public static String bytes2hex(byte[] value) {
        if (value == null)
            return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            if ((value[i] <= 0x0F) && (value[i] >= 0))
                result.append('0');
            result.append(Integer.toHexString(value[i] & 0xFF));
        }
        return result.toString();
    }
}

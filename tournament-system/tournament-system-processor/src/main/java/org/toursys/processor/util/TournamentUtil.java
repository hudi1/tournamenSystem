package org.toursys.processor.util;

import java.security.MessageDigest;

public class TournamentUtil {

    public static final String ALGORITHM_SHA = "SHA-512";

    /*
     * position zacinat od 1
     */
    public static int getRound(int playerCount, int position) {
        // pri vypisovani kola treba rozlisit prve
        if (position == 0) {
            return 0;
        }

        if (playerCount == position) {
            return getRound(playerCount, position - 1);
        }

        int rounds = binlog(playerCount);
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

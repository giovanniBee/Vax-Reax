package com.vaxreact;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class Encrypt {
    /* *
    * Applies hash algorithm (SHA-256) to given user's password and salt and returns digest
    */
    public static byte[] getSHA(UserModel user, String salt) throws NoSuchAlgorithmException {
        String toHash = user.getPassword() + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //using digest method to compute digest of password and e
        return md.digest(toHash.getBytes(StandardCharsets.UTF_8));
    }
    /* *
    * Converts digest into String, then return String
    */
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    /* *
    * Returns String containing salt corresponding to specified user
    */
    public static String findSalt(UserModel user){
        //looks for salt into DB
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        String lookForSalt = "SELECT \"Sale\" FROM \"Utente\" WHERE \"Username\" = '" + user.getUser()+ "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(lookForSalt);

            assert queryResult != null;
            if (!queryResult.next()) {
                System.out.println("errore 1");
                return "";
            }
            return queryResult.getString(1);
            //System.out.println(queryOutput.getString(1));



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /* *
    * Returns a String containing a cryptographically secure random number
    */

    public static String CSPRNG() {
        //returns an unseeded instance of default RNG algorithm based on most preferred provider from list of providers configured in java.security
        // On Windows, SHA1PRNG algorithm, which can be self-seeded or explicitly seeded is returned.
        SecureRandom secRan = new SecureRandom();
        byte[] ranBytes = new byte[20];
        secRan.nextBytes(ranBytes);
        //encodes into string
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(ranBytes);
    }
}

package com.kary69.bca.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMACUtils {
    private static String Lowercase(String str) {
        return str.toLowerCase();
    }

    private static String HexEncode(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] SHA_256(String requestBody) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                requestBody.getBytes(StandardCharsets.UTF_8));
        return encodedhash;
    }

    public static String calculateSignature(String apiSecret, String stringToSign) {
//        return HMAC_SHA256(apiSecret, stringToSign);
        try {
            return HMAC2(apiSecret, stringToSign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String createStringToSign(String authToken, String timestamp, String relativeUrl, String httpMethod, String requestBody) {
        String stringToSign = null;

        try {
            stringToSign = httpMethod+":"+relativeUrl+":"+authToken+":"+Lowercase(HexEncode(SHA_256(requestBody)))+":"+timestamp;
//            System.out.println(stringToSign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringToSign;
    }

//    public static String HMAC_SHA256(String secret, String message)
//    {
//        String hash="";
//        try {
//            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//            sha256_HMAC.init(secret_key);
//
//            hash = Base64.encode(sha256_HMAC.doFinal(message.getBytes()));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return hash.trim();
//    }

    public static String HMAC2(String key, String message) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return HexEncode(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
    }
}

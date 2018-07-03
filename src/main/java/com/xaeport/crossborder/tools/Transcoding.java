package com.xaeport.crossborder.tools;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Created by baozhe on 2017-7-28.
 */
public class Transcoding {

    public static String Crypto(String rawKey,boolean... isDecrypt) throws Exception{
        String encoding = "UTF8";
        byte[] data = isDecrypt.length > 0 ? Base64.getDecoder().decode(rawKey): rawKey.getBytes(encoding);
        byte[] key = "xa_eport".getBytes(encoding);
        byte[] iv = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).getBytes(encoding);

        DESKeySpec keySpec = new DESKeySpec(key);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        if(isDecrypt.length > 0){
            cipher.init(Cipher.DECRYPT_MODE,factory.generateSecret(keySpec),new IvParameterSpec(iv));
        }else{
            cipher.init(Cipher.ENCRYPT_MODE,factory.generateSecret(keySpec),new IvParameterSpec(iv));
        }
        byte[] result = cipher.doFinal(data);
        return isDecrypt.length > 0 ? new String(result,encoding):Base64.getEncoder().encodeToString(result);
    }

//    public static void main(String[] args){
//        String icNo = "XhkzMvSzZ6U=";
//        try {
//            String converageAfter = Transcoding.Crypto(icNo,true);
//            System.out.println(converageAfter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        icNo = "9000000010610";
//        try {
//            String converageAfter = Transcoding.Crypto(icNo);
//            System.out.println(converageAfter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        icNo = "66666666";
//        try {
//            String converageAfter = Transcoding.Crypto(icNo);
//            System.out.println(converageAfter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

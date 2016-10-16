package cortez.pdsofttrab2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Usuario on 15/10/2016.
 */

public class Security {

    Security(){

    }

    static public String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

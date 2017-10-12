package pl.pwsztar.services;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
@Service
public class KeyGenerator {

    public String generate(String s){
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

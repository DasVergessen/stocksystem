package my.work.stock.system.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class MD5Service {
    public String getMD5(String str) {
        if (str == null) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}

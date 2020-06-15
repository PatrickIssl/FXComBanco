/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

/**
 *
 * @author Vinicius Teider
 */
import java.io.FileNotFoundException;
import java.security.*;
import java.math.*;

public class MD5 {
    public String toMD5(String normal) throws NoSuchAlgorithmException{
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(normal.getBytes(),0,normal.length());
        return new BigInteger(1,m.digest()).toString(16);
    }
    
    
    
}
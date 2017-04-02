/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steganography;

/**
 *
 * @author accede
 */
import java.io.File;
import java.io.FileInputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

public class JavaCrypt
{
    public static void main(String[] args) throws Exception {
           File f=new File("C:/Users/accede/Desktop/Test.txt");
           int ch;

               StringBuffer strContent = new StringBuffer("");
               FileInputStream fin = null;
               try {
               fin = new FileInputStream(f);
               while ((ch = fin.read()) != -1)
                   strContent.append((char) ch);
                   fin.close();
                   } 
               catch (Exception e) {
                   System.out.println(e);
                   }

               System.out.println("Original string: " +strContent.toString()+"\n");
               // Get the KeyGenerator

           KeyGenerator kgen = KeyGenerator.getInstance("AES");
           kgen.init(128); // 192 and 256 bits may not be available
           

           // Generate the secret key specs.
           SecretKey skey = kgen.generateKey();
           byte[] raw = skey.getEncoded();

           SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");


           // Instantiate the cipher

           Cipher cipher = Cipher.getInstance("AES");

           cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

           byte[] encrypted = cipher.doFinal(strContent.toString().getBytes());
               /* for(int i=0;i<encrypted.length;i++){
                System.out.println(encrypted[i]);
                }*/
           System.out.println("encrypted string: " + encrypted.toString());

           cipher.init(Cipher.DECRYPT_MODE, skeySpec);
           byte[] original =cipher.doFinal(encrypted);

           String originalString = new String(original);
           System.out.println("Original string: " +originalString);
         }
}
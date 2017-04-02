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
    import java.io.*;  
    class FileRead{  
         FileInputStream fin;
         FileOutputStream fout;
    public static void main(String args[])throws Exception{  
         FileRead fr = new FileRead();
        }

    public FileRead() throws FileNotFoundException, IOException {
    fin=new FileInputStream(new File("C:/Users/accede/Desktop/Reqjar.txt"));  
//    fout=new FileOutputStream(new File("C:/Users/accede/Desktop/output2.txt"));  
    int i=0;  
    while((i=fin.read())!=-1){  
        
//    fout.write((byte)i);  
    }  
    fin.close();  
    }  
    }
 
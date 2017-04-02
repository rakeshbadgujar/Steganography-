/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steganography;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

/**
 *
 * @author accede
 */
public class SelectFileAESCrypto extends javax.swing.JPanel {

    /**
     * Creates new form SelectFileAESCrypto
     */
    public SelectFileAESCrypto() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MyRootPaneljPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgAreaFile = new javax.swing.JTextArea();
        btnSelfileAes = new javax.swing.JButton();
        LblSelectedFile = new javax.swing.JLabel();
        LblFilePath = new javax.swing.JLabel();

        msgAreaFile.setColumns(20);
        msgAreaFile.setRows(5);
        jScrollPane1.setViewportView(msgAreaFile);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSelfileAes.setText("Select file for AES");
        btnSelfileAes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelfileAesActionPerformed(evt);
            }
        });

        LblSelectedFile.setText("Selected File is: ");

        javax.swing.GroupLayout MyRootPaneljPanelLayout = new javax.swing.GroupLayout(MyRootPaneljPanel);
        MyRootPaneljPanel.setLayout(MyRootPaneljPanelLayout);
        MyRootPaneljPanelLayout.setHorizontalGroup(
            MyRootPaneljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyRootPaneljPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(LblSelectedFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MyRootPaneljPanelLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(MyRootPaneljPanelLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(btnSelfileAes, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MyRootPaneljPanelLayout.setVerticalGroup(
            MyRootPaneljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyRootPaneljPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelfileAes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MyRootPaneljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LblSelectedFile)
                    .addComponent(LblFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MyRootPaneljPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MyRootPaneljPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelfileAesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelfileAesActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileReader reader = null;

                File selectedFile = fileChooser.getSelectedFile();
                reader = new FileReader(selectedFile.getAbsolutePath());
                BufferedReader br = new BufferedReader(reader);
                msgAreaFile.read(br, evt);
                br.close();

                //Crypto coding starts here
                int ch;
                StringBuffer strContent = new StringBuffer("");
                FileInputStream fin = null;
                try {
                    fin = new FileInputStream(selectedFile);
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
                System.out.println("The secret key is    :"+skeySpec);

                // Instantiate the cipher

                Cipher cipher = Cipher.getInstance("AES");
                String strContent1 = strContent.toString();

                System.out.println("The contents are : "+strContent1);

                byte [] newarr = strContent1.getBytes();

                System.out.println("The nearra are : "+newarr);
                String a = new String(newarr);
                System.out.println("The nearra are : "+a);

                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

                byte[] encrypted = cipher.doFinal(strContent.toString().getBytes());

                System.out.println("encrypted string in SelectFileCryptoAES: " + encrypted.toString());

                String abcde=new String(encrypted);

                System.out.println("Selected ejfjfjjhhjghjggbjkgjkgbjkgkgjkgkjgjkgjkgkjgk "+abcde);

                System.out.println("Selected file: "+selectedFile);
                LblFilePath.setText(selectedFile.toString());
                reader.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(SelectFileCryptoAES.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnSelfileAesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblFilePath;
    private javax.swing.JLabel LblSelectedFile;
    private javax.swing.JPanel MyRootPaneljPanel;
    private javax.swing.JButton btnSelfileAes;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgAreaFile;
    // End of variables declaration//GEN-END:variables
}

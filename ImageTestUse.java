/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author accede
 */
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageTestUse extends JFrame {
  public static void main(String[] args) {
    new ImageTestUse();
  }
    private String imagePath;
    String Title;

  public ImageTestUse(String imagePath,String caller) {
    if(caller.equals("newImage")){
        Title  = "Embeded Image";
    }else if(caller.equals("originalImage")){
        Title  = "Original Image";
    }
    this.imagePath = imagePath;  
    this.setTitle(Title);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel panel1 = new JPanel();
    ImageIcon pic = new ImageIcon(imagePath);
    panel1.add(new JLabel(pic));
    this.add(panel1);
    this.pack();
    this.setVisible(true);
  }

    private ImageTestUse() {
        
    }
}
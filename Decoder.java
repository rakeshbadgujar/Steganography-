

import Tests.RSAEncryptionDecryption;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Test1.TextFileSaver;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Decoder extends JFrame implements ActionListener {
	JButton Browse,Decode,bnoise,bClose;
        JButton goToEncode;
        JButton viewOriginalImage, viewNewImage;
	String msg,file;
	JLabel filename;
	JTextField noisethresh;
//	JLabel msgarea;
        JTextArea msgarea;
        JLabel lblOpMsg;
        JLabel lblEmptyMsg;
	ImageDemo openImg;
	int MAXLEN=100000;
	char[] Msg=new char[MAXLEN];
	BufferedImage rawImage;
	int iRows,iCols,thresh;
	int[] oneDPix;
	int[][][] threeDPix;
	char[][][] segmentedMsg; 
        String newImagePath;
        String originalImagePath;

    public String getOriginalImagePath() {
        return originalImagePath;
    }

    public void setOriginalImagePath(String originalImagePath) {
        this.originalImagePath = originalImagePath;
    }
        
        
        
        
	public Decoder(){
            
		setTitle("Decoder");
		setSize(800,600);
		setLayout(new GridLayout(5,2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Browse =new JButton("Browse");
		Decode= new JButton("Decode");
		filename= new JLabel("Filename appears here");
		noisethresh = new JTextField("34");
		msgarea= new JTextArea("Output will be displayed here!");
		bnoise = new JButton("Save threshold");
                goToEncode = new JButton("Go to Encoder");
                bClose= new JButton("Close");
                viewOriginalImage = new JButton("View Original Image");
                viewNewImage = new JButton("View Embeded Image");
                
		add(filename);
		add(Browse);
		add(noisethresh);
		add(bnoise);
        	add(msgarea);
		add(Decode);
                add(viewOriginalImage);
                add(viewNewImage);
                add(goToEncode);
                add(bClose);
                
                
		Browse.addActionListener(this);
		Decode.addActionListener(this);
		Decode.setVisible(false);
		bnoise.addActionListener(this);
		bnoise.setVisible(false);
                bClose.addActionListener(this);
		msgarea.setEditable(false);
                goToEncode.addActionListener(this);
		noisethresh.setEditable(false);
                viewOriginalImage.addActionListener(this);
                viewOriginalImage.setVisible(false);
                viewNewImage.addActionListener(this);
                viewNewImage.setVisible(false);
	}
	public static void main(String[] args) {
		Decoder obj= new Decoder();
		obj.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(obj==Browse) {
                JFileChooser chooser1 = new JFileChooser();
                chooser1.setMultiSelectionEnabled(true);
                int option = chooser1.showOpenDialog(Decoder.this);
                if (option == JFileChooser.APPROVE_OPTION)
                {
                  File[] sf = chooser1.getSelectedFiles();
                String image_path=null;
                  if(sf.length>0)
                  {
                      System.out.println("file Image "+sf[0]);
                      image_path=sf[0].getAbsolutePath();
                      newImagePath = image_path;
                      filename.setText(image_path);
//                      noisethresh.setEditable(true);
                      bnoise.setVisible(true);
                  }
                  System.out.println("image path by getting in variable "+image_path);
//                  Setting path on encrypted image in IcondemoApp 
                  
//                  Setting path on encrypted image in IcondemoApp ends
                         try {
//getting the path of Image
                             rawImage = ImageIO.read(new File(image_path));
                             } catch (Exception e1) {
                             e1.printStackTrace();
                             }
                         } 
		}
		else if(obj==Decode) {
                    try {
                        grabPixels();
                        convertToThreeD();
                        readData obj2=new readData(threeDPix,thresh,iRows,iCols);
                        Msg=obj2.getMsg();
                        msg = new String(Msg);
//                        System.out.println("The BeforeMsg---------------------------------------------------------------");
                        byte[] by_new = msg.getBytes();
//                        System.out.println("msg is>>>>> "+msg);
//                        System.out.println("THe after Message---------------------------------------------------------------");
                        msgarea.setText(msg.replaceAll("~", ""));
//                          String trimmedMsg= msg.replaceAll("~", "");
                        
                        
                        String outPutString = msgarea.getText();
                        String finalText = outPutString.replaceAll("~", "");
                        System.out.println("msgarea.getText()"+finalText);
                        
                        
//                       CODE TO SAVE TEXT FILE AT PERTICULAR LOCATION ________________________________________________________________
                                JFileChooser chooser = new JFileChooser();
                                chooser.setCurrentDirectory(new java.io.File("."));
                                chooser.setDialogTitle("choosertitle");
                                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                String filename = JOptionPane.showInputDialog("Write filename to save");
                                System.out.println("filename"+filename);
                                chooser.setAcceptAllFileFilterUsed(false);
                                
                                JOptionPane.showMessageDialog(null,"Select directory for Output Text file");                                
                                
                                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                  System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                                  System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                                } else {
                                  System.out.println("No Selection ");
                                }
                                
                                String Path = chooser.getSelectedFile()+"/"+filename+".txt";
                                
                                PrintWriter outStream = new PrintWriter(new BufferedWriter(new FileWriter(Path)));

                                outStream.println(finalText);
                                outStream.close();
                                
                                JOptionPane.showMessageDialog(null,"File saved as  :"+filename+".txt"+"\n at Directory  : \n"+chooser.getSelectedFile());                                
                                originalImagePath = newImagePath;
//                        ________________________________________________________________                        
                        
                        
                        byte[] check;
                        
                        //Decrypt symmetric Key by private key
                        //File WriteFile = new File("E:\\abcd.text");
                        //FileInputStream fs=new FileInputStream(WriteFile);
                       
                        Path path = Paths.get("abcd.text");
                      byte[] data1 = Files.readAllBytes(path);
                        
                        RSAEncryptionDecryption rsaObject =  new RSAEncryptionDecryption();
                        byte[] aesdecrykey= rsaObject.decryptData(data1);
                                
                        SecretKey originalKey = new SecretKeySpec(aesdecrykey, 0, aesdecrykey.length, "AES");
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE, originalKey);
                        byte[] original =cipher.doFinal(by_new);
                        System.out.println("Final decrypted data" + original.toString());
                        
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    viewOriginalImage.setVisible(true);
                    
                    viewNewImage.setVisible(true);
                    
		}
		else if(obj==bnoise) {
			thresh=Integer.parseInt(noisethresh.getText());
			Decode.setVisible(true);
		}else if(obj == bClose){
                    System.exit(0);
                }else if(obj == goToEncode){
                    try {
                        chooseImage obj1= new chooseImage();
                        obj1.setVisible(true);
                        this.setVisible(false);
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(obj == viewOriginalImage){
                   ImageTestUse itu = new ImageTestUse(originalImagePath,"originalImage");
                    
                }else if(obj == viewNewImage){
                   ImageTestUse itu1 = new ImageTestUse(newImagePath,"newImage");
                }
	}
	//Grab Pixels function
	public void grabPixels()  {
		iRows=rawImage.getHeight();
		iCols=rawImage.getWidth();
		oneDPix= new int[iRows*iCols];
		System.out.println("Height:"+iRows+"\nWidth:"+iCols);
		try {
			PixelGrabber pgObj =new PixelGrabber(rawImage,0,0,iCols,iRows,oneDPix,0,iCols);
			if(pgObj.grabPixels()&&((pgObj.getStatus()&(ImageObserver.ALLBITS)))!=0) {
				
			}
		} catch (InterruptedException e) {
			System.out.println("Pixel Grab failure");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Pixel grab succesfull");
	}
	
	//ConvertToThreeD
	public void convertToThreeD() {
		int[] aRow= new int[iCols];
		threeDPix=new int[iRows][iCols][4];
		int row,col;
		for(row=0;row<iRows;row++) {
			for(col=0;col<iCols;col++) {
		        int element = row * iCols + col;				
				aRow[col]=oneDPix[element];
			}
			for(col=0;col<iCols;col++) {
				
		        threeDPix[row][col][0] = (aRow[col] >> 24) & 0xFF;
//Red data
		        threeDPix[row][col][1] = (aRow[col] >> 16) & 0xFF;
//Green data
		        threeDPix[row][col][2] = (aRow[col] >> 8) & 0xFF;
//Blue data
		        threeDPix[row][col][3] = (aRow[col]) & 0xFF;
			}
		}
		System.out.println("Two D transformation successfull");
	}
}

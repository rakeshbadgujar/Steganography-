//Using Swings
import Tests.RSAEncryptionDecryption;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
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

public class tempChooImg extends JFrame implements ActionListener{
        FileInputStream fin;  
        BufferedInputStream bin;
	JButton Browse,Next,Message,bAnalyze,bnoise,bsecretMsg;
        JButton SelectFile;
//        JButton ClickToDecode;
	JLabel imgname;
	JTextArea msgArea;
	JTextField tnoise,tsecretMsg;
	String filename,secretMsg;
	ImageDemo openImg;
	int[] oneDPix,oneDPixMod;
	int[][][] threeDPix;
	int[][][] threeDPixMod;
	char[][][] segmentedMsg; 
        byte[] raw;
        byte [] data;
        public byte[] symmetricKey;

        
//	byte[][][] plane0,plane1,plane2;
//	BufferedImage rawImg=new BufferedImage();
	BufferedImage rawImg,image;
	Image modImg;
	int iRows,iCols;
	int noiseThreshold,secretMsgThreshold;
        private static final String PUBLIC_KEY_FILE = "Public.key";
        private static final String PRIVATE_KEY_FILE = "Private.key";
        
      
	public tempChooImg () throws Exception {
		setTitle("Choose Image");
		setSize(400,300);
		setLayout(new GridLayout(6,2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//XInitialing all  components one by one
		bnoise = new JButton("Save Noise threshold");
		bsecretMsg=new JButton("Save SecretMsg threshold");
		tnoise = new JTextField("34");
		tsecretMsg=new JTextField("40");
//		tnoise = new JTextField("Enter any number between 1 and 110");
//		tsecretMsg=new JTextField("Enter any number between 1 and 110");
		Browse = new JButton("Browse");
		imgname= new JLabel("Filename appears here");
		Next = new JButton("Encode");
		SelectFile = new JButton("Select Text File to Encode");
//                ClickToDecode = new JButton("Click to Decode");
                Message =new JButton("Save Message");
		msgArea= new JTextArea("Enter your secret message");
                bAnalyze= new JButton("Analyze");
                
                
                add(imgname);
		add(Browse);
		add(msgArea);
		Message.setVisible(false);
		add(Message);
		add(tnoise);
		tnoise.setEditable(false);
		add(bnoise);
		bnoise.addActionListener(this);
		bnoise.setVisible(false);
		add(tsecretMsg);
		tsecretMsg.setEditable(false);
		add(bsecretMsg);
		bsecretMsg.setVisible(false);
		add(bAnalyze);
		bAnalyze.addActionListener(this);
		bAnalyze.setVisible(false);
		add(Next);
                
                add(SelectFile);
                SelectFile.addActionListener(this);
//                add(ClickToDecode);
//                ClickToDecode.addActionListener(this);
                
		Next.setVisible(false);
		msgArea.setEditable(false);
		Message.addActionListener(this);
		Browse.addActionListener(this);
		Next.addActionListener(this);
                
	}	
	//ActionEvent Handling
	public  void actionPerformed(ActionEvent e) {
		Object src=e.getSource();
		if(src==Browse) {
		JFileChooser chooser1 = new JFileChooser();
                chooser1.setMultiSelectionEnabled(true);
                int option = chooser1.showOpenDialog(tempChooImg.this);
                if (option == JFileChooser.APPROVE_OPTION)
                {
                  File[] sf = chooser1.getSelectedFiles();
                String image_path=null;
                  if(sf.length>0)
                  {
                  System.out.println("file Image "+sf[0]);
              
                image_path=sf[0].getAbsolutePath();
                  }
                  System.out.println("image path by getting in variable "+image_path);
                  
                         try {
//getting the path of Image
                             image = ImageIO.read(new File(image_path));
//getting Height and Width of image
                             System.out.println(image.getHeight());
                             System.out.println(image.getWidth());
                             iRows= image.getHeight();
                             iCols = image.getWidth();
                             } catch (Exception e1) {
                             e1.printStackTrace();
                             }
                            rawImg=image;
//                          grabPixels();
                            msgArea.setEditable(true);
                            Message.setVisible(true);
                            imgname.setText(image_path);
                         }
		}
		else if(src==Message) {
//			secretMsg=msgArea.getText();
                    
                        secretMsg = new String(data);
//                        secretMsg1 = encryptedData;
//                        System.out.println("String encryptedData is : "+secretMsg1);
//                        System.out.println("----------------------------------------------------------");
//                        System.out.println("The raw is assigned to secret message:" +data.toString());
//                        System.out.println("----------------------------------------------------------");
                        JOptionPane.showMessageDialog(null,"You want to Hide this msg:\n\""+secretMsg+"\"\nin\n"+filename);
			tnoise.setEditable(true);
			bnoise.setVisible(true);
		}
		else if(src==bnoise) {
			noiseThreshold=Integer.parseInt(tnoise.getText());
			//noiseThreshold=Integer.parseInt(tnoise.getText());
			tsecretMsg.setEditable(true);
			bsecretMsg.setVisible(true);	
			bAnalyze.setVisible(true);
			Next.setVisible(true);
		}
		else if(src==bsecretMsg) {
			secretMsgThreshold=Integer.parseInt(tsecretMsg.getText());
//			secretMsgThreshold=Integer.parseInt(tsecretMsg.getText());
		}
		else if(src==bAnalyze) {
                    System.out.println("imagedemo analyse");
                        grabPixels();
			convertToThreeD();
                        System.out.println("imagedemo analyse2222");
                        Analyze obj=new Analyze(threeDPix,iRows,iCols,noiseThreshold);
		}
		else if(src==Next){
				grabPixels();
				convertToThreeD();
				int noOfSegments;
				transformMsg obj1=new transformMsg(secretMsg,noiseThreshold);
				noOfSegments=obj1.getnoOfSegments();
				segmentedMsg=obj1.getMsgSegments();
				HideData obj2=new HideData(threeDPix, segmentedMsg, noiseThreshold, noOfSegments, iRows,iCols);
				threeDPixMod=obj2.getthreeDPix();
				convertToOneD();
				image= new BufferedImage(iCols,iRows,BufferedImage.TYPE_INT_RGB);
				image.setRGB(0,0,iCols, iRows, oneDPixMod,0,iCols);
		        //  modImg =  createImage(
		          //        new MemoryImageSource(
		            //      iCols,iRows,oneDPixMod,0,iCols));
		          try {
		        	    // retrieve image
                              System.out.println("Saved File path below........");
		        	    File outputfile = new File("C:/Users/accede/Desktop/saved.bmp");
//                                    System.out.println("O/P File path is...."+outputfile);
                                    System.out.println("Saved File path above........");
                                    ImageIO.write( image ,"png", outputfile);
		        	} catch (IOException ef) {
		        	   
		        	} catch (Exception ex) {  
                        Logger.getLogger(chooseImage.class.getName()).log(Level.SEVERE, null, ex);
                    }  
				
//			}
		}
                else if(src == SelectFile){
                    /*FileReader reader = new FileReader( "C:/Users/accede/Desktop/ReqJar.txt" );
                    BufferedReader br = new BufferedReader(reader);
                    msgArea.read(br, src);
                    br.close();*/
                    JFileChooser fileChooser1 = new JFileChooser();
                    fileChooser1.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result1 = fileChooser1.showOpenDialog(this);
                    if (result1 == JFileChooser.APPROVE_OPTION) {
                        FileReader reader1 = null;
                        try {
                            File selectedFile1 = fileChooser1.getSelectedFile();
                            reader1 = new FileReader(selectedFile1.getAbsolutePath());
                            BufferedReader br = new BufferedReader(reader1);
                            msgArea.read(br, src);
                            br.close();
                            System.out.println("Selected file: "+selectedFile1);
                            System.out.println("Reader file: "+reader1);

                            int ch;
                            StringBuffer strContent = new StringBuffer("");
                            FileInputStream fin = null;

                            fin = new FileInputStream(selectedFile1);
                            while ((ch = fin.read()) != -1)
                            strContent.append((char) ch);

                            fin.close();
                            System.out.println("Original string: " +strContent.toString()+"\n");
                            String strContent1 = strContent.toString();
                            data = strContent1.getBytes();
//                          R Coding Starts Here  
                        
                            System.out.println("-------GENRATE PUBLIC and PRIVATE KEY-------------");
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(3072); //1024 used for normal securities
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			System.out.println("Public Key - " + publicKey);
			System.out.println("Private Key - " + privateKey);

			//Pullingout parameters which makes up Key
			System.out.println("\n------- PULLING OUT PARAMETERS WHICH MAKES KEYPAIR----------\n");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
			System.out.println("PubKey Modulus : " + rsaPubKeySpec.getModulus());
			System.out.println("PubKey Exponent : " + rsaPubKeySpec.getPublicExponent());
			System.out.println("PrivKey Modulus : " + rsaPrivKeySpec.getModulus());
			System.out.println("PrivKey Exponent : " + rsaPrivKeySpec.getPrivateExponent());
			
			//Share public key with other so they can encrypt data and decrypt thoses using private key(Don't share with Other)
			System.out.println("\n--------SAVING PUBLIC KEY AND PRIVATE KEY TO FILES-------\n");
			RSAEncryptionDecryption rsaObj = new RSAEncryptionDecryption();
			rsaObj.saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
			rsaObj.saveKeys(PRIVATE_KEY_FILE, rsaPrivKeySpec.getModulus(), rsaPrivKeySpec.getPrivateExponent());
			
                            
//                        AES Key Gen starts
                        
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
                        
                        
//                        AES Key Gen Ends
                        
                      
                       byte[] RSAEncrypt= rsaObj.encryptData( skeySpec.getEncoded());
                       File WriteFile = new File("abcd.text");
                            FileOutputStream fs=new FileOutputStream(WriteFile);
                       fs.write(RSAEncrypt);
                       
//                        rsaObj.saveKeys(PUBLIC_KEY_FILE, rsaPubKeySpec.getModulus(), rsaPubKeySpec.getPublicExponent());
                            
//                          R Coding Ends Here                            
                            

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(chooseImage.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(chooseImage.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidKeySpecException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPaddingException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadPaddingException ex) {
                            Logger.getLogger(tempChooImg.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                                try {
                                    reader1.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(chooseImage.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                    }
                }
//                else if(src == ClickToDecode){
//                decodeMethod();
//                }
	}

        
	//Grab Pixels function
	public void grabPixels()  {
		iRows=image.getHeight();
		iCols=image.getWidth();
                
              System.out.println("Rows are: "+iRows);
                System.out.println("Rows are: "+iCols);
                              
                
		oneDPix= new int[iRows*iCols];
		System.out.println("Height:"+iRows+"\nWidth:"+iCols);
		try {
			PixelGrabber pgObj =new PixelGrabber(rawImg,0,0,iCols,iRows,oneDPix,0,iCols);
			if(pgObj.grabPixels()&&((pgObj.getStatus()&(ImageObserver.ALLBITS)))!=0) {
				
			}
		} catch (InterruptedException e) {
			System.out.println("Pixel Grab failure");
			e.printStackTrace();
			System.exit(1);
		}
		//Here starts
	/*	image= new BufferedImage(iCols,iRows,BufferedImage.TYPE_INT_RGB);
		image.setRGB(0,0,iCols, iRows, oneDPix,0,iCols);
          try {
        	    // retrieve image
        	    File outputfile = new File("saved.png");
        	    ImageIO.write( image ,"bmp", outputfile);
        	} catch (IOException ef) {
        	   
        	}  */
          //Here ends
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
		System.out.println("Two Dee transformation successfull");
	}
	public void convertToOneD() {
		     oneDPixMod = new int[ iCols * iRows ];
		     for(int row = 0,cnt = 0;row < iRows;row++){
		       for(int col = 0;col < iCols;col++){
		         oneDPixMod[cnt] = ((threeDPixMod[row][col][0] << 24)
		                                    & 0xFF000000)
		                      | ((threeDPixMod[row][col][1] << 16)
		                                    & 0x00FF0000)
		                       | ((threeDPixMod[row][col][2] << 8)
		                                    & 0x0000FF00)
		                            | ((threeDPixMod[row][col][3])
		                                    & 0x000000FF);
		         cnt++;
		       }
		     } 
		     System.out.println("One D transformation successfull");
	}
	//
	public static void main(String[] args) throws Exception {
		
		try {
			tempChooImg frame1=new tempChooImg();
			frame1.setVisible(true);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

//    private void decodeMethod() {
//      new Decoder();
//    }
}

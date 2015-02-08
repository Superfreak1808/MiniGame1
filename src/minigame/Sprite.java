package minigame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Sprite {

    private BufferedImage img = null;

    private BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);

    private Graphics g2 = bi.getGraphics();

    public void showFiles() {
        // Directory path here
    	try {
    		
    		// Enter Project path here
	        String path = "./res";
	
	        String files;
	        File folder = new File(path);
	        File[] listOfFiles = folder.listFiles();
		        for (int i = 0; i < listOfFiles.length; i++) {
		
		                files = listOfFiles[i].getName();
		                System.out.println(files);
		            
		        }
	        } catch (NullPointerException e) {
	        	
	        }
    	}
    

    public void init() {
        
       //showFiles();

        try {
        	// And the same path here with the file :) - kk.. Now just to draw!! Any ideas? - Non at the moment!
            img = ImageIO.read(new File("./res/ship.png"));
        } catch (IOException e) {
            if (img == null) {
                System.out.println("Empty");
            }
        }
       
    }
    
    public BufferedImage getImage() {
    	return img;
    }

}
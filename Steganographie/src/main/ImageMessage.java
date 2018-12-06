package main;
/**
 * @author
 */
import static org.junit.Assert.*;
public final class ImageMessage {
	private static final int threshold = 113;


    /*
     * ********************************************
     * Part 1a: prepare image message (RGB image <-> BW image)
     * ********************************************
     */
    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getRed(int rgb) {
     
    	rgb = rgb >> 16;
        return rgb & 0b11111111;
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
    	rgb = rgb >> 8;
        return rgb & 0b11111111;
    }
    

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {

        return rgb & 0b11111111;
    }
    

    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int)
     */
    public static int getGray(int rgb) {
        
        return (getRed(rgb)+ getGreen(rgb)+ getBlue(rgb))/3;
    }

    /**
     * @param gray an integer between 0 and 255
     * @param threshold
     * @return true if gray is greater or equal to threshold, false otherwise
     */
    public static boolean getBW(int gray, int threshold) {
       if (gray >= threshold){
    	   return true;   
       }else{
    	   return false;
       } 
    }

    
    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red an integer between 0 and 255
     * @param green an integer between 0 and 255
     * @param blue an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
    	red = testInteger(red);
    	green = testInteger(green);
    	blue = testInteger(blue);
    	
    	red = red<<16;
    	green = green<<8;
    	return red | green | blue;
       
    }
    

    /**
     * Returns packed RGB components from given grayscale value.
     * @param gray an integer between 0 and 255
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(int gray) {
    	gray = testInteger(gray);
    	return (gray<<16)|(gray<<8)|(gray);
        
    }

    

    /**
    * Returns packed RGB components from a boolean value.
    * @param value a boolean
    * @return 32-bits RGB encoding of black if value is false
    * and encoding of white otherwise
    */
    public static int getRGB(boolean value) {
        if (!value){
        	return 0;
        }else{
        	return getRGB(255);
        }
    }    


    /**
     * Converts packed RGB image to grayscale image.
     * @param image a HxW int array
     * @return a HxW int array
     * @see #encode
     * @see #getGray
     */
    public static int[][] toGray (int[][] image) {
      assertTrue ( Utils.isImage(image) );
      
      int nombreLignes = image.length;
      int nombreColonnes = image[0].length;
      
      int[][] newGray = new int [nombreLignes][nombreColonnes];
    	
    	
        for (int i=0; i<nombreLignes; ++i){
        	for (int j=0; j<nombreColonnes; ++j){
        		newGray[i][j] = getGray(image[i][j]);
        	}
        }
        		
        	return newGray;
    }

    /**
     * Converts grayscale image to packed RGB image.
     * @param channels a HxW float array
     * @return a HxW int array
     * @see #decode
     * @see #getRGB(float)
     */
    public static int[][] toRGB(int[][] gray) {
    	assertTrue (Utils.isImage(gray));
    	
    	int abs =gray.length;
    	int ord =gray[0].length;
        int[][] newRGB = new int[abs][ord];
       
        for (int i =0; i<abs; ++i){
        	for (int j =0; j<ord; ++j){
        		newRGB[i][j]=getRGB(gray[i][j]);
        	}
        }
        return newRGB; 
    }

    /**
     * Converts grayscale image to a black and white image using a given threshold
     * @param gray a HxW int array
     * @param threshold an integer threshold
     * @return a HxW int array
     */
    public static boolean[][] toBW(int[][] gray, int threshold) {
    	assertTrue ( Utils.isImage(gray) );
    	
    	int abs=gray.length;
    	int ord=gray[0].length;	
    	boolean[][] newBW = new boolean [abs][ord];
    	
    		for (int i =0; i<gray.length; ++i){
    			for (int j=0; j<gray[0].length; ++j){
    				newBW[i][j] = getBW(gray[i][j],threshold);
    			}
    		}
    	return newBW;
    }

    /**
     * Converts a black and white image to packed RGB image
     * @param image a HxW boolean array (false stands for black)
     * @return a HxW int array
     */
    public static int[][] toRGB(boolean[][] image) {
    	assertTrue ( Utils.isImage(image) );
    	
    	int abs =image.length;
    	int ord =image[0].length;
        int[][] newRGB = new int[abs][ord];
        
        for (int i =0; i<abs; ++i){
        	for (int j =0; j<ord; ++j){
        		newRGB[i][j]=getRGB(image[i][j]);
        	}
        }
        return newRGB;
    }

    /*
     * ********************************************
     * Part 3: prepare image message for spiral encoding (image <-> bit array)
     * ********************************************
     */
    /**
     * Converts a black-and-white image to a bit array
     * @param bwImage A black and white (boolean) image
     * @return A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @see ImageMessage#bitArrayToImage(boolean[])
     */
    public static boolean[] bwImageToBitArray(boolean[][] bwImage) {
    	assertTrue ( Utils.isImage(bwImage) );
    	
    int hauteur = bwImage.length;
    int largeur = bwImage[0].length;
    int maintaille = 2*32 + hauteur*largeur;
    boolean[] maintab = new boolean [maintaille];
    
    assertTrue( Utils.isbitArray(maintab) );
    boolean[] tabhauteur = TextMessage.intToBitArray(hauteur, 32);
    boolean[] tablargeur = TextMessage.intToBitArray(largeur, 32);
    
    assertTrue( Utils.isbitArray(maintab, hauteur, largeur) );
    
	    for (int i=0; i<32; ++i){  // rempli les 32 premieres cases de maintab par la hauteur de l'image.
	    	maintab[i]=tabhauteur[i]; 
	    }
	    for(int j=32; j<64; ++j){ // rempli les 32 cases suivantes (de 32 à 64) de maintab par la largeur de l'image.
	    	maintab[j]=tablargeur[j-32];
	    }
	    
	    int compteur=64; // La premiere case vide de maintab est la 64;
	   
	    for (int l=0;l<hauteur; ++l){
	    	for (int m=0; m<largeur; ++m){
	    		maintab[compteur]=bwImage[l][m];
	    		++compteur;
	    	}
	    }
	return maintab;    

    }

    /**
     * Converts a bit array back to a black and white image
     * @param bitArray A boolean array containing the binary representation of the image's height and width (32 bits each), followed by the image's pixel values
     * @return The reconstructed image
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     */
    public static boolean[][] bitArrayToImage(boolean[] bitArray) {
    	assertTrue (Utils.isNotEmpty(bitArray)) ;
    	
    	boolean[] sousTabHaut = remplirTab(bitArray, 0, 31);
        int hauteur = TextMessage.bitArrayToInt(sousTabHaut);
        
        boolean[] sousTabLarg = remplirTab(bitArray, 32, 63);
        int largeur = TextMessage.bitArrayToInt(sousTabLarg);
    	
    	int compteur = 64;
    	boolean[][] bidimTab = new boolean[hauteur][largeur];
        for (int height=0; height<hauteur; ++height) { // Parcourt de chaque ligne du tableau bidimTab
        	for (int width=0; width<largeur; ++width){ // Parcourt de la colonne du tableau bidimTab 
    		bidimTab[height][width] = bitArray[compteur];
    		++compteur;
        	}
        }
        return bidimTab;
    }
    
    
    /** 
     * 
     * @param bitArray
     * @param debut
     * @param fin
     * @return un nouveau tableau à partir de bitArray de la position début à la position fin
     */
    
    public static boolean[] remplirTab (boolean[]bitArray , int debut, int fin){ // créé un sous tableau du tableau bitArray de debut jusqu'à fin inclus
  
    	boolean[] sousTab= new boolean[fin-debut+1];
    		for (int i=debut;i<=fin; ++i){  // On remplie sousTabHaut avec les bits de début à fin du tableau bitArray
    			sousTab[i-debut]=bitArray[i];
    		}
    	return sousTab;
    	
    }

    
/**
 * 
 * @param rgb : entier dont on veut imposer qu'il soit entre 255 et 0
 * @return : l'arrondi au borne 255 et 0 de l'entier reçue en argument
 */

public static int testInteger (int rgb){
	if (rgb<0){
		rgb=0;
	}
	if (rgb>255){
		rgb=255;
	}
return rgb;	
}

}
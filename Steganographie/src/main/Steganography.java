package main;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Steganography {

    /*
     * ********************************************
     * Part 1b: embed/reveal BW
     * image ********************************************
     */

    /*
     * Methods to deal with the LSB
     */
    /**
     * Inserts a boolean into the Least Significant Bit of an int
     * @param value The int in which to insert a boolean value
     * @param m The boolean value to be inserted into the int
     * @return An int corresponding to {@code value} with {@code m} inserted into its LSB
     */
    public static int embedInLSB(int value, boolean m) {
       
    	if (m){
        	return (value | 0b1);  //transforme le dernier bit en 1
       
        }else{
        	return (value & ~(0b1)); //On prend le complémentaire de 1, c'est à dire : 111...1110. Le bit retourné contiendra donc un 0 comme première valeur
        }
        
    }

    /**
     * Extracts the Least Significant Bit of an integer
     * @param value The integer from which to extract the LSB value
     * @return A boolean corresponding to the value of {@code value}'s LSB
     */
    public static boolean getLSB(int value) {
       
    	if ((value & 0b1) == 0b1){
        	return true;
       
    	} else{
        	return false;
        }
    }

    /*
     * Linear embedding
     */
    /**
     * Embeds a black and white image into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBWImage(int[][] cover, boolean[][] message) { 
    	
    	assertTrue (Utils.isCoverLargeEnough(cover, message));
    	assertTrue ( Utils.isImage(cover));
    	assertTrue (Utils.isImage(message)) ;
    	
    	
    	int ligne =cover.length;
    	int colonne =cover[0].length;
    	int tailleLigneM = message.length;
    	int taillecolonneM = message[0].length;
    	int[][] newCover = new int [ligne][colonne];
	    	
    	for (int i = 0; i<ligne; ++i){
	    		
    		for (int j=0; j<colonne; ++j){
	    			
    				if ( (i<tailleLigneM) && (j<taillecolonneM) ){   // Si on est dans les limites du message, alors on peut incruster ce bit dans l'image cover
	    				newCover[i][j]=embedInLSB(cover[i][j], message[i][j]);	
	    			
    				}else { // out-side the coded message no need to change anything
	    				newCover[i][j]=cover[i][j];
	    			
    				}
	    		
    		}
	    	
    	}
	    return newCover;
	    	
    }

    /**
     * Reveals a black and white image which was embedded in the LSB layer of another
     * @param cover A color image containing an image embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     */
    public static boolean[][] revealBWImage(int[][] cover) { // on récupère le 1er bit de chaque pixel pour retrouver l'image 
    	
    	assertTrue ( Utils.isImage(cover) );
    	
    	int ligne =cover.length;
    	int colonne =cover[0].length;
    	boolean[][] newCover = new boolean[ligne][colonne];
    	
    	for (int i = 0; i<ligne; ++i){
    		
    		for (int j=0; j<colonne; ++j){
    			newCover[i][j]=getLSB(cover[i][j]);
    		}
    		
    	}
    	return newCover;
    }

    /*
     * ********************************************
     * Part 2b: embed/reveal
     * BitArray (Text)
     ********************************************
     */

    /**
     * Embeds a boolean array into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The boolean array to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a linear fashion in the LSB layer
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
    	assertTrue(Utils.isCoverLargeEnough(cover, message));
    	assertTrue  ( Utils.isImage(cover) ) ;
    	assertTrue  ( Utils.isNotEmpty(message) ) ; 
    	
    	
    	int ligne = cover.length;
    	int colonne = cover[0].length;
    	int m = message.length;
    	int compteur=0;
    	int[][] newCover = new int [ligne][colonne];
    	
    	for (int i=0; i<ligne; ++i){
    		
    			for (int j=0;j<colonne; ++j){
    				
    				if (compteur < m){
    					newCover[i][j]=embedInLSB(cover[i][j],message[compteur]); // Transforme le dernier bit de cover[i][j] si message[reste+j] est true.
    					++compteur;
    				}
    				
    				else { // Si le compteur atteint la valeur de m, alors tous les indices constituant message( de 0 jusqu'à m-1) ont été parcourus.
    					newCover[i][j] = cover[i][j]; 
    				}
    				
    			}
    			
    	}		
    		
    	return newCover;
    	
    }

    
    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealBitArray(int[][] cover) {
    	assertTrue (Utils.isImage(cover) );
 
    	int tailleLigne =cover.length;
    	int tailleColonne=cover[0].length;
    	int compteur=0;
    	
    	boolean[] newCover = new boolean[tailleLigne*tailleColonne];
	   
    	for (int i=0; i<tailleLigne; ++i){ // Parcourt des lignes de cover.
	    	
    		for (int j=0; j<tailleColonne; ++j){ // Parcourt des colonnes de cover.
	    		
	    		newCover[compteur]=getLSB(cover[i][j]);	
	    		++compteur;   //Le compteur varie de 0 jusqu'à la taille de cover-1
	    	}
	    		
	   	}
    	return newCover;
    	
    }

    /**
     * Embeds a String into the LSB layer of a color image, in a linear fashion
     * @param cover The image in which to embed the bit array
     * @param message The String to be embedded
     * @return A <b>copy</b> of {@code cover} with {@code message}'s binary representation embedded in a linear fashion in the LSB layer
     * @see TextMessage#stringToBitArray(String)
     * @see Steganography#embedBitArray(int[][], boolean[])
     */
    public static int[][] embedText(int[][] cover, String message) {
    	
    	boolean[] m = TextMessage.stringToBitArray(message); // Transfome le message String en tableau de boolean;
    	int[][] newCover = embedBitArray (cover, m);
    	
    	return newCover;
    }

    
    /**
     * Reveals a String which was embedded in the LSB layer of an image
     * @param cover A color image containing a String embedded in its LSB layer
     * @return The String extracted from the LSB layer of {@code cover}
     * @see TextMessage#bitArrayToString(boolean[])
     */
    public static String revealText(int[][] cover) {
    	assertTrue (Utils.isImage(cover)) ;
    	
    	boolean[] tab =revealBitArray(cover);
    	return TextMessage.bitArrayToString(tab);
    }

    /*
     * ********************************************
     * Part 3: embed/reveal bit
     * array with spiral embedding
     ********************************************
     */

    /**
     * Embeds a black and white image into a color image's LSB layer using spiral embedding
     * @param cover The image in which to embed {@code message}
     * @param message The image to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s pixel values embedded in a spiral fashion in the LSB layer
     * @see ImageMessage#bwImageToBitArray(boolean[][])
     * @see Steganography#embedSpiralBitArray(int[][], boolean[])
     */
    public static int[][] embedSpiralImage(int[][] cover, boolean[][] bwImage) {
    	
    	boolean[] tab = ImageMessage.bwImageToBitArray(bwImage);
    	
    	return embedSpiralBitArray(cover,tab);
    }
    
    

    /**
     * Reveals an image which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The image extracted from the LSB layer of {@code cover}
     * @see ImageMessage#bitArrayToImage(boolean[])
     * @see Steganography#revealSpiralBitArray(int[][])
     */
    public static boolean[][] revealSpiralImage(int[][] cover) {
    	
    	boolean[][] hidden = ImageMessage.bitArrayToImage( (revealSpiralBitArray(cover)) );
    	
    	return hidden;
    }

    
    /**
     * Embeds a bit array into a color image's LSB layer using linear embedding
     * @param cover The image in which to embed {@code message}
     * @param message The boolean array to embed into {@code cover}
     * @return A <b>copy</b> of {@code cover} with {@code message}'s values embedded in a spiral fashion in the LSB layer
     */
    public static int[][] embedSpiralBitArray(int[][] cover, boolean[] message) {

		assertTrue(Utils.isCoverLargeEnough(cover, message)); 
		assertTrue ( Utils.isImage(cover));
		assertTrue (Utils.isNotEmpty(message) ); 
		
		int ligne = cover.length;
    	int colonne = cover[0].length;
    	int[][] maintab = new int[ligne][colonne];
    	
 
    	int infcolonne = 0; //borne inférieure des colonnes.
    	int supcolonne = colonne-1; // borne supérieure des colonnes.
    	int infligne = 0; // borne inférieure des lignes.
    	int supligne = ligne-1; //borne supérieure des lignes.
    	
    	int direction=0;
    	int position=0; // position détermine la première place disponible du tableau maintab.
    	
    	int taille = message.length;
    	
    	while (( infligne <= supligne) && ( infcolonne <= supcolonne)){
    		
    		
	    	switch (direction) { // On définit 4 direction 0:droite  1:bas  2:gauche   3:droite
	    	case 0: 
	    		for (int i=infcolonne; i<=supcolonne; ++i){ // Phase de gauche à droite
	    			
	    			if(position < taille){
	    				maintab[infligne][i] = embedInLSB(cover[infligne][i], message [position]);  // On va de Gauche à droite sur la ligne la plus en haut non parcourue
	    			
	    			} else {
	    				maintab[infligne][i]=cover[infligne][i];
	    			}
	    			++position;		
	    		}
	    		
	    		++infligne; //On ne retournera plus dans la ligne infligne de cover;
	    		
	    		break;
	    		
	    	case 1:
	    		for (int j=infligne; j<=supligne; ++j){ //Phase descendante
	    			
	    			if(position < taille){
	    				maintab[j][supcolonne] = embedInLSB(cover[j][supcolonne], message [position]);
	    			
	    			}else{
	    				maintab[j][supcolonne] = cover[j][supcolonne];
	    			}
	    			++position;
	    		}
	    		
	    		--supcolonne; // On ne retournera plus dans la colonne supDroit de cover
	    		break;
	    		
	    	case 2:
	    		for (int k=supcolonne; k>=infcolonne; --k){// Phase de droite à gauche
	    			
	    			if (position<taille){
	    				maintab[supligne][k] = embedInLSB(cover[supligne][k], message [position]);
	    			
	    			}else{
	    				maintab[supligne][k]=cover[supligne][k];
	    			}
	    			++position;
	    		}
	    		
	    		--supligne; // On ne retournera plus dans la ligne infDroit de cover;
	    		break;
	    		
	    		
	    	case 3:
	    		for (int l=supligne; l>=infligne; --l){ // Phase montante
	    			
	    			if(position<taille){
	    				maintab[l][infcolonne] = embedInLSB(cover[l][infcolonne], message [position]);
	    			
	    			}else{
	    				maintab[l][infcolonne]= cover[l][infcolonne];
	    			}
	    			++position;
	    		}
	    		
	    		++infcolonne;
	    		break;
	    		
	    	}
	    	
	    	direction += 1; 
	    	direction %=4; //on fait modulo 4 pour parcourir les directions de 0 à 3 puis revenir à la direction 0.
	       
	    }
    	return maintab;
    }

    
    /**
     * Reveals a boolean array which was embedded in the LSB layer of an image in a spiral fashion
     * @param cover A color image containing an bit array embedded in its LSB layer
     * @return The bit array extracted from the LSB layer of {@code cover}
     */
    public static boolean[] revealSpiralBitArray(int[][] hidden) {
    	
    	assertTrue ( Utils.isImage(hidden) ) ;
    	
    	
    	int ligne = hidden.length;
    	int colonne = hidden[0].length;
    	boolean[] maintab = new boolean[ligne*colonne];
    	
    	int infcolonne = 0; // borne inférieure des colonnes
    	int supcolonne = colonne-1; // borne supérieure des colonnes
    	int infligne = 0; // borne inférieure des lignes
    	int supligne = ligne-1; //borne supérieure des lignes
    	
    	int direction=0;  
    	int position=0;  // position détermine la première place disponible du tableau maintab.
    	
    	while (( infligne <= supligne) && ( infcolonne <= supcolonne)){
    		
    		
	    	switch (direction) { // On définit 4 direction 0:droite  1:bas  2:gauche   3:droite
	    	case 0: 
	    		for (int i=infcolonne; i<=supcolonne; ++i){ // Phase de gauche à droite
	    			maintab[position]=getLSB(hidden[infligne][i]);
	    			++position;		
	    		}
	    		
	    		++infligne; //On ne retournera plus dans la ligne infligne de cover.
	    		
	    		break;
	    		
	    	case 1:
	    		for (int j=infligne; j<=supligne; ++j){ //Phase descendante.
	    			maintab[position] = getLSB(hidden[j][supcolonne]);
	    			++position;
	    		}
	    	
	    		--supcolonne; // On ne retournera plus dans la colonne supDroit de cover.
	    		break;
	    		
	    	case 2:
	    		for (int k=supcolonne; k>=infcolonne; --k){// Phase de droite à gauche
	    			maintab[position] = getLSB(hidden[supligne][k]);
	    			++position;
	    		}
	    		
	    		--supligne; // On ne retournera plus dans la ligne infDroit de cover.
	    		break;
	    		
	    		
	    	case 3:
	    		for (int l=supligne; l>=infligne; --l){ // Phase montante
	    			maintab[position] = getLSB(hidden[l][infcolonne]);
	    			++position;
	    		}
	    		
	    		++infcolonne; // On ne retournera plus dans la colonne infcolonne de cover.
	    		break;
	    		
	    	}
	    	
	    	direction += 1; 
	    	direction %=4; // Les positions varient successivement de 0 à 3 puis on revient à 0 et les directions son de nouveaux parcouruts.
	       
	    }
    	return maintab;
    }  
}






package main;

import java.util.Arrays;

public class TextMessage {

    /*
     * ********************************************
     * Part 2a: prepare text message (text <-> bit array)
     * ********************************************
     */
    /**
     * Converts an integer to its binary representation
     * @param value The integer to be converted
     * @param bits The number of bits for {@code value}'s binary representation
     * @return A boolean array corresponding to {@code value}'s {@code bits}-bit binary representation
     */
    public static boolean[] intToBitArray(int value, int bits) {
        boolean[] bitsTab = new boolean[bits];
        
        for (int i=0; i<bits; ++i){
        	bitsTab[i]=Steganography.getLSB(value);
        	value = value >>1;
        }
        
        return bitsTab;
        
    }

    /**
     * Converts a bit array to it's integer value
     * @param bitArray A boolean array corresponding to an integer's binary representation
     * @return The integer that the array represented
     */
    public static int bitArrayToInt(boolean[] bitArray) {
    	int l = bitArray.length;
        int[] tabInt = new int [l];
        
        int somme=0;
        int puissanceDeDeux =1;
        
        for (int i=0; i<l ; ++i){ //passage boolean a valeur binaire
        	
        	if (bitArray[i]){
        		somme+= puissanceDeDeux;
        	}
        	
        puissanceDeDeux = puissanceDeDeux<<1;
        } 		
        return somme;
   }
   

    /**
     * Converts a String to its binary representation, i.e. the sequence of the 16-bit binary representations of its chars' integer values
     * @param message The String to be converted
     * @return A boolean array corresponding to the String's binary representation
     */
    public static boolean[] stringToBitArray(String message) {
      int[] tab1 = stringToInt(message);
      int l = tab1.length;
      boolean[] mainTab = new boolean[l*16];
      
	      for (int i=0; i < l*16; i+=16){ //On fait i+=16 car 16 cases du tableau sousTab sont remplies  à chaque itération.  
    	 
	       boolean[] sousTab = intToBitArray(tab1[i/16],16); 	// Rend un tableau de 16 places avec les représentations binaires d'une valeur  du tableau à la place i/16
	    													 	//qui sera utilise sous sa forme int à la place u=i/16 du message;
	       														// u appartient a [ | 0 ; message.length()-1 | ]
	       for (int j=0; j<16;++j) { // Pour chaque nombre qui représente une lettre
	    	  
	    	  mainTab[i+j] = sousTab[j]; 		  
	    	  }
	      }
	      return mainTab;
    }
    
    /**
     * Converts a String to  a sequence of integers
     * @param message
     * @returnA A tab of integers corresponding to the representation of the String. 
     */
    
    public static int[] stringToInt(String message){ 
    	int l=message.length();
        int[] tab1 = new int [l];
         
        for (int i =0; i<l; ++i){
      	   int c = (int)message.charAt(i);
      	   tab1[i]=c;   
         }
        
         return tab1;
    }

    /**
     * Converts a boolean array to the String of which it is the representation
     * @param bitArray A boolean array representing a String
     * @return The String that the array represented
     * @see TextMessage#stringToBitArray(String)
     */
    public static String bitArrayToString(boolean[] bitArray) {
    	
    	assert (Utils.isNotEmpty(bitArray)) : "bitArray is empty or not rectangle";
    	
    	if (!Utils.isNotEmpty(bitArray)){
    		return null;
    	}
    	
    	int l = bitArray.length;
    	boolean[] intermediaire = new boolean[16];
    	int a =0;
    	String message="";
    		
    	for (int i=0;i<l-15; i+=16){ // i varie de 0 jusqu'à la 1ere position du dernier tableau (qui est l-16).
    			intermediaire = Arrays.copyOfRange(bitArray,i,i+15);
    			
    			a = bitArrayToInt(intermediaire); //Retrouve la lettre du mot à partir du tableau de 16 bits.
    			
    			message+=(char)a;
    	}
    	
    	return message	;  
    }
    
}
    

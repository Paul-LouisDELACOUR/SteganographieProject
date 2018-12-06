package main;

public class Utils {

    /*
     * ***********************************************************
     * Helper functions
     * ***********************************************************
     */
	
	 /**
     * Checks if a cover image is large enough to embed a sequence of bits 
     * @param cover A 2D integer array
	 * @param message a sequence of bits to embed into the cover  (using LSB)
     * @return {@code true} if the cover is large enough  {@code false} otherwise
     */
    public static boolean isCoverLargeEnough(int[][] cover, boolean[] message) {
        if (!isImage(cover)) return false;

        int coverHeight = cover.length;
        int coverWidth = cover[0].length;

        return (coverWidth * coverHeight >= message.length);
    }

      /**
     * Checks if a cover image is large enough to embed a black and white  image
     * @param cover A 2D integer array
	 * @param message  A 2D boolean array to embed into the cover (using LSB)
     * @return {@code true} if the cover is large enough  {@code false} otherwise
     */
    public static boolean isCoverLargeEnough(int[][] cover, boolean[][] message) {
        return (
                   isImage(cover)
                   && isImage(message)
                   && cover.length >= message.length
                   && cover[0].length >= message[0].length
               );
    }

	 /**
     * Checks if a cover image is large enough to embed another image
	 * (to be further converted to a black and white one)
     * @param cover A 2D integer array
	 * @param message  A 2D integer array to embed into the cover (using LSB after
	 * conversion to black and white)
     * @return {@code true} if the cover is large enough  {@code false} otherwise
     */
    public static boolean isCoverLargeEnough(int[][] cover, int[][] message) {
        return (
                   isImage(cover)
                   && isImage(message)
                   && cover.length >= message.length
                   && cover[0].length >= message[0].length
               );
    }

    /**
     * Checks if an array is a valid image; i.e. if it is rectangular and non-empty
     * @param cover A 2D integer array to be checked
     * @return {@code true} if the array is an image, {@code false} otherwise
     */
    public static boolean isImage(int[][] cover) {
        if (cover == null)
            return false;
        if (cover.length == 0)
            return false;

        int width = cover[0].length;
        if (width == 0)
            return false;

        for (int[] line : cover) {
            if (line.length != width)
                return false;
        }
        return true;
    }
   /**
     * Checks if an array is a valid image; i.e. if it is rectangular and non-empty
     * @param cover A 2D boolean array to be checked
     * @return {@code true} if the array is an image, {@code false} otherwise
     */
    public static boolean isImage(boolean[][] cover) {
        if (cover == null)
            return false;
        if (cover.length == 0)
            return false;

        int width = cover[0].length;
        if (width == 0)
            return false;

        for (boolean[] line : cover) {
            if (line.length != width)
                return false;
        }
        return true;
    }
    
    /**
     * 
     * @param bitArray
     * @return return true if there is enough space to code the width and height in the bitArray.
     */
    public static boolean isbitArray(boolean[] bitArray){
    	return (bitArray.length>=64);
    }
    
    /**
     * 
     * @param bitArray
     * @param hauteur
     * @param largeur
     * @return checks in the remaining space and return true (not counting 
     * the space reserved to height and width)
     * if there is enough space for the image to be coded in the bitArray.
     */
    public static boolean isbitArray(boolean[] bitArray, int hauteur, int largeur) {
    	int taille = bitArray.length;
       
        int condition = 32 + 32 + hauteur*largeur;
      
        return taille>=condition;
       
        
    }
    
    /**
     * 
     * @param tab
     * @return une valeur boolean true si le tableau tab n'est pas vide
     */
    public static boolean isNotEmpty (boolean[] tab){ 
    		return (tab.length!=0);
    }
    
}

    
    
   
    

    


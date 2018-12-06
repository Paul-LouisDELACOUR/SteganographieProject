package main;

import java.util.Arrays;

/**
 * @author
 */
public final class Main {

	public static void main(String[] args) {

		int[][] cover = Helper.read("images/" + "StringImage128_128" + ".png");
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In laoreet sodales dictum. Nam risus sem, sagittis a ante ut, varius faucibus magna. Nulla sit amet sodales nisi, vel elementum tortor. Aliquam sollicitudin leo eget urna porta ultricies. Fusce aliquet ut est vitae vehicula. Proin suscipit lectus urna, eu pharetra justo faucibus nec. Fusce magna purus, congue ut suscipit sed, posuere a quam. Mauris ornare ullamcorper mi sit amet interdum. Suspendisse consectetur ornare eros a fringilla. Interdum et malesuada fames ac ante ipsum primis in faucibus.Nam sed elementum eros, in tincidunt erat. Sed sed turpis ac metus pellentesque venenatis ultricies ornare felis. Aliquam eu est lacus. Curabitur id elit sit amet lectus vestibulum maximus vitae ac sem. Curabitur a molestie sapien. Integer egestas sem eu justo pulvinar, a molestie sapien sodales. Nunc sit amet libero non odio porttitor porta. Duis consequat nunc id viverra vulputate. Suspendisse ornare nisi risus, nec molestie neque egestas nec. Aliquam condime"; //128*128 / 16 chars = 1024
	
		int[][] hiddenCover = Steganography.embedText(cover, message);
		//Helper.show(cover, "cover");
		//Helper.show(hiddenCover, "hiddenCover");
		String hiddenMessage = Steganography.revealText(hiddenCover);
		System.out.println(message);
		System.out.println(hiddenMessage);
		assert (message.equals(hiddenMessage));
	
	}

}

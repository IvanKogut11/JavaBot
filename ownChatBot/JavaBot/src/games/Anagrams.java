package games;
import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Anagrams {
	
	private static ArrayList<String> words = new ArrayList<String>();

	private static final int WORD_COUNT = 1000;


	public Anagrams() {
		String abs_path = new File(".").getAbsolutePath() + "/ownChatBot/JavaBot/src/games/word_bucket.txt";
		try (FileReader reader = new FileReader(abs_path);
			 BufferedReader br = new BufferedReader(reader)) {

			// read line by line
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	public void Run() {
		
	}
}

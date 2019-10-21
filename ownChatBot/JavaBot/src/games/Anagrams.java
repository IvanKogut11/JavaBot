package games;
import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Anagrams {
	
	private static final int WORD_COUNT = 1000;
	private static final int SHUFFLE_CNT = 15;
	private static ArrayList<String> words = new ArrayList<String>();
	private static Random rand = new Random();

	private String shuffle(String word) {
		String shuffledWord = word;
		int wordSize = word.length();
		for(int i = 0; i < SHUFFLE_CNT; ++i) {
			int position1 = rand.nextInt(wordSize);
			int position2 = rand.nextInt(wordSize);
			shuffledWord = swapCharacters(shuffledWord, position1, position2);
		}
		return shuffledWord;
	}

	private String swapCharacters(String shuffledWord, int position1, int position2) {
		char[] charArray = shuffledWord.toCharArray();
		char temp = charArray[position1];
		charArray[position1] = charArray[position2];
		charArray[position2] = temp;
		return new String(charArray);
	}

	public Anagrams() {
		String abs_path = new File(".").getAbsolutePath() + "/ownChatBot/JavaBot/src/games/word_bucket.txt";
		try (FileReader reader = new FileReader(abs_path);
			 BufferedReader br = new BufferedReader(reader)) {

			String line;
			while ((line = br.readLine()) != null) {
				words.add(line);
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	private void RunRound(Scanner input) {
		int word_id = rand.nextInt(WORD_COUNT);
		String puz = words.get(word_id);
		String req = shuffle(puz);
		String ans = "";

		System.out.println("What is :" + req + "?");
		while (!ans.equals(puz)) {
			ans = input.nextLine();
			if (ans.equals("no idea")) {
				System.out.println(puz);
				return;
			}
		}
		System.out.println("You right!");
	}

	public void Run(Scanner inputStream) {
		System.out.println("");
		String arg = "y";
		while (!arg.equals("n")) {
			RunRound(inputStream);
			System.out.println("Continue?(y\\n)");
			arg = inputStream.nextLine();
		}
	}
}

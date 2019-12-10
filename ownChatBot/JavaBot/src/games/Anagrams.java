package games;
import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import bot.*;

public class Anagrams implements Game {
	private Bot curBot;
	public static final String GAME_NAME = "anagrams";
	private static final int WORD_COUNT = 1000;
	private static final int SHUFFLE_CNT = 15;
	private static ArrayList<String> words = new ArrayList<String>();
	private static Random rand = new Random();
	private static final String RULES = "In this game you are given English word which letters are shuffled.\n"
			   							+ "You need guess it!\n"
			   							+ "If you don't know what the word is, just write \"no idea\""
			   							+ " to skip it and get the right answer\n"
			   							+ "Good luck!\n";
	
	public Anagrams() {
		String abs_path = new File(".").getAbsolutePath();
		abs_path = abs_path.substring(0, abs_path.length() - 1) + File.separatorChar + "games" 
				+ File.separatorChar + "word_bucket.txt"; // TODO File.separatorChar
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
	
	public void printRules()
	{
		curBot.output(RULES);
	}

	public void run(Bot bot) {
		curBot = bot;
		curBot.output("");
		String arg = "y";
		printRules();
		while (!arg.equals("n")) {
			runRound();
			curBot.output("Continue?(y\\n)");
			arg = curBot.input();
			while (!arg.equals("n") && !arg.equals("y"))
				arg = curBot.input();
		}
	}

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

	private void runRound() {
		int word_id = rand.nextInt(WORD_COUNT);
		String puz = words.get(word_id);
		String req = shuffle(puz);
		String ans = "";

		curBot.output("What is :" + req + "?");
		while (!ans.equals(puz)) {
			ans = curBot.input();
			if (ans.equals("no idea")) {
				curBot.output(puz);
				return;
			}
		}
		curBot.output("You right!");
	}
}

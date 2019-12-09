package games;
import javax.security.auth.login.FailedLoginException;
import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Anagrams implements GameImpl {
	private static final int WORD_COUNT = 1000;
	private static final int SHUFFLE_CNT = 15;
	private static ArrayList<String> words = new ArrayList<String>();
	private static Random rand = new Random();
	private static final String RULES = "In this game you are given English word which letters are shuffled.\n"
			   							+ "You need guess it!\n"
			   							+ "If you don't know what the word is, just write \"no idea\""
			   							+ " to skip it and get the right answer\n"
			   							+ "Good luck!\n";

	private enum State {
		WAIT_BEGIN,
		IN_GAME,
		OVER
	}
	private State state_;

	private static boolean IsWaitBegin(String command) {
		return command.equals("\\anagrams");
	}

	private static boolean IsInGame(String command) {
		return true;
	}

	private static boolean IsOver(String command) {
		return true;
	}

	static
	{
		//LoadWords();
	}

	public Anagrams() {
		//state_ = State.WAIT_BEGIN;
	}

	private static void LoadWords() {
		String abs_path = new File(".").getAbsolutePath();
		abs_path = abs_path.substring(0, abs_path.length() - 1) + File.separatorChar + "ownChatBot" + File.separator
				+ "JavaBot" + File.separatorChar + "src" + File.separatorChar + "games"
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

	public String GetRules() {
		return RULES;
	}

	@Override
	public boolean ExpectCommand(String command) {
		if (state_ == State.WAIT_BEGIN) {
			return IsWaitBegin(command);
		}
		if (state_ == State.IN_GAME) {
			return IsInGame(command);
		}
		return IsOver(command);
	}

	private String puz;

	@Override
	public String Execute(String command) { // TODO Сделать словарь с командами и вызывать Commands[command](state_)
		if (state_ == State.WAIT_BEGIN) {
			state_ = State.IN_GAME;
			puz = GetPuz();
			return GetRules() + puz;
		}
		if (state_ == State.IN_GAME) {
			if (command.equals(puz)) {
				state_ = State.OVER;
				return "Right!\n Do you want to try again?(y/n)"; //TODO вынести куда-то
			}
			return "Wrong:(";
		}
		if (command.equals("y")) {
			state_ = State.IN_GAME;
			puz = GetPuz();
			return puz;
		}
		state_ = State.WAIT_BEGIN;
		return "";
	}

	@Override
	public void SaveBackup() {
	}

	private String GetPuz() {
		int word_id = rand.nextInt(WORD_COUNT);
		return shuffle(words.get(word_id));
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
}

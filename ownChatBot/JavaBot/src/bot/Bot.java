package bot;
import java.util.Scanner;
//import java.util.HashMap;
import games.*;

public class Bot {
	
	private static final String HELP_MESSAGE = "Commands:\n"
			+ "\\help - prints all available commands\n"
			+ "\\anagrams - play game \"anagrams\"\n"
			+ "\\hangman - play game \"hangman\"\n"
			+ "\\exit - finish chatting with the bot";
	private static final String EXIT_COMMAND = "\\exit";
	private static final String FINISH_MESSAGE = "The work with the bot was finished";
	private static final String RETURNING_MESSAGE = "You've returned to bot";
	private Scanner inputStream_;
	private boolean finished_;

	public Bot() {
		inputStream_ = new Scanner(System.in);
		finished_ = false;
	}
	
	public void Run() {
		printHelp();
		String data = inputStream_.nextLine();
		while (isCorrect(data)) {
			doCommand(data);
			if (finished_) {
				break;
			}
			data = inputStream_.nextLine();
		}
		printFinish();
		inputStream_.close();
	}

	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.Run();
	}

	private boolean isCorrect(String data) {
		return true;
	}

	private void runAnagrams() {
	    Anagrams anagrams = new Anagrams();
		System.out.println("Start anagrams game");
		anagrams.run();
	}

	private void runHangman() {
		Hangman hangman = new Hangman();
		System.out.println("Start hangman game");
		hangman.run();
	}

	private void doCommand(String command) {
		boolean wasGame = true;
		if (command.equals(EXIT_COMMAND)) {
			finished_ = true;
			wasGame = false;
		} else if (command.equals("\\" + Anagrams.GAME_NAME)) { //TODO change
			runAnagrams();
		} else if (command.equals("\\" + Hangman.GAME_NAME)) { //TODO change
			runHangman();
		} else {
			printHelp();
			wasGame = false;
		}
		if (wasGame)
			printReturningMessage();
	}

	private void printFinish()
	{
		System.out.println(FINISH_MESSAGE);
	}
	
	private void printReturningMessage()
	{
		System.out.println(RETURNING_MESSAGE);
	}

	private void printHelp()
	{
		System.out.println(HELP_MESSAGE);
	}
}

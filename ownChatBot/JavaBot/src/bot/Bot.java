package bot;
import java.util.Scanner;
import java.util.HashMap;
import games.*;

public class Bot {
	
	private static String helpMessage = "Commands:\n"
			+ "\\help - prints all available commands\n"
			+ "\\anagrams - play game \"anagrams\"\n"
			+ "\\exit - finish chatting with the bot";
	
	private static HashMap<String, Integer> availableGames; //TODO value type?

	private Scanner inputStream_;
	private boolean finished_;

	public Bot() {
		inputStream_ = new Scanner(System.in);
		finished_ = false;
	}

	boolean IsCorrect(String data) {
		return true;
	}

	void RunAnagrams() {
	    Anagrams anagrams = new Anagrams();
		System.out.println("Start anagrams game");
		anagrams.Run();
	}

	void DoCommand(String data) {
		if (data.equals("exit")) {
			finished_ = true;
		} else if (data.equals("anagrams")) {
			RunAnagrams();
		} else {
			PrintHelp();
		}
	}

	private static void PrintFinish()
	{
		System.out.println("The work with the bot was finished");
	}

	private static void PrintHelp()
	{
		System.out.println(helpMessage);
	}

	public void Run() {
		String data = inputStream_.nextLine();
		while (IsCorrect(data)) {
			DoCommand(data);
			if (finished_) {
				break;
			}
			data = inputStream_.nextLine();
		}
		PrintFinish();
		inputStream_.close();
	}

	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.Run();
	}
}

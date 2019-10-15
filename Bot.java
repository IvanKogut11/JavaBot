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
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String arg = "";
		while (arg != "\\exit")
		{
			arg = in.nextLine().trim();
			if (!availableGames.containsKey(arg.substring(1)))
				printHelp();
			else //TODO change if
			{
				if (arg == "anagrams")
				{
					Anagrams game = new Anagrams();
					game.Run();
				}
			}
		}
		printFinish();
		in.close();
	}
	
	private static void printFinish()
	{
		System.out.println("The work with the bot was finished");
	}
	
	private static void printHelp()
	{
		System.out.println(helpMessage);
	}
}

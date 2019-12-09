package bot;
import java.util.Scanner;
import java.util.HashMap;
import games.*;

public class Bot implements BotImpl {
	private Scanner inputStream_;
	HashMap<String, GameImpl> games_ = new HashMap<>();

	public Bot() {
		inputStream_ = new Scanner(System.in);
		// Add games Here
		games_.put("Anagrams", new Anagrams());
		games_.put("Hangman", new Hangman());
	}

	public void Run() {
		Help helper = new Help();
		SendMessage(helper.GetInfo());
		while (true) {
			SaveBackup();
			String message = GetMessage();
			if (message.equals("\\exit")) {
				break;
			}
			try {
				DoCommand(message);
			} catch (Exception ex) {
				LoadBackup();
			}
		}
		inputStream_.close();
	}

	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.Run();
	}

	private void SaveBackup() { // TODO
		for (GameImpl game : games_.values()) {
			game.SaveBackup();
		}
	}

	private void LoadBackup() { // TODO
		for (GameImpl game : games_.values()) {
			game.LoadBackup();
		}
	}

	private void SendMessage(String message) {
		System.out.println(message);
	}

	private String GetMessage() {
		return inputStream_.nextLine();
	}

	private void DoCommand(String command) {
		for (GameImpl game : games_.values()) {
			if (game.ExpectCommand(command)) {
				String reply = game.Execute(command);  //TODO отдельный тип для ответа(не String)
				if (!reply.equals("")) {
					SendMessage(reply);
				}
			}
		}
	}
}

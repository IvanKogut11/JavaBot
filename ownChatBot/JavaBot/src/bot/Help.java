package bot;

public class Help {
    private static final String HELP_MESSAGE = "Commands:\n"
            + "\\help - prints all available commands\n"
            + "\\anagrams - play game \"anagrams\"\n"
            + "\\hangman - play game \"hangman\"\n"
            + "\\exit - finish chatting with the bot";
    private static final String EXIT_COMMAND = "\\exit";
    private static final String FINISH_MESSAGE = "The work with the bot was finished";
    private static final String RETURNING_MESSAGE = "You've returned to bot";

    String GetInfo() {
        return HELP_MESSAGE;
    }
}

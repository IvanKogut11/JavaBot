package games;
import java.io.File;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hangman implements Game {
    public static final String GAME_NAME = "hangman";
    private static final int ATTEMPTS_COUNT = 6;
    private static final int WORD_COUNT = 832;
    private static ArrayList<String> words = new ArrayList<String>();
    private static Random rand = new Random();
    private static final String RULES = "In this game you need to guess the word in 6 attempts.\n"
            + "You can ask if there is a letter in the word!\n"
            + "If true, then the letters will be inserted and the attempt will not be considered\n"
            + "Good luck!\n";

    public Hangman() {
        String abs_path = new File(".").getAbsolutePath();
        abs_path = abs_path.substring(0, abs_path.length() - 1)
                + File.separatorChar + "ownChatBot"
                + File.separatorChar + "JavaBot"
                + File.separatorChar + "src"
                + File.separatorChar + "games"
                + File.separatorChar + "hangman_words.txt"; // TODO File.separatorChar
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
        System.out.println(RULES);
    }

    public void run(Scanner inputStream) {
        System.out.println("");
        String arg = "y";
        printRules();
        while (!arg.equals("n")) {
            runRound(inputStream);
            System.out.println("Continue?(y\\n)");
            arg = inputStream.nextLine();
            while (!arg.equals("n") && !arg.equals("y"))
                arg = inputStream.nextLine();
        }
    }

    private boolean runAttempt(int attempts, StringBuilder actual, String answer, Scanner input) {
        printStage(attempts, actual);
        String playerAnswer = input.nextLine();
        boolean isCorrectTry = true;
        if (playerAnswer.length() != 1 || !Character.isLowerCase(playerAnswer.charAt(0))) {
            System.out.println("You entered an invalid character");
        } else {
            isCorrectTry = false;
            for (int i = 0; i < actual.length(); ++i) {
                if (answer.charAt(i) == playerAnswer.charAt(0) && actual.charAt(i) == '-') {
                    actual.setCharAt(i, playerAnswer.charAt(0));
                    isCorrectTry = true;
                }
            }
        }
        return isCorrectTry;
    }

    private void printStage(int stage, StringBuilder current_word) {
        System.out.println("Attempt: " + stage);
        System.out.println("Word: " + current_word);
        System.out.println("You character: ");
    }

    private boolean isGameFinish(String expected, StringBuilder actual) {
        if (expected.length() != actual.length()) {
            return false;
        }
        for (int i = 0; i < expected.length(); ++i) {
            if (expected.charAt(i) != actual.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private String replace(String s, int pos, char new_char) {
        return s.substring(0, pos) + new_char + s.substring(pos + 1);
    }

    private void runRound(Scanner input) {
        int word_id = rand.nextInt(WORD_COUNT);
        String puz = words.get(word_id);
        StringBuilder current_word = new StringBuilder();
        for (int i = 0; i < puz.length(); ++i) {
            if (i == 0 || i + 1 == puz.length()) {
                current_word.append(puz.charAt(i));
            } else {
                current_word.append('-');
            }
        }
        System.out.println(puz);
        for (int attempts = 0; attempts < ATTEMPTS_COUNT;) {
            if (!runAttempt(attempts, current_word, puz, input)) {
                ++attempts;
            }
            if (isGameFinish(puz, current_word)) {
                System.out.println("You win!");
                return;
            }
        }
        System.out.println("You lose");
    }
}

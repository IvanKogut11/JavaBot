package games;
import java.io.*;
import java.util.*;

public class Hangman implements GameImpl {
    private static final int ATTEMPTS_COUNT = 6;
    private static final int WORD_COUNT = 832;
    private static ArrayList<String> words = new ArrayList<>();
    private static Random rand = new Random();
    private static final String RULES = "In this game you need to guess the word in 6 attempts.\n"
            + "You can ask if there is a letter in the word!\n"
            + "If true, then the letters will be inserted and the attempt will not be considered\n"
            + "Good luck!\n";

    private enum State {
        WAIT_BEGIN,
        IN_GAME,
        OVER
    }

    private Hangman.State state_;
    private int attempt_num_;
    private String puz_;
    private String user_word_;

    private static boolean IsWaitBegin(String command) {
        return command.equals("\\hangman");
    }

    private static boolean IsInGame(String command) {
        return true;
    }

    private static boolean IsOver(String command) {
        return true;
    }

    static
    {
        LoadWords();
    }

    private static void LoadWords() {
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

    public Hangman() {
        state_ = Hangman.State.WAIT_BEGIN;
        attempt_num_ = 1;
    }

    private String GetStageInfo(int stage, String current_word) {
        return "Attempt: " + stage + "\nWord: " + current_word +"\nYou character: ";
    }

    private String UpdateString(String puz, String user_word_, char ch) {
        for (int i = 0; i < puz.length(); ++i) {
            if (puz.charAt(i) == ch) {
                user_word_ = replace(user_word_, i, ch);
            }
        }
        return user_word_;
    }

    private String replace(String s, int pos, char new_char) {
        return s.substring(0, pos) + new_char + s.substring(pos + 1);
    }

    private String GetPuz() {
        int word_id = rand.nextInt(WORD_COUNT);
        return words.get(word_id);
    }

    private String GetUserWord(String puz) {
        return ".".repeat(puz.length());
    }

    @Override
    public String GetRules() {
        return RULES;
    }

    @Override
    public boolean ExpectCommand(String command) {
        if (state_ == State.WAIT_BEGIN && IsWaitBegin(command)) {
            return true;
        }
        if (state_ == State.IN_GAME && IsInGame(command)) {
            return true;
        }
        return state_ == State.OVER && IsOver(command);
    }

    @Override
    public String Execute(String command) {
        if (state_ == State.WAIT_BEGIN) {
            puz_ = GetPuz();
            user_word_ = GetUserWord(puz_);
            state_ = State.IN_GAME;
            return GetRules() + GetStageInfo(attempt_num_, user_word_);
        }
        if (state_ == State.IN_GAME) {
            if (command.length() != 1) {
                return "Put only one symbol!";
            }

            String up_word = UpdateString(puz_, user_word_, command.charAt(0));
            if (up_word.equals(user_word_)) {
                ++attempt_num_;
            }
            user_word_ = up_word;

            if (puz_.equals(user_word_)) {
                state_ = State.OVER;
                return "You win!\nDo you want to try again?(y/n)";
            }

            if (attempt_num_ > ATTEMPTS_COUNT) {
                state_ = State.OVER;
                return "You lose\nRight word: " + puz_+"\nDo you want to try again?(y/n)";
            }

            return GetStageInfo(attempt_num_, user_word_);
        }

        if (command.equals("y")) {
            attempt_num_ = 1;
            puz_ = GetPuz();
            user_word_ = GetUserWord(puz_);
            state_ = State.IN_GAME;
            return GetStageInfo(attempt_num_, user_word_);
        }
        state_ = State.WAIT_BEGIN;
        return "";
    }

    @Override
    public void SaveBackup() {
        String abs_path = new File(".").getAbsolutePath();
        abs_path = abs_path.substring(0, abs_path.length() - 1)
                + "ownChatBot/JavaBot/tmp/hangman.txt";
        try (FileWriter reader = new FileWriter(abs_path);
             BufferedWriter br = new BufferedWriter(reader)) {

            br.write(state_.toString() + "\n" + attempt_num_ + "\n" + puz_ + "\n" + user_word_);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Override
    public void LoadBackup() {
        String abs_path = new File(".").getAbsolutePath();
        abs_path = abs_path.substring(0, abs_path.length() - 1)
                + "ownChatBot/JavaBot/tmp/hangman.txt";
        try (FileReader reader = new FileReader(abs_path);
             BufferedReader br = new BufferedReader(reader)) {

            state_ = Hangman.State.valueOf(br.readLine());
            attempt_num_ = Integer.parseInt(br.readLine());
            puz_ = br.readLine();
            user_word_ = br.readLine();

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}

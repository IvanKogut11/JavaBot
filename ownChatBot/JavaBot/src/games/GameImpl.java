package games;

import java.util.Scanner;

public interface GameImpl {
	String GetRules();
	boolean ExpectCommand(String command);
	String Execute(String command); // TODO сделать отдельный тип для возвращения ответа
	void SaveBackup();
}

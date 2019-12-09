package games;

public interface GameImpl {
	String GetRules();
	boolean ExpectCommand(String command);
	String Execute(String command); // TODO сделать отдельный тип для возвращения ответа. Позводит делать статистику
	void SaveBackup();
	void LoadBackup();
}

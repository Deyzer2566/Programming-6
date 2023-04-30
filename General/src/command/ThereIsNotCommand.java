package command;

/**
 * Исключение, вызываемое если команда не существует
 */
public class ThereIsNotCommand extends RuntimeException{
	public ThereIsNotCommand(String message){
		super(message);
	}
}
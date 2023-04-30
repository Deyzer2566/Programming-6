package command;

/**
 * Исключение, возникающее, если команда не смогла использовать переданные аргументы
 */
public class InvalidCommandArgumentException extends Exception{
	public InvalidCommandArgumentException(String message){
		super(message);
	}
}
package command;
import storage.*;
import io.Writer;

/**
 * Команда вывода информации о коллекции (тип, дата создания, количество групп)
 */
public class InfoCommand implements Command{
	private Database db;
	private Writer writter;
	
	public InfoCommand(Database db, Writer writter){
		this.db = db;
		this.writter = writter;
	}
	
	@Override
	public void execute(String [] args){
		writter.writeln(db.getInfo());
	}

	@Override
	public String description(){
		return "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
	}
}
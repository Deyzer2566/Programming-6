package command;
import storage.Database;
import storage.Database;
import io.IOHandler;
import data.StudyGroup;

/**
 * Команда для добавления элемента в базу
 */
public class AddCommand implements Command{
	private Database db;
	private IOHandler ioHandler;
	
	public AddCommand(Database db, IOHandler ioHandler){
		this.db = db;
		this.ioHandler = ioHandler;
	}

	@Override
	public void execute(String [] args) throws InvalidCommandArgumentException{
		StudyGroup newGroup = ioHandler.readStudyGroup();
		db.add(newGroup);
	}

	@Override
	public String description() {
		return "добавить новый элемент в коллекцию";
	}
}
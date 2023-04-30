package command;
import storage.Database;
import storage.Database;
import io.IOHandler;
import data.StudyGroup;

/**
 * Команда добавления элемента в базу, если он больше всех элементов в базе
 */
public class AddIfMaxCommand implements Command{
    private Database db;
    private IOHandler ioHandler;

    public AddIfMaxCommand(Database db, IOHandler ioHandler){
        this.db = db;
        this.ioHandler = ioHandler;
    }

    @Override
    public void execute(String [] args) throws InvalidCommandArgumentException{
        StudyGroup group = ioHandler.readStudyGroup();
        db.addIfMax(group);
    }

    @Override
    public String description() {
        return "добавить новый элемент в коллекцию";
    }
}
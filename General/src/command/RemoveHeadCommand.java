package command;

import data.StudyGroup;
import storage.Database;

import io.Writer;
import storage.GroupDidNotFound;

/**
 * Команда удаления первого элемента базы
 */
public class RemoveHeadCommand implements Command{

    private Database db;
    private Writer writer;

    public RemoveHeadCommand(Database db, Writer writer){
        this.db=db;
        this.writer=writer;
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        StudyGroup group=null;
        try{
            group = db.removeHead();
        } catch(GroupDidNotFound e){
            writer.writeError(e.getMessage());
        }
        writer.writeObject(group);
    }

    @Override
    public String description() {
        return "вывести первый элемент коллекции и удалить его";
    }
}

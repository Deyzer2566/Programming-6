package command;

import storage.Database;
import storage.GroupDidNotFound;

/**
 * Команда удаления элемента базы по его id
 */
public class RemoveById implements Command{

    private Database db;

    public RemoveById(Database db){
        this.db = db;
    }

    @Override
    public String description() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        long id = 0;
        try{
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException|NullPointerException e){
            throw new InvalidCommandArgumentException("Не удалось считать id группы!");
        }
//        if(db.getGroup(id)==null)
//            throw new InvalidCommandArgumentException("Группа с указанным id не найдена!");
        try{
            db.remove(id);
        } catch (GroupDidNotFound e){
            throw new InvalidCommandArgumentException(e.getMessage());
        }
    }
}

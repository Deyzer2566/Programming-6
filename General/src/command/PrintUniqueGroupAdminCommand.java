package command;

import data.StudyGroup;
import io.Writer;
import storage.Database;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Команда вывода уникальных значений админов групп
 */
public class PrintUniqueGroupAdminCommand implements Command{

    private Database db;
    private Writer writer;

    public PrintUniqueGroupAdminCommand(Database db, Writer writer) {
        this.db = db;
        this.writer = writer;
    }

    @Override
    public String description() {
        return "вывести уникальные значения поля groupAdmin всех элементов в коллекции";
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        Collection<String> uniqueNames = db.getUniqueNamesGroupsAdmins();
        writer.writeObject(uniqueNames);
    }
}

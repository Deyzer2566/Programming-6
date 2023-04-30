package command;

import io.Writer;
import storage.Database;

import java.util.Collection;

/**
 * Команда для вывода всех элементов базы данных, отсортированных по возрастанию поля expelledStudents
 */
public class PrintFieldAscendingExpelledStudentsCommand implements Command{

    private Database db;
    private Writer writer;

    public PrintFieldAscendingExpelledStudentsCommand(Database db, Writer writer) {
        this.db = db;
        this.writer = writer;
    }

    @Override
    public String description() {
        return "вывести значения поля expelledStudents всех элементов в порядке возрастания";
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        Collection<Long> expelledStudents = db.getExpelledStudentsCount();
        expelledStudents = expelledStudents.stream().sorted().toList();
        writer.writeObject(expelledStudents);
    }
}

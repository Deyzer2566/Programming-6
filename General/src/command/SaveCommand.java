package command;
import io.FileAccessException;
import io.Writer;
import storage.DatabaseCSVSaver;

import java.io.FileNotFoundException;

/**
 * Команда сохранения базы в файл
 */
public class SaveCommand implements Command{
    private DatabaseCSVSaver dbSaver;
    private Writer writter;

    public SaveCommand(DatabaseCSVSaver dbSaver, Writer writter){
        this.dbSaver=dbSaver;
        this.writter=writter;
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        try{
            dbSaver.save();
        } catch (FileNotFoundException | FileAccessException e){
            writter.writeln(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "сохранить коллекцию в файл";
    }
}
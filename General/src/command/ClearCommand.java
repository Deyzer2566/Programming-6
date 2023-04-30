package command;

import storage.Database;

/**
 * Команда очистки базы
 */
public class ClearCommand implements Command{

    private Database db;

    public ClearCommand(Database db){
        this.db = db;
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        db.clear();
    }

    @Override
    public String description() {
        return "очистить коллекцию";
    }
}

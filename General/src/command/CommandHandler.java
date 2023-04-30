package command;

import io.IOHandler;
import storage.Database;
import storage.DatabaseCSVSaver;

import java.util.HashMap;

/**
 * Обработчик команд
 */
public class CommandHandler {

    private String [] history;

    private int historyStartPointer;
    private HashMap<String, Command> commands;
    private Database db;
    private IOHandler ioHandler;

    public CommandHandler(Database db, IOHandler ioHandler){
        commands = new HashMap<>();
        history = new String[13];
        this.db = db;
        historyStartPointer=0;
        this.ioHandler=ioHandler;
        register("help",new HelpCommand(this,this.ioHandler));
        register("info",new InfoCommand(db,this.ioHandler));
        register("show",new ShowCommand(db,this.ioHandler));
        register("add",new AddCommand(db,this.ioHandler));
        register("update",new UpdateByIdCommand(db,this.ioHandler));
        register("remove", new RemoveById(db));
        register("clear",new ClearCommand(db));
        register("remove_head", new RemoveHeadCommand(db,this.ioHandler));
        register("add_if_max", new AddIfMaxCommand(db,this.ioHandler));
        register("history", new HistoryCommand(this,this.ioHandler));
        register("max_by_students_count", new MaxByStudentsCountCommand(db,this.ioHandler));
        register("print_unique_group_admin",new PrintUniqueGroupAdminCommand(db,this.ioHandler));
        register("print_field_ascending_expelled_students",
                new PrintFieldAscendingExpelledStudentsCommand(db,this.ioHandler));
        register("execute_script", new ExecuteScriptCommand(this.ioHandler, this));
    }

    /**
     *
     * @return база данных, для которой исполняются команды
     */
    public Database getDb() {
        return db;
    }

    public void changeIO(IOHandler io){
        this.ioHandler=io;
    }

    /**
     *
     * @return возвращает поддерживаемые команды
     */
    public HashMap<String, Command> getCommands(){
        return commands;
    }

    /**
     * егистрирует новую команду
     * @param commandName название команды
     * @param command команда
     */
    public void register(String commandName, Command command){
        commands.put(commandName, command);
    }

    /**
     * Выполняет указанную команду
     * @param commandName название команды
     * @param args аргументы команды
     * @throws ThereIsNotCommand если команда не существует
     * @throws InvalidCommandArgumentException если команда не смогла использовать введенные аргументы
     */
    public void execute(String commandName,String [] args) throws ThereIsNotCommand, InvalidCommandArgumentException {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new ThereIsNotCommand("Команды " + commandName + " не существует");
        }
        history[historyStartPointer]=commandName;
        historyStartPointer++;
        historyStartPointer %=13;
        command.execute(args);
    }

    /**
     *
     * @return 13 последних выполняемых команд
     */
    public String[] getHistory(){
        String [] returnedHistory = new String[13];
        for(int i = 0;i<13;i++) {
            returnedHistory[i] = history[(i + historyStartPointer) % 13];
        }
        return returnedHistory;
    }
}

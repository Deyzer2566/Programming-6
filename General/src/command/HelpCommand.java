package command;

import io.Writer;

import java.util.Map;

/**
 * Команда, выводящая справку по командам
 */
public class HelpCommand implements Command{
    private final CommandHandler commandHandler;
    Writer writer;

    public HelpCommand(CommandHandler commandHandler, Writer writer){
        this.commandHandler=commandHandler;
		this.writer = writer;
    }

    @Override
    public void execute(String [] args) {
        StringBuilder printString = new StringBuilder();
        for(Map.Entry<String, Command> command : commandHandler.getCommands().entrySet())
            printString.append(command.getKey()).append(": ").append(command.getValue().description()).append("\n");
        writer.write(printString.toString());
    }

    @Override
    public String description() {
        return "вывести справку по доступным командам";
    }
}

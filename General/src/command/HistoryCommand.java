package command;

import io.Writer;

/**
 * Команда, выводящая последние 13 выполненных команд
 */
public class HistoryCommand implements Command{

    private CommandHandler ch;
    private Writer writter;

    public HistoryCommand(CommandHandler ch, Writer writter){
        this.ch = ch;
        this.writter = writter;
    }

    @Override
    public String description() {
        return "вывести последние 13 команд (без их аргументов)";
    }

    @Override
    public void execute(String[] args) throws InvalidCommandArgumentException {
        String [] history = ch.getHistory();
        StringBuilder outString = new StringBuilder();
        for(String s: history) {
            if(s != null)
                outString.append(s).append("\n");
        }
        writter.writeln(outString.toString());
    }
}

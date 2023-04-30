package command;

/**
 * Интерфейс для всех команд
 */
public interface Command {

    /**
     * Выполняет команду
     * @param args аргументы команды
     * @throws InvalidCommandArgumentException команда не смогла использовать переданные аргументы
     */
    void execute(String [] args) throws InvalidCommandArgumentException;

    /**
     * Возвращает описание команды
     * @return описание команды
     */
    String description();
}

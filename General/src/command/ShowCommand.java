package command;
import storage.Database;
import io.Writer;

/**
 * Команда вывода всех групп базы
 */
public class ShowCommand implements Command{
	private Database db;
	private Writer writer;
	
	public ShowCommand(Database db, Writer writer){
		this.db = db;
		this.writer = writer;
	}
	
	/**
     * выводит все элементы базы
     */
	private void show(){
		String outString = db.showAllGroups();
		writer.writeln(outString);
	}

	@Override
	public void execute(String [] args){
		show();
	}

	@Override
	public String description() {
		return "вывести все элементы коллекции в строковом представлении";
	}
}
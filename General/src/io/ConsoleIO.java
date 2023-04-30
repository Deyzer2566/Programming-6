package io;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleIO extends IOHandler{

	private Scanner scanner;

	public ConsoleIO(){
		scanner = new Scanner(System.in);
	}

	@Override
	public void write(String str){
		System.out.print(str);
	}

	@Override
	public void writeln(String str){
		System.out.println(str);
	}

	@Override
	public void writeError(String str) {
		System.err.println(str);
	}

	@Override
	public void writeObject(Object obj) {
		if(obj != null)
			System.out.println(obj.toString());
		else System.out.println("null");
	}

	@Override
	public String read() throws NoSuchElementException{
		String ret = scanner.next();
		return ret;
	}

	@Override
	public String readLine() throws NoSuchElementException{
		String ret = scanner.nextLine();
		return ret;
	}

	@Override
	public boolean hasNext() {
		return scanner.hasNext();
	}

	@Override
	public boolean hasNextLine() {
		return scanner.hasNextLine();
	}
}
import command.*;
import io.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String [] args) throws IOException {
        RemoteDatabase db = new RemoteDatabase("127.0.0.1", 25565);
        ConsoleIO console = new ConsoleIO();
        CommandHandler ch = new CommandHandler(db, console);
        console.write(">");
        while(db.isConnected()) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String command = null;
            if(System.in.available()==0)
                continue;
            try{
                command = console.readLine();
            } catch (NoSuchElementException e){
                break;
            }
            if(command == null)
                break;
            if(command.equals("exit")){
                break;
            }
            if(command.equals("")){
                continue;
            }
            System.out.println(db.isConnected());
            LinkedList<String> commandArgs = new LinkedList<>(Arrays.asList(command.split(" ")));
            command = commandArgs.get(0);
            commandArgs.remove(0);
            try{
                ch.execute(command,commandArgs.size()==0?null:commandArgs.toArray(new String[commandArgs.size()]));
            } catch(ThereIsNotCommand | InvalidCommandArgumentException e){
                console.writeError(e.getMessage());
            }
            console.write(">");
        }
        db.disconnect();
    }
}

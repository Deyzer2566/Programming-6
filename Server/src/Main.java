import command.*;
import io.*;
import storage.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class Main {
    static org.slf4j.Logger log;
    static void execute(IOHandler client, CommandHandler ch){
        String command = null;
        if(!client.hasNext())
            return;
        try {
            command = client.readLine();
        } catch (NoSuchElementException e) {
            return;
        }
        if (command == null)
            return;
        if (command.equals("")) {
            return;
        }
        LinkedList<String> commandArgs = new LinkedList<>(Arrays.asList(command.split(" ")));
        command = commandArgs.get(0);
        commandArgs.remove(0);
        log.info("Пришла команда "+command);
        try {
            ch.execute(command, commandArgs.size() == 0 ? null : commandArgs.toArray(new String[commandArgs.size()]));
        } catch (ThereIsNotCommand | InvalidCommandArgumentException e) {
            client.writeError(e.getMessage());
        }
    }
    public static void main(String [] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(25565));
        ServerSocket serverSocket = serverSocketChannel.socket();
        LocalDatabase db = new LocalDatabase();
        ConsoleIO console = new ConsoleIO();
        DatabaseCSVSaver saver = new DatabaseCSVSaver(db,args[0]);
        DatabaseCSVLoader loader = new DatabaseCSVLoader(db,args[0]);
        CommandHandler ch = new CommandHandler(db,console);
        ch.register("save",new SaveCommand(saver,console));
        try {
            loader.load();
        } catch (FileNotFoundException | ParseFileException e) {
            System.out.println(e.getMessage());
        }
        LinkedList<Client> clients = new LinkedList<>();
        console.write(">");
        log = org.slf4j.LoggerFactory.getLogger("main");
        log.info("Начало работы");
        while(true){
            if(System.in.available()>0)
            {
                String command = null;
                try {
                    command = console.readLine();
                } catch (NoSuchElementException e) {
                    break;
                }
                if (command == null)
                    break;
                if (command.equals("exit")) {
                    break;
                }
                if (command.equals("")) {
                    break;
                }
                LinkedList<String> commandArgs = new LinkedList<>(Arrays.asList(command.split(" ")));
                command = commandArgs.get(0);
                commandArgs.remove(0);
                try {
                    ch.execute(command, commandArgs.size() == 0 ? null : commandArgs.toArray(new String[commandArgs.size()]));
                } catch (ThereIsNotCommand | InvalidCommandArgumentException e) {
                    console.writeError(e.getMessage());
                }
                console.write(">");
            }
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                SocketChannel errChannel = null;
                while(errChannel == null)
                    errChannel = serverSocketChannel.accept();
                clients.add(new Client(socketChannel.socket(),errChannel.socket()));
                log.info("Новое подключение");
            }
            for(Client client: clients) {
                if (client.isConnected()) {
                    execute(client, new CommandHandler(db, client));
                } else {
                    client.disconnect();
                    clients.remove(client);
                    log.info("Клиент отключился");
                }
            }
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(Client client: clients){
            client.disconnect();
        }
        serverSocket.close();
    }
}

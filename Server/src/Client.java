import data.StudyGroup;
import io.IOHandler;

import java.io.*;
import java.net.Socket;
import java.nio.channels.IllegalBlockingModeException;
import java.util.NoSuchElementException;

public class Client extends IOHandler {

    public Socket main;
    private Socket err;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObjectOutputStream errOut;
    private InputStream kostyl;

    private boolean isConnected = false;

    public Client(Socket main, Socket err) throws IOException {
        this.main = main;
        //this.main.getChannel().configureBlocking(false);
        this.err = err;
        this.kostyl = main.getInputStream();
        this.in = new ObjectInputStream(kostyl);
        this.out = new ObjectOutputStream(main.getOutputStream());
        this.errOut = new ObjectOutputStream(err.getOutputStream());
        isConnected = true;
    }

    @Override
    public String read() throws NoSuchElementException {
        try {
            return (String)in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            isConnected = false;
            throw new NoSuchElementException("Не удалось прочитать строку!");
        }
    }

    @Override
    public String readLine() throws NoSuchElementException {
        return read();
    }

    @Override
    public boolean hasNext() {
        try {
            return kostyl.available()>0;
        } catch (IOException e) {
            isConnected = false;
            return false;
        }
    }

    @Override
    public boolean hasNextLine() {
        return hasNext();
    }

    @Override
    public void writeObject(Object obj) {
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            isConnected = false;
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void write(String str) {
        writeObject(str);
    }

    @Override
    public void writeln(String str) {
        writeObject(str);
    }

    @Override
    public void writeError(String str) {
        try{
            errOut.writeObject(str);
        } catch (IOException e) {
            isConnected = false;
        }
    }

    @Override
    public StudyGroup readStudyGroup() {
        try{
            return (StudyGroup)in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
            isConnected = false;
            return null;
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void disconnect(){
        try {
            isConnected=false;
            main.close();
            err.close();
        } catch (IOException e) {

        }
    }
}

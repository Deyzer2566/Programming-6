package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileReader extends IOHandler{
    private Scanner scanner;

    public FileReader(String filename) throws FileNotFoundException, FileAccessException{
        File file = new File(filename);
        if(!file.exists()){
            throw new FileNotFoundException("Файл не найден!");
        }
        if(!file.canRead()){
            throw new FileAccessException("Нет прав для чтения из файла!");
        }
        scanner = new Scanner(file);
    }

    @Override
    public String read() {
        String ret = "";
        try{
            ret = scanner.next();
        } catch(NoSuchElementException e){
            writeError("Ошибка!");
        }
        return ret;
    }

    @Override
    public String readLine() {
        String ret = "";
        try{
            ret = scanner.nextLine();
        } catch(NoSuchElementException e){
            writeError("Ошибка!");
        }
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

    @Override
    public void write(String str) {

    }

    @Override
    public void writeln(String str) {

    }

    @Override
    public void writeError(String str) {
        System.err.println(str);
    }

    @Override
    public void writeObject(Object obj) {

    }
}

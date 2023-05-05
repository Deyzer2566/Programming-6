package storage;

import data.*;
import io.ParseFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DatabaseCSVLoader {
    private LocalDatabase db;
    private String fileName;

    /**
     *
     * @param db база, в которую будут записываться данные
     * @param fileName название файла-источника
     */
    public DatabaseCSVLoader(LocalDatabase db, String fileName){
        this.db = db;
        this.fileName = fileName;
    }

    /**
     * Читает информацию из файла в базу
     * @throws FileNotFoundException если файл не обнаружен
     * @throws ParseFileException если возникли ошибки с разбиением строки
     */
    public void load() throws FileNotFoundException, ParseFileException{
        File file;
        try {
            file = new File(fileName);
        } catch (NullPointerException e){
            throw new FileNotFoundException("Название файла не может быть пустым!");
        }
        if(!file.exists() || !file.canRead())
            throw new FileNotFoundException("Файл не обнаружен или доступ к нему запрещен!");
        Scanner scanner = new Scanner(new File(fileName));
        String header = scanner.nextLine();
        if (!header.equals("id;name;coordinatesX;coordinatesY;creationDate;studentsCount;expelledStudents;"+
                "shouldBeExpelled;semesterEnum;name;weight;eyeColor;hairColor;nationality")){
            throw new ParseFileException("Проблема с заголовками файла!");
        }
        while(scanner.hasNextLine()){
            String cur = scanner.nextLine();
            String [] params = cur.split(";");
            try {
                long id = Long.parseLong(params[0]);
                if(id < 0) throw new ParseFileException("Не удалось прочитать файл!");
                String name = params[1];
                if(name == "") throw new ParseFileException("Не удалось прочитать файл!");
                Float x = Float.parseFloat(params[2]);
                if(x<=-312.0) throw new ParseFileException("Не удалось прочитать файл!");
                Double y = Double.parseDouble(params[3]);
                if(y > 38.0) throw new ParseFileException("Не удалось прочитать файл!");
                ZonedDateTime creationTime = ZonedDateTime.parse(params[4]);
                Long studentsCount=null;
                if(!params[5].equals("null")) {
                    studentsCount = Long.parseLong(params[5]);
                    if (studentsCount < 0) throw new ParseFileException("Не удалось прочитать файл!");
                }
                Long expelledStudents = null;
                if(!params[6].equals("null")) {
                    expelledStudents = Long.parseLong(params[6]);
                    if (expelledStudents < (long)0) throw new ParseFileException("Не удалось прочитать файл!");
                }
                Integer shouldBeExpelled = Integer.parseInt(params[7]);
                if(shouldBeExpelled < 0) throw new ParseFileException("Не удалось прочитать файл!");
                Semester semester=null;
                if(!params[8].equals("null"))
                    semester = Semester.valueOf(params[8]);
                Person admin = null;
                if(!(params[9].equals("null") && params.length==10)) {
                    String adminName = params[9];
                    int weight = Integer.parseInt(params[10]);
                    if(weight < 0) throw new ParseFileException("Не удалось прочитать файл!");
                    Color eyeColor = Color.valueOf(params[11]);
                    Color hairColor = Color.valueOf(params[12]);
                    Country nationality = Country.valueOf(params[13]);
                    admin = new Person(adminName, weight, eyeColor, hairColor, nationality);
                }
                StudyGroup group = new StudyGroup(id, name, new Coordinates(x, y), creationTime, studentsCount,
                        expelledStudents, shouldBeExpelled, semester, admin);
                try{
                    db.put(group);
                } catch(ThereIsGroupWithThisIdException e){
                    throw new ParseFileException("Не удалось прочитать файл!");
                }
            } catch(NumberFormatException e){
                throw new ParseFileException("Не удалось прочитать число!");
            } catch(IllegalArgumentException e){
                throw new ParseFileException("Не удалось прочитать семестр или цвет!");
            } catch(DateTimeParseException e){
                throw new ParseFileException("Не удалось прочитать дату!");
            } catch(ArrayIndexOutOfBoundsException e){
                throw new ParseFileException("Количество столбцов меньше необходимого!");
            }
        }
    }
}

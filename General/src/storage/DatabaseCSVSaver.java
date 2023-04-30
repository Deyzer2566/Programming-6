package storage;

import data.StudyGroup;
import data.Person;
import io.FileAccessException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class DatabaseCSVSaver {
    private LocalDatabase db;
    private String fileName;

    /**
     *
     * @param db база, данные которой сохраняются
     * @param fileName название файла, в который сохраняется база
     */
    public DatabaseCSVSaver(LocalDatabase db, String fileName){
        this.db = db;
        this.fileName = fileName;
    }

    /**
     * Сохраняет файл
     * @throws FileNotFoundException
     * @throws FileAccessException
     */
    public void save() throws FileNotFoundException, FileAccessException {
        String data = "id;name;coordinatesX;coordinatesY;creationDate;studentsCount;expelledStudents;shouldBeExpelled;semesterEnum;"+
                "name;weight;eyeColor;hairColor;nationality\n";
        for(StudyGroup group: db.getAllGroups()){
            data += Long.toString(group.getId())+';'+group.getName()+';'+group.getCoordinates().getX()+';'+
                    group.getCoordinates().getY()+';'+group.getCreationDate()+';'+
                    (group.getStudentsCount()==null?"null":group.getStudentsCount())+';'+
                    (group.getExpelledStudents()==null?"null":group.getExpelledStudents())+';'
                    +group.getShouldBeExpelled()+';'+
                    (group.getSemesterEnum()==null?"null":group.getSemesterEnum().name())+';';
            Person admin = group.getGroupAdmin();
            if(admin != null) {
                data += admin.getName() + ';' + admin.getWeight() + ';' + admin.getEyeColor().name() + ';' + admin.getHairColor().name() + ';' +
                        admin.getNationality().name();
            }
            else{
                data+="null";
            }
            data+="\n";
        }
        File file = new File(fileName);
        if(!file.exists())
            try {
                file.createNewFile();
            } catch(IOException e){
                throw new FileAccessException("Не удалось создать файл!");
            }
        if(!file.canWrite())
            throw new FileAccessException("Доступ к файлу запрещен!");
        PrintWriter pw = new PrintWriter(file);
        pw.print(data);
        pw.flush();
        pw.close();
    }
}

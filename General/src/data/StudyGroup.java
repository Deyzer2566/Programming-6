package data;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Long expelledStudents; //Значение поля должно быть больше 0, Поле может быть null
    private Integer shouldBeExpelled; //Значение поля должно быть больше 0, Поле не может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле может быть null

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public Long getExpelledStudents() {
        return expelledStudents;
    }

    public Integer getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public StudyGroup(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Long studentsCount,
                      Long expelledStudents, Integer shouldBeExpelled, Semester semesterEnum, Person groupAdmin) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.shouldBeExpelled = shouldBeExpelled;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public long getId(){
        return id;
    }

	public void changeId(long id){
		this.id = id;
	}
	
    @Override
    public String toString(){
        return "{"+Long.toString(id)+", "+name+", "+creationDate.toString()+", "+
                (studentsCount==null?"null":studentsCount.toString()) + ", " +
                (expelledStudents==null?"null":expelledStudents.toString()) + ", " +
                shouldBeExpelled.toString()+", "+(semesterEnum==null?"null":semesterEnum.toString())+", "+
                (groupAdmin==null?"null":groupAdmin.toString())+"}";
//        return "Информация о группе: "+Long.toString(id)+"\n"+
//                "Название группы: "+name+"\n"+
//                "Создана: "+creationDate.toString()+"\n"+
//                "Количество выживших: "+(studentsCount==null?"null":studentsCount.toString())+"\n"+
//                "Количество павших душ: "+(expelledStudents==null?"null":expelledStudents.toString())+"\n"+
//                "Сколько душ скоро погубят: "+shouldBeExpelled.toString()+"\n"+
//                "Семестр: "+(semesterEnum==null?"null":semesterEnum.toString())+"\n"+
//                "Админ группы: "+(groupAdmin==null?"null":groupAdmin.toString());
    }

    @Override
    public int compareTo(StudyGroup otherGroup) {
        return name.compareTo(otherGroup.name);
    }
}
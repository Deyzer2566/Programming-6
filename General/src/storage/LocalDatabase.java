package storage;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import data.*;

public class LocalDatabase extends Database {
    private PriorityQueue<StudyGroup> collection;
    private ZonedDateTime creationTime;

    public LocalDatabase(){
        collection = new PriorityQueue<>();
        creationTime = ZonedDateTime.now();
    }

    @Override
    public void add(StudyGroup newGroup){
        long id = 0;
        for(StudyGroup group: getAllGroups().stream().sorted((o1, o2) -> o1.getId()>o2.getId()?1:0).toList()
        ){
            if(group.getId() == id){
                id++;
            }
        }
        newGroup.changeId(id);
        StudyGroup group = new StudyGroup(newGroup.getId(),newGroup.getName(), newGroup.getCoordinates(),
                ZonedDateTime.now(), newGroup.getStudentsCount(), newGroup.getExpelledStudents(),
                newGroup.getShouldBeExpelled(), newGroup.getSemesterEnum(), newGroup.getGroupAdmin());
        collection.add(newGroup);
    }

    @Override
    public void put(StudyGroup newGroup) throws ThereIsGroupWithThisIdException{
        for(StudyGroup group: getAllGroups()){
            if(group.getId() == newGroup.getId()){
                throw new ThereIsGroupWithThisIdException("Группа с указанным ID уже есть!");
            }
        }
        collection.add(newGroup);
    }
    @Override
    public StudyGroup getGroup(long id) throws GroupDidNotFound{
        for(StudyGroup group: collection){
            if(group.getId() == id){
                return group;
            }
        }
        throw new GroupDidNotFound("Группа с указанным id не найдена");
    }

    @Override
    public void remove(long id) throws GroupDidNotFound{
        StudyGroup group = getGroup(id);
        if(group == null){
            throw new GroupDidNotFound("Группа не найдена!");
        }
        collection.remove(group);
    }

    @Override
    public void clear(){
        collection.clear();
    }


    @Override
    public StudyGroup removeHead() throws GroupDidNotFound{
        StudyGroup group = null;
        try {
            group = collection.remove();
        }catch(NoSuchElementException e){
            throw new GroupDidNotFound("База пуста!");
        }
        return group;
    }

    /**
     *
     * @return коллекция базы
     */
    public Collection<StudyGroup> getAllGroups(){
        return this.collection;
    }

    @Override
    public String getInfo() {
        return "Тип: "+collection.getClass().getTypeName()+"\n"+
                "Дата инициализации: "+creationTime+"\n"+
                "Количество групп: "+collection.size();
    }

    @Override
    public StudyGroup getMax(){
        StudyGroup max = null;
        for(StudyGroup group: collection){
            if(max == null || group.compareTo(max)>0){
                    max = group;
            }
        }
        return max;
    }
    @Override
    public int getSize(){
        return collection.size();
    }

    @Override
    public void update(long id, StudyGroup group) throws GroupDidNotFound{
        remove(id);
        group.changeId(id);
        add(group);
    }

    @Override
    public void addIfMax(StudyGroup group){
        if(group.compareTo(getMax())>0)
            add(group);
    }

    @Override
    public StudyGroup getMaxByStudentsCountGroup() {
        StudyGroup max = getAllGroups().stream()
                .max((o1, o2) -> o1.getStudentsCount() > o2.getStudentsCount() ? 1 : 0).get();
        return max;
    }

    @Override
    public Collection<Long> getExpelledStudentsCount() {
        LinkedList<Long> expelledStudents = new LinkedList<>();
        for(StudyGroup group: getAllGroups()){
            expelledStudents.add(group.getExpelledStudents());
        }
        expelledStudents.sort((o1, o2) -> o1.compareTo(o2));
        return expelledStudents;
    }

    @Override
    public Collection<String> getUniqueNamesGroupsAdmins() {
        LinkedList<String> uniqueNames = new LinkedList<>();
        for(StudyGroup group: getAllGroups()){
            if(!uniqueNames.contains(group.getGroupAdmin().getName())){
                uniqueNames.add(group.getGroupAdmin().getName());
            }
        }
        return uniqueNames;
    }

    @Override
    public String showAllGroups() {
        StringBuilder stringBuilder = new StringBuilder();
        for(StudyGroup group: getAllGroups()) {
            stringBuilder.append(group.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
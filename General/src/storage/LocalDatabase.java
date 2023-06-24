package storage;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import data.*;

public class LocalDatabase extends Database {
    private PriorityQueue<StudyGroup> collection;
    private ZonedDateTime creationTime;

    public LocalDatabase(){
        collection = new PriorityQueue<>();
        creationTime = ZonedDateTime.now();
    }

    public void add(StudyGroup newGroup){
        long id = 0;
        for(StudyGroup group: this.getAllGroups().stream().sorted((o1, o2) -> o1.getId()>o2.getId()?1:-1).toList()
        ){
            if(group.getId() == id){
                id++;
            }
        }
        newGroup.changeId(id);
        StudyGroup group = new StudyGroup(newGroup.getId(),newGroup.getName(), newGroup.getCoordinates(),
                ZonedDateTime.now(), newGroup.getStudentsCount(), newGroup.getExpelledStudents(),
                newGroup.getShouldBeExpelled(), newGroup.getSemesterEnum(), newGroup.getGroupAdmin());
        this.collection.add(newGroup);
    }

    /**
     * Добавляет группу в базу, изменяя её id
     * @param newGroup группа, которую нужно добавить в коллекцию
     * @return true, если группа успешно добавлена в базу, false, если нет
     */
    public void put(StudyGroup newGroup) throws ThereIsGroupWithThisIdException{
        for(StudyGroup group: getAllGroups()){
            if(group.getId() == newGroup.getId()){
                throw new ThereIsGroupWithThisIdException("Группа с указанным ID уже есть!");
            }
        }
        collection.add(newGroup);
    }

    /**
     * Возвращает группу по её id
     * @param id ИД группы
     * @return StudyGroup группа с указаным id, если группа с таким id есть в коллекции, null, если группа не найдена
     */
    private StudyGroup getGroup(long id) throws GroupDidNotFound{
        List group = getAllGroups().stream().filter(x->x.getId()==id).toList();
        if(!group.isEmpty())
            return (StudyGroup) group.get(0);
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
    protected Collection<StudyGroup> getAllGroups(){
        return this.collection;
    }

    @Override
    public String getInfo() {
        return "Тип: "+collection.getClass().getTypeName()+"\n"+
                "Дата инициализации: "+creationTime+"\n"+
                "Количество групп: "+collection.size();
    }

    /**
     *
     * @return максимальную группу (сортировка по названию)
     */
    public StudyGroup getMax(){
        try{
            return getAllGroups().stream().max((o1,o2)->o1.compareTo(o2)).get();
        } catch (NoSuchElementException e){
            return null;
        }
    }

    /**
     *
     * @return размер базы
     */
    public int getSize(){
        return collection.size();
    }

    @Override
    public void update(long id, StudyGroup group) throws GroupDidNotFound{
        collection.remove(getGroup(id));
        group.changeId(id);
        collection.add(group);
    }

    @Override
    public void addIfMax(StudyGroup group){
        StudyGroup max = getMax();
        if(max == null || group.compareTo(getMax())>0)
            add(group);
    }

    @Override
    public StudyGroup getMaxByStudentsCountGroup() {
        try{
            StudyGroup max = getAllGroups().stream()
                    .max((o1, o2) -> o1.getStudentsCount() > o2.getStudentsCount() ? 1 : 0).get();
            return max;
        } catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public Collection<Long> getExpelledStudentsCount() {
        return getAllGroups().stream().map(StudyGroup::getExpelledStudents)
                .sorted(Long::compareTo).toList();
    }

    @Override
    public Collection<String> getUniqueNamesGroupsAdmins() {
        List<String> uniqueNames = getAllGroups().stream().map(x->x.getGroupAdmin()).
                filter(x -> x!=null).map(x->x.getName()).distinct().sorted().toList();
        return uniqueNames;
    }

    @Override
    public Collection<StudyGroup> showAllGroups() {
        return getAllGroups();
    }
}
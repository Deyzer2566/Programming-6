import data.StudyGroup;
import storage.Database;
import storage.GroupDidNotFound;
import storage.ThereIsGroupWithThisIdException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;
import java.util.Collection;

public class RemoteDatabase extends Database {

//    SocketChannel socketChannel;
//    SocketChannel socketChannelErr;

    private SocketChannel socketChannel;
    private SocketChannel socketChannelErr;

    private boolean isConnected = false;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObjectInputStream err;

    public RemoteDatabase(String host, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannelErr = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host, port));
        socketChannelErr.connect(new InetSocketAddress(host, port));
//        socketChannel.configureBlocking(false);
//        socketChannelErr.configureBlocking(false);

        this.out = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        this.in = new ObjectInputStream(socketChannel.socket().getInputStream());
        this.err = new ObjectInputStream(socketChannelErr.socket().getInputStream());

        isConnected = true;
        System.out.println("connected!");
    }

    public void send(Object obj){
        //ByteArrayOutputStream buff = new ByteArrayOutputStream(10240);
        //ObjectOutputStream out = null;
        try {
            //out = new ObjectOutputStream(buff);
            out.writeObject(obj);
            //socketChannel.write(ByteBuffer.wrap(buff.toByteArray()));
        } catch (IOException e) {

        }
    }

    private boolean sendCommandWithObject(String command, Object arg) {
        try {
            //ByteArrayOutputStream buff = new ByteArrayOutputStream(10240);
            //ObjectOutputStream out = new ObjectOutputStream(buff);
            out.writeObject(command);
            out.writeObject(arg);
            //socketChannel.write(ByteBuffer.wrap(buff.toByteArray()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean sendCommandWithArgs(String command, String[] args){
        StringBuilder sendStr = new StringBuilder();
        sendStr.append(command);
        if(args != null)
            for(String arg:args)
                sendStr.append(" ").append(arg);
        try {
            //ByteArrayOutputStream buff = new ByteArrayOutputStream(10240);
            //ObjectOutputStream out = new ObjectOutputStream(buff);
            out.writeObject(sendStr.toString());
            //socketChannel.write(ByteBuffer.wrap(buff.toByteArray()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /*public Object recieve() throws IOException, ClassNotFoundException {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ByteBuffer buff = ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead = socketChannel.read(buff);
        System.out.println(Integer.valueOf(bytesRead).toString() + " " + isConnected());
        int totalRead = 0;
        while (bytesRead != 0 || totalRead == 0) {
            totalRead += bytesRead;
            buff.flip();
            while(buff.hasRemaining())
                baos.write(buff.get());
            //buff.alignmentOffset(len,1);
            buff.clear();
            bytesRead = socketChannel.read(buff);
        }
        byte[] objectBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    public Object recieveErr() throws IOException, ClassNotFoundException {
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        ByteBuffer buffer = ByteBuffer.allocate(10240);
        if(socketChannel.read(buffer)<0) return null;
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
        return ois.readObject();
    }

    public int recieveInt() throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(10240);
        if(socketChannel.read(buffer)<0)
            throw new IOException("НЕТ ДАННЫХ!");
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
        return ois.readInt();
    }
     */

    public Object recieve() throws ClassNotFoundException {
        try{
            return in.readObject();
        } catch (IOException e){
            isConnected = false;
            return null;
        }
    }

//    public int recieveInt() throws IOException {
//        return in.readInt();
//    }

    public Object recieveErr() throws IOException, ClassNotFoundException {
        try{
            return err.readObject();
        } catch (IOException e){
            isConnected = false;
            return null;
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void disconnect(){
        try {
            isConnected = false;
            socketChannel.close();
            socketChannelErr.close();
        } catch (IOException e) {

        }
    }

    @Override
    public void add(StudyGroup newGroup) {
        if(!sendCommandWithObject("add",newGroup)) return;
    }

    @Override
    public void put(StudyGroup newGroup) throws ThereIsGroupWithThisIdException {
//        if(!sendCommandWithObject("put",newGroup)) return;
//        try {
//            String err = (String)recieveErr();
//            throw new ThereIsGroupWithThisIdException(err);
//        } catch (IOException | ClassNotFoundException | ClassCastException e) {
//        }
    }

    @Override
    public StudyGroup getGroup(long id) throws GroupDidNotFound{
//        if(!sendCommandWithArgs("getGroup",new String[]{Long.valueOf(id).toString()}))
//            return null;
//        try {
//            String err = (String)recieveErr();
//            throw new GroupDidNotFound(err);
//        } catch (IOException | ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
//        }
//        StudyGroup group = null;
//        try {
//            group = (StudyGroup) recieve();
//        } catch (ClassNotFoundException | ClassCastException e) {
//        }
//        return group;
        return null;
    }

    @Override
    public void remove(long id) throws GroupDidNotFound {
        if(!sendCommandWithArgs("remove",new String[]{Long.valueOf(id).toString()}))
            return;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            String err = (String)recieveErr();
            throw new GroupDidNotFound(err);
        } catch (IOException | ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
        }
    }

    @Override
    public void clear() {
        sendCommandWithArgs("clear",null);
    }

    @Override
    public Collection<StudyGroup> getAllGroups() {
//        sendCommandWithArgs("getAllGroups",null);
//        Collection<StudyGroup> groups = null;
//        try {
//            groups = (Collection<StudyGroup>)recieve();
//        } catch (ClassNotFoundException | ClassCastException e) {
//            return null;
//        }
//        return groups;
        return null;
    }

    @Override
    public StudyGroup removeHead() throws GroupDidNotFound {
        sendCommandWithArgs("remove_head", null);
        StudyGroup ans = null;
        try {
            ans = (StudyGroup)recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
        }
        try {
            String err = (String)recieveErr();
            throw new GroupDidNotFound(err);
        } catch (IOException | ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
        }
        if(ans == null)
            throw new GroupDidNotFound("Группа не найдена!");
        return ans;
    }

    @Override
    public StudyGroup getMax() {
//        if(!sendCommandWithArgs("getMax",null)) return null;
//        StudyGroup group = null;
//        try {
//            group = (StudyGroup) recieve();
//        } catch (ClassNotFoundException | ClassCastException e) {
//            return null;
//        }
//        return group;
        return null;
    }

    @Override
    public int getSize() {
//        if(!sendCommandWithArgs("getSize",null))return -1;
//        int size = 0;
//        try {
//            size = recieveInt();
//        } catch (IOException e) {
//            return -1;
//        }
//        return size;
        return -1;
    }

    @Override
    public void update(long id, StudyGroup group) throws GroupDidNotFound {
        if(!sendCommandWithArgs("update",new String[]{Long.valueOf(id).toString()}))
            return;
        send(group);
        try {
            String err = (String)recieveErr();
            throw new GroupDidNotFound(err);
        } catch (IOException | ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
        }
    }

    @Override
    public void addIfMax(StudyGroup group) {
        sendCommandWithObject("add_if_max",group);
    }

    @Override
    public String getInfo() {
        if(!sendCommandWithArgs("info",null))
            return null;
        String info = null;
        try {
            info = (String) recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
            return null;
        }
        return info;
    }

    @Override
    public StudyGroup getMaxByStudentsCountGroup() {
        if(!sendCommandWithArgs("max_by_students_count",null))
            return null;
        StudyGroup group = null;
        try {
            group = (StudyGroup) recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
            return null;
        }
        return group;
    }

    @Override
    public Collection<Long> getExpelledStudentsCount() {
        if(!sendCommandWithArgs("print_field_ascending_expelled_students",null))
            return null;
        Collection<Long> ESCounts = null;
        try {
            ESCounts = (Collection<Long>) recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
            return null;
        }
        return ESCounts;
    }

    @Override
    public Collection<String> getUniqueNamesGroupsAdmins() {
        if(!sendCommandWithArgs("print_unique_group_admin",null))
            return null;
        Collection<String> UGAdmins = null;
        try {
            UGAdmins = (Collection<String>)recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
            return null;
        }
        return UGAdmins;
    }

    @Override
    public String showAllGroups() {
        if(!sendCommandWithArgs("show",null))
            return null;
        String info = null;
        try {
            info = (String) recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return info;
    }
}

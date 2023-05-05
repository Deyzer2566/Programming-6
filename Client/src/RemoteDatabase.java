import data.StudyGroup;
import storage.Database;
import storage.GroupDidNotFound;
import storage.ThereIsGroupWithThisIdException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;
import java.util.Collection;

public class RemoteDatabase extends Database {

    private SocketChannel socketChannel;
    private SocketChannel socketChannelErr;

    private boolean isConnected = false;
    private boolean wasNull = false;

//    private ObjectInputStream in;
//    private ObjectOutputStream out;
//    private ObjectInputStream err;

    public RemoteDatabase(String host, int port) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannelErr = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host, port));
        socketChannelErr.connect(new InetSocketAddress(host, port));
        socketChannel.configureBlocking(false);
        socketChannelErr.configureBlocking(false);

//        this.out = new ObjectOutputStream(socketChannel.socket().getOutputStream());
//        this.in = new ObjectInputStream(socketChannel.socket().getInputStream());
//        this.err = new ObjectInputStream(socketChannelErr.socket().getInputStream());

        isConnected = true;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //ByteBuffer b = ByteBuffer.allocate(1024);
        //socketChannel.read(b);
        //socketChannelErr.read(b);

        System.out.println("connected!");
    }

    public void send(Object[] obj) throws IOException{
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buff);
        for (Object o:obj)
            out.writeObject(o);
        out.flush();
        socketChannel.write(ByteBuffer.wrap(buff.toByteArray()));
    }

    public Object recieve() throws ClassNotFoundException {
        ByteBuffer buff = ByteBuffer.allocate(10240);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead = 0;
        try {
            bytesRead = socketChannel.read(buff);
        } catch (IOException e) {
            disconnect();
            return null;
        }
        int totalRead = 0;
        int length = 0;
        while (totalRead < length || length == 0){
            totalRead += bytesRead;
            buff.flip();
            while(buff.hasRemaining())
                baos.write(buff.get());
            if(length == 0){
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
                    length = ois.readInt();
                    ois.close();
                    totalRead -= 6;
                } catch (IOException e) {
                }
            }
            buff.clear();
            try {
                bytesRead = socketChannel.read(buff);
            } catch (IOException e) {
                disconnect();
                return null;
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(bytesRead == -1)
        {
            disconnect();
            return null;
        }
        byte[] objectBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readInt();
            return ois.readObject();
        } catch (IOException e) {
            return null;
        }
    }

    public Object recieveErr() throws ClassNotFoundException {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ByteBuffer buff = ByteBuffer.allocate(10240);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead = 0;
        try {
            bytesRead = socketChannelErr.read(buff);
        } catch (IOException e) {
            disconnect();
            return null;
        }
        int totalRead = 0;
        while (bytesRead > 0 || (totalRead != 0 && totalRead < 5)){
            totalRead += bytesRead;
            buff.flip();
            while(buff.hasRemaining())
                baos.write(buff.get());
            buff.clear();
            try {
                bytesRead = socketChannelErr.read(buff);
            } catch (IOException e) {
                disconnect();
                return null;
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(totalRead == 0)
            return null;
        if(bytesRead == -1)
        {
            disconnect();
        }
        byte[] objectBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public Object recieve() throws ClassNotFoundException {
//        try{
//            return in.readObject();
//        } catch (IOException e){
//            isConnected = false;
//            return null;
//        }
//    }
//
////    public int recieveInt() throws IOException {
////        return in.readInt();
////    }
//
//    public Object recieveErr() throws IOException, ClassNotFoundException {
//        try{
//            return err.readObject();
//        } catch (IOException e){
//            isConnected = false;
//            return null;
//        }
//    }

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
        try {
            send(new Object[]{"add",newGroup});
        } catch (IOException e) {
            disconnect();
        }
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
        try {
            send(new Object[]{"remove "+Long.valueOf(id).toString()});
        } catch (IOException e) {
            disconnect();
            return;
        }
        try {
            String err = (String)recieveErr();
            if(err != null)
                throw new GroupDidNotFound(err);
        } catch (ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
        }
    }

    @Override
    public void clear() {
        try {
            send(new Object[]{"clear"});
        } catch (IOException e) {
            disconnect();
        }
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
        try {
            send(new Object []{"remove_head"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
        try {
            String err = (String)recieveErr();
            if(err != null)
                throw new GroupDidNotFound(err);
        } catch (ClassNotFoundException | ClassCastException e) {
        }
        StudyGroup ans = null;
        try {
            ans = (StudyGroup)recieve();
        } catch (ClassNotFoundException | ClassCastException e) {
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
        try {
            send(new Object[]{"update "+Long.valueOf(id).toString(),group});
        } catch (IOException e) {
            disconnect();
            return;
        }
        try {
            String err = (String)recieveErr();
            if(err != null)
                throw new GroupDidNotFound(err);
        } catch (ClassNotFoundException | ClassCastException | IllegalBlockingModeException e) {
        }
    }

    @Override
    public void addIfMax(StudyGroup group) {
        try {
            send(new Object[]{"add_if_max",group});
        } catch (IOException e) {
            disconnect();
        }
    }

    @Override
    public String getInfo() {
        try {
            send(new Object[]{"info"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
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
        try {
            send(new Object[]{"max_by_students_count"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
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
        try {
            send(new Object[]{"print_field_ascending_expelled_students"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
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
        try {
            send(new Object[]{"print_unique_group_admin"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
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
        try {
            send(new Object[]{"show"});
        } catch (IOException e) {
            disconnect();
            return null;
        }
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

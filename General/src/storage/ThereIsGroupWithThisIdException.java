package storage;

public class ThereIsGroupWithThisIdException extends RuntimeException{
    public ThereIsGroupWithThisIdException(String message){
        super(message);
    }
}

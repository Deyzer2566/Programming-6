package io;

public class MinBiggerThanMaxException extends Error{

    MinBiggerThanMaxException(){
        super("Минимальное значение больше максимального!");
    }
}

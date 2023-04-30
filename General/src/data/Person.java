package data;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int weight; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Person(String name, int weight, Color eyeColor, Color hairColor, Country nationality) {
        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "{"+name+", "+weight+", "+eyeColor+", "+(hairColor==null?"null":hairColor.toString()) +
                ", "+nationality+"}";
//        return "Информация о студенте: " + name + '\n' +
//                "Вес: " + weight + '\n' +
//                "Цвет глаз: " + eyeColor + '\n' +
//                "Цвет волос: " + (hairColor==null?"null":hairColor.toString()) + '\n' +
//                "Национальность: " + nationality;
    }
}
package data;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x; //Значение поля должно быть больше -312, Поле не может быть null
    private Double y; //Максимальное значение поля: 38, Поле не может быть null

    public Coordinates(Float x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Координаты (x,y): "+x.toString()+", "+y.toString();
    }

    public Float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
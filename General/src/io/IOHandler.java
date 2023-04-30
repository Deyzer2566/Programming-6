package io;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import data.*;

public abstract class IOHandler implements Reader, Writer {

    /**
     * Читает строчку
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @return String введенная строка или null, если пользователь не ввел строку и canBeNull==true
     */
	public String readString(String messageToUser, boolean canBeNull){
        String ret=null;
        write(messageToUser);
        while (true) {
            if(!hasNextLine())
                break;
            try {
                ret = readLine();
            } catch (NoSuchElementException e){
                ret = "";
            }
			if(ret.equals("")){
				ret = null;
			}
			if(ret == null && !canBeNull){
				writeError("Ошибка! Введите строку: ");
                continue;
			}
			break;
        }
        return ret;
	}

    /**
     * Читает Double
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @param min минимальное значение для вводимого числа, null если ограничений нет
     * @param max максимальное значение для вводимого числа, null если ограничений нет
     * @return введенное число или null, если пользователь не ввел число и canBeNull==true
     * @throws MinBiggerThanMaxException если min > max
     */
    public Double readDouble(String messageToUser, boolean canBeNull, Double min, Double max) throws MinBiggerThanMaxException {
        if(min==null) {
            min = -Double.MAX_VALUE;
        }
        if(max == null) {
            max = Double.MAX_VALUE;
        }
        if(min > max){
            throw new MinBiggerThanMaxException();
        }
        Double ret=null;
        write(messageToUser);
        while (true) {
            if(!hasNextLine())
                break;
            String str = null;
            try{
                str = readLine();
            } catch (NoSuchElementException e){
                str = "";
            }
            try {
                ret = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                if(str.isEmpty() && canBeNull) {
                    break;
                }
                writeError("Ошибка! Введите число: ");
                continue;
            } catch (NullPointerException e) {
                if(!canBeNull) {
                    writeError("Введите число: ");
                    continue;
                }
            }
            if(ret != null) {
                if (ret < min) {
                    writeError("Число меньше, чем минимально возможное("+min.toString()+")! Введите новое число: ");
                    continue;
                }
                if (ret > max) {
                    writeError("Число больше, чем максимально возможное("+max.toString()+")! Введите новое число: ");
                    continue;
                }
            }
            if(ret != null || canBeNull) {
                break;
            }
        }
        return ret;
    }

    /**
     * Читает Float
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @param min минимальное значение для вводимого числа, null если ограничений нет
     * @param max максимальное значение для вводимого числа, null если ограничений нет
     * @return введенное число или null, если пользователь не ввел число и canBeNull==true
     * @throws MinBiggerThanMaxException если min > max
     */
    public Float readFloat(String messageToUser, boolean canBeNull, Float min, Float max) throws MinBiggerThanMaxException {
        if(min==null) {
            min = -Float.MAX_VALUE;
        }
        if(max == null) {
            max = Float.MAX_VALUE;
        }
        if(min > max){
            throw new MinBiggerThanMaxException();
        }
        Float ret=null;
        write(messageToUser);
        while (true) {
            if(!hasNextLine())
                break;
            String str = null;
            try{
                str = readLine();
            } catch(NoSuchElementException e){
                str = "";
            }
            try {
                ret = Float.parseFloat(str);
            } catch (NumberFormatException e) {
                if(str.isEmpty() && canBeNull) {
                    break;
                }
                writeError("Ошибка! Введите число: ");
                continue;
            } catch (NullPointerException e) {
                if(!canBeNull){
                    writeError("Введите число: ");
                    continue;
                }
            }
            if(ret != null) {
                if (ret < min) {
                    writeError("Число меньше, чем минимально возможное("+min.toString()+")! Введите новое число: ");
                    continue;
                }
                if (ret > max) {
                    writeError("Число больше, чем максимально возможное("+max.toString()+")! Введите новое число: ");
                    continue;
                }
            }
            if(ret != null || canBeNull) {
                break;
            }
        }
        return ret;
    }

    /**
     * Читает Integer
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @param min минимальное значение для вводимого числа, null если ограничений нет
     * @param max максимальное значение для вводимого числа, null если ограничений нет
     * @return введенное число или null, если пользователь не ввел число и canBeNull==true
     * @throws MinBiggerThanMaxException если min > max
     */
    public Integer readInt(String messageToUser, boolean canBeNull, Integer min, Integer max) throws MinBiggerThanMaxException {
        if(min==null) {
            min = -Integer.MAX_VALUE;
        }
        if(max == null) {
            max = Integer.MAX_VALUE;
        }
        if(min > max){
            throw new MinBiggerThanMaxException();
        }
        Integer ret=null;
        write(messageToUser);
        while (true) {
            if(!hasNextLine())
                break;
            String str = null;
            try{
                str = readLine();
            } catch(NoSuchElementException e){
                str = "";
            }
            try {
                ret = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                if(str.isEmpty() && canBeNull) {
                    break;
                }
                writeError("Ошибка! Введите число: ");
                continue;
            } catch (NullPointerException e) {
                if(!canBeNull){
                    writeError("Введите число: ");
                    continue;
                }
            }
            if(ret != null) {
                if (ret < min) {
                    writeError("Число меньше, чем минимально возможное("+min.toString()+")! Введите новое число: ");
                    continue;
                }
                if (ret > max) {
                    writeError("Число больше, чем максимально возможное("+max.toString()+")! Введите новое число: ");
                    continue;
                }
            }
            if(ret != null || canBeNull) {
                break;
            }
        }
        return ret;
    }

    /**
     * Читает Long
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @param min минимальное значение для вводимого числа, null если ограничений нет
     * @param max максимальное значение для вводимого числа, null если ограничений нет
     * @return введенное число или null, если пользователь не ввел число и canBeNull==true
     * @throws MinBiggerThanMaxException если min > max
     */
    public Long readLong(String messageToUser, boolean canBeNull, Long min, Long max) throws MinBiggerThanMaxException {
        if(min==null) {
            min = -Long.MAX_VALUE;
        }
        if(max == null) {
            max = Long.MAX_VALUE;
        }
        if(min > max){
            throw new MinBiggerThanMaxException();
        }
        Long ret=null;
        write(messageToUser);
        while (true) {
            if(!hasNextLine())
                break;
            String str = null;
            try{
                str = readLine();
            } catch(NoSuchElementException e){
                str = "";
            }
            try {
                ret = Long.parseLong(str);
            } catch (NumberFormatException e) {
                if(str.isEmpty() && canBeNull) {
                    break;
                }
                writeError("Ошибка! Введите число: ");
                continue;
            } catch (NullPointerException e) {
                if(!canBeNull){
                    writeError("Введите число: ");
                    continue;
                }
            }
            if(ret != null) {
                if (ret < min) {
                    writeError("Число меньше, чем минимально возможное("+min.toString()+")! Введите новое число: ");
                    continue;
                }
                if (ret > max) {
                    writeError("Число больше, чем максимально возможное("+max.toString()+")! Введите новое число: ");
                    continue;
                }
            }
            if(ret != null || canBeNull) {
                break;
            }
        }
        return ret;
    }

    /**
     * Читает координаты
     * @param canBeNull может ли возвращаемое значение быть null
     * @return введенные координаты или null, если пользователь не ввел координаты и canBeNull==true
     */
	public Coordinates readCoordinates(boolean canBeNull) {
        Float x = null;
        Double y = null;
        x = readFloat("Введите координату X: ", false || canBeNull, Float.valueOf(-312), null);
        if(x == null)
            return null;
        y = readDouble("Введите координату Y: ", false, null, Double.valueOf(38));
        return new Coordinates(x, y);
    }

    /**
     * Читает Enum
     * @param messageToUser сообщение, которое необходимо вывести пользователю перед вводом
     * @param canBeNull может ли возвращаемое значение быть null
     * @param enumClass enum, элемент которого вводит пользователь
     * @return введенное значение или null, если пользователь не ввел строку и canBeNull==true
     */
	public <T extends Enum> Enum readEnum(String messageToUser, boolean canBeNull, Class<T> enumClass){
		String nameOfEnumElement = null;
		Enum ret = null;
		while(true){
			nameOfEnumElement = readString(messageToUser,canBeNull);
			try{
				ret = Enum.valueOf(enumClass, nameOfEnumElement);
			} catch(IllegalArgumentException e) {
                writeError("Такого номера не существует!");
                continue;
            } catch(NullPointerException e){
                if(!canBeNull) {
                    writeError("Ошибка!");
                    continue;
                }
                else{
                    break;
                }
            }
			break;
		}
		return ret;
	}

    /**
     * Читает информацию о человеке
     * @param canBeNull может ли возвращаемое значение быть null
     * @return информацию о человеке или null, если пользователь не ввел информацию о человеке и canBeNull==true
     */
	public Person readPerson(boolean canBeNull){
		String name = readString("Введите имя админа: ",false || canBeNull);
        if(name == null)
            return null;
		
		Integer weight = readInt("Введите вес: ",false,Integer.valueOf(0),null).intValue();
		
		Color eyeColor = (Color)readEnum("Введите цвет глаз(GREEN, RED, BLUE, ORANGE, BROWN): ",
                false, Color.class);
		
		Color hairColor = (Color)readEnum("Введите цвет волос(GREEN, RED, BLUE, ORANGE, BROWN): ",
                true, Color.class);
		
		Country nationality = (Country)readEnum("Введите национальность(UNITED_KINGDOM, SOUTH_KOREA, NORTH_KOREA): ",
                false,Country.class);

		return new Person(name,weight,eyeColor,hairColor,nationality);
	}

    /**
     * Читает информацию о группе
     * @return группу или null, если пользователь не ввел группу и canBeNull==true
     */
	public StudyGroup readStudyGroup(){
		String name = readString("Введите название группы: ",false);
		
		Coordinates coordinates = readCoordinates(false);
		
		Long studentsCount = readLong("Введите количество студентов: ", true, Long.valueOf(0), null);
		
		Long expelledStudents = readLong("Введите количество отчисленных студентов: ", true, Long.valueOf(0), null);
		
		Integer shouldBeExpelled = readInt("Введите количество студентов на ППА2: ", false, Integer.valueOf(0), null);
		
		Semester semesterEnum = (Semester)readEnum("Введите номер семестра(FIRST, THIRD, EIGHTH): ", true, Semester.class);
		
		Person groupAdmin = readPerson(true);
		
		return new StudyGroup((long)0, name,coordinates, ZonedDateTime.now(),studentsCount,expelledStudents,shouldBeExpelled,semesterEnum,groupAdmin);
	}
}

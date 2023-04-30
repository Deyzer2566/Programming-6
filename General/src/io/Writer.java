package io;
public interface Writer {
    /**
     * Выводит строку
     * @param str выводимая строка
     */
    void write(String str);

    /**
     * Выводит строку и переходит на новую
     * @param str выводимая строка
     */
    void writeln(String str);

    /**
     * Выводит сообщение об ошибке
     * @param str выводимое сообщение
     */
    void writeError(String str);

    /**
     * Выводит объект
     * @param obj выводимый объект
     */
    void writeObject(Object obj);
}

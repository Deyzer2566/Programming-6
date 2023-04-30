package io;

import java.util.NoSuchElementException;

public interface Reader {
    /**
     * читает слово
     * @return введенное слово или пустая строка
     */
    String read() throws NoSuchElementException;

    /**
     * читает строку
     * @return введенная строка или пустая строка
     */
    String readLine() throws NoSuchElementException;

    /**
     * проверяет наличие следующего слова
     * @return true, если есть слово, false, если нет
     */
    boolean hasNext();

    /**
     * проверяет наличие следующей строки
     * @return true, если есть строка, false, если нет
     */
    boolean hasNextLine();
}

package data;

import java.io.Serializable;

public enum Semester implements Serializable {
    FIRST{
        @Override
        public String toString() {
            return "первый";
        }
    },
    THIRD{
        @Override
        public String toString() {
            return "третий";
        }
    },
    EIGHTH{
        @Override
        public String toString() {
            return "восьмой";
        }
    };
}
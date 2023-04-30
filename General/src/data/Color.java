package data;

import java.io.Serializable;

public enum Color implements Serializable {
    GREEN{
        @Override
        public String toString() {
            return "зеленый";
        }
    },
    RED{
        @Override
        public String toString() {
            return "красный";
        }
    },
    BLUE{
        @Override
        public String toString() {
            return "синий";
        }
    },
    ORANGE{
        @Override
        public String toString() {
            return "оранжевый";
        }
    },
    BROWN{
        @Override
        public String toString() {
            return "коричневый";
        }
    };
}
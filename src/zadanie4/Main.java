package zadanie4;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<Person> people = new HashSet<>();

        Person p1 = new Person("X", 30);
        people.add(p1);
        System.out.println(people.contains(p1));
        System.out.println(people.contains(new Person("X", 30)));

        //Dzieje się tak ponieważ nie zaimplementowaliśmy metody equals i hashCode w klasie Person, przez co nie jest
        //właściwie dostosowana do aktualnych potrzeb (do porównania obiektów na podstawie ich wartości, w tym przypadku
        // name i age). Domyślnie metoda equals porównuje obiekty na podstawie referencji, a nie ich zawartości.
    }
}

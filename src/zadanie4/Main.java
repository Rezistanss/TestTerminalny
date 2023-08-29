package zadanie4;
//Mamy dany kod:
//class Person { String name, int age, konstruktor, gety, sety, tostring }
//Set<Person> people = new HashSet<>();
//Person p1 = new Person("X", 30);
//people.add(p1);
//i teraz dlaczego:
//people.contains(p1); // zwroci true
//ale juz
//people.contains(new Person("X", 30)); // zwroci false.
//co nalezy zrobic zeby w drugim przypadku tez zwrocilo true?
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

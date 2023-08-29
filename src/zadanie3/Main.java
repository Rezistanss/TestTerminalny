package zadanie3;
//Stworz klase MinMax ktora jako pola klasy pobiera instancje min oraz max. Klasa MinMax powinna byc do pewnego stopnia immutable.
//Nastepnie stworz klase MinMaxService ktora bedzie miala statyczna metode MinMax getMinAndMax(List elements) zadaniem tej metody jest zwracanie mina i maxa
//z listy w tym jednym obiekcie wg naturalnego porządku obiektu,
//klasa MinMax powinna byc generyczna i metody getMin oraz getMax powinny zwraca instancje tego co bedzie na liscie elements,
//czyli jak przekazemy liste stringow to getMax ma zwraca String, getMin tez stringa a jak damy liste osob to ma getMax zwraca osobe, itp itd.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(3, null, 4, 3, 5, 9, 2, 6, 5, 3, 415));
        MinMax<Integer> intMinMax = MinMaxService.getMinMax(numbers);
        System.out.println("Min: " + intMinMax.getMin());
        System.out.println("Max: " + intMinMax.getMax());

        List<String> strings = new ArrayList<>(Arrays.asList("apple", null, "grape", "oraaaaaaaaaaaaaange"));

        MinMax<String> stringMinMax = MinMaxService.getMinMax(strings);
        System.out.println("Min: " + stringMinMax.getMin());
        System.out.println("Max: " + stringMinMax.getMax());

    }
}

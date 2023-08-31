package zadanie2;

import java.io.File;
import java.util.Map;

import static zadanie2.PrimitiveCounter.countPrimitives;

//trzeba napisac metodę Map<String, Integer> countPrimitives(File ... javaFiles)
//Zadaniem metody jest policzenie i zwrocenie raportu w postaci mapy: ile jest zmiennych prymitywnych w kazdym z plikow java.
//np: intow jest 5, doubli jest 3, booleanow sa 2. itp.
//UWAGA: nalezy sie upewnic ze przekazane pliki istnieja i sa plikami java [jesli nie to je pomijamy]
//UWAGA: wyszukiwanie ma sie dziec rownolegle dla wszystkich plikow.
//UWAGA: pomijamy zmienne w literałach oraz komentarzach tj jak mamy:
//String napis = "ala ma kota int x = 5; basia ma psa;
//// to jest komentarz int x = 5
///* to jest
//komentarz
//int x = 5;
//*/
//to takie rzeczy maja byc pomijane nie ma tu zadnego zadeklarowanego prymitywu, (i polecam sobie taki plik dla testow zrobic)

public class Main {
    public static void main(String[] args) {

        //File 1 zawiera inty zakomentowane za pomocą //
        File file1 = new File("src/zadanie2/File1.java");

        //File 2 zawiera inty zakomentowane wewnątrz comment boxa /* */
        //oraz Stringa z wartością "int = 5; boolean = true;";
        File file2 = new File("src/zadanie2/File2.java");

        //File 3 Zawiera liste imion, w którym został podany int
        File file3 = new File("src/zadanie2/File3WithoutPrimitives.java");

        //File 4 - jset to plik tekstowy
        File file4 = new File("src/zadanie2/TestFile");

        Map<String, Integer> primitiveCounts = countPrimitives(file1, file2, file3, file4);
        System.out.println(primitiveCounts);

        // jest użyty thread by każdy plik używał się w jednym momencie oraz jest użyty synchronized by nie naliczało dodatkowo intów
        // wynik został przetestowany pod względem występowania primitywnych zmiennych w cydzysłowie, w pliku textowym lub w komnetarzu czy w
        // comment boxie
        //Wynik: {boolean=2, double=1, int=2, long=1}
    }
}

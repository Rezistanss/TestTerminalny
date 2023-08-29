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
        File file1 = new File("src/zadanie2/File1.java");
        File file2 = new File("src/zadanie2/File2.java");

        Map<String, Integer> primitiveCounts = countPrimitives(file1, file2);
        System.out.println(primitiveCounts);
    }
}

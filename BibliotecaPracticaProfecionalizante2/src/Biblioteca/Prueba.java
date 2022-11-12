package Biblioteca;

import java.util.ArrayList;

public class Prueba {
    public static void main(String[] args) {
        int i=0;
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();
        numerosCorrectos.add(1);
        numerosCorrectos.add(9);
        numerosCorrectos.add(20);

        numerosCorrectos.stream().reduce((acumulador, numero) -> {

            return acumulador + numero;

        });


    }
}

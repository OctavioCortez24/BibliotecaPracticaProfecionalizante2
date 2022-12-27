package Biblioteca;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

public class Prueba {
    public static void main(String[] args) {
       /* int i = 0;
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();
        numerosCorrectos.add(6);
        numerosCorrectos.add(0);
        numerosCorrectos.add(0);

        int numerLibro = 6;

        int resultado2 = numerosCorrectos.stream().reduce(0, (acumulador, numero) -> {
            return acumulador + numero;
        });

        System.out.println(resultado2);

        /*boolean resultado = numerosCorrectos.stream().reduce(false,
                (acumulador, item) -> */;

        //false||(numerLibro==6)

       /* boolean b = false || (numerosCorrectos.get(1) == numerLibro);


        boolean acumulador1 = false;

       for (i = 0; i < numerosCorrectos.size(); i++) {
            acumulador1 = acumulador1 || (numerosCorrectos.get(i) == numerLibro);
        }

        UUID p=UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        String id=String.valueOf(uuid2).substring(0,8);*/
        ArrayList<Integer> numeroCorrectos=new ArrayList<>();


        System.out.println("Ingrese el numero, si desea salir [-1]:");

        int io=sistemaParaElegir(numeroCorrectos);
        System.out.println(io);

    }

    public static int sistemaParaElegir(ArrayList<Integer> numerosCorrectos){
        numerosCorrectos.add(-1);
        System.out.println("--> Si desea salir [-1]");
        Scanner leerNumer = new Scanner(System.in);
        int numeroElegido = 0;
        boolean bandera = false;
        boolean banderaNumeroCorrecto = false;
        do {
            bandera = true;
            try {
                numeroElegido = leerNumer.nextInt();
                //Bucle para comprobar si el numero que elijio es correcto
                for (int i = 0; i < numerosCorrectos.size(); i++) {
                    if (numeroElegido == numerosCorrectos.get(i)) {
                        //Si esta condicion es verdadera significa que el usuario ingreso uno de los numeros
                        // que estan disponibles
                        banderaNumeroCorrecto = true;
                        break;
                    }else if(numeroElegido==-1){
                        banderaNumeroCorrecto=true;
                        break;
                    }
                }
                if (!banderaNumeroCorrecto) {
                    bandera = false;
                    System.out.printf("Numero no encontrado, vuelva a intentarlo:");
                }
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                leerNumer.nextLine();
            }

        } while (!bandera);
        return numeroElegido;
    }


}

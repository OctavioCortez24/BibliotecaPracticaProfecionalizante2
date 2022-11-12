package Biblioteca;

import Biblioteca.Libro;

import java.util.*;

public class Vista {
    //prueba

    public static int menu() {
        Scanner Leer = new Scanner(System.in);
        int eleccion = 0;
        System.out.println("Programa de la biblioteca");
        System.out.println("----------------------------------");
        System.out.println("Seleccione su opción");
        System.out.println("[0]--> Salir");
        System.out.println("[1]--> Añadir un Socio");
        System.out.println("[2]--> Añadir un libro");
        System.out.println("[3]--> Ver socios");
        System.out.println("[4]--> Ver libros");
        System.out.println("[5]--> Dar de baja un Biblioteca.Libro");
        System.out.println("[6]--> Dar de baja un Socio");
        System.out.println("[7]--> Pedir un libro");
        System.out.println("[8]--> Mostrar Pedidos");
        System.out.println("[9]--> Devolver libro");
        System.out.print("Ingrese su opción: ");
        boolean bandera = false;
        do {
            bandera = true;
            try {
                eleccion = Leer.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelva a intentarlo:");
                bandera = false;
                Leer.nextLine();
            }
        } while (!bandera);
        return eleccion;

    }

    public static ArrayList crearLibro() {
        ArrayList<String> array = new ArrayList<>();
        Scanner Leer = new Scanner(System.in);

        System.out.print("Ingrese el nombre del libro: ");
        String nombLib = Leer.nextLine();
        array.add(nombLib);

        System.out.print("Ingrese su Autor: ");
        String autorLib = Leer.nextLine();
        array.add(autorLib);

        System.out.print("Ingrese su categoria:");
        String categoriaLib = Leer.nextLine();
        array.add(categoriaLib);

        return array;

    }

    public static ArrayList crearSocio() {
        ArrayList<String> atributosS = new ArrayList<>();

        Scanner Leer = new Scanner(System.in);

        System.out.println("Igrese su nombre");
        String nombre = Leer.nextLine();
        atributosS.add(nombre);

        System.out.println("Ingrese su apellido:");
        String apellido = Leer.nextLine();
        atributosS.add(apellido);

        System.out.println("Ingrese su DNI:");
        String DNI = Leer.nextLine();
        atributosS.add(DNI);


        return atributosS;


    }

    public static int eleccionSocio(ArrayList<Socio> Socios) {
        int numerSocio = 0;
        Scanner leerNumer = new Scanner(System.in);
        System.out.println("Seleccione el numero del socio:");
        for (int i = 0; i < Socios.size(); i++) {
            System.out.println("[" + Socios.get(i).getSocioID() + "]--> " + Socios.get(i));
        }
        boolean bandera = false;
        do {
            bandera = true;
            try {
                numerSocio = leerNumer.nextInt();
                if (numerSocio == 0) {
                    bandera = false;
                    System.out.println("No puede ingresar el numero 0, vuelva a intentarlo: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                leerNumer.nextLine();
            }
        } while (!bandera);

        return numerSocio;
    }

    public static void mostrarLosSocios(ArrayList<Socio> socios) {
        System.out.println("o---o---o---o---o---o---o---o---o---o---o");
        for (int i = 0; i < socios.size(); i++) {
            System.out.println(socios.get(i));
        }
        System.out.println("o---o---o---o---o---o---o---o---o---o---o");
    }

    public static void mostrarLosLibros(ArrayList<Libro> libros) {

        for (int i = 0; i < libros.size(); i++) {
            System.out.println("o---o---o---o---o---o---o---o---o---o---o");
            System.out.println(libros.get(i));
            System.out.println("o---o---o---o---o---o---o---o---o---o---o");
        }

    }

    public static void mostrarLosPedidos(ArrayList<Pedido> pedidos) {
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.println("o---o---o---o---o---o---o---o---o---o---o");
            System.out.println(pedidos.get(i));
            System.out.println("o---o---o---o---o---o---o---o---o---o---o");
        }
    }

    public static void validacionDeDatos(String resultado) {
        System.out.println(resultado);
    }

    public static int eleccionLibrosDisponibles(ArrayList<Libro> Libros) {
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elejir el usuario
        boolean bandera = false;
        Scanner leerNumer = new Scanner(System.in);
        System.out.println("Seleccione el numero del Biblioteca.Libro:");
        for (int i = 0; i < Libros.size(); i++) {
            if (Libros.get(i).isDisponibilidad() & !Libros.get(i).isDesactivado()) {
                System.out.println("[" + Libros.get(i).getLibroID() + "]--> " + Libros.get(i).getTituloDeLib());
                numerosCorrectos.add(Libros.get(i).getLibroID());//Añado los numeros que el usuario tiene para elejir
            }
        }
        int numerLibro = 0;
        boolean banderaNumeroCorrecto=false;
        do {
            bandera = true;
            try {
                numerLibro = leerNumer.nextInt();
               /* numerosCorrectos.stream().reduce((acumulador, numero)->{

                    return acumulador+numero;
                });*/
                //Bucle para comprobar si el numero que elijio es correcto
                for (int i = 0; i < numerosCorrectos.size(); i++) {
                    System.out.println(numerosCorrectos.get(i));
                    if (numerLibro == numerosCorrectos.get(i)) {
                        //Si esta condicion es verdadera significa que el usuario ingreso uno de los numeros
                        // que estan disponibles
                        banderaNumeroCorrecto =  true;
                        break;
                    }
                }
                if (!banderaNumeroCorrecto){
                    bandera=false;
                    System.out.printf("Numero no encontrado, vuelva a intentarlo:");
                }
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                leerNumer.nextLine();
            }
        } while (!bandera);
        return numerLibro;
    }


    public static int eleccionPedidoParaDevolver(ArrayList<Pedido> pedidos, ArrayList<Libro>libros) {
        boolean bandera = false;
        Scanner leerNumer = new Scanner(System.in);
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elejir el usuario
        System.out.println("Seleccione el numero del Biblioteca.Libro:");
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getFecha_ReintegroLibro()==null) {
                Libro l=libros.get(pedidos.get(i).getLibroID());//Obtengo el libro que se pidio mediante la idLibro que tiene asignado el Biblioteca.Pedido i
                System.out.println("[" + i + "]--> " + l.getTituloDeLib());//Muestro el titulo del libro
                numerosCorrectos.add(i);
            }
        }
        int numerPedido = 0;
        boolean banderaNumeroCorrecto=false;
        do {
            bandera = true;
            try {
                numerPedido = leerNumer.nextInt();
                //Bucle para comprobar si el numero que elijio es correcto
                for (int i = 0; i < numerosCorrectos.size(); i++) {
                    if (numerPedido == numerosCorrectos.get(i)) {
                        //Si esta condicion es verdadera significa que el usuario ingreso uno de los numeros
                        // que estan disponibles
                        banderaNumeroCorrecto =  true;
                        break;
                    }
                }
                if (!banderaNumeroCorrecto){
                    bandera=false;
                    System.out.printf("Numero no encontrado, vuelva a intentarlo:");
                }
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                leerNumer.nextLine();
            }

        } while (!bandera);
        return numerPedido;
    }

    public static int eleccionSocioNoDesactivados(ArrayList<Socio> Socios) {
        ArrayList<Integer> numerosCorrectos=new ArrayList<>();
        int numerSocio = 0;
        Scanner leerNumer = new Scanner(System.in);
        System.out.println("Seleccione el numero del socio:");
        for (int i = 0; i < Socios.size(); i++) {
            if (Socios.get(i).isDesactivado()){
                System.out.println("[" + Socios.get(i).getSocioID() + "]--> " + Socios.get(i));
                numerosCorrectos.add(Socios.get(i).getSocioID());
            }
        }
        boolean bandera = false;
        boolean banderaNumeroCorrecto=false;
        do {
            bandera = true;
            try {

                numerSocio = leerNumer.nextInt();
                //Bucle para comprobar si el numero que elijio es correcto
                for (int i = 0; i < numerosCorrectos.size(); i++) {
                    if (numerSocio == numerosCorrectos.get(i)) {
                        //Si esta condicion es verdadera significa que el usuario ingreso uno de los numeros
                        // que estan disponibles
                        banderaNumeroCorrecto =  true;
                        break;
                    }
                }
                if (!banderaNumeroCorrecto){
                    bandera=false;
                    System.out.printf("Numero no encontrado, vuelva a intentarlo:");
                }
                numerSocio = numerSocio - 1;
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                leerNumer.nextLine();
            }
        } while (!bandera);


        return numerSocio;
    }

    public static int elegirLibrosNoDesactivados(ArrayList<Libro> libros) {
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elejir el usuario
        Scanner LeerNumer = new Scanner(System.in);
        int elecion = 0;
        for (int i = 0; i < libros.size(); i++) {
            if (!libros.get(i).isDesactivado()) {
                System.out.println("[" + libros.get(i).getLibroID() + "]--> " + libros.get(i).getTituloDeLib());
                numerosCorrectos.add(libros.get(i).getLibroID());//Añado el numero del libro que si se puede elejir
            }
        }
        boolean bandera = false;
        boolean banderaNumeroCorrecto=false;
        do {
            bandera = true;
            try {
                elecion = LeerNumer.nextInt();
                //Bucle para comprobar si el numero que elijio es correcto
                for (int i = 0; i < numerosCorrectos.size(); i++) {
                    if (elecion == numerosCorrectos.get(i)) {
                        //Si esta condicion es verdadera significa que el usuario ingreso uno de los numeros
                        // que estan disponibles
                        banderaNumeroCorrecto =  true;
                        break;
                    }
                }
                if (!banderaNumeroCorrecto){
                    bandera=false;
                    System.out.printf("Numero no encontrado, vuelva a intentarlo:");
                }
                elecion = elecion - 1;
            } catch (InputMismatchException e) {
                System.out.println("No debes ingresar letras o simbolos, vuelve a intentarlo:");
                bandera = false;
                LeerNumer.nextLine();
            }
        } while (!bandera);


        return elecion;
    }


}

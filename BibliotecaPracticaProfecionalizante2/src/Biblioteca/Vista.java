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
        System.out.println("[5]--> Dar de baja un Libro");
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

        System.out.print("Ingrese el titulo del libro: ");
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

        System.out.println("Igrese su nombre:");
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

    public static String eleccionSocio(ArrayList<Socio> socios) {
        ArrayList<Integer>numeroCorrectos=new ArrayList<>();
        int numerSocio = 0;
        Scanner leerNumer = new Scanner(System.in);
        System.out.println("Seleccione el numero del socio:");
        for (int i = 0; i < socios.size(); i++) {
            if (!socios.get(i).isDesactivado()) {
                System.out.println("[" + i + "]--> " + socios.get(i));
                numeroCorrectos.add(i);
            }
        }
        boolean bandera = false;
        numerSocio=sistemaParaElegir(numeroCorrectos);

        if (numerSocio!=-1){
            return socios.get(numerSocio).getSocioID();
        }else{
            return "exit";
        }
    }

    public static void mostrarLosSocios(ArrayList<Socio> socios) {
        System.out.println("o---o---o---o---o---o---o---o---o---o---o");
        for (int i = 0; i < socios.size(); i++) {
            String estado=socios.get(i).isDesactivado()?"Desactivado":"No desactivado";
            System.out.println(socios.get(i)+" "+estado);
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

    public static String eleccionLibrosDisponibles(ArrayList<Libro> Libros) {
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elejir el usuario
        System.out.println("Seleccione el numero del Libro:");
        for (int i = 0; i < Libros.size(); i++) {
            if (Libros.get(i).isDisponibilidad() & !Libros.get(i).isDesactivado()) {
                System.out.println("[" + i + "]--> " + Libros.get(i).getTituloDeLib());
                numerosCorrectos.add(i);//Añado los numeros que el usuario tiene para elejir
            }
        }
        int numerLibro = 0;
        numerLibro = sistemaParaElegir(numerosCorrectos);

        if (numerLibro!=-1) {
            return Libros.get(numerLibro).getLibroID();
        }else {
            return "exit";
        }
    }


    public static int eleccionPedidoParaDevolver(ArrayList<Pedido> pedidos, ArrayList<Libro> libros) {
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elejir el usuario
        System.out.println("Seleccione el numero del Libro:");
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getFecha_ReintegroLibro() == null) {
                String libroID=pedidos.get(i).getLibroID();

                Optional<Libro> l = libros.stream().filter(libro ->libro.getLibroID().equals(libroID)).findFirst();

                //Obtengo el libro que se pidio mediante la idLibro que tiene asignado el Biblioteca.Pedido i
                System.out.println("[" + i + "]--> " + l.get().getTituloDeLib());//Muestro el titulo del libro
                numerosCorrectos.add(i);
            }
        }
        int numerPedido = 0;
        numerPedido=sistemaParaElegir(numerosCorrectos);
        return numerPedido;
    }

    public static int eleccionSocioNoDesactivados(ArrayList<Socio> Socios) {
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();
        System.out.println("Seleccione el numero del socio:");
        for (int i = 0; i < Socios.size(); i++) {
            if (!Socios.get(i).isDesactivado()) {
                System.out.println("[" + i + "]--> " + Socios.get(i));
                numerosCorrectos.add(i);
            }
        }
        int numerSocio = 0;
        numerSocio=sistemaParaElegir(numerosCorrectos);
        return numerSocio;
    }

    public static int elegirLibrosNoDesactivados(ArrayList<Libro> libros) {
        System.out.println("Dar de baja un libro");
        ArrayList<Integer> numerosCorrectos = new ArrayList<>();// Este array sirve para coprobar si el usuario elijio correctamente
        //El array numerosCorrectos son los numeros que deberia elegir el usuario


        for (int i = 0; i < libros.size(); i++) {
            if (!libros.get(i).isDesactivado()) {
                System.out.println("[" + i + "]--> " + libros.get(i).getTituloDeLib());
                numerosCorrectos.add(i);//Añado el numero del libro que si se puede elejir
            }
        }
        int elecion = 0;
        elecion=sistemaParaElegir(numerosCorrectos);

        return elecion;
    }


    public static int sistemaParaElegir(ArrayList<Integer> numerosCorrectos){
        numerosCorrectos.add(-1);
        Scanner leerNumer = new Scanner(System.in);
        int numeroElegido = 0;
        boolean bandera = false;
        boolean banderaNumeroCorrecto = false;
        System.out.println("Ingrese el numero, si desea salir [-1]:");
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

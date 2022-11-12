package Biblioteca;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Modelo {

    public static void anadirLibro(Libro l) {
        ArrayList<String> Libros = new ArrayList<>();
        Libros.addAll(GestorArchivos.cargarArray("ArrayLibros.txt"));
        Libros.add(l.toString("%"));
        //Guardo el array de String con el metodo guardarArray del Gestor de Archivos
        GestorArchivos.guardarArray(Libros, "ArrayLibros.txt");
    }

    public static void anadirSocio(Socio s) {
        //Para guardar en txt
        ArrayList<String> Socios = new ArrayList<>();
        Socios.addAll(GestorArchivos.cargarArray("ArraySocios.txt"));
        Socios.add(s.toString("%"));
        GestorArchivos.guardarArray(Socios, "ArraySocios.txt");
        //------
    }

    public static void crearPedido(Pedido p) {
        ArrayList<String> Pedidos = new ArrayList<>();
        Pedidos.addAll(GestorArchivos.cargarArray("ArrayPedidos.txt"));
        Pedidos.add(p.toString("%"));
        GestorArchivos.guardarArray(Pedidos, "ArrayPedidos.txt");
    }

    public static void darDeBajaUnLibro(Libro l) {
        ArrayList<String> LibrosStr = GestorArchivos.cargarArray("ArrayLibros.txt");//Array de String del txt
        l.setDesactivado(true);
        int posicionAr = l.getLibroID() - 1;
        if (posicionAr != (-1)) {
            LibrosStr.set(posicionAr, l.toString("%"));//Actualizo el valor en el array guardado en txt
        }

        GestorArchivos.guardarArray(LibrosStr, "ArrayLibros.txt");
    }

    public static void darDeBajaUnSocio(Socio s) {
        ArrayList<String> SociosStr = GestorArchivos.cargarArray("ArraySocios.txt");
        s.setDesactivado(true);
        int posicionAr = s.getSocioID() - 1;
        SociosStr.set(posicionAr, s.toString("%"));
        GestorArchivos.guardarArray(SociosStr, "ArraySocios.txt");
    }

    public static void devolverLibro(Pedido p) {
        //Actualizo el atributo fecha_ReintegroLibro en el txt
       /* ArrayList<String>pedidosStr=GestorArchivos.cargarArray("ArrayPedidos.txt");//Cargo los pedidos que se encuentran en txt
        for (int i=0;i<pedidosStr.size();i++){
            if (pedidosStr.get(i).equals(p.toString("%"))){
                pedidosStr.set(i,p.toString("%"));
            }
        }

        //Actualizo el estado del libro en el txt
        ArrayList<String>librosStr=GestorArchivos.cargarArray("ArrayLibros.txt");//Cargo el txt de los libros
        Biblioteca.Libro l=Modelo.cargarLibros().get(p.getLibroID());//Obtengo el libro mediante el atributo libroID del objeto Biblioteca.Pedido.
        l.setDisponibilidad(true);//Le asigno el nuevo valor a la disponibilidad del libro
        int posicionAr=l.getLibroID()-1;//Obtengo la posicion en la cual se encuentra en el array librosStr
        if(posicionAr!=(-1)){
            librosStr.set(posicionAr, l.toString("%"));//Actualizo el valor en el array guardado en txt
        }

        GestorArchivos.guardarArray(librosStr, "ArrayLibros.txt");//Guardo
        */
        System.out.println("No esta terminado");

    }

    public static ArrayList<Libro> cargarLibros() {
        ArrayList<Libro> Libros = new ArrayList<>();

        try {
            PreparedStatement pSSelect = Conexion.getInstance().prepareStatement("SELECT * FROM libros");
            ResultSet rs = pSSelect.executeQuery();
            while (rs.next()) {

                int id = rs.getInt(1);
                String titulo = rs.getString("tituloLibro");
                String autor = rs.getString("autorLibro");
                String categoria = rs.getString("categoriaLibro");
                boolean disponibilidad = rs.getBoolean(5);
                boolean desactivado = rs.getBoolean(6);

                Libro l = new Libro(id, titulo, autor, categoria, disponibilidad, desactivado);
                Libros.add(l);
            }
            pSSelect.close();

        } catch (SQLException e) {

            ArrayList<String> LibrosCargados = GestorArchivos.cargarArray("ArrayLibros.txt");//Cargo los libros del txt
            String vector[];
            for (int i = 0; i < LibrosCargados.size(); i++) {
                vector = LibrosCargados.get(i).split("%");
                int id = Integer.parseInt(vector[0]);
                String titulo = vector[1];
                String autor = vector[2];
                String categoria = vector[3];
                boolean disponibilidad = Boolean.parseBoolean(vector[4]);
                boolean desactivado = Boolean.parseBoolean(vector[5]);
                Libro l = new Libro(id, titulo, autor, categoria, disponibilidad, desactivado);
                Libros.add(l);
            }

        }
        return Libros;
    }

    public static ArrayList<Socio> cargarSocios() {

        ArrayList<Socio> Socios = new ArrayList<>();

        try {
            PreparedStatement pSSelect = Conexion.getInstance().prepareStatement("SELECT * FROM socios");
            ResultSet rS = pSSelect.executeQuery();

            while (rS.next()) {

                int id = rS.getInt("socioID");
                String nombre = rS.getString("nombreSocio");
                String apellido = rS.getString("apellidoSocio");
                int dNI = rS.getInt("dniSocio");
                boolean desactivado = rS.getBoolean("desactivado");

                Socio s = new Socio(id, nombre, apellido, dNI, desactivado);
                Socios.add(s);
            }
            pSSelect.close();

        } catch (SQLException e) {
            ArrayList<String> SociosTxt = GestorArchivos.cargarArray("ArraySocios.txt");//Cargo los socios del txt
            String vector[];
            for (int i = 0; i < SociosTxt.size(); i++) {
                vector = SociosTxt.get(i).split("%");
                        /*
            A continuacion lo que hago es ceparar el String cada ves que enuente el caracter %
            significa que debe separar el String. Ahora ese String que se dividio en varias partes
            lo guardo en un vector, cada posicion de el vector almacena un atributo de el socio
            en la posicion 0 se guarda la ID, en la 1 el nombre, la 2 el apellido, 3 el DNI y la 4 la disponibilidad
             */
                int id = Integer.parseInt(vector[0]);
                String nombre = vector[1];
                String apellido = vector[2];
                int dNI = Integer.parseInt(vector[3]);
                boolean desactivado = Boolean.parseBoolean(vector[4]);
                Socio s = new Socio(id, nombre, apellido, dNI, desactivado);
                Socios.add(s);
            }
        }

        return Socios;
    }

    public static ArrayList<Pedido> cargarPedidos() {

        ArrayList<Pedido> Pedidos = new ArrayList<>();

        try {
            //Probar esto pporque no se si funciona
            PreparedStatement pSSelect = Conexion.getInstance().prepareStatement("SELECT * FROM pedidos");
            ResultSet rs = pSSelect.executeQuery();
            LocalDate fechaReaintegro;
            while (rs.next()) {
                LocalDate fechaPrestamo=LocalDate.parse(rs.getString("fechaPrestamo"));
                LocalDate fechaDevolverPedido=LocalDate.parse(rs.getString("fechaDevolverPedido"));
                int libroPedidoID= rs.getInt("libroPedidoID");
                int socioPedidoID= rs.getInt("socioPedidoID");

                try {
                    fechaReaintegro = LocalDate.parse(rs.getString("fechaReintegroLibro"));
                } catch (DateTimeParseException f) {
                    fechaReaintegro = null;
                }

                Pedido p=new Pedido(fechaPrestamo,fechaDevolverPedido,libroPedidoID,socioPedidoID,fechaReaintegro);

                Pedidos.add(p);
            }

            pSSelect.close();

        } catch (SQLException e) {
            ArrayList<String> SociosString = GestorArchivos.cargarArray("ArrayPedidos.txt");
            String vector[];
            LocalDate fechaReaintegro;

            for (int i = 0; i < SociosString.size(); i++) {
                vector = SociosString.get(i).split("%");//Cadena de texto que contiene los atributos del Biblioteca.Pedido concatenados con un %
                LocalDate fechaPrestamo=LocalDate.parse(vector[0]);
                LocalDate fechaDevolverPedido=LocalDate.parse(vector[1]);
                int libroPedidoID= Integer.parseInt(vector[2]);
                int socioPedidoID= Integer.parseInt(vector[3]);

                try {
                    fechaReaintegro = LocalDate.parse(vector[4]);
                } catch (DateTimeParseException f) {
                    fechaReaintegro = null;
                }
                Pedido p=new Pedido(fechaPrestamo,fechaDevolverPedido,libroPedidoID,socioPedidoID,fechaReaintegro);
                Pedidos.add(p);
            }
        }

        return Pedidos;

    }
}
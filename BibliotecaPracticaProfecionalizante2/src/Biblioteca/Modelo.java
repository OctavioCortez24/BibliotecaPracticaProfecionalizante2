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

        try {
            Libros.addAll(GestorArchivos.cargarArray("ArrayLibros.txt"));
        } catch (NullPointerException e) {

        }
        Libros.add(l.toString("%"));
        //Guardo el array de String con el metodo guardarArray del Gestor de Archivos
        GestorArchivos.guardarArray(Libros, "ArrayLibros.txt");
    }

    public static void anadirSocio(Socio s) {
        //Para guardar en txt
        ArrayList<String> Socios = new ArrayList<>();

        try {
            Socios.addAll(GestorArchivos.cargarArray("ArraySocios.txt"));
        } catch (NullPointerException e) {

        }
        Socios.add(s.toString("%"));
        GestorArchivos.guardarArray(Socios, "ArraySocios.txt");
        //------
    }

    public static void crearPedido(Pedido p) {
        ArrayList<String> Pedidos = new ArrayList<>();
        ArrayList<Libro> libros=cargarLibros();//Array de libros pero Objeto
        ArrayList<String>librosString=new ArrayList<>();//Array de libros pero String
        try{
            Pedidos.addAll(GestorArchivos.cargarArray("ArrayPedidos.txt"));
        }catch (NullPointerException e){

        }
        Pedidos.add(p.toString("%"));
        GestorArchivos.guardarArray(Pedidos, "ArrayPedidos.txt");

        for (int i=0;i<libros.size();i++){
            if (libros.get(i).getLibroID().equals(p.getLibroID())){
                libros.get(i).setDisponibilidad(false);
            }
            librosString.add(libros.get(i).toString("%"));
        }


        GestorArchivos.guardarArray(librosString, "ArrayLibros.txt");
    }

    public static void darDeBajaUnLibro(Libro l) {
        ArrayList<String> librosStr = GestorArchivos.cargarArray("ArrayLibros.txt");//Array de String del txt
        l.setDesactivado(false);
        int posicionAr = 0;//Obtengo la posicion en la cual se encuentra en el array librosStr
        for (int i = 0; i < librosStr.size(); i++) {
            if (librosStr.get(i).equals(l.toString("%"))) {
                l.setDesactivado(true);
                posicionAr = i;
            }
        }
        librosStr.set(posicionAr, l.toString("%"));//Actualizo el valor en el array guardado en txt
        GestorArchivos.guardarArray(librosStr, "ArrayLibros.txt");
    }

    public static void darDeBajaUnSocio(Socio s) {
        ArrayList<String> SociosStr = GestorArchivos.cargarArray("ArraySocios.txt");
        s.setDesactivado(false);
        int posicionAr = 0;
        for (int i = 0; i < SociosStr.size(); i++) {
            if (SociosStr.get(i).equals(s.toString("%"))) {
                s.setDesactivado(true);
                posicionAr = i;
            }
        }
        SociosStr.set(posicionAr, s.toString("%"));
        GestorArchivos.guardarArray(SociosStr, "ArraySocios.txt");
    }

    public static void devolverLibro(Pedido p) {
        //Actualizo el atributo fecha_ReintegroLibro en el txt
        ArrayList<String> pedidosStr = GestorArchivos.cargarArray("ArrayPedidos.txt");//Cargo los pedidos que se encuentran en txt
        ArrayList<Libro> libros=cargarLibros();//Array de libros pero Objeto
        ArrayList<String>librosString=new ArrayList<>();//Array de libros pero String
        for (int i = 0; i < pedidosStr.size(); i++) {
            if (pedidosStr.get(i).equals(p.toString("%"))) {

                p.setFecha_ReintegroLibro(LocalDate.now());
                pedidosStr.set(i, p.toString("%"));
            }
        }
        GestorArchivos.guardarArray(pedidosStr, "ArrayPedidos.txt");

        //Actualizo el estado del libro en el txt
        for (int i=0;i<libros.size();i++){
            if (libros.get(i).getLibroID().equals(p.getLibroID())){
                libros.get(i).setDisponibilidad(true);
            }
            librosString.add(libros.get(i).toString("%"));
        }


        GestorArchivos.guardarArray(librosString, "ArrayLibros.txt");

    }

    public static ArrayList<Libro> cargarLibros() {
        ArrayList<Libro> Libros = new ArrayList<>();

        try {
            PreparedStatement pSSelect = Conexion.getInstance().prepareStatement("SELECT * FROM libros");
            ResultSet rs = pSSelect.executeQuery();
            while (rs.next()) {

                String id = rs.getString(1);
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

            try {
                ArrayList<String> LibrosCargados = GestorArchivos.cargarArray("ArrayLibros.txt");//Cargo los libros del txt
                String vector[];
                for (int i = 0; i < LibrosCargados.size(); i++) {
                    vector = LibrosCargados.get(i).split("%");
                    String id = vector[0];
                    String titulo = vector[1];
                    String autor = vector[2];
                    String categoria = vector[3];
                    boolean disponibilidad = Boolean.parseBoolean(vector[4]);
                    boolean desactivado = Boolean.parseBoolean(vector[5]);
                    Libro l = new Libro(id, titulo, autor, categoria, disponibilidad, desactivado);
                    Libros.add(l);
                }
            } catch (NullPointerException f) {

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

                String id = rS.getString("socioID");
                String nombre = rS.getString("nombreSocio");
                String apellido = rS.getString("apellidoSocio");
                int dNI = rS.getInt("dniSocio");
                boolean desactivado = rS.getBoolean("desactivado");

                Socio s = new Socio(id, nombre, apellido, dNI, desactivado);
                Socios.add(s);
            }
            pSSelect.close();

        } catch (SQLException e) {

            try {
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
                    String id = (vector[0]);
                    String nombre = vector[1];
                    String apellido = vector[2];
                    int dNI = Integer.parseInt(vector[3]);
                    boolean desactivado = Boolean.parseBoolean(vector[4]);
                    Socio s = new Socio(id, nombre, apellido, dNI, desactivado);
                    Socios.add(s);
                }
            } catch (NullPointerException f) {

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
                String idPedido = rs.getString("pedidosID");
                LocalDate fechaPrestamo = LocalDate.parse(rs.getString("fechaPrestamo"));
                LocalDate fechaDevolverPedido = LocalDate.parse(rs.getString("fechaDevolverPedido"));
                String libroPedidoID = rs.getString("libroPedidoID");
                String socioPedidoID = rs.getString("socioPedidoID");

                try {
                    fechaReaintegro = LocalDate.parse(rs.getString("fechaReintegroLibro"));

                } catch (NullPointerException f) {

                    fechaReaintegro = null;
                }


                Pedido p = new Pedido(idPedido, fechaPrestamo, fechaDevolverPedido, libroPedidoID, socioPedidoID, fechaReaintegro);

                Pedidos.add(p);
            }

            pSSelect.close();

        } catch (SQLException e) {
            ArrayList<String> SociosString = GestorArchivos.cargarArray("ArrayPedidos.txt");
            String[] vector;
            LocalDate fechaReaintegro;

            for (String s : SociosString) {
                vector = s.split("%");//Cadena de texto que contiene los atributos del Biblioteca.Pedido concatenados con un %
                String idPedido = vector[0];
                LocalDate fechaPrestamo = LocalDate.parse(vector[1]);
                LocalDate fechaDevolverPedido = LocalDate.parse(vector[2]);
                String libroPedidoID = vector[3];
                String socioPedidoID = vector[4];

                try {
                    fechaReaintegro = LocalDate.parse(vector[5]);
                } catch (DateTimeParseException f) {
                    fechaReaintegro = null;
                }
                Pedido p = new Pedido(idPedido, fechaPrestamo, fechaDevolverPedido, libroPedidoID, socioPedidoID, fechaReaintegro);
                Pedidos.add(p);
            }
        }

        return Pedidos;

    }


}
package Biblioteca;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Libro {
    private String tituloDeLib;
    private boolean disponibilidad;
    private String nombreDelAutor;
    private String categoria;
    private String libroID;
    private boolean desactivado;


    public Libro() {

    }

    public Libro(String idLibro, String nombre, String nombreDelAutor, String categoria, boolean disponibilidad, boolean desactivado) {
        this.libroID = idLibro;
        this.tituloDeLib = nombre;
        this.nombreDelAutor = nombreDelAutor;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
        this.desactivado = desactivado;
    }
    public Libro(String nombre, String nombreDelAutor, String categoria) {
        UUID uuid2 = UUID.randomUUID();
        this.libroID = String.valueOf(uuid2).substring(0, 8);
        this.tituloDeLib = nombre;
        this.nombreDelAutor = nombreDelAutor;
        this.categoria = categoria;
        this.disponibilidad = true;
        this.desactivado = false;
    }

    public String getTituloDeLib() {
        return tituloDeLib;
    }

    public void setTituloDeLib(String tituloDeLib) {
        this.tituloDeLib = tituloDeLib;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getNombreDelAutor() {
        return nombreDelAutor;
    }

    public void setNombreDelAutor(String nombreDelAutor) {
        this.nombreDelAutor = nombreDelAutor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLibroID() {
        return libroID;
    }

    public void setLibroID(String libroID) {
        this.libroID = libroID;
    }

    public boolean isDesactivado() {
        return desactivado;
    }

    public void setDesactivado(boolean desactivado) {
        this.desactivado = desactivado;
    }

    @Override
    public String toString() {
        String dispo = disponibilidad ? "Disponible" : "No Disponible";
        String desac = desactivado ? "Si" : "No";

        return "Titulo: " + tituloDeLib + "\n"
                + "Autor: " + nombreDelAutor + "\n"
                + "Categoria: " + categoria + "\n"
                + "Disponibilidad: " + dispo + "\n" +
                "Dado de baja: " + desac;
    }

    public String toString(String ceparador) {
        return libroID + ceparador + tituloDeLib + ceparador + nombreDelAutor + ceparador + categoria + ceparador + disponibilidad + ceparador + desactivado;
    }

    @Override
    public boolean equals(Object obj) {
        Libro l = (Libro) obj;

        return tituloDeLib.equals(l.tituloDeLib) & nombreDelAutor.equals(l.nombreDelAutor) & categoria.equals(l.categoria);
    }

    public void añadirLibro() {
        try {
            PreparedStatement pSInsert = Conexion.getInstance().prepareStatement("INSERT INTO libros VALUES(?,?,?,?,?,?)");
            pSInsert.setString(1, libroID);
            pSInsert.setString(2, tituloDeLib);
            pSInsert.setString(3, nombreDelAutor);
            pSInsert.setString(4, categoria);
            pSInsert.setBoolean(5, disponibilidad);
            pSInsert.setBoolean(6, desactivado);
            pSInsert.executeUpdate();

            pSInsert.close();

        } catch (SQLException e) {
           Modelo.anadirLibro(this);

        }
    }

    public void darDeBaja() {

        try {
            PreparedStatement psUdate = Conexion.getInstance().prepareStatement(
                    "  UPDATE libros SET isDesactivado='" + 1 + "'WHERE libroID='" + libroID+"'");
            psUdate.executeUpdate();

            psUdate.close();

        } catch (SQLException e) {
            Modelo.darDeBajaUnLibro(this);// Dar de baja en el TXT
        }

    }

    public boolean validacion() {
        ArrayList<Libro> LibrosC = Modelo.cargarLibros();
        boolean retorno = false;
        for (int i = 0; i < LibrosC.size(); i++) {
            if (equals(LibrosC.get(i))) {
                retorno = true;
            }
        }
        return retorno;
    }
}

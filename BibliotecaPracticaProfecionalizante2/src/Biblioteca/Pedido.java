package Biblioteca;

import Biblioteca.Conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class Pedido {
    private LocalDate fecha_Prestamo;
    private LocalDate fecha_Devolver;
    private LocalDate fecha_ReintegroLibro;
    private int libroID;
    private int socioID;

    public Pedido() {
    }

    //Colocar el LocalDate fecha_ReintegroLibro en el constructor
    public Pedido(LocalDate prestamo, LocalDate fecha_Devolver, int libroID, int socioID, LocalDate fecha_ReintegroLibro) {
        this.fecha_Prestamo = prestamo;
        this.fecha_Devolver = fecha_Devolver;
        this.libroID = libroID;
        this.socioID = socioID;
        this.fecha_ReintegroLibro = fecha_ReintegroLibro;
    }

    //Getters and Setters

    public LocalDate getFecha_ReintegroLibro() {
        return fecha_ReintegroLibro;
    }

    public void setFecha_ReintegroLibro(LocalDate fecha_ReintegroLibro) {
        this.fecha_ReintegroLibro = fecha_ReintegroLibro;
    }

    public int getLibroID() {
        return libroID;
    }

    public void setLibroID(int libroID) {
        this.libroID = libroID;
    }

    public int getSocioID() {
        return socioID;
    }

    public void setSocioID(int socioID) {
        this.socioID = socioID;
    }

    public LocalDate getFecha_Prestamo() {
        return fecha_Prestamo;
    }

    public void setFecha_Prestamo(LocalDate fecha_Prestamo) {
        this.fecha_Prestamo = fecha_Prestamo;
    }

    public LocalDate getFecha_Devolver() {
        return fecha_Devolver;
    }

    public void setFecha_Devolver(LocalDate fecha_Devolver) {
        this.fecha_Devolver = fecha_Devolver;
    }

    //--------------------------------------------------------------------------------------------------
    public void devolverLibro() {
        this.fecha_ReintegroLibro = LocalDate.now();
        try {
            //Actualizo la fecha de reitegro de null a la fecha en la cual se ejecute este metodo
            PreparedStatement psUpdatePedido = Conexion.getInstance().prepareStatement(
                    "UPDATE pedidos SET fechaReintegroLibro='"+fecha_ReintegroLibro+"' " +
                            "WHERE libroPedidoID='"+libroID+"' AND socioPedidoID="+socioID);

            psUpdatePedido.executeUpdate();
            psUpdatePedido.close();
            //Actualizo el estado del libro de ocupado a disponible, el 1 significa disponible
            PreparedStatement psUpdateLibro = Conexion.getInstance().prepareStatement(
                    "UPDATE libros SET fechaReintegroLibro=" + 1 + "WHERE libroID=" + libroID);
            psUpdateLibro.executeUpdate();
            psUpdateLibro.close();
        } catch (SQLException e) {

            Modelo.devolverLibro(this);

        }

    }

    public void anadirPedido() {

        try {
            PreparedStatement pSInsert = Conexion.getInstance().prepareStatement("INSERT INTO pedidos VALUES(?,?,?,?,?,?)");
            pSInsert.setString(1, null);
            pSInsert.setString(2, fecha_Prestamo + "");
            pSInsert.setString(3, fecha_Devolver + "");
            pSInsert.setInt(4, libroID);
            pSInsert.setInt(5, socioID);
            pSInsert.setNull(6, 6, null);
            pSInsert.executeUpdate();
            pSInsert.close();

            PreparedStatement psUpdate = Conexion.getInstance().prepareStatement(
                    "  UPDATE libros SET isDisponible='" + 0 + "'WHERE libroID=" + libroID);

            psUpdate.executeUpdate();
            psUpdate.close();


        } catch (SQLException e) {
            Modelo.crearPedido(this);//Añado el pedido en el txt
        }
    }

    @Override
    public String toString() {
        String fechaRI;
        if (fecha_ReintegroLibro == null) {
            fechaRI = "Todavia no ha sido devuelto";
        } else {
            fechaRI = fecha_ReintegroLibro.toString();
        }
        return "Fecha que fue prestado: " + fecha_Prestamo + "\n" +
                "Fecha para ser devuelto: " + fecha_Devolver + "\n" +
                "ID del Biblioteca.Libro pedido: " + libroID + "\n" +
                "ID del Socio : " + socioID + "\n" +
                "Reintegro del Biblioteca.Libro: " + fechaRI;
    }

    public String toString(String ceparador) {
        return fecha_Prestamo + ceparador + fecha_Devolver + ceparador + libroID + ceparador + socioID + ceparador + fecha_ReintegroLibro;
    }
}

package Biblioteca;

import Biblioteca.Conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;


public class Pedido {
    private String idPedido;
    private LocalDate fecha_Prestamo;
    private LocalDate fecha_Devolver;
    private LocalDate fecha_ReintegroLibro;
    private String libroID;
    private String socioID;

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Pedido() {
    }

    //Colocar el LocalDate fecha_ReintegroLibro en el constructor
    public Pedido(String idPedido,LocalDate prestamo, LocalDate fecha_Devolver, String libroID, String socioID, LocalDate fecha_ReintegroLibro) {
       this.idPedido=idPedido;
        this.fecha_Prestamo = prestamo;
        this.fecha_Devolver = fecha_Devolver;
        this.libroID = libroID;
        this.socioID = socioID;
        this.fecha_ReintegroLibro = fecha_ReintegroLibro;
    }
    public Pedido(String libroID, String socioID){
        UUID uuid2 = UUID.randomUUID();
        this.idPedido= String.valueOf(uuid2).substring(0, 8);
        LocalDate fechaPrestamo = LocalDate.now();//Fecha en la cual se ejecuto esta opcion
        LocalDate fechaDevolver = LocalDate.now().plusDays(15);//Fecha en la cual se ejecuto esta opcion mas 15 dias
        this.fecha_Prestamo = fechaPrestamo;
        this.fecha_Devolver = fechaDevolver;
        this.libroID = libroID;
        this.socioID = socioID;
        this.fecha_ReintegroLibro = null;
    }

    //Getters and Setters

    public LocalDate getFecha_ReintegroLibro() {
        return fecha_ReintegroLibro;
    }

    public void setFecha_ReintegroLibro(LocalDate fecha_ReintegroLibro) {
        this.fecha_ReintegroLibro = fecha_ReintegroLibro;
    }

    public String getLibroID() {
        return libroID;
    }

    public void setLibroID(String libroID) {
        this.libroID = libroID;
    }

    public String getSocioID() {
        return socioID;
    }

    public void setSocioID(String socioID) {
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

        try {
            this.fecha_ReintegroLibro = LocalDate.now();
            //Actualizo la fecha de reitegro de null a la fecha en la cual se ejecute este metodo
            PreparedStatement psUpdatePedido = Conexion.getInstance().prepareStatement(
                    "UPDATE pedidos SET fechaReintegroLibro='" + fecha_ReintegroLibro + "' " +
                            "WHERE libroPedidoID='" + libroID + "' AND socioPedidoID='" + socioID+"'");

            psUpdatePedido.executeUpdate();
            psUpdatePedido.close();
            //Actualizo el estado del libro de ocupado a disponible, el 1 significa disponible
            PreparedStatement psUpdateLibro = Conexion.getInstance().prepareStatement(
                    "UPDATE libros SET isDisponible=1 WHERE libroID='" + libroID+"'");
            psUpdateLibro.executeUpdate();
            psUpdateLibro.close();
        } catch (SQLException e) {
            this.fecha_ReintegroLibro = null;
            Modelo.devolverLibro(this);


        }

    }

    public void anadirPedido() {

        try {
            PreparedStatement pSInsert = Conexion.getInstance().prepareStatement("INSERT INTO pedidos VALUES(?,?,?,?,?,?)");
            pSInsert.setString(1, idPedido);
            pSInsert.setString(2, fecha_Prestamo + "");
            pSInsert.setString(3, fecha_Devolver + "");
            pSInsert.setString(4, libroID);
            pSInsert.setString(5, socioID);
            pSInsert.setNull(6, 6, null);
            pSInsert.executeUpdate();
            pSInsert.close();

            PreparedStatement psUpdate = Conexion.getInstance().prepareStatement(
                    "  UPDATE libros SET isDisponible='" + 0 + "'WHERE libroID='" + libroID+"'");

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
                "ID del Libro pedido: " + libroID + "\n" +
                "ID del Socio : " + socioID + "\n" +
                "Reintegro del Libro: " + fechaRI;
    }

    public String toString(String ceparador) {
        return idPedido+ceparador+fecha_Prestamo + ceparador + fecha_Devolver + ceparador + libroID + ceparador + socioID + ceparador + fecha_ReintegroLibro;
    }
}

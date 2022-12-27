package Biblioteca;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Socio {

    private String nombre;
    private String apellido;
    private int DNI;
    private String socioID;
    private boolean desactivado;

    public Socio() {
    }

    public Socio(String socioID, String nombre, String apellido, int DNI, boolean desactivado) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.socioID = socioID;
        this.desactivado = desactivado;
    }

    public Socio(String nombre, String apellido, int DNI) {
        UUID uuid2 = UUID.randomUUID();
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.socioID = String.valueOf(uuid2).substring(0, 8);
        ;
        this.desactivado = false;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public boolean isDesactivado() {
        return desactivado;
    }

    public void setDesactivado(boolean desactivado) {
        this.desactivado = desactivado;
    }

    public String getSocioID() {
        return socioID;
    }

    public void setSocioID(String socioID) {
        this.socioID = socioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return DNI;
    }

    public void setDni(int ID) {
        this.DNI = DNI;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " " + DNI;
    }

    public String toString(String ceparador) {
        return socioID + ceparador + nombre + ceparador + apellido + ceparador + DNI + ceparador + desactivado;
    }

    @Override
    public boolean equals(Object obj) {
        Socio s = (Socio) obj;
        return nombre.equals(s.nombre) & apellido.equals(s.apellido) & DNI == s.DNI;
    }

    public void agregarSocio() {
        try {
            PreparedStatement pSInsert = Conexion.getInstance().prepareStatement("INSERT INTO socios VALUES(?,?,?,?,?)");
            pSInsert.setString(1, socioID);
            pSInsert.setString(2, nombre);
            pSInsert.setString(3, apellido);
            pSInsert.setInt(4, DNI);
            pSInsert.setBoolean(5, desactivado);
            pSInsert.executeUpdate();


            pSInsert.close();

        } catch (SQLException e) {
            //Para guardar en txt
            Modelo.anadirSocio(this);
            //------

        }
    }

    public void darDeBaja() {
        try {
            PreparedStatement psUpdate = Conexion.getInstance().prepareStatement(
                    "UPDATE socios SET desactivado='" + 1 + "'WHERE socioID='" + socioID + "'");
            psUpdate.executeUpdate();
            psUpdate.close();
        } catch (SQLException e) {
            Modelo.darDeBajaUnSocio(this);//Dar de baja en el TXT
        }


    }

    public boolean validacion() {
        ArrayList<Socio> SociosC = Modelo.cargarSocios();
        boolean retorno = false;
        for (int i = 0; i < SociosC.size(); i++) {
            if (equals(SociosC.get(i))) {
                retorno = true;
            }
        }
        return retorno;
    }

}

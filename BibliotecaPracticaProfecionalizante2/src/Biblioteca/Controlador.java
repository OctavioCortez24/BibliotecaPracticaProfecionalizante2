package Biblioteca;

import java.time.LocalDate;
import java.util.*;

public class Controlador {


    public static void main(String[] args) {

        int eleccion = 0;
        //Menu
        do {

            eleccion = Vista.menu();
            if (eleccion == 1) {

                //Añadir un socio a la biblioteca
                ArrayList<String> atributosSocio = Vista.crearSocio();//Recibo los atributos que el usuario indico
                String nombre = atributosSocio.get(0);
                String apellido = atributosSocio.get(1);
                int dNI = Integer.parseInt(atributosSocio.get(2));
                int idSocio = Modelo.cargarSocios().size() + 1;

                Socio socio1 = new Socio(idSocio, nombre, apellido, dNI, false);//Instancio un socio
                //Compruebo si el socio se encuentra en la BD
                if (socio1.validacion()) {
                    Vista.validacionDeDatos("El Socio ya esta añadido");
                } else {
                    socio1.agregarSocio();
                    Vista.validacionDeDatos("Se añadio correctamente");
                }


            } else if (eleccion == 2) {

                //Añadir un libro a la biblioteca
                ArrayList<String> atributosLi = Vista.crearLibro();//Recibo los atributos que el usuario indico

                int idLIbro = Modelo.cargarLibros().size() + 1;
                String titulo = atributosLi.get(0);
                String autorNombre = atributosLi.get(1);
                String categoria = atributosLi.get(2);

                Libro libro1 = new Libro(idLIbro, titulo, autorNombre, categoria, true, false);//Instancio un libro

                //Compruebo si el libro se encuentra en la BD
                if (libro1.validacion()) {
                    Vista.validacionDeDatos("El libro ya se encuentra en la biblioteca");
                } else {
                    libro1.añadirLibro();//Añado el libro a la base de datos.
                    Vista.validacionDeDatos("El libro se añadio correctamente");
                }

            } else if (eleccion == 3) {

                //Mostro los libros
                Vista.mostrarLosSocios(Modelo.cargarSocios());

            } else if (eleccion == 4) {

                //Mostrar los libros
                Vista.mostrarLosLibros(Modelo.cargarLibros());

            } else if (eleccion == 5) {

                System.out.println("Dar de baja un libro");
                int numeroLibro = Vista.elegirLibrosNoDesactivados(Modelo.cargarLibros());
                Libro l = Modelo.cargarLibros().get(numeroLibro);//Cargo el libro desde la BD
                l.darDeBaja();//Doy de baja el libro, es para BD

            } else if (eleccion == 6) {

                //Dar de baja un socio
                int numeroSocio = Vista.eleccionSocioNoDesactivados(Modelo.cargarSocios());
                Socio s = Modelo.cargarSocios().get(numeroSocio);//Cargo el socio desde la BD

                s.darDeBaja();//utilizo el metodo darDeBaja, es para BD

            } else if (eleccion == 7) {
                //Registrar un pedido
                ArrayList<Libro> libros = Modelo.cargarLibros();//Cargo los libros de la BD
                ArrayList<Socio> socios = Modelo.cargarSocios();//Cargo los socios de la BD

                //Biblioteca.Libro
                int idLibro = Vista.eleccionLibrosDisponibles(libros);
                //-----
                //Socio
                int idSocio = Vista.eleccionSocio(socios);
                //------
                LocalDate fechaPrestamo = LocalDate.now();//Fecha en la cual se ejecuto esta opcion
                LocalDate fechaDevolver = LocalDate.now().plusDays(15);//Fecha en la cual se ejecuto esta opcion mas 15 dias

                Pedido p = new Pedido(fechaPrestamo, fechaDevolver, idLibro, idSocio, null);//Instancio un pedido
                p.anadirPedido();//Añado el pedido en la BD

            } else if (eleccion == 8) {
                //Mostrar Pedidos

                //Cargo los pedidos
                ArrayList<Pedido> pedidos = Modelo.cargarPedidos();
                //------------------------------------
                Vista.mostrarLosPedidos(pedidos);

            } else if (eleccion == 9) {
                //Devolver Libros
                boolean comprobacion=false;

                //Cargo los pedidos y los libros
                ArrayList<Pedido> pedidos = Modelo.cargarPedidos();
                ArrayList<Libro> libros = Modelo.cargarLibros();
                //------------------------------------
                //Compruebo si existe un un libro que esta pedido
                for (int i=0;i< libros.size();i++){
                    if (!libros.get(i).isDisponibilidad()){
                        comprobacion=true;
                    }
                }

                if (comprobacion){
                    int numeroPedido = Vista.eleccionPedidoParaDevolver(pedidos, libros);

                    Pedido p = Modelo.cargarPedidos().get(numeroPedido);
                    p.devolverLibro();
                }else{
                    Vista.validacionDeDatos("----> No hay libros pedidos <----");
                }

            }

        } while (eleccion != 0);
    }


}

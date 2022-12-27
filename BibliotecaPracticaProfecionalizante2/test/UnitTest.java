import Biblioteca.Libro;
import Biblioteca.Modelo;
import Biblioteca.Socio;
import Biblioteca.Vista;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    void testear(){
        Socio socioPrueba= new Socio("1ehuie1221","Octavio","Andres",43213252,false);
        //SocioPrueba es un socio que se encuentra en la Base de Datos, voy a verificar si el metodo de validacion
        //de datos funciona correctamente

        assertEquals(socioPrueba.validacion(), true);

    }

}

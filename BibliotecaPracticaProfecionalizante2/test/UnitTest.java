import Biblioteca.Libro;
import Biblioteca.Modelo;
import Biblioteca.Socio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    void testear(){
        ArrayList<Socio>p=Modelo.cargarSocios();
        p.add(new Socio(20,"M","P",777, true));
       assertEquals(p,Modelo.cargarSocios().add(new Socio(20,"M","P",777, true)));


    }

}

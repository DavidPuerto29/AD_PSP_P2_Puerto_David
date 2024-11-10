package dao;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import static dao.AutoriaDAO.crearActualizarAutorias;
import static dao.AutoriaDAO.leerTodasAutorias;
import static dao.LibroDao.*;

public class TEST {
    public static void main(String[] args) {
        Conexion con = new Conexion();
/*
        Autoria a1 = new Autoria(1,"david","puerto cuenca");
        Autoria a2 = new Autoria(2,"juan","Valdes Marquez");
        Autoria a3 = new Autoria(3,"paco","martin cuenca");

        con.createTables();
        */

/*
        insertarAutoria(a1);
        insertarAutoria(a2);
        insertarAutoria(a3);
        insertarAutoria(a4);
*/

        //actualizarAutoria(a4);

        //eliminarAutoria(4);

        //LIBROS

        /*
        Libro l1 = new Libro("54857415A","La celestina",a1);
        Libro l2 = new Libro("87894561E","El Quijote",a2);
        Libro l3 = new Libro("13256786Q","4 Wings",a3);

        insertarLibro(l1);
        insertarLibro(l2);
        insertarLibro(l3);
           */

/*
        Libro actualizadoL1 = new Libro("54857415A","Libro actualizado",a3);
        /*
        actualizarLibro(actualizadoL1);
        */

        /*
        eliminarLibro("54857415A");.
         */
/*
        HashMap<String,Libro> test = new HashMap<>();
        test = leerTodosLibros();

        System.out.println(test);
*/
/*
        HashMap<Integer, Autoria> testAutor = new HashMap<>();
        testAutor = leerTodasAutorias();
        System.out.println(testAutor);
        */

        /*
        HashMap<Integer, Autoria> autorias = new HashMap<>();
        */
        /*
        Autoria a1 = new Autoria(1,"manolo","paco gomez");
        Autoria a2 = new Autoria(26,"paco","juana gomez");
        Autoria a3 = new Autoria(41,"luis","paco manoli");
        Autoria a4 = new Autoria(85,"luisa","paco test");
        /*
        crearActualizarAutorias(autorias);
        */
/*
        HashMap<String, Libro> librosTest = new HashMap<>();
        Libro l1 = new Libro("54857415A","La celestina",a1);
        Libro l2 = new Libro("111111111","libro mis",a4);
        Libro l3 = new Libro("24563485647A","La libro nuevo",a4);
        Libro l4 = new Libro("5647867654E","La libro nuevo",a2);

        librosTest.put("54857415A", l1);
        librosTest.put("111111111", l2);
        librosTest.put("24563485647A", l3);
        librosTest.put("5647867654E", l4);

        crearActualizarLibros(librosTest);
    }
    */

}

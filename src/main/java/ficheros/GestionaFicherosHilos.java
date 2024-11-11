package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que encapsula todos los métodos que permiten la escritura y lectura de ficheros y binarios.
 * En la clase biblioteca se realizan llamadas a estos métodos para realizar diferentes acciones de gestión de datos.
 *
 * ---------------------------------------------------Nota------------------------------------------------------
 * Si ejecutas el programa en linux es probable que puedas encontrarte errores de que no lea bien los ficheros,
 * ya que ha sido desarrollado en windows y en el ordenador de clase con ubuntu he tenido algunos conflictos
 * con los ficheros al probar el programa.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */

public class GestionaFicherosHilos implements Serializable{
    /**
     *Ya que en los métodos de escritura y lectura de fichero binario no se pide archivo File
     *Se declara en la clase una variable estática File con la ruta definida del bin.
     */
    private static final File  ficheroBin = new File("./files/biblioteca.bin");


    public static void exportarFichero(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, InterruptedException {
       boolean append = false; //PROGRAMAR BIEN
        EscritorTexto es = new EscritorTexto(f,autores,libros,append);
        System.out.println("hola");
        es.start();
        es.join();
    }

    public static void importarFicheros(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, InterruptedException {
        LectorTexto lt = new LectorTexto(f,autores,libros);
        lt.start();
        lt.join();
    }


    public static void guardarBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, InterruptedException {
        EscritorBinario eb = new EscritorBinario(ficheroBin,autores,libros);
        eb.start();
        eb.join();
    }


    public static void leerBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, ClassNotFoundException, InterruptedException {
        LectorBinario lb = new LectorBinario(ficheroBin,autores,libros);
        lb.start();
        lb.join();
    }
}

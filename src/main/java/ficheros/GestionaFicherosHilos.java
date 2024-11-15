package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que permite gestionar todas las ejecuciónes multihilo de las diferentes clases
 * de lectura/escritura.
 *
 * @author David Puerto Cuenca
 * @version 2.0
 */

public class GestionaFicherosHilos implements Serializable{
    /**
     *Ya que en los métodos de escritura y lectura de fichero binario no se pide archivo File
     *Se declara en la clase una variable estática File con la ruta definida del bin.
     */
    private static final File  ficheroBin = new File("./files/biblioteca.bin");

    /**
     * Se encarga de gestionar la ejecución multihilo de la clase EscritorTexto.
     *
     * @param f El archivo file que contiene la ruta donde se guardaran los datos.
     * @param autores El HashMap que contiene los datos de las autorias.
     * @param libros  El HashMap que contiene los datos de los libros.
     * @throws InterruptedException En caso de que algún hilo quede interrumpido en la ejecución
     */
    public static void exportarFichero(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws  InterruptedException {
        EscritorTexto es = new EscritorTexto(f,autores,libros,false);
        System.out.println("hola");
        es.start();
    }

    /**
     * Se encarga de gestionar la ejecucion multihilo de la clase LectorTexto.
     *
     * @param f El archivo file que contiene la ruta donde se guardaran los datos.
     * @param autores El HashMap que contiene los datos de las autorias.
     * @param libros  El HashMap que contiene los datos de los libros.
     * @throws InterruptedException En caso de que algún hilo quede interrumpido en la ejecución
     */
    public static void importarFicheros(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws InterruptedException {
        LectorTexto lt = new LectorTexto(f,autores,libros);
        lt.start();
        //Sincronizamos mediante el uso del método join.
        lt.join();
    }

    /**
     * Se encarga de gestionar la ejecución multihilo de la clase EscritorBinario.
     *
     * @param ficheroBin El archivo file que contiene la ruta donde se guardaran los datos.
     * @param autores El HashMap que contiene los datos de las autorias.
     * @param libros  El HashMap que contiene los datos de los libros.
     * @throws InterruptedException En caso de que algún hilo quede interrumpido en la ejecución
     */
    public static void guardarBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws InterruptedException {
        EscritorBinario eb = new EscritorBinario(ficheroBin,autores,libros);
        eb.start();
    }

    /**
     * Se encarga de gestionar la ejecución multihilo de la clase LectorBinario.
     *
     * @param ficheroBin El archivo file que contiene la ruta donde se guardaran los datos.
     * @param autores El HashMap que contiene los datos de las autorias.
     * @param libros  El HashMap que contiene los datos de los libros.
     * @throws InterruptedException En caso de que algún hilo quede interrumpido en la ejecución
     */
    public static void leerBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws InterruptedException {
        LectorBinario lb = new LectorBinario(ficheroBin,autores,libros);
        lb.start();
        //Sincronizamos mediante el uso del método join.
        lb.join();
    }
}

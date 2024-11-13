package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Implementa la programación multihilo al método en la clase, mediante la
 * llamada y correcta gestión de la interfaz Runnable.
 *
 *@author David Puerto Cuenca
 *@version 2.0
 */
public class EscritorBinario extends Thread{
    private File ficheroBin;
    private HashMap<Integer, Autoria> autores;
    private HashMap <String, Libro> libros;

    public EscritorBinario(File ficheroBin, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.ficheroBin = ficheroBin;
        this.autores = autores;
        this.libros = libros;
    }

    /**
     * Este método gestiona los hilos del programa y también ejecuta
     * el método definido dentro, con su correspondiente control de
     * excepciones.
     */
    @Override
    public void run(){
        try {
            guardarBin(autores,libros,ficheroBin);
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al intentar guardar el fichero binario.");
        }
    }

    /**
     *Este método permite guardas los datos del programa en un fichero binario mediante la ruta definida en la clase, ya que siempre se va a guardar en la misma posición
     * mediante un ObjectOutputStream se escribe primero el HashMap de autores y después el de libros.
     *
     * @param autores HashMap que almacena todas las variables de los autores.
     * @param libros  HashMap que almacena todas las variables de los libros.
     * @throws IOException Puede lanzar un error de entrada/salida.
     */
    private static void guardarBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros,File ficheroBin) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroBin));
        oos.writeObject(autores);
        oos.writeObject(libros);
        oos.close();
    }
}

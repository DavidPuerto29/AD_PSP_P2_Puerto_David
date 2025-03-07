package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Implementa la programación multihilo al método en la clase, mediante la
 * llamada y correcta gestión de la interfaz Runnable.
 *
 *@author David Puerto Cuenca
 *@version 2.0
 */
public class LectorTexto extends Thread{
    private File f;
    private HashMap<Integer, Autoria> autores;
    private HashMap <String, Libro> libros;

    public LectorTexto(File f, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.f = f;
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
            //Añadimos el método de sincronización, ya que varios hilos no puede acceder a un fichero a la vez.
            synchronized (f) {
                importarFicheros(f, autores, libros);
            }
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al intentar importar los datos de un fichero.");
        }
    }

    /**
     * Este método permite importar ficheros de una ruta definida mediante el archivo file a los Hashmap de autores y libros,
     * mediante un Buffered Reader y dos bucles while que tienen en cuenta la separación de autores y libros añadiendo primero los autores
     * y después los libros.
     *
     * @param f Ruta donde se creara el fichero.
     * @param autores Se piden los dos HashMap al añadir el método,
     * @param libros  para realizar las integraciones (put) correctamente
     * @throws IOException  Puede lanzar un error de entrada/salida.
     */
    private static void importarFicheros(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException {
        String id = null;
        String nombre = null;
        String apellido = null;
        Autoria var = null;
        BufferedReader br = new BufferedReader(new FileReader(f));
        id = br.readLine();  //Asi evitamos conflictos con ***AUT***
        while (!(id = br.readLine()).equals("***LIB***")) {
            nombre = br.readLine();
            apellido = br.readLine();
            autores.put(Integer.valueOf(id),new Autoria(Integer.valueOf(id),nombre,apellido));
        }
        //Leemos libros y también reutilizamos variables para evitar consumo de procesos abundante.
        while ((nombre = br.readLine()) != null) {
            apellido = br.readLine();    //(Titulo)
            id = br.readLine();         //(Id del autor)
            var = autores.get(Integer.valueOf(id));
            libros.put(nombre,(new Libro(nombre,apellido,var)));
        }
        br.close();
    }
}

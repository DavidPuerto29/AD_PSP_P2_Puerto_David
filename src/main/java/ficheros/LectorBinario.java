package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Implementa la programación multihilo al método en la clase, mediante la
 * llamada y correcta gestión de la interfaz Runnable.
 *
 *@author David Puerto Cuenca
 *@version 2.0
 */
public class LectorBinario extends Thread{
    private final File ficheroBinario;
    private HashMap<Integer, Autoria> autores;
    private  HashMap <String, Libro> libros;

    public LectorBinario(File ficheroBinario, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.ficheroBinario = ficheroBinario;
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
        try {   //TEST
            //Añadimos el método de sincronización, ya que varios hilos no puede acceder a un fichero a la vez.
            synchronized (ficheroBinario) {
                leerBin(ficheroBinario, autores, libros);
            }
        } catch (IOException e) {
            System.out.println("No hay un fichero binario guardado, por favor cree uno primero.");
        }catch (ClassNotFoundException e) {
            System.out.println("La clase deseada no ha sido encontrada.");
        }
    }

    /**
     * Este método se encarga de leer el fichero binario, depositado en la ruta marcada por la función File de la clase mediante un
     * ObjectInputStream se lee el fichero binario, y primero se guarda en una variable auxiliar de los HashMap realizando un casting
     * del mismo para posteriormente mediante el método (putAll) añadir a los HashMap del programa todas las variables obtenidas en los
     * HashMap auxiliares.
     *
     * @param autores   HashMap que almacena todas las variables de los autores.
     * @param libros    HashMap que almacena todas las variables de los libros.
     * @throws IOException Puede lanzar un error de entrada/salida.
     * @throws ClassNotFoundException Puede lanzar un error de clase no encontrada.
     */
    public static void leerBin(File ficheroBin,HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroBin));
        HashMap<Integer, Autoria> autoresAUX = (HashMap<Integer, Autoria>) ois.readObject();
        autores.putAll(autoresAUX);
        HashMap<String, Libro> librosAux = (HashMap<String, Libro>) ois.readObject();
        libros.putAll(librosAux);
        ois.close();
    }
}

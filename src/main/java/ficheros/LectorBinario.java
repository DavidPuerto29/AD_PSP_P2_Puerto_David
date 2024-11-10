package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class LectorBinario extends Thread{
    private File ficheroBinario;
    private HashMap<Integer, Autoria> autores;
    private  HashMap <String, Libro> libros;

    public LectorBinario(File ficheroBinario, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.ficheroBinario = ficheroBinario;
        this.autores = autores;
        this.libros = libros;
    }

    @Override
    public void run(){
        try {   //TEST
            leerBin(ficheroBinario,autores,libros);
        } catch (IOException | ClassNotFoundException e) { //TEST
            System.out.println("e");
        } //TEST
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

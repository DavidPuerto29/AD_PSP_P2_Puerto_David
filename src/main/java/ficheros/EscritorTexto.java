package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementa la programación multihilo al método en la clase, mediante la
 * llamada y correcta gestión de la interfaz Runnable.
 *
 *@author David Puerto Cuenca
 *@version 2.0
 */
public class EscritorTexto extends Thread{

    private File f;
    private HashMap<Integer, Autoria> autores;
    private HashMap <String, Libro> libros;
    private boolean append;

    public EscritorTexto(File f, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros, boolean append) {
        this.f = f;
        this.autores = autores;
        this.libros = libros;
        this.append = append;
    }

    /**
     * Este método gestiona los hilos del programa y también ejecuta
     * el método definido dentro, con su correspondiente control de
     * excepciones.
     */
    @Override
    public void run(){
        try {
            exportarFichero(f,autores,libros,append);
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al intentar exportar el fichero.");
        }
    }

    /**
     * Este método permite exportar datos a un fichero recorriendo los HashMaps y escribiéndolos en el fichero de texto seleccionado,
     * mediante un PrintWriter y dos bucles para recorrer y escribir en uno los autores y en otro los libros.
     *
     * @param f Ruta donde se creara el fichero.
     * @param autores   Se necesita crear una variable del objeto para poder guardar ahi los datos depositados en el HashMap
     * @param libros    y posteriormente hacer get a sus atributos en un PrintWriter para realizar la escritura,
     * @throws IOException Puede lanzar un error de entrada/salida.
     *
     * Ejemplo de como se ordena el fichero:
     *  ***AUT***
     *  1       (Id)
     *  manuel  (Nombre)
     *  gonzalez (Apellido)
     *  ***LIB***   Separación de libros con autores.
     *  89797456    (Isbn)
     *  lazarillo   (Título)
     *  1   (Id de autor)
     */
    private static void exportarFichero(File f, HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros,boolean append) throws IOException {
        Autoria a = null;
        Libro libroCopia = null;
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        //Escritura de autores en el fichero
        pw.println("***AUT***");
        for (int i = 1; i <= autores.size(); ++i) {
            a = autores.get(i);
            System.out.println("a");
            pw.println(a.getId());
            pw.println(a.getNombre());
            pw.println(a.getApellido());
        }
        //Escritura de libros en el fichero
        pw.println("***LIB***");
        for (Map.Entry<String, Libro> l: libros.entrySet()) {
            libroCopia = l.getValue();
            pw.println(libroCopia.getIsbn());
            pw.println(libroCopia.getTitulo());
            a = libroCopia.getAutoria();
            pw.println(a.getId());
        }
        pw.close();
    }


}

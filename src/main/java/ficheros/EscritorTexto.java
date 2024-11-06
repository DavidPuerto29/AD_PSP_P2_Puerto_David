package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void run(){
        try {   //TEST //TEST
            exportarFichero(f,autores,libros,append);
        } catch (IOException e) { //TEST //TEST //TEST
            throw new RuntimeException(e);
        } //TEST //TEST //TEST
    }


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

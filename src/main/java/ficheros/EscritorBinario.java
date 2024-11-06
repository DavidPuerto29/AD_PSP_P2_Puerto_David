package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class EscritorBinario extends Thread{
    private File ficheroBin;
    private HashMap<Integer, Autoria> autores;
    private HashMap <String, Libro> libros;

    public EscritorBinario(File ficheroBin, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.ficheroBin = ficheroBin;
        this.autores = autores;
        this.libros = libros;
    }

    @Override
    public void run(){
        try {   //TEST //TEST //TEST
            guardarBin(autores,libros,ficheroBin);
        } catch (IOException e) { //TEST //TEST //TEST
            throw new RuntimeException(e);
        } //TEST
    }

    private static void guardarBin(HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros,File ficheroBin) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroBin));
        oos.writeObject(autores);
        oos.writeObject(libros);
        oos.close();
    }
}

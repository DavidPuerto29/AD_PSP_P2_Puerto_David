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

    public static void leerBin(File ficheroBin,HashMap<Integer, Autoria> autores, HashMap <String, Libro> libros) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroBin));
        HashMap<Integer, Autoria> autoresAUX = (HashMap<Integer, Autoria>) ois.readObject();
        autores.putAll(autoresAUX);
        HashMap<String, Libro> librosAux = (HashMap<String, Libro>) ois.readObject();
        libros.putAll(librosAux);
    }
}

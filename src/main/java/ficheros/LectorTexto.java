package ficheros;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LectorTexto extends Thread{
    private File f;
    private HashMap<Integer, Autoria> autores;
    private HashMap <String, Libro> libros;

    public LectorTexto(File f, HashMap<Integer, Autoria> autores, HashMap<String, Libro> libros) {
        this.f = f;
        this.autores = autores;
        this.libros = libros;
    }

    @Override
    public void run(){
        try {       //TEST //TEST //TEST //TEST //TEST
            importarFicheros(f,autores,libros);
        } catch (IOException e) {  //TEST //TEST //TEST //TEST //TEST
            throw new RuntimeException(e);
        } //TEST //TEST //TEST
    }

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
        while ((nombre = br.readLine()) != null) {       //Leemos libros y también reutilizamos variables para evitar consumo de procesos abundante.
            apellido = br.readLine();    //(Titulo)
            id = br.readLine();         //(Id del autor)
            var = autores.get(Integer.valueOf(id));
            libros.put(nombre,(new Libro(nombre,apellido,var)));
        }
        br.close();
    }
}

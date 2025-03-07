package interfaz;
import biblioteca.Autoria;
import biblioteca.Libro;
import ficheros.GestionaFicherosHilos;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static dao.AutoriaDAO.*;
import static dao.Conexion.createTables;
import static dao.LibroDao.*;
import static ficheros.GestionaFicherosHilos.*;

/**
 * Clase que representa a un programa de gestión de biblioteca gestionando los autores y los libros
 * registrados en su HashMap correspondiente misma además contando de exportación/importación a ficheros
 * y a ficheros binarios, aquí se encuentra el main del programa y sus correspondientes métodos estáticos.
 *
 * Cuentas con un archivo de texto llamado prefab en la carpeta files del proyecto que incluye un ejemplo de
 * biblioteca con 50 instancias de autores con sus respectivos 50 libros.
 *
 * @author David Puerto Cuenca
 * @version 2.0
 */
public class Biblioteca implements Serializable {
    /**
     * Se declaran los dos HashMap en la clase biblioteca que serán los utilizados
     * para la gestión de los datos en tiempo real.
     */
    private static HashMap <String, Libro> libros = new HashMap<>();
    private static HashMap <Integer, Autoria> autores = new HashMap<>();
    private static int instancias = 0;

    /**
     * En el método main primero se intenta leer el fichero binario para importar datos
     * que se registraron en otra instancia del programa, en caso de que no se encuentre
     * se le da una mejor bienvenida al usuario, ya que es necesario causar una buena impresión a
     * usuarios nuevos para que vuelvan a utilizar el programa más veces, después se encuentra
     * el menu del programa formado por un bucle y un switch case, en el que al usuario
     * se le mostraran diferentes opciónes y dependiendo de la opción elegida se llamara a un método o a otro,
     * en algunos métodos se realiza la gestión de excepciones en el propio switch case y otros se gestionan
     * en el propio método al que se ha llamado, para finalizar cuando el usuario finalice el programa
     * se realizará una exportación de fichero binario pra guardar todos los datos nuevos que haya
     * podido introducir y se mostrara un mensaje de programa finalizado.
     *
     * V2.0 Se añade el uso de sentencias sql y todos los datos del programa son guardados en ella.
     *
     */
    public static void main(String[] args) {
        boolean bucle = true;

        System.out.println("Bienvenido al gestor de biblioteca");

            //Creamos las tablas sql de la base de datos en caso de que no existan.
            createTables();
            //Leemos las tablas sql de autorias y de libros.
            //Importante primero leer los autores, ya que si no los libros al no tener idAutor puede crear conflictos.
            autores = leerTodasAutorias();
            libros = leerTodosLibros();

        while (bucle) {
                System.out.println("""
                             ╔════════════════════|Menu Biblioteca|══════════════════════╗  \r
                        	 ║	               1 Crear autores		                     ║\r
                        	 ║	               2 Ver autores                             ║\r
                        	 ║	               3 Crear Libro				  	         ║\r
                        	 ║	               4 Mostrar libros				  	         ║\r
                        	 ║	               5 Eliminar un libro 				  	     ║\r
                        	 ║	               6 Para exportar a fichero de texto		 ║\r
                        	 ║	               7 Importar datos de un fichero de texto   ║\r
                        	 ║	               8 Guardar datos en un fichero binario	 ║\r
                        	 ║	               9 Leer datos de un fichero binario		 ║\r
                        	 ║	               0 Para finalizar el programa.	 		 ║\r
                             ╚═══════════════════════════════════════════════════════════╝\r
                        """);
                Integer op = escribirInteger();
                switch (op) {
                    case 0:
                        bucle = false;
                        break;
                    case 1:
                        crearAutor();
                        break;
                    case 2:
                        mostrarAutores();
                        break;
                    case 3:
                        crearLibro();
                        break;
                    case 4:
                        mostrarLibros();
                        break;
                    case 5:
                        eliminarLibros();
                        break;
                    case 6:
                        exportarAlFichero();
                        break;
                    case 7:
                        importarDeFichero();
                        break;
                    case 8:
                        if(!autores.isEmpty()) {
                            try {
                                guardarBin(Biblioteca.autores, Biblioteca.libros);
                            }catch (InterruptedException e) {
                                System.out.println("Hilo interrumpido en la ejecución");
                            }
                        }else{
                            System.out.println("Para exportar un fichero binario debe de tener al menos un autor registrado.");
                        }
                        break;
                    case 9:
                        try {
                            leerBin(Biblioteca.autores, Biblioteca.libros);
                            //Introducimos los nuevos datos en la base de datos.
                            instancias = crearActualizarAutorias(autores);
                            instancias += crearActualizarLibros(libros);
                            System.out.println("Archivo importado correctamente, filas sql afectadas: "+instancias);
                        }catch (InterruptedException e) {
                            System.out.println("Hilo interrumpido en la ejecución");
                        }
                        break;

                    case null:
                        break;
                    default:
                        System.out.println("La opción elegida no es correcta.");
                        break;
                }
            }
        //Antes de finalizar el programa comprobamos que todos los datos estén sincronizados.
        instancias = crearActualizarAutorias(autores);
        instancias += crearActualizarLibros(libros);
        System.out.println("Programa finalizado, filas sql afectadas: "+instancias);
    }

    /**
     * Este método se encarga de añadir un autor al HashMap de autores, primero se le pide al usuario
     * la id con la que desea guardar a ese autor, se comprueba que esa, id no este en uso y en caso de estarlo se informa
     * al usuario que la id ya está en uso y que debe de elegir otra en caso de no estar en uso se pide el nombre y apellido
     * y posteriormente se añade al HashMap informando al usuario de que la operación ha sido correcta.
     *
     * @see #escribirInteger() Es un metodo usado para la gestion de errores en la introducción de numeros asegurandose de que sea
     * un dato correcto.
     * @see #comprobarString(String) Es un metodo usado para evitar que se creen instancias del HashMap null.
     *
     * V2.0 Se añade la llamada metodo insertarAutoria para insertar el dato en la sql.
     */
    private static void crearAutor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                     ╔══════════════════╗  \r
                	 ║Creador de autores║\r
                     ╚══════════════════╝\r
                """);
        System.out.println("Introduzca el id del nuevo autor:");
            Integer id = escribirInteger();
        if(autores.containsKey(id)){
            System.out.println("Ese id ya se encuentra registrado, por favor introduzca un id diferente.");
        }else if(id != null){
            System.out.println("Introduzca el nombre: ");
                String nombre = sc.nextLine();
                    System.out.println("Introduzca el apellido: ");
                        String apellido = sc.nextLine();
                        //Se comprueba que los String no tengan números.
                        if((comprobarString(nombre) && comprobarString(apellido))) {
                            autores.put(id,new Autoria(id,nombre,apellido));
                                    //Insertamos el nuevo autor en la base de datos.
                                    instancias = insertarAutoria(autores.get(id));
                                        System.out.println("Autor añadido correctamente, filas sql afectadas: "+instancias);
                        }else{
                            System.out.println("Por favor, introduzca un nombre o apellido sin caracteres numéricos.");
                        }
        }
    }

    /**
     * Este método se encarga de mostrar los autores guardados en el HashMap
     * con los datos pertinentes.
     */
    private static void mostrarAutores(){
        System.out.println("""
                     ╔═══════════════════╗  \r
                	 ║Autores registrados║\r
                     ╚═══════════════════╝\r
                """);
        if(autores.isEmpty()) {
            //Para evitar que se muestre el HashMap vació y desconcierte al usuario.
            System.out.println("No hay autores registrados en la biblioteca.");
        }else{
            for(Integer i:autores.keySet()) {
                //Error parcheado.
                System.out.println(autores.get(i));
            }
        }
    }

    /**
     *Este método se encarga de añadir un libro al HashMap de libros, para comenzar se pide al usuario que introduzca el isbn del libro
     * y se comprueba que ese isbn no esté en uso, en caso de estarlo se informa al usuario del problema, en caso de que no este añadido
     * se le pide al usuario que introduzca el título del libro y id de autor al que pertenece, este último dato es importante,
     * ya que en caso de que id de autor no este vinculado a ningún autor se informara al usuario de que la operación no puede realizarse.
     *
     * @see #escribirInteger() Es un metodo usado para la gestion de errores en la introducción de numeros asegurandose de que sea
     * un dato correcto.
     * @see #comprobarString(String) Es un metodo usado para evitar que se creen instancias del HashMap null.
     *
     * V2.0 Se añade la llamada metodo insertarLibro para insertar el dato en la sql.
     */
    private static void crearLibro(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                     ╔════════════════════╗  \r
                	 ║ Creador de libros  ║\r
                     ╚════════════════════╝\r
                """);
        System.out.println("Introduzca el Isbn del libro a registrar: ");
            String isbn = sc.nextLine();
        if(libros.containsKey(isbn)){
            System.out.println("Este isbn ya esta registrado, por favor introduzca un isbn diferente.");
        }else{
            System.out.println("Introduzca el título: ");
                String titulo = sc.nextLine();
                    System.out.println("Introduzca el id de autoría: ");
                        Integer idAutor = escribirInteger();
        //Se comprueba que el título no contiene caracteres numéricos.
        if(comprobarString(titulo)) {
            if (autores.containsKey(idAutor)) {
                libros.put(isbn, new Libro(isbn, titulo, autores.get(idAutor)));
                    //Insertamos el nuevo libro en la base de datos.
                    instancias = insertarLibro(libros.get(isbn));
                        System.out.println("Libro añadido correctamente, filas sql afectadas: "+instancias);
            } else if (idAutor != null) {
                //En caso de que id sea null se informa al usuario de que el autor no existe.
                System.out.println("El id de autor introducido no corresponde a ningún autor registrado.");
            }
        }else{
            System.out.println("Por favor, introduzca un titulo sin caracteres numéricos.");
        }
        }
    }

    /**
     * Este método se encarga de mostrar los libros guardados en el HashMap
     * con los datos pertinentes.
     */
    private static void mostrarLibros(){
        System.out.println("""
                     ╔════════════════════╗  \r
                	 ║ Libros registrados ║\r
                     ╚════════════════════╝\r
                """);
        if(libros.isEmpty()) {
            //Para evitar que se muestre el HashMap vació y desconcierte al usuario.
            System.out.println("No hay libros registrados en la biblioteca.");
        }else{
            for (Map.Entry<String, Libro> l: libros.entrySet()) {
                System.out.println(l.getValue());
            }
        }
    }

    /**
     * Este método se encarga de eliminar un libro del HashMap, se pide al usuario el isbn y se comprueba su existencia
     * en caso de estar en la colección se elimina y se informa al usuario de que la operación ha sido correcta,
     * en caso de no encontrarse en la colección se informa al usuario de que el isbn no ha sido encontrado y si quiere
     * volver a introducir un isbn o volver al menu principal haciendo que si no introduce un dato de los pedidos se mantenga
     * en el segundo bucle hasta que introduzca el dato deseado pudiendo ser volver al menu(finalizar los dos bucles) o
     * introducir otro isbn(cerrar solo el segundo bucle).
     *
     * @see #escribirInteger() Es un metodo usado para la gestion de errores en la introducción de numeros asegurandose de que sea
     * un dato correcto.
     *
     * V2.0 Se añade la llamada metodo eliminarLibro para insertar el dato en la sql.
     */
    private static void eliminarLibros(){
        Scanner sc = new Scanner(System.in);
        boolean menu = true;
        //Se encapsula en un bucle while, ya que si el usuario quiere volver a introducir el isbn el método siga en ejecución.
        while(menu) {
            System.out.println("""
                         ╔════════════════════╗  \r
                    	 ║ Eliminar un libro  ║\r
                         ╚════════════════════╝\r
                    """);
            System.out.println("Introduzca un isbn");
                String isbn = sc.nextLine();
            if (libros.containsKey(isbn)) {
                libros.remove(isbn);
                    //Eliminamos el libro de la base de datos.
                    instancias = eliminarLibro(isbn);
                        System.out.println("Libro eliminado correctamente, filas sql afectadas: "+instancias);
                            break;
            } else {
                boolean menu2 = true;
                //Se encapsula en un bucle while, ya que si el usuario introduce un dato no correcto se le vuelva a requerir un dato correcto.
                while(menu2) {
                    System.out.println("""
                            \r
                            El isbn no ha sido encontrado, por favor seleccione una opción:
                                    ╔═══════════════════════════════════════╗  
                            	    ║	1 Para volver a introducir el isbn  ║\r
                            	    ║	2.Para volver al menu principal		║\r
                                    ╚═══════════════════════════════════════╝\r
                            """);
                    Integer op = escribirInteger();
                    switch (op){
                        case 1:
                            menu2 = false;
                                break;
                        case 2:
                            menu2 = false;
                            menu = false;
                                break;
                        case null:
                            break;
                        default:
                            System.out.println("Elija una opción valida.");
                    }
                }
            }
        }
    }

    /**
     * Este método se encarga de gestionar la exportación de datos del programa a un fichero de texto,
     * para empezar se pide al usuario que introduzca el nombre que quiere asignar al fichero de texto
     * y se comprueba si ya existe o no en caso de no existir lo crea y lo escribe, pero en caso de que
     * exista informa al usuario de que existe y si desea sobreescribirlo si el usuario elige que no
     * desea sobreescribirlo se le llevara al menu principal.
     *
     *@see GestionaFicherosHilos#exportarFichero(File, HashMap, HashMap) Gestiona la salida de los datos.
     */
    private static void exportarAlFichero() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero al que se quiere exportar: ");
            String ruta = ("./files/"+sc.nextLine()+".txt");
                File f = new File(ruta);
                if(!autores.isEmpty()) {
                    try {
                        if (f.exists()) {
                            System.out.println("¿Desea sobreescribir el fichero?");
                            //Reutilización de variables para ahorrar memoria.
                            ruta = sc.nextLine().toLowerCase();
                            if (ruta.equals("si")) {
                                exportarFichero(f, autores, libros);
                                System.out.println("Archivo sobreescrito correctamente.");
                            } else {
                                System.out.println("Volviendo al menu principal...");
                            }
                        } else {
                            exportarFichero(f, autores, libros);
                            System.out.println("Archivo exportado correctamente.");
                        }
                    }catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("Para exportar un fichero debe de tener al menos un autor registrado.");
                }
    }

    /**
     * Este método se encarga de importar los datos de un fichero de texto al programa, para comenzar se pide al
     * usuario que introduzca el nombre del fichero que quiere importar y se comprueba si ese fichero existe,
     * en caso de no existir se informará al usuario de que el fichero no ha sido encontrado y en caso de sí
     * existir se llamará al método importarFicheros de la clase GestionaFicheros y se informará al usuario de
     * que la exportación ha sido correcta.
     *
     * @see GestionaFicherosHilos#importarFicheros(File, HashMap, HashMap) Gestiona la entrada de los datos.
     *
     * V2.0 Se añade la llamada metodo crearActualizarAutorias y crearActualizarLibros
     * para insertar los datos nuevos o en caso de existir actualizarlos en la sql.
     */
    private static void importarDeFichero(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre del fichero que se quiere importar: ");
            String ruta = ("./files/"+sc.nextLine()+".txt");
                File f = new File(ruta);
        if(f.exists()){
            try {
                importarFicheros(f,autores,libros);
                    //Introducimos los nuevos datos en la base de datos.
                    instancias = crearActualizarAutorias(autores);
                    instancias += crearActualizarLibros(libros);
                    System.out.println("Archivo importado correctamente, filas sql afectadas: "+instancias);
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Error el archivo no ha sido encontrado.");
        }
    }

    /**
     *Este método se utiliza cuando al usuario se le pide una entrada de un dato entero
     * debido a que se puede producir una excepción, si el usuario introduce otro dato
     * se gestiona la excepción, se informa al usuario de que introduzca un dato correcto
     * y se hace return de null.
     *
     * @return En caso de que el dato no sea numérico se devolverá null.
     */
    private static Integer escribirInteger(){
        Scanner num = new Scanner(System.in);
        try{
            return num.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Introduzca un dato correcto.");
                return null;
        }
    }

    /**
     * Este método se encarga de la gestión de errores al pedirle al usuario que introduzca textos como pueden ser nombres, apellidos etc...,
     * ya que si se introducen caracteres numéricos puede crear errores y HashMaps nulls.
     *
     * @return true si el texto no contiene números, false si contiene números.
     */
    private static boolean comprobarString(String texto){
        return texto.matches("[a-zA-Z\\s]+");
    }
}

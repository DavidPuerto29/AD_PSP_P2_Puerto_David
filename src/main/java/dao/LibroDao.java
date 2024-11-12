package dao;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static dao.AutoriaDAO.leerTodasAutorias;

/**
 * Esta clase se encarga de gestionar los métodos sql de la clase
 * de libro conteniendo las sentencias básicas y las requeridas
 * para el funcionamiento y gestión de datos correcta.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */
public class LibroDao {
    private static int instancias = -1;

    /**
     * Este método se encarga de insertar en la tabla de libros los nuevos
     * libros con sus parámetros, a partir del libro pedido por parámetro.
     *
     * @param libro El objeto de libro que contiene los datos de la nueva instancia.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int insertarLibro(Libro libro){  //Funciona
        String sql = "insert into libros" +
                "(isbn,titulo,idautoria)" +
                "values(?,?,?)";
        try(Connection con = Conexion.conectar()){
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, libro.getIsbn());
            s.setString(2, libro.getTitulo());
            s.setInt(3, libro.getAutoria().getId());
            instancias = s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    /**
     * Este método se encarga de leer en la tabla de sql libros
     * con el isbn correspondiente que se ha enviado por parámetro.
     *
     * @param isbn El isbn de libro usado para realizar la consulta.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static Libro leerLibro(String isbn){    //testear ns paq se usa
        Libro l1 = null;
        String sql = "select * from libros where isbn = ?";
        instancias = 0;

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, isbn);
            ResultSet rs = p.executeQuery();
            while(rs.next()){       //COMPROBAR ESTO Q NO ME FIO
                instancias++;
            }
            if(rs.next()){
                String num = rs.getString(1);
                String titulo = rs.getString("titulo");
                Autoria autorLibro = (Autoria) rs.getObject("idautoria");   //Rezar para q funcione el cast
                l1 = new Libro(isbn,titulo,autorLibro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return l1;
    }

    /**
     * Este método se encarga de actualizar en la tabla sql
     * el libro envidado por parámetro.
     *
     * @param libro El objeto de libro que contiene los datos de la nueva instancia.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int actualizarLibro(Libro libro){    //Funciona
        String sql = "update libros set titulo = ?, idautoria = ? where isbn = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, libro.getTitulo());
            p.setInt(2, libro.getAutoria().getId());
            p.setString(3, libro.getIsbn());
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    /**
     * Este método se encarga de eliminar en la tabla sql
     * el libro envidado por parámetro.
     *
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int eliminarLibro(String isbn){ //Funciona
        String sql = "delete from libros where isbn = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, isbn );
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    /**
     * Este método se encarga de leer todos los libros de la tabla sql
     * y de almacenarlos en un HashMap, a tener en cuenta que Libro contiene la
     * autoria con la que se relaciona, por lo tanto, necesitaremos el objeto autoria
     * correspondiente, por eso para evitar repetición de código se guardan en un hashmap
     * todas las autorias y mediante el método get se obtiene el objeto autoria.
     *
     * No se deberían de producir errores, ya que para crear un libro tiene que haber autores
     * creados, porque si o si hay que relacionarlo con un autor.
     *
     * @return Devuelve el hashMap con todos los libros importados.
     */
    public static HashMap<String, Libro> leerTodosLibros(){//Funciona
        HashMap<String, Libro> libros = new HashMap<>();
        //Reutilizamos el método para evitar la repetición de código, ya que este nos devuelve todas las autorias.
        HashMap<Integer, Autoria> autores = leerTodasAutorias();
        String sql = "select * from libros";
        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
                ResultSet rs = p.executeQuery();
            while (rs.next()){
                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                int  idAutor = rs.getInt("idautoria");
                    Autoria autorLibro = autores.get(idAutor);
                        libros.put(isbn,new Libro(isbn,titulo,autorLibro));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libros;
    }

    /**
     * Este método se encarga de crear instancias en caso de que los libros no existan o de actualizarlos en caso
     * de que existan pero tengan parámetros diferentes.
     *
     * @param libros Contiene los datos introducidos en el programa para exportarlos a la base de datos.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int crearActualizarLibros(HashMap<String, Libro> libros){  //Funciona
        String sql = "insert into libros" +
                "(isbn,titulo,idautoria)" +
                "values(?,?,?)" +
                "on duplicate key update " +
                "titulo = values(titulo)," +
                "idautoria = values(idautoria)";

        for(Map.Entry<String, Libro> hmLibros : libros.entrySet()){
            Libro lPrefab = hmLibros.getValue();
        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, lPrefab.getIsbn());
            p.setString(2, lPrefab.getTitulo());
            p.setInt(3, lPrefab.getAutoria().getId());
                instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
        return instancias;
    }
}

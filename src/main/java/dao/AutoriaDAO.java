package dao;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static dao.LibroDao.leerTodosLibros;

/**
 * Esta clase se encarga de gestionar los métodos sql de la clase
 * de autoria conteniendo las sentencias básicas y las requeridas
 * para el funcionamiento y gestión de datos correcta.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */
public class AutoriaDAO {
    private static int instancias = 0;

    /**
     * Este método se encarga de insertar en la tabla de autorias las nuevas
     * autorias con sus parámetros, a partir de la autoria pedida por parámetro.
     *
     * @param autor El objeto de autoria que contiene los datos de la nueva instancia.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int insertarAutoria(Autoria autor){
        String sql = "insert into autorias" +
                    "(id,nombre,apellido)" +
                    "values(?,?,?)";
        try(Connection con = Conexion.conectar()){
            PreparedStatement s = con.prepareStatement(sql);
            s.setInt(1, autor.getId());
            s.setString(2, autor.getNombre());
            s.setString(3, autor.getApellido());
                instancias = s.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        return instancias;
    }

    /**
     * Este método se encarga de leer en la tabla de sql la autoria
     * con el id correspondiente que se ha enviado por parámetro.
     *
     * @param id El id de autoria usado para realizar la consulta.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static Autoria leerAutoria(int id){
        Autoria a1 = null;
        String sql = "select * from autorias where id = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, id);
                    ResultSet rs = p.executeQuery();
                    if(rs.next()){
                        int num = rs.getInt(1);
                        String nombre = rs.getString("nombre");
                        String apellido = rs.getString("apellidos");
                        a1 = new Autoria(num,nombre,apellido);
                    }
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        return a1;
    }

    /**
     * Este método se encarga de actualizar en la tabla sql
     * la autoria envidada por parámetro.
     *
     * @param autor El objeto de autoria que contiene los datos de la nueva instancia.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int actualizarAutoria(Autoria autor){
        String sql = "update autorias set nombre = ?, apellido = ? where id = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
                p.setString(1, autor.getNombre());
                p.setString(2, autor.getApellido());
                p.setInt(3, autor.getId());
                instancias = p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        return instancias;
    }

    /**
     * Este método se encarga de eliminar en la tabla sql
     * la autoria envidada por parámetro.
     *
     * Hay que tener cuidado con que autorias se eliminan
     * ya que los libros están relacionados con la id de autor
     * entonces si se elimina un autor sin eliminar también sus libros
     * puede haber conflictos.
     *
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int eliminarAutoria(int id){
        String sql = "delete from autorias where id = ?";
        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, id );
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        return instancias;
    }

    /**
     * Este método se encarga de leer todas las autorias de la tabla sql
     * y de almacenarlos en un HashMap.
     *
     * @return Devuelve el HashMap con los datos de autorias autorizados.
     */
    public static HashMap<Integer, Autoria> leerTodasAutorias(){
        HashMap<Integer, Autoria> autorias = new HashMap<>();
        String sql = "select * from autorias";
        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String nombre= rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Autoria autorLibro = new Autoria(id, nombre, apellido);
                autorias.put(id,autorLibro);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        return autorias;
    }

    /**
     * Este método se encarga de crear en caso de que las autorias no existan o de actualizarlas en caso
     * de que existan pero tengan parámetros diferentes.
     *
     * @param autores Contiene los datos introducidos en el programa para exportarlos a la base de datos.
     * @return Devuelve el número de filas de la tabla afectadas.
     */
    public static int crearActualizarAutorias(HashMap <Integer, Autoria> autores){
        String sql = "insert into autorias " +
                     "(id, nombre, apellido)" +
                     "values (?,?,?)" +
                     "on duplicate key update " +
                     "nombre = values(nombre)," +
                     "apellido = values(apellido)";

        for(Map.Entry<Integer, Autoria> hmAutores : autores.entrySet()){
            Autoria aPrefab = hmAutores.getValue();
        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, aPrefab.getId());
            p.setString(2, aPrefab.getNombre());
            p.setString(3, aPrefab.getApellido());
                instancias = p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
        }
        return instancias;
    }

}

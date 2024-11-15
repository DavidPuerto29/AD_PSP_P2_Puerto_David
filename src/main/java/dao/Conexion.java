package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase se encarga de almacenar los métodos que el programa utiliza para conectarse
 * a la base de datos deseada para el correcto almacenamiento de datos.
 *
 * @author David Puerto Cuenca
 * @version 1.0
 */
public class Conexion {

    /**
     * Se encarga de almacenar los datos para la correcta conexión a la base de datos y
     * también de realizar la conexión con el método Connection.
     *
     * @return Devuelve los datos de la clase Connection.
     * @throws SQLException Puede lanzar una excepción sql.
     */
    protected static Connection conectar() throws SQLException {
        String user = "root";
        String passInsti = "Sandia4you";
        String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
        return DriverManager.getConnection(url, user ,passInsti);
    }

    /**
     * Este método se encarga de crear las tablas sql de autoria y libros del
     * programa para su correcta gestión, en caso de existir las tablas no se
     * crearán como asi indica la sentencia de sql.
     *
     * @throws SQLException Puede lanzar una excepción sql.
     */
    public static void createTables(){
        String sqlAu ="create table if not exists autorias"+
                "(id varchar(50) primary key," +
                "nombre varchar(50) not null," +
                "apellido varchar(50) not null)";
        String sqlLib = "create table if not exists libros"+
                 "(isbn varchar(50) primary key," +
                 "titulo varchar(50) not null," +
                 "idautoria varchar(50) not null," +
                 "foreign key (idautoria) references autorias(id))";

        try(Connection c = conectar()){
            Statement st = c.createStatement();
                st.executeUpdate(sqlAu);
            st = c.createStatement();
                st.executeUpdate(sqlLib);
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos.");
        }
    }
}

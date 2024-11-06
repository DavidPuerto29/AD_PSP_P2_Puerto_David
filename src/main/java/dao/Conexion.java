package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    protected static Connection conectar() throws SQLException {
        String user = "";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
        Connection con = DriverManager.getConnection(url, user ,pass);
        return con;
    }

    public void createTables(){ //comprobar todo
        String sqlAu ="create table if not exists autores"+
                "(id varchar(50) primary key," +
                "nombre varchar(50) not null," +
                "apellido varchar(50))";
        String sqlLib = "create table if not exist libros"+
                 "(isbn varchar(50) primary key," +
                 "titulo varchar(50) not null," +
                 "id autoria varchar(50))";

        try(Connection c = conectar()){
            Statement st = c.createStatement();
                st.executeUpdate(sqlAu);
            st = c.createStatement();
                st.executeUpdate(sqlLib);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

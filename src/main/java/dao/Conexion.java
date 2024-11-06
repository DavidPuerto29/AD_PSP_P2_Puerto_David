package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    protected static Connection conectar() throws SQLException {    //furula
        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
        Connection con = DriverManager.getConnection(url, user ,pass);
        return con;
    }

    public void createTables(){ //furula
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
            throw new RuntimeException(e);
        }
    }
}

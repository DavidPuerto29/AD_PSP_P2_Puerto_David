package dao;

import biblioteca.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LibroDao {
    private static int instancias = -1;

    public static int insertarLibro(Libro libro){  //TEST
        String sql = "insert into libros" +
                "(isbn,titulo,idautoria)" +
                "values(?,?,?)";
        try(Connection con = Conexion.conectar()){
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, libro.getIsbn());
            s.setString(2, libro.getTitulo());
            s.setObject(3, libro.getAutoria());
            instancias = s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int leerLibro(String isbn){    //testear ns paq se usa
        Libro l1 = null;
        String sql = "select * from libros where isbn = ?";
        instancias = 0;

        try(Connection con = Conexion.conectar()){  //ACABAR
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, isbn);
            ResultSet rs = p.executeQuery();
            while(rs.next()){       //COMPROBAR ESTO Q NO ME FIO
                instancias++;
            }
            if(rs.next()){
                String num = rs.getString(1);
                String titulo = rs.getString("titulo");
               // Autoria autorLibro = rs.getObject("idautoria");
              //  l1 = new Libro(isbn,titulo,autorLibro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int actualizarLibro(Libro libro){    //ARREGLAR
        String sql = "update libros set titulo = ?, idautoria = ? where isbn = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, libro.getTitulo());
          //  p.setString(2, libro.getAutoria());
            p.setString(3, libro.getIsbn());
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int eliminarAutoria(String isbn){ //TEST
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

    public static int leerTodasAutorias(HashMap<String, Libro> libros){//test
        String isbn;
        for(Map.Entry<String, Libro> hm : libros.entrySet()){
            isbn = hm.getKey();
            instancias = leerLibro(isbn);
        }
        return instancias;
    }

    public static int crearActualizarLibros(HashMap<String, Libro> libros){  //TEST
       //todo
        return 1;
    }
}

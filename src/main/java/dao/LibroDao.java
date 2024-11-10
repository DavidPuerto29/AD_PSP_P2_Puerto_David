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

public class LibroDao {
    private static int instancias = -1;

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

    public static int leerLibro(String isbn){    //testear ns paq se usa
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
        return instancias;
    }

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
     * Notas se supoone q el libro debe de tener siempre autor por lo tanto podemos llamar al metodo de
     * obtener todos los autores para evitar repeticion de codigo y de tener q hacer otra sentencia sql
     * a si que alk estar siempre el autor creado no deberia de habre nulls etc
     * explicar bonito cuando haga el javadoc
     * @return
     */
    public static HashMap<String, Libro> leerTodosLibros(){//Funciona
        HashMap<String, Libro> libros = new HashMap<>();
        //Reutilizamos el metodo para evitar la repetición de codigo ya que este nos devuelve todas las autorias.
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

    public static int crearActualizarLibros(HashMap<String, Libro> libros){  //TEST
        String sql = "insert into libros" +
                "(isbn,titulo,idautoria)" +
                "values(?,?,?)" +
                "on duplicate key update " +
                "titulo = values(titiulo)," +
                "apellido = values(apellido)";
        return 1;
    }
}

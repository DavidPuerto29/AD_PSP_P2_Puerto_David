package dao;

import biblioteca.Autoria;
import biblioteca.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AutoriaDAO {
    private static int instancias = -1;

    public static int insertarAutoria(Autoria autor){  //Funciona
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
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int leerAutoria(int id){    //testear
        Autoria a1 = null;
        String sql = "select * from autorias where id = ?";
        instancias = 0;

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
                p.setInt(1, id);
                    ResultSet rs = p.executeQuery();
                    while(rs.next()){       //COMPROBAR ESTO Q NO ME FIO
                        instancias++;
                    }
                    if(rs.next()){
                        int num = rs.getInt(1);
                            String nombre = rs.getString("nombre");
                                String apellido = rs.getString("apellidos");
                                    a1 = new Autoria(num,nombre,apellido);
                    }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int actualizarAutoria(Autoria autor){    //TEST
        String sql = "update autorias set nombre = ?, apellido = ? where id = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
                p.setString(1, autor.getNombre());
                p.setString(2, autor.getApellido());
                p.setInt(3, autor.getId());
                instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int eliminarAutoria(int id){ //TEST
        String sql = "delete from autorias where id = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static int leerTodasAutorias(HashMap <Integer, Autoria> autores){//test
        int id;
        for(Map.Entry<Integer, Autoria> hm : autores.entrySet()){
            id = hm.getKey();
            instancias = leerAutoria(id);
        }
        return instancias;
    }

    /*  Hacer pero sera algo tipo si no exite creas si existe actualizas pero a ver como coño ves desde la sql si esta la instancia mientras recorres el bucle
    public static int crearActualizarAutorias(HashMap <Integer, Autoria> autores){  //TEST
        int id;
        for(Map.Entry<Integer, Autoria> hm : autores.entrySet()){
            id = hm.getKey();
            instancias = leerAutoria(id);
        }
        return instancias;
    }
    */
}

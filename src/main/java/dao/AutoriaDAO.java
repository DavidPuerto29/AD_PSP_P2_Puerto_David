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

    public static int leerAutoria(int id){    //testear ns paq se usa
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

    public static int actualizarAutoria(Autoria autor){    //FUNCIONA
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

    public static int eliminarAutoria(int id){ //FUNCIONA
        String sql = "delete from autorias where id = ?";

        try(Connection con = Conexion.conectar()){
            PreparedStatement p = con.prepareStatement(sql);
            p.setInt(1, id );
            instancias = p.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instancias;
    }

    public static HashMap<Integer, Autoria> leerTodasAutorias(){ //Test
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
            throw new RuntimeException(e);
        }
        return autorias;
    }

    public static int crearActualizarAutorias(HashMap <Integer, Autoria> autores){  //Funciona
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
            throw new RuntimeException(e);
        }
        }
        return instancias;
    }

}

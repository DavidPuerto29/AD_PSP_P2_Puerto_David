package dao;

import biblioteca.Autoria;

import static dao.AutoriaDAO.insertarAutoria;

public class TEST {
    public static void main(String[] args) {
        Conexion con = new Conexion();
        Autoria a1 = new Autoria(1,"david","puerto cuenca");
        con.createTables();

        insertarAutoria(a1);
    }
}

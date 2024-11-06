package dao;

import biblioteca.Autoria;

import static dao.AutoriaDAO.*;

public class TEST {
    public static void main(String[] args) {
        Conexion con = new Conexion();
        /*
        Autoria a1 = new Autoria(1,"david","puerto cuenca");
        Autoria a2 = new Autoria(2,"juan","Valdes Marquez");
        Autoria a3 = new Autoria(3,"paco","martin cuenca");
        Autoria a4 = new Autoria(4,"luisa","joaquin cuenca");

         */
        con.createTables();
/*
        insertarAutoria(a1);
        insertarAutoria(a2);
        insertarAutoria(a3);
        insertarAutoria(a4);
*/

        //actualizarAutoria(a4);

        eliminarAutoria(4);
    }
}

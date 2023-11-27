/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConexionDBDe {

    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";
    static final String USER = "David Suarez";
    static final String PASS = "1234";
    static final String QUERY = "SELECT * FROM videojuegos";
    static final String QUERY1 = "SELECT nombre FROM videojuegos where nombre = ?";


    public static void main(String[] args)  {
        Scanner teclado = new Scanner(System.in);
        int numero = 0;      
        while(numero != 10){
            System.out.println("");
            System.out.println("");
        System.out.println("Inserte 10 para salir del menu");
        System.out.println("1 - Busca nombre del juego");
        System.out.println("2 - Lanza una consulta");
        System.out.println("3 - Inserta un videojuego con los datos por parametros");
        System.out.println("4 - Inserta un videjuego pasando por teclado los datos del videojuego: ");
        System.out.println("5 - Inserta l nombre del videojuego que deseas eliminar: ");
            System.out.println("");
        System.out.println("Introduce que deseas hacer : ");
        numero = teclado.nextInt();
        switch(numero){
            case 1 -> {
                teclado.nextLine();
                 System.out.println("Introduce el nombre del videojuego: ");
                 String juego = teclado.nextLine(); 
                 boolean buscaNombres = buscaNombre(juego);
            }
            case 2 -> {
                 System.out.println("Lanzamos consulta : ' en este caso sera Select * from videojuego ' ");
                 System.out.println("");
                 lanzaConsulta(QUERY);
                 System.out.println("");
                 System.out.println("Consulta lanzada");
            }
            case 3 -> {
                System.out.println("Vamos a insertar un videojuego que sera : Fifa, Futbol, 2003-10-18, Ea sport, 60 euros");
                String nombre="Fifa24", genero="Futbol", fecha="2003-10-18", compañia="EA Sport", precio="60";
                nuevoReguistro(nombre, genero, compañia, precio, fecha);
            }
            case 4 -> {   
                teclado.nextLine();
                String nombres, genero, fecha, compañia, precio;
                System.out.println("Introduce el nombre del videojuego : ");
                nombres = teclado.nextLine();
                System.out.println("Introduce el genero del videojuego: ");
                genero = teclado.nextLine();
                System.out.println("Introduce el nombre de la compañia del videojuego: ");
                compañia = teclado.nextLine();
                System.out.println("Introduce el precio del videojuego ");
                precio = teclado.nextLine();
                System.out.println("Introduce la fecha del videojuego (yyyy-mm-d)");
                fecha = teclado.nextLine();
                
                nuevoReguistroPorTeclado(nombres, genero, compañia, precio, fecha);
            }
            case 5 -> {
                teclado.nextLine();
                System.out.println("Introduce el nombre del videojuego que deseas borrar: ");
                String nombreJuego = teclado.nextLine(); 
                boolean borrarJuego = eliminaRegistro(nombreJuego);
            }
        }
    }
                
    }
    public static boolean buscaNombre(String juego) {
        
        boolean resultado = false;         
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS) ;
            PreparedStatement sentencia = conn.prepareStatement(QUERY1);
            sentencia.setString(1, juego);
            ResultSet rs = sentencia.executeQuery();
            
            while (rs.next()) {
                //System.out.println("Nombre: " + rs.getString("Nombre"));
                String nombre = rs.getString("Nombre");
                if (nombre.equals(juego)) {
                     System.out.println("Nombre: " + rs.getString("Nombre"));
                    resultado = true;
                    break;
                }else{
                    resultado = false;
                }
               
            }
        
        rs.close();
       
        if (resultado == true){
            System.out.println("Juego encontrado!!");
        }else {
            System.out.println("Juego no encontrado");
        }
    }catch (SQLException e) {
            e.printStackTrace();
    }
        return resultado;
    }
    public static void lanzaConsulta(String miQuery){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
        Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(QUERY);) {
            while (rs.next()) {
                System.out.print("  ID: " + rs.getInt("id"));
                System.out.print(", Nombre: " + rs.getString("Nombre"));
                System.out.print(", Genero: " + rs.getString("Genero"));
                System.out.print(", Precio: " + rs.getFloat("Precio"));
                System.out.print(", Compañia: " + rs.getString("Compañia"));
                System.out.print(", Fecha Lanzamiento" + rs.getDate("FechaLanzamiento"));
                
                System.out.println("");
            }
             stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void nuevoReguistro(String nombre, String genero, String compañia, String precio, String fecha) {
      try {
          Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  
                        PreparedStatement sentencia = conn.prepareStatement(
                     "insert into videojuegos values (null, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

        
        //String queryInsert = "INSERT INTO `videojuegos` (`id`, `nombre`, `genero`, `fechalanzamiento`, `compañia`, `precio`) VALUES (NULL, '"+nombre+"', '"+genero+"', '"+fecha+"', '"+compañia+"', '"+precio+"')";
              sentencia.setString(1, nombre);
              sentencia.setString(2, genero);
              sentencia.setString(3, fecha);
              sentencia.setString(4, compañia);
              sentencia.setString(5, precio);



              int filasAfectadas = sentencia.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Videojuego añadido");
        } else {
            System.out.println("No se pudo añadir el videojuego");
        }

    } catch (SQLException e) {
            e.printStackTrace();
        }
      }

    public static void nuevoReguistroPorTeclado(String nombre, String genero, String compañia, String precio, String fecha) {
      try {
          Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  
                        PreparedStatement sentencia = conn.prepareStatement(
                     "insert into videojuegos values (null, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

        
        String queryInsert = "INSERT INTO `videojuegos` (`id`, `nombre`, `genero`, `fechalanzamiento`, `compañia`, `precio`) VALUES (NULL, '"+nombre+"', '"+genero+"', '"+fecha+"', '"+compañia+"', '"+precio+"')";
              sentencia.setString(1, nombre);
              sentencia.setString(2, genero);
              sentencia.setString(3, fecha);
              sentencia.setString(4, compañia);
              sentencia.setString(5, precio);
              
              
              int filasAfectadas = sentencia.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Videojuego añadido");
        } else {
            System.out.println("No se pudo añadir el videojuego");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static boolean eliminaRegistro(String nombreJuego){
        boolean resultado = false;
         try{
             Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             
              PreparedStatement sentencia = conn.prepareStatement(
                     "DELETE FROM `videojuegos` where nombre = ( ? )",
                    PreparedStatement.RETURN_GENERATED_KEYS);
              
              String query="DELETE FROM `videojuegos` WHERE `nombre` = '"+nombreJuego+"'";
            sentencia.setString(1, nombreJuego);       

         int filasAfectadas = sentencia.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Videojuego borrado");
        } else {
            System.out.println("No se pudo borrar el videojuego");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
        return resultado;
    } 
}
    



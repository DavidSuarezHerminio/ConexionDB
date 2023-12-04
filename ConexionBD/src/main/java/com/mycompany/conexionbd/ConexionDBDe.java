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

    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd"; //aqui paso la url de la base de datos
    static final String USER = "David Suarez"; // mi usuario
    static final String PASS = "1234"; //mi contraseña 
    static final String QUERY = "SELECT * FROM videojuegos"; //aqui hago una consulta y la guardo en un String llamado QUERY
    static final String QUERY1 = "SELECT nombre FROM videojuegos where nombre = ?"; //Aqui hago otra consulta diferente a la anterior pero de la misma manera 


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
        System.out.println("4 - Inserta un videjuego pasando por teclado los datos del videojuego: ");  //Aqui lo que hago es un menu para antes de lanzar cualquier funcion pueda visualizar y elegir lo que deseo hacer 
        System.out.println("5 - Inserta l nombre del videojuego que deseas eliminar: ");
            System.out.println("");
        System.out.println("Introduce que deseas hacer : ");
        numero = teclado.nextInt();
        switch(numero){
            case 1 -> {
                teclado.nextLine();
                 System.out.println("Introduce el nombre del videojuego: ");
                 String juego = teclado.nextLine();                                 //Aqui a demas de hacer la llamada de la funcion, paso por parametro el nombre del juego a la funcion
                 boolean buscaNombres = buscaNombre(juego);
            }
            case 2 -> {
                 System.out.println("Lanzamos consulta : ' en este caso sera Select * from videojuego ' ");
                 System.out.println("");
                 lanzaConsulta(QUERY); //Aqui paso la consulta anterior mente por paranmetro 
                 System.out.println("");
                 System.out.println("Consulta lanzada");
            }
            case 3 -> {
                System.out.println("Vamos a insertar un videojuego que sera : Fifa, Futbol, 2003-10-18, Ea sport, 60 euros");
                String nombre="Fifa24", genero="Futbol", fecha="2003-10-18", compañia="EA Sport", precio="60";//Aqui guardo en variables todo el videojuego que quiero insertar 
                nuevoReguistro(nombre, genero, compañia, precio, fecha); //Aqui les paso todas las variables para que lo coja por parametro 
            }
            case 4 -> {   
                teclado.nextLine();
                String nombres, genero, fecha, compañia, precio;
                System.out.println("Introduce el nombre del videojuego : ");
                nombres = teclado.nextLine();
                System.out.println("Introduce el genero del videojuego: ");
                genero = teclado.nextLine();
                System.out.println("Introduce el nombre de la compañia del videojuego: ");      //en este caso introduzco por teclado y guardo, para luego pasar por parametro
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
                String nombreJuego = teclado.nextLine();                                            //Aqui hago lo mismo, introduzco el nombre de un videojuego, pero para borrar
                boolean borrarJuego = eliminaRegistro(nombreJuego);
            }
        }
    }
                
    }
    public static boolean buscaNombre(String juego) {
        
        boolean resultado = false;         
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS) ;  //Aqui establezco la conexion a la base de datos, la conexion la almaceno en conn, paso la url mi usuario y mi contraseña, la utilizo para interactuar con la base de datos 
            PreparedStatement sentencia = conn.prepareStatement(QUERY1); // esto lo utilizo para ejecutar consultas sql precompiladas
            sentencia.setString(1, juego);//aqui paso el valor del primer parametro, como solo tengo un parametro solo estara el 1 y lo que pasas, si fueran mas serian 2, 3....
            ResultSet rs = sentencia.executeQuery();//ejecuta y almacena el conjunto de resultados, en este caso executeQuery la ejecuta y guardo en rs
            
                while (rs.next()) {

                    String nombre = rs.getString("Nombre");
                    if (nombre.equals(juego)) { //aqui lo que hago es comparar el nombre que le paso por los de mi base de datos, cuando coincida uno se parara el bucle 
                         System.out.println("Nombre: " + rs.getString("Nombre"));
                        resultado = true;
                        break;
                    }else{
                        resultado = false;
                    }

                }
        
        rs.close(); //cierro el conjunto de resultados 
       
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
    
    //es el unico que dejo igual que la practica anterior, no la edito
    public static void lanzaConsulta(String miQuery){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //Aqui establezco la conexion a la base de datos, la conexion la almaceno en conn, paso la url mi usuario y mi contraseña, la utilizo para interactuar con la base de datos 
        Statement stmt = conn.createStatement(); //el statement lo utilizo para ejecurar consultas sql, el stmt lo utilizo para enviar las consultas, 
        ResultSet rs = stmt.executeQuery(QUERY);) {// el rs alamacena la consulta ejecutada por stmt, el resulSet es un conjunto de resultados que representa los datos devueltos
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
          Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  //Aqui establezco la conexion a la base de datos, la conexion la almaceno en conn, paso la url mi usuario y mi contraseña, la utilizo para interactuar con la base de datos 
                        PreparedStatement sentencia = conn.prepareStatement(// esto lo utilizo para ejecutar consultas sql precompiladas
                     "insert into videojuegos values (null, ?, ?, ?, ?, ?)", //esta es la consulta sql que se va ejecutar los ? los utilizo para pasar los parametros mass adelante, el null lo pongo cuando hay algo que se define por si solo sin tener que introducirlo como por ejemplo un id
                    PreparedStatement.RETURN_GENERATED_KEYS);

        
              sentencia.setString(1, nombre);
              sentencia.setString(2, genero);
              sentencia.setString(3, fecha);             //aqui paso el valor del parametro, como tengo varios parametros indico antes cual sera.
              sentencia.setString(4, compañia);
              sentencia.setString(5, precio);



         //copruebo que el videojuego ha sido insertado:
        int filasAfectadas = sentencia.executeUpdate(); //si alguna fila ha sido modificada el numero de update de filas no seria 0 y entraria en "videojuego añadido".
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
          Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  //Aqui establezco la conexion a la base de datos, la conexion la almaceno en conn, paso la url mi usuario y mi contraseña, la utilizo para interactuar con la base de datos 
                        PreparedStatement sentencia = conn.prepareStatement(// esto lo utilizo para ejecutar consultas sql precompiladas
                     "insert into videojuegos values (null, ?, ?, ?, ?, ?)",//esta es la consulta sql que se va ejecutar los ? los utilizo para pasar los parametros mass adelante, el null lo pongo cuando hay algo que se define por si solo sin tener que introducirlo como por ejemplo un id
                    PreparedStatement.RETURN_GENERATED_KEYS);

        
              sentencia.setString(1, nombre);
              sentencia.setString(2, genero);
              sentencia.setString(3, fecha);      //aqui paso el valor del parametro, como tengo varios parametros indico antes cual sera.
              sentencia.setString(4, compañia);
              sentencia.setString(5, precio);
              
              
              
              //copruebo que el videojuego ha sido insertado : 
              int filasAfectadas = sentencia.executeUpdate(); //si alguna fila ha sido modificada el numero de update de filas no seria 0 y entraria en "videojuego añadido".
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
             Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);//Aqui establezco la conexion a la base de datos, la conexion la almaceno en conn, paso la url mi usuario y mi contraseña, la utilizo para interactuar con la base de datos 
              PreparedStatement sentencia = conn.prepareStatement(// esto lo utilizo para ejecutar consultas sql precompiladas
                     "DELETE FROM `videojuegos` where nombre = ( ? )",//esta es la consulta sql que se va ejecutar los ? los utilizo para pasar los parametros mass adelante, el null lo pongo cuando hay algo que se define por si solo sin tener que introducirlo como por ejemplo un id
                    PreparedStatement.RETURN_GENERATED_KEYS);
              
            sentencia.setString(1, nombreJuego);   //aqui paso el valor del primer parametro, como solo tengo un parametro solo estara el 1 y lo que pasas, si fueran mas serian 2, 3....    

            
            //copruebo que el videojuego ha sido borrado : 
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
    



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius Teider
 */
public class ConexaoMysql {

    private static boolean status = false;
    private static Connection connection = null;

    public static java.sql.Connection getConexaoMySQL() throws SQLException {
        
        
        if(connection != null){
            if(!connection.isClosed()){
                return connection;
            }
        }        
        
        try {
            
            
            
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            
            //String host = "10.22.7.200";
            String host = "192.168.52.4";
            String database = "sisbm_novo";
            String url = "jdbc:mysql://" + host + "/" + database;
            String username = "root";
            String password = "ka3031no&";
            
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                status = true;
                System.out.println("Conectado com sucesso!");
            } else {
                status = false;
                System.out.println("Não foi possivel realizar conexão");
            }

            return connection;

        } catch (ClassNotFoundException e) {

            System.out.println("O driver expecificado nao foi encontrado.");
            return null;

        } catch (SQLException e) {
            
            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
            
        }
        
    }
    
    public  static boolean getStatus(){
        return status;
    }
    
    public static void fecharConexao(){
        
        try {
            ConexaoMysql.getConexaoMySQL().close();
        } catch (SQLException ex) {
            System.out.println("Não foi possivel fechar a conexão do Banco de Dados.");
        }
        
    }

}

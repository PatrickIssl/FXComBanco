/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.TabelaViaturasDespacho;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Teider
 */
public class TabelaViaturasDespachoDAO {
    
    
    public List<TabelaViaturasDespacho> getViaturasDespachos(int idrgo) throws SQLException{
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT rgo_horarios.idrgo_horarios, " +
                "        rgo_horarios.nome_viatura," +
                "        rgo_horarios.h_saidaQ," +
                "        rgo_horarios.h_chegadaL," +
                "        rgo_horarios.h_saidaL," +
                "        rgo_horarios.h_chegadaH," +
                "        rgo_horarios.h_saidaH," +
                "        rgo_horarios.h_chegadaQ," +
                "        rgo_horarios.nome_ultimo_evento," +
                "        rgo_horarios.qta FROM `rgo_horarios` WHERE `rgo_idrgo` = ?");
        
        
        preparedStatement.setInt(1, idrgo);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<TabelaViaturasDespacho> viaturasDespachos = new ArrayList<>();
        
        while(resultSet.next()){
            viaturasDespachos.add(
                new TabelaViaturasDespacho(
                        resultSet.getInt(1),
                        resultSet.getTimestamp(3),
                        resultSet.getTimestamp(4),
                        resultSet.getTimestamp(5),
                        resultSet.getTimestamp(6),
                        resultSet.getTimestamp(7),
                        resultSet.getTimestamp(8),
                        resultSet.getString(2),
                        resultSet.getInt(10),
                        resultSet.getInt(9)
                )
            );
        }    
        
        return viaturasDespachos;
        
        
    }
    
    
    public boolean atualizarHorario(int idHorarios, String campo, int ultimoEvento) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo_horarios " +
                "SET "+campo+" = current_timestamp, nome_ultimo_evento = ? " +
                "WHERE " +
                " idrgo_horarios = ?");
        
        preparedStatement.setInt(1, ultimoEvento);
        preparedStatement.setInt(2, idHorarios);
        
        System.out.println(preparedStatement.toString());
        
        preparedStatement.execute();
        
        
        return true;
    }
    
    public boolean atualizarHorarioString(int idHorarios, String campo, int ultimoEvento) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo_horarios " +
                "SET "+campo+", nome_ultimo_evento = ? " +
                "WHERE " +
                " idrgo_horarios = ?");
        
        preparedStatement.setInt(1, ultimoEvento);
        preparedStatement.setInt(2, idHorarios);
        
        System.out.println(preparedStatement.toString());
        
        preparedStatement.execute();
        
        
        return true;
    }
    
}

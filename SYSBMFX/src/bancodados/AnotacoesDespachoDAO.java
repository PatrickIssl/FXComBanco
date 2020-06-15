/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.AnotacoesDespacho;
import classes.Subnatureza;
import classes.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Vinicius Teider
 */
public class AnotacoesDespachoDAO {
    
    
    public List<AnotacoesDespacho> getAnotacoes(int idrgo) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT " +
                "	rgo_anotacoes_despacho.idrgo_anotacoes_despacho," +
                "	rgo_anotacoes_despacho.fk_rgo," +
                "	rgo_anotacoes_despacho.anotacoes," +
                "	rgo_anotacoes_despacho.data_hora," +
                "	rgo_anotacoes_despacho.rg_responsavel," +
                "	rgo_anotacoes_despacho.nome_responsavel " +
                "FROM" +
                "	rgo_anotacoes_despacho " +
                "WHERE" +
                "	rgo_anotacoes_despacho.fk_rgo = ?");
        
        preparedStatement.setInt(1, idrgo);

        System.out.println(preparedStatement.toString());
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<AnotacoesDespacho> anotacoes = new ArrayList<>();

        while (resultSet.next()) {
            anotacoes.add(
                    new AnotacoesDespacho(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getTimestamp(4),
                            resultSet.getString(5),
                            resultSet.getString(6)                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return anotacoes;        
        
    } 
    
    public boolean insert(int idrgo, String descritivo,int tipoNotificacao, int notificar) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO `sisbm_novo`.`rgo_anotacoes_despacho` ( " +
                    "	`fk_rgo`, " +
                    "	`anotacoes`, " +
                    "	`data_hora`, " +
                    "	`rg_responsavel`, " +
                    "	`nome_responsavel`, " +
                    "	`tipo_notificacao`, " +
                    "	`notificar`) " +
                    "VALUES ( " +
                    "	?, " +
                    "	?, " +
                    "	current_timestamp, " +
                    "	?, " +
                    "	?," +
                    "	?," +
                    "	?);");
        
        preparedStatement.setInt(1, idrgo);
        preparedStatement.setString(2, descritivo);
        preparedStatement.setString(3, Usuario.getRG());
        preparedStatement.setString(4, Usuario.getNome_efetivo());
        
        preparedStatement.setInt(5, tipoNotificacao);
        preparedStatement.setInt(6, notificar);
        
        return preparedStatement.execute();
        
    }
    
    
}

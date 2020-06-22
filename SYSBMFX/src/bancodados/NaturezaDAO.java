/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.Natureza;
import classes.Subnatureza;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class NaturezaDAO {

    public NaturezaDAO() {
    }

    public List<Natureza> getNaturezas() throws SQLException {

        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT ts_nateventos.idts_nateventos, ts_nateventos.evento FROM ts_nateventos").executeQuery();

        List<Natureza> naturezas = new ArrayList<>();

        naturezas.add(new Natureza(-1, "Selecione..."));
        
        while (resultSet.next()) {
            naturezas.add(
                    new Natureza(
                            resultSet.getInt("idts_nateventos"),
                            resultSet.getString("evento")
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return naturezas;

    }
    
    
    public List<Subnatureza> getSubnaturezas(int idnatureza) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT " +
                "ts_subnateventos.idts_subnateventos, " +
                "ts_subnateventos.descricao, " +
                "ts_subnateventos.ts_nateventos_idts_nateventos " +
                "FROM " +
                "ts_subnateventos " +
                "WHERE ts_subnateventos.ativo AND ts_subnateventos.ts_nateventos_idts_nateventos = ?;");
        
        preparedStatement.setInt(1, idnatureza);

        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Subnatureza> subnaturezas = new ArrayList<>();
        
        subnaturezas.add(new Subnatureza(-1, "Selecione...",0));

        while (resultSet.next()) {
            subnaturezas.add(
                    new Subnatureza(
                            resultSet.getInt("idts_subnateventos"),
                            resultSet.getString("descricao"),
                            resultSet.getInt("ts_nateventos_idts_nateventos")
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return subnaturezas;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.Cidade;
import classes.Natureza;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import classes.Bairro;
import classes.Logradouro;
import java.sql.PreparedStatement;

/**
 *
 * @author Vinicius Teider
 */
public class EnderecoDAO {

    public List<Cidade> getCidades() throws SQLException {

        //Cria a conexao e junto executa a query de busca
        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "	idcad_municipio, "
                + "	municipio "
                + "FROM "
                + "	cad_municipio "
                + "ORDER BY "
                + "	municipio").executeQuery();

        //Lista de cidades
        List<Cidade> cidades = new ArrayList<>();

        //Popula lista de cidades 
        while (resultSet.next()) {
            cidades.add(
                    new Cidade(
                            resultSet.getInt("idcad_municipio"),
                            resultSet.getString("municipio")
                    )
            );
            
            
        }

        ConexaoMysql.fecharConexao();

        return cidades;

    }

    public List<Bairro> getBairro(int idMunicipio) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "idcad_bairro,bairro,cad_municipio_idcad_municipio "
                + "FROM "
                + "cad_municipio "
                + "INNER JOIN cad_bairro ON  cad_municipio_idcad_municipio = idcad_municipio "
                + "WHERE cad_municipio_idcad_municipio = ? AND `status` = 1 ");

        preparedStatement.setInt(1, idMunicipio);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Bairro> bairros = new ArrayList<>();

        while (resultSet.next()) {
            Bairro bairro = new Bairro(
                    resultSet.getInt("idcad_bairro"),
                    resultSet.getString("bairro"),
                    resultSet.getInt("cad_municipio_idcad_municipio")
            );
            
            bairros.add(bairro);
        }

        ConexaoMysql.fecharConexao();

        return bairros;

    }

      public List<Logradouro> getLogradouro(int idMunicipio) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "idcad_logradouro,nome_logradouro,cad_municipio_idcad_municipio "
                + "FROM "
                + "cad_localizacao "
                + "WHERE cad_municipio_idcad_municipio = ? AND `ativo` = 1 ");

        preparedStatement.setInt(1, idMunicipio); 

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Logradouro> logradouros = new ArrayList<>();
 
        while (resultSet.next()) {
            Logradouro logradouro = new Logradouro(
                    resultSet.getInt("idcad_logradouro"),
                    resultSet.getString("nome_logradouro"),
                    resultSet.getInt("cad_municipio_idcad_municipio")
            );
            
            logradouros.add(logradouro);
        }

        ConexaoMysql.fecharConexao();

        return logradouros;

    }

    
}

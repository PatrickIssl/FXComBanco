/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import classes.Usuario;

/**
 *
 * @author Vinicius Teider
 */
public class Login {
    
   public boolean realizarLogin(String usuario, String senha) throws SQLException, NoSuchAlgorithmException{
         

        PreparedStatement statement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT" +
"	sisefetivo_novo.view_dados_efetivo.idefetivo," +
"	sisefetivo_novo.view_dados_efetivo.nome_completo," +
"	sisefetivo_novo.view_dados_efetivo.RG," +
"	sisefetivo_novo.view_dados_efetivo.email," +
"	sisefetivo_novo.view_dados_efetivo.obm_idobm," +
"	sisefetivo_novo.view_dados_efetivo.subunidade_idsubunidade," +
"	sisefetivo_novo.view_dados_efetivo.id_municipio_presta_servico," +
"	sisefetivo_novo.view_dados_efetivo.graduacao," +
"	sisefetivo_novo.view_dados_efetivo.CPF," +
"       sisefetivo_novo.view_dados_efetivo.cidade_presta_servico"+                
" FROM " +
"	sisefetivo_novo.view_dados_efetivo " +
"	INNER JOIN sisefetivo_novo.seguranca_users ON sisefetivo_novo.seguranca_users.efetivo_idefetivo = sisefetivo_novo.view_dados_efetivo.idefetivo \n" +
" WHERE " +
"	sisefetivo_novo.view_dados_efetivo.efetivo_ts_status_idefetivo_ts_status = 2" +
"	AND RG = ? AND sisefetivo_novo.seguranca_users.pswd = ?");
       
        
        
        statement.setString(1, usuario);
        statement.setString(2, new MD5().toMD5(senha));
        
        System.out.println(statement.toString());
        
       ResultSet resultSet = statement.executeQuery();
       
       
       
       while (resultSet.next()){
           
           
           Usuario.setIdefetivo(resultSet.getInt("idefetivo"));
           Usuario.setNome_efetivo(resultSet.getString("nome_completo"));
           Usuario.setRG(resultSet.getString("RG"));
           Usuario.setEmail(resultSet.getString("email"));
           Usuario.setObm_idobm(resultSet.getInt("obm_idobm"));
           Usuario.setSubunidade_idsubunidade(resultSet.getInt("subunidade_idsubunidade"));
           Usuario.setMunicipio_presta_servico(resultSet.getInt("id_municipio_presta_servico"));
           Usuario.setGraduacao(resultSet.getString("graduacao"));
           Usuario.setCpf(resultSet.getString("CPF"));
           Usuario.setNomeCidade(resultSet.getString("cidade_presta_servico"));
           
           ConexaoMysql.fecharConexao();
           
           return true;
       }
       
       ConexaoMysql.fecharConexao();
       
       return false;
   }
    
    
}

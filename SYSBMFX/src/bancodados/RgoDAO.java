/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.MeioAviso;
import classes.Natureza;
import classes.RGO;
import classes.Subnatureza;
import classes.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sysbmfx.TelaCadastro;

/**
 *
 * @author Administrador
 */
public class RgoDAO {
    
    public RGO getRGO(int idrgo) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT" +
                    "	rgo.idrgo," +
                    "	rgo.nr_rgo," +
                    "	rgo.datahora_recebimento," +
                    "	rgo.ts_meioaviso_idts_meioaviso," +
                    "	rgo.solicitante_nome," +
                    "	rgo.solicitante_telefone," +
                    "	rgo.ts_nateventos_idts_nateventos," +
                    "	rgo.ts_subnateventos_idts_subnateventos," +
                    "	rgo.urgente," +
                    "	rgo.descritivo," +
                    "	rgo.perimetro," +
                    "	rgo.municipio," +
                    "	rgo.bairro," +
                    "	rgo.endereco_solicitacao," +
                    "	rgo.tipo_num," +
                    "	rgo.endereco_num," +
                    "	rgo.esquina," +
                    "	rgo.ponto_referencia," +
                    "	rgo.atendente," +
                    "	rgo.id_atendente," +
                    "	rgo.sb," +
                    "	rgo.lat," +
                    "	rgo.lng," +
                    "	rgo.fk_id_posto," +
                    "	rgo.id_municipio," +
                    "	rgo.id_obm_designada," +
                    "	rgo.fk_id_obm," +
                    "	rgo.fk_id_unidade," +
                    "	ts_nateventos.evento," +
                    "	ts_subnateventos.descricao " +
                    "   FROM" +
                    "	rgo" +
                    "	INNER JOIN ts_nateventos ON rgo.ts_nateventos_idts_nateventos = ts_nateventos.idts_nateventos" +
                    "	INNER JOIN ts_subnateventos ON rgo.ts_subnateventos_idts_subnateventos = ts_subnateventos.idts_subnateventos " +
                    "   WHERE" +
                    "	rgo.idrgo = ?");
        
        preparedStatement.setInt(1, idrgo);
        
        System.out.println(preparedStatement.toString());
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
            Natureza natureza = new Natureza(resultSet.getInt(7), resultSet.getString(29));
            Subnatureza subNatureza = new Subnatureza(resultSet.getInt(8), resultSet.getString(30),0);
            
            RGO rgo = new RGO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7),
                    resultSet.getInt(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15),
                    resultSet.getInt(16),
                    resultSet.getString(17),
                    resultSet.getString(18),
                    resultSet.getString(19),
                    resultSet.getInt(20),
                    resultSet.getString(21),
                    resultSet.getString(22),
                    resultSet.getString(23),
                    resultSet.getInt(24),
                    resultSet.getInt(25),
                    resultSet.getInt(26),
                    resultSet.getInt(27),
                    resultSet.getInt(28),
                    natureza,
                    subNatureza
            );
                        
            ConexaoMysql.fecharConexao();
            
            return rgo;
        }
        
        ConexaoMysql.fecharConexao();
        return null;
    }
    
    public List<MeioAviso> getMeioAviso() throws SQLException {

        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT idts_meioaviso, aviso FROM ts_meioaviso").executeQuery();

        List<MeioAviso> meioAvisos = new ArrayList<>();

        while (resultSet.next()) {
            meioAvisos.add(
                    new MeioAviso(
                            resultSet.getInt("idts_meioaviso"),
                            resultSet.getString("aviso")
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return meioAvisos;

    }
      public String pegarNumRgo() {
       PreparedStatement preparedStatement;
        try {           
            preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT" +
"	CONCAT(" +
"	sisbm_novo.rgo_indice.ano," +
"	REPLACE ( REPLACE ( sigmavi.obm.Obm, 'º', '' ), ' ', '' )," +
"	( sisbm_novo.rgo_indice.idrgo ) + 1" +
"	) " +
"       FROM" +
"	sisbm_novo.rgo_indice" +
"	INNER JOIN sigmavi.obm ON sigmavi.obm.id_obm = sisbm_novo.rgo_indice.obm" +
"       WHERE" +
"	sisbm_novo.rgo_indice.obm = ?" +
"	AND sisbm_novo.rgo_indice.ano = YEAR ( now( ) ) " +
"	AND sisbm_novo.rgo_indice.tipo = ? ");         
                preparedStatement.setInt(1, Usuario.getObm_idobm());
                preparedStatement.setString(2,(Usuario.isMilitarADC() ? "MILITAR" :"ADC"));     
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    String numrgo = resultSet.getString(1);
                         ConexaoMysql.fecharConexao();
                         return numrgo;
                }
           
        } catch (SQLException ex) {
            Logger.getLogger(TelaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
}

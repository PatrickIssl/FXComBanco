/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.Combo;
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
import sysbmfx.FormularioAberturaController;

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
                    resultSet.getTimestamp(3),
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
"	sisbm_novo.ts_prefixos_rgos.prefixo_obm," +
"	sisbm_novo.rgo_indice.idrgo " +
"	) " +
"       FROM" +
"	sisbm_novo.rgo_indice" +
"	INNER JOIN sigmavi.obm ON sigmavi.obm.id_obm = sisbm_novo.rgo_indice.obm" +
"       INNER JOIN sisbm_novo.ts_prefixos_rgos "+
"       WHERE" +
"	sisbm_novo.rgo_indice.obm = ?" +
"	AND sisbm_novo.rgo_indice.ano = YEAR ( now( ) ) " +
"	AND sisbm_novo.rgo_indice.tipo = ? " +
"       AND sisbm_novo.ts_prefixos_rgos.codigo_obm = ? " +
"       AND sisbm_novo.ts_prefixos_rgos.corpdec = ?");         
                preparedStatement.setInt(1, Usuario.getObm_idobm());
                preparedStatement.setString(2,(Usuario.isMilitarADC() ? "MILITAR" :"PBC"));     
                preparedStatement.setInt(3, Usuario.getObm_idobm());
                preparedStatement.setInt(4,(Usuario.isMilitarADC() ? 0 : 1));     
                
                ResultSet resultSet = preparedStatement.executeQuery();
                
                atualizarIndice();
                
                while(resultSet.next()){
                    String numrgo = resultSet.getString(1);
                         ConexaoMysql.fecharConexao();
                         return numrgo;
                }
                
                inserirIndice();
                
                return pegarNumRgo();
           
        } catch (SQLException ex) {
            Logger.getLogger(FormularioAberturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
    public void atualizarIndice() throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo_indice SET idrgo = idrgo+1 WHERE obm = ? AND ano = YEAR(CURRENT_DATE) AND tipo = ?;");
        
        preparedStatement.setInt(1, Usuario.getObm_idobm());
        preparedStatement.setString(2,(Usuario.isMilitarADC() ? "MILITAR" :"PBC")); 
        
        preparedStatement.executeUpdate();
    
    }
    
    public void inserirIndice() throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO rgo_indice(obm,ano,idrgo,tipo) VALUES(?,YEAR(CURRENT_DATE),'1',?)");
        
        preparedStatement.setInt(1, Usuario.getObm_idobm());
        preparedStatement.setString(2,(Usuario.isMilitarADC() ? "MILITAR" :"PBC")); 
        
        preparedStatement.execute();
    
    }
    
    
    public List<Combo> getIntervencao() throws SQLException{
        
        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT idintervencao, intervencao FROM ts_intervencao where tipo = 'Cancelada' ORDER BY intervencao").executeQuery();

        List<Combo> intervencoes = new ArrayList<>();

        while (resultSet.next()) {
            intervencoes.add(
                    new Combo(
                            resultSet.getInt(1),
                            resultSet.getString(2)
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return intervencoes;
        
    }
    
    
    public void cancelarOcorrencia(int idIntervencao, String historico, int idrgo) throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo SET ts_statusbasico_idts_statusbasico = 6,"
                + " realizados = 'Ocorrência cancelada.',"
                + " ts_intervencao_idts_intervencao = ?,"
                + " historico = ? "
                + " WHERE idrgo = ? ");
        
        preparedStatement.setInt(1, idIntervencao);
        preparedStatement.setString(2, historico);
        preparedStatement.setInt(3, idrgo);
        
        
        System.out.println(preparedStatement.toString());
        
        preparedStatement.execute();
        
    }
    
    public void encerrarOcorrencia(int idrgo) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo SET ts_statusbasico_idts_statusbasico = 6, realizados = 'Ocorrência repassada para outro orgão.', ts_intervencao_idts_intervencao = 13 WHERE idrgo = ?");
        
        preparedStatement.setInt(1, idrgo);
        
        preparedStatement.execute();
        
        
    }
    
    
    
    public List<RGO> buscarOcorrencia(String nrRgo, int idObm, int idFracao, int idMunicipio, int idPosto, int idNatureza, int idSubnatureza, String endereco, String vitima, String data1, String data2, int idOficial, List idStatus) throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT " +
                    "rgo.idrgo, " +
                    "rgo.nr_rgo, " +
                    "rgo.fk_id_unidade, " +
                    "rgo.fk_id_posto, " +
                    "rgo.id_municipio, " +
                    "rgo.id_obm_designada, " +
                    "rgo.ts_nateventos_idts_nateventos, " +
                    "rgo.ts_subnateventos_idts_subnateventos, " +
                    "IF(rgo.tipo_num = 'Esquina',CONCAT(rgo.endereco_solicitacao,' ESQUINA COM ',rgo.esquina),CONCAT(rgo.endereco_solicitacao,', ',tipo_num,' ',endereco_num)) AS endereco, " +
                    "rgo.id_oficial_area, " +
                    "rgo.ts_statusbasico_idts_statusbasico, " +
                    "rgo_vitima.nome_vitima AS vitimas, " +
                    "rgo.datahora_recebimento, " +
                    "sigmavi_obm.Obm, " +
                    "rgo.municipio, " +
                    "ts_nateventos.evento, " +
                    "ts_subnateventos.descricao, " +
                    "oficial_area, " +
                    "IF (id_oficial_area <> 0, oficial_area, IF(id_chefe_guarnicao <> 0, chefe_guarnicao, atendente)) AS responsavel, " +
                    "GROUP_CONCAT(DISTINCT ' - ',rgo_viaturas.nome_viatura SEPARATOR '<BR/>') AS viaturas, " +
                    "ts_statusbasico.`status`, " +
                    "view_area_atuacao.id_fracao, " +
                    "view_area_atuacao.nome_fracao, " +
                    "rgo.lat, " +
                    "rgo.lng " +
                "FROM " +
                    "rgo " +
                "LEFT JOIN rgo_vitima ON rgo_vitima.rgo_idrgo = rgo.idrgo " +
                "LEFT JOIN sigmavi_obm ON sigmavi_obm.id_obm = rgo.id_obm_designada " +
                "LEFT JOIN ts_nateventos ON rgo.ts_nateventos_idts_nateventos = ts_nateventos.idts_nateventos " +
                "LEFT JOIN ts_subnateventos ON rgo.ts_subnateventos_idts_subnateventos = ts_subnateventos.idts_subnateventos " +
                "LEFT JOIN rgo_viaturas ON rgo_viaturas.rgo_idrgo = rgo.idrgo " +
                "LEFT  JOIN ts_statusbasico ON rgo.ts_statusbasico_idts_statusbasico = ts_statusbasico.idts_statusbasico " +
                "LEFT JOIN view_area_atuacao ON view_area_atuacao.id_cidade = rgo.id_municipio " +
                "GROUP BY idrgo " +
                "ORDER BY rgo.datahora_recebimento DESC");
        
    
        return null;
    }
    
    
    
    
}

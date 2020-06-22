/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.ListaRGO;
import classes.Usuario;
import classes.Viaturas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Teider
 */

public class DespachoDAO {

    public int getCidadeCapitoes() throws SQLException{
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT cad_funcao_idcad_funcao FROM `cad_escala` WHERE `id_efetivo` = ? AND `data_escala` = current_date and cad_funcao_idcad_funcao = 38");
        
        preparedStatement.setString(1, Usuario.getRG());
        
        int cidadesCapitoes = Usuario.getMunicipio_presta_servico();
        
        if(preparedStatement.executeQuery().getFetchSize() > 0){
            cidadesCapitoes = 95;
        }

        return cidadesCapitoes;
    }
    
    public List<ListaRGO> getDespachar(int cidadesCapitoes) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT" +
                    "	rgo.idrgo," +
                    "	sigmavi.obm.Obm," +
                    "	rgo.municipio," +
                    "	rgo.bairro," +
                    "	ts_subnateventos.descricao," +
                    "	DATE_FORMAT(datahora_recebimento,'%d/%m %H:%i')," +
                    "	rgo.lat," +
                    "	rgo.lng," +
                    "	rgo.ts_nateventos_idts_nateventos, " +
                    "   rgo_anotacoes_despacho.tipo_notificacao,"+
                    "   rgo_anotacoes_despacho.idrgo_anotacoes_despacho "+
                    "FROM" +
                    "	rgo" +
                    "	LEFT JOIN rgo_viaturas ON rgo_viaturas.rgo_idrgo = rgo.idrgo" +
                    "	LEFT JOIN rgo_horarios ON rgo_horarios.rgo_idrgo = rgo.idrgo " +
                    "	AND rgo_viaturas.idsigmavi_viaturas = rgo_horarios.idsigmavi_viatura" +
                    "	INNER JOIN ts_subnateventos ON rgo.ts_subnateventos_idts_subnateventos = ts_subnateventos.idts_subnateventos" +
                    "	INNER JOIN cad_areaatuacao_municipio ON cad_areaatuacao_municipio.idmunicipio_2 = rgo.id_municipio" +
                    "	INNER JOIN sigmavi.obm ON sigmavi.obm.id_obm = rgo.id_obm_designada " +
                    "   LEFT JOIN rgo_anotacoes_despacho on rgo_anotacoes_despacho.fk_rgo = rgo.idrgo AND rgo_anotacoes_despacho.notificar = 1 " +
                    "WHERE" +
                    "	(" +
                    "		ISNULL( rgo_viaturas.idsigmavi_viaturas ) " +
                    "		AND DATEDIFF( NOW( ), rgo.datahora_recebimento ) < 3 " +
                    "		AND cad_areaatuacao_municipio.idmunicipio_1 = ? " +
                    "		AND ts_statusbasico_idts_statusbasico IN (1,3) "+
                    "	) " +
                    "ORDER BY idrgo DESC");
        
        preparedStatement.setInt(1, cidadesCapitoes);
                
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<ListaRGO> rgos = new ArrayList<>();

        while (resultSet.next()) {
            rgos.add(
                    new ListaRGO(
                            resultSet.getInt(1), 
                            resultSet.getString(2), 
                            resultSet.getString(3), 
                            resultSet.getString(4), 
                            resultSet.getString(5), 
                            resultSet.getString(6),
                            resultSet.getInt(10),
                            resultSet.getInt(11) )
            );
        }
        
        //ConexaoMysql.fecharConexao();
        
        return rgos;
    
    }
    
    public List<ListaRGO> getDespachadas(int cidadesCapitoes) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT" +
                "	rgo.idrgo," +
                "	sigmavi.obm.Obm," +
                "	rgo.municipio," +
                "	rgo.bairro," +
                "	ts_subnateventos.descricao," +
                "	DATE_FORMAT( datahora_recebimento, '%d/%m %H:%i' )," +
                "	rgo_viaturas.nome_viatura," +
                "	rgo_viaturas.idsigmavi_viaturas," +                
                "	rgo.lat," +
                "	rgo.lng," +
                "	rgo.ts_nateventos_idts_nateventos," +
                "	MID( rgo.solicitante_telefone, 1, 2 ) AS ddd," +
                "	MID( rgo.solicitante_telefone, 3 ) AS telefone," +
                "	rgo.solicitante_nome," +
                "	rgo.descritivo," +
                "	rgo.bairro," +
                "	rgo.endereco_solicitacao," +
                "	rgo.endereco_num," +
                "	rgo.esquina," +
                "	rgo.ponto_referencia," +
                "	rgo.empenhada_samu, " +
                "       rgo_anotacoes_despacho.tipo_notificacao,"+
                "       rgo_anotacoes_despacho.idrgo_anotacoes_despacho "+
                "FROM" +
                "	rgo_viaturas" +
                "	INNER JOIN rgo_horarios ON rgo_horarios.idsigmavi_viatura = rgo_viaturas.idsigmavi_viaturas " +
                "	AND rgo_horarios.rgo_idrgo = rgo_viaturas.rgo_idrgo " +
                "	AND rgo_horarios.tipo_viatura = rgo_viaturas.tipo_viatura " +
                "	AND rgo_horarios.indexador_saidas = rgo_viaturas.indexador_saidas " +
                "	AND rgo_horarios.`data` = rgo_viaturas.`data`" +
                "	INNER JOIN rgo ON rgo_viaturas.rgo_idrgo = rgo.idrgo " +
                "	AND rgo_horarios.rgo_idrgo = rgo.idrgo" +
                "	INNER JOIN ts_subnateventos ON rgo.ts_subnateventos_idts_subnateventos = ts_subnateventos.idts_subnateventos" +
                "	INNER JOIN cad_areaatuacao_municipio ON cad_areaatuacao_municipio.idmunicipio_2 = rgo.id_municipio" +
                "	INNER JOIN sigmavi.obm ON sigmavi.obm.id_obm = rgo.id_obm_designada " +
                "       LEFT JOIN rgo_anotacoes_despacho on rgo_anotacoes_despacho.fk_rgo = rgo.idrgo AND rgo_anotacoes_despacho.notificar = 1 " +
                "WHERE" +
                "	cad_areaatuacao_municipio.idmunicipio_1 = ? " +
                "	AND IFNULL( rgo_horarios.nome_ultimo_evento, 0 ) <> 7 " +
                "	AND rgo.ts_statusbasico_idts_statusbasico <> 6 " +
                "	AND DATEDIFF( now( ), rgo.datahora_recebimento ) < 30 " +
                "GROUP BY" +
                "	rgo_viaturas.idsigmavi_viaturas," +
                "	rgo_viaturas.rgo_idrgo," +
                "	rgo_viaturas.tipo_viatura," +
                "	rgo_viaturas.indexador_saidas," +
                "	rgo_viaturas.`data` " +
                "ORDER BY" +
                "	rgo_viaturas.rgo_idrgo," +
                "	rgo_viaturas.tipo_viatura," +
                "	rgo_viaturas.indexador_saidas," +
                "	rgo_viaturas.`data`");
        
        preparedStatement.setInt(1, cidadesCapitoes);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<ListaRGO> rgos = new ArrayList<>();

        
        while (resultSet.next()) {
            
            if(rgos.size() > 0){
                if(resultSet.getInt(1) == rgos.get( rgos.size() -1 ).getIdrgo()){
                    
                    rgos.get( rgos.size() -1 ).getViaturas().add(
                        new Viaturas(
                            resultSet.getInt(8),
                            resultSet.getString(7)
                        )
                    );
                    
                    continue;
                }
            }
            
            ListaRGO listaRGO = new ListaRGO(
                            resultSet.getInt(1), 
                            resultSet.getString(2), 
                            resultSet.getString(3), 
                            resultSet.getString(4), 
                            resultSet.getString(5), 
                            resultSet.getString(6),
                            resultSet.getInt(22), 
                            resultSet.getInt(23)
            );
            
            listaRGO.getViaturas().add(new Viaturas(
                    resultSet.getInt(8),
                    resultSet.getString(7)
            ));            
            
            rgos.add(listaRGO);
        }
        
        //ConexaoMysql.fecharConexao();
        return rgos;
    
    }
    
    public void atualizarNotificacao(int idNotificacao) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE `sisbm_novo`.`rgo_anotacoes_despacho` SET `notificar` = 0 WHERE `idrgo_anotacoes_despacho` = ?");
        preparedStatement.setInt(1, idNotificacao);
        
        preparedStatement.executeUpdate();
        
        
    }
    
    
    private int getUltimoEvento(int idHorario) throws SQLException{
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT rgo_horarios.nome_ultimo_evento FROM	rgo_horarios WHERE rgo_horarios.idrgo_horarios = "+idHorario);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        
        return 0;
    }
    
    
    
    public void registrarQTA(int idHorario, int idIntervencao, String anotacao) throws SQLException{
    
        String horarios = "";
        int ultimoEvento = getUltimoEvento(idHorario);
        
        switch(ultimoEvento){
            case 2:
            case 1:
                horarios = " ,h_saidaL = current_timestamp ";
                ultimoEvento = 3;
                break;
            case 3:
                break;
            case 5:
            case 4:
                horarios = " ,h_saidaH = current_timestamp ";
                ultimoEvento = 6;
                break;
            default:
                horarios = " ,h_chegadaL = current_timestamp, ";
                horarios += " h_saidaL = current_timestamp ";
                ultimoEvento = 3;
                break;
        }
        
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE `sisbm_novo`.`rgo_horarios` SET "
                + "`ts_intervencao` = ?, "
                + "`anotacao_qta` = ?, "
                + "`rg_responsavel_qta` = ?, "
                + "`nome_responsavel_qta` = ?, "
                + "`nome_ultimo_evento` = ?, "
                + "`qta` = 1 " + horarios 
                + "WHERE `idrgo_horarios` = ?");
                
        preparedStatement.setInt(1, idIntervencao);
        preparedStatement.setString(2, anotacao);
        preparedStatement.setString(3, Usuario.getRG());
        preparedStatement.setString(4, Usuario.getNome_efetivo());
        preparedStatement.setInt(5, ultimoEvento);
        preparedStatement.setInt(6, idHorario);
        
        preparedStatement.execute();
        
    }
    
}

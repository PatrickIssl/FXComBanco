/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.Combo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius Teider
 */
public class CamposFiltroDao {

    private int idTitulo;
    private String textoTitulo;
    private boolean usarTitulo = false;

    public CamposFiltroDao(int idTitulo, String textoTitulo) {
        this.idTitulo = idTitulo;
        this.textoTitulo = textoTitulo;
        this.usarTitulo = true;
    }

    public CamposFiltroDao() {
    }
    
    
    
    public List<Combo> getOBM() throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT id_obm, Obm FROM sigmavi.obm");
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        return combos;
        
    }
    
    
    public List<Combo> getFracao(int idObm) throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT DISTINCT id_fracao, nome_fracao FROM view_area_atuacao WHERE id_obm = ?");
        
        preparedStatement.setInt(1, idObm);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        return combos;
        
    }
    
    
    public List<Combo> getCidade(int idObm, int idFracao) throws SQLException{
    
        
        
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT DISTINCT view_area_atuacao.id_cidade, view_area_atuacao.nome_cidade FROM view_area_atuacao "
                + "WHERE " + (idObm != 0 ? "id_obm = " + idObm : "" ) + " " + (idFracao != 0 ? (idObm != 0 ? "AND" : "")+ " id_fracao = " + idFracao : "" ) 
                + " ORDER BY id_cidade");

        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        return combos;
        
    }
    
    
    public List<Combo> getPosto(int idObm, int idFracao) throws SQLException{
    
                
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT distinct sigmavi_tab_imoveis.id_imovel, sigmavi_tab_imoveis.descricao_imovel FROM sigmavi_tab_imoveis "
                + "INNER JOIN view_area_atuacao ON view_area_atuacao.id_cidade = sigmavi_tab_imoveis.id_municipio " +
                "WHERE " + (idObm != 0 ? "fk_id_obm = " + idObm : "" ) + " " + (idFracao != 0 ? (idObm != 0 ? "AND" : "")+ " id_fracao = " + idFracao : "" ));

        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        return combos;
        
    }
    
    
    public List<Combo> getOficial(int idObm) throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT DISTINCT id_efetivo, nome_efetivo FROM sisbm_novo.cad_escala " +
                "WHERE (cad_funcao_idcad_funcao = 11 OR cad_funcao_idcad_funcao = 12) AND fk_id_obm = ? " +
                "ORDER BY nome_efetivo ");
        
        preparedStatement.setInt(1, idObm);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        
        
        return combos;
        
    }
    
    
    public List<Combo> getStatus() throws SQLException{
    
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT idts_statusbasico, status FROM ts_statusbasico ORDER BY status");
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while(resultSet.next()){
            combos.add(new Combo(resultSet.getInt(1), resultSet.getString(2)));
        }
    
        return combos;
        
    }
    
    
    public List<Combo> getNaturezas() throws SQLException {

        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT ts_nateventos.idts_nateventos, ts_nateventos.evento FROM ts_nateventos").executeQuery();

        List<Combo> combos = new ArrayList<>();

        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }
        
        while (resultSet.next()) {
            combos.add(
                    new Combo(
                            resultSet.getInt(1),
                            resultSet.getString(2)
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return combos;

    }
    
    
    public List<Combo> getSubnaturezas(int idnatureza) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT " +
                "ts_subnateventos.idts_subnateventos, " +
                "ts_subnateventos.descricao, " +
                "ts_subnateventos.ts_nateventos_idts_nateventos " +
                "FROM " +
                "ts_subnateventos " +
                "WHERE ts_subnateventos.ativo AND ts_subnateventos.ts_nateventos_idts_nateventos = ?;");
        
        preparedStatement.setInt(1, idnatureza);

        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<Combo> combos = new ArrayList<>();
        
        if (usarTitulo) {
            combos.add(new Combo(idTitulo, textoTitulo));
        }

        while (resultSet.next()) {
            combos.add(
                    new Combo(
                            resultSet.getInt(1),
                            resultSet.getString(2)
                    )
            );
        }
        
        ConexaoMysql.fecharConexao();
        
        return combos;

    }
    
    
}

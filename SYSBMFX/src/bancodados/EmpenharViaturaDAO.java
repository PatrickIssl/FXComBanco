/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodados;

import classes.TabelaEmpenharViatura;
import classes.Usuario;
import classes.Viaturas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class EmpenharViaturaDAO {

    public List<TabelaEmpenharViatura> getViaturas(int tipoData, int idrgo, String dataFormulario, int idPosto) throws SQLException {

        String dataOcorrencia = "CURRENT_TIMESTAMP";
        String dataPesquisa = "cad_escala.horario_entrada <= CURRENT_TIMESTAMP AND cad_escala.horario_saida >= CURRENT_TIMESTAMP AND ";

        switch (tipoData) {
            case 1:
                dataOcorrencia = buscarDataOcorrencia(idrgo);
                dataPesquisa = "cad_escala.horario_entrada <= '" + dataOcorrencia + "' AND cad_escala.horario_saida >= '" + dataOcorrencia + "' AND ";
                break;
            case 2:
                dataOcorrencia = transformarStringData(dataFormulario, "dd/MM/yyyy", "yyyyMMdd");
                dataPesquisa = "cad_escala.data_escala = " + dataOcorrencia + " AND ";
                break;
        }

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "cad_escala.id_viatura, "
                + "cad_escala.nome_viatura, "
                + "cad_escala.cad_postobm_id_cadpostobm, "
                + "cad_escala.nome_postobm, "
                + "cad_escala.nome_efetivo"
                + (tipoData == 0 ? ",IFNULL((SELECT ts_despacho_evento.nome_evento FROM rgo_horarios,ts_despacho_evento WHERE idsigmavi_viatura = cad_escala.id_viatura AND codigo_evento = nome_ultimo_evento ORDER BY rgo_horarios.idrgo_horarios DESC LIMIT 1),'Disponivel') AS ultimo_evento"
                        : ",''")
                + ",sigmavi.obm.Obm, "
                + "sigmavi.tab_imoveis.id_municipio, "
                + "cad_escala.viaturaorcentral, "
                + "sigmavi.tab_imoveis.fk_id_obm, "
                + "IFNULL(rgo_viaturas.rgo_idrgo,false) "
                + "FROM "
                + "cad_escala "
                + "INNER JOIN sigmavi.tab_imoveis ON sigmavi.tab_imoveis.id_imovel = cad_escala.cad_postobm_id_cadpostobm "
                + "INNER JOIN view_area_atuacao ON view_area_atuacao.id_cidade = sigmavi.tab_imoveis.id_municipio "
                + "INNER JOIN sigmavi.obm ON sigmavi.obm.id_obm = cad_escala.fk_id_obm "
                + "LEFT JOIN rgo_viaturas ON cad_escala.id_viatura = rgo_viaturas.idsigmavi_viaturas AND rgo_viaturas.rgo_idrgo = " + idrgo + " "
                + "WHERE "
                + dataPesquisa
                + "cad_escala.viaturaorcentral <> 1 AND "
                + "view_area_atuacao.id_fracao in (SELECT view_area_atuacao.id_fracao FROM view_area_atuacao WHERE view_area_atuacao.id_cidade = " + Usuario.getMunicipio_presta_servico() + ") AND "
                + "view_area_atuacao.id_obm < 100  " + (idPosto > 0 ? "AND cad_escala.cad_postobm_id_cadpostobm = " + idPosto : "") + " "
                + "GROUP BY "
                + "cad_escala.id_viatura "
                + "ORDER BY "
                + "sigmavi.obm.Obm, "
                + "cad_escala.nome_postobm ASC, "
                + "cad_escala.nome_viatura ASC");

        System.out.println(preparedStatement.toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        List<TabelaEmpenharViatura> empenharViaturas = new ArrayList<>();

        while (resultSet.next()) {
            empenharViaturas.add(
                    new TabelaEmpenharViatura(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9),
                            resultSet.getInt(10),
                            resultSet.getInt(11)
                    )
            );
        }

        return empenharViaturas;

    }

    public String buscarDataOcorrencia(int idrgo) throws SQLException {
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT DATE_FORMAT(rgo.datahora_recebimento,'%Y%m%d%H%i%s') FROM `rgo` WHERE idrgo = ?");

        preparedStatement.setInt(1, idrgo);

        System.out.println("Data ocorrencia: "+preparedStatement.toString());
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            return dateFormat.format(resultSet.getTimestamp(1));
        }
        return "";
    }

    private String transformarStringData(String dataS, String formatIN, String formatOUT) {
        if (!dataS.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatIN);
            dateFormat.setLenient(false);
            try {
                Date data = dateFormat.parse(dataS);
                dateFormat.applyPattern(formatOUT);
                return dateFormat.format(data);
            } catch (ParseException ex) {
                return "";
            }
        }

        return "";
    }

    public List<Viaturas> getTodasViaturas() throws SQLException {

        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT\n"
                + "	id_vtr,"
                + "	concat( prefixobm, '-', prefixopm, ' Placa: ', placa ) AS nome_viatura "
                + "FROM "
                + "	sigmavi.tab_viatura "
                + "WHERE "
                + "	fk_cod_situacao = 1 "
                + "ORDER BY "
                + "	prefixobm ASC;").executeQuery();

        List<Viaturas> listaViaturas = new ArrayList<>();

        while (resultSet.next()) {
            listaViaturas.add(
                    new Viaturas(resultSet.getInt(1), resultSet.getString(2))
            );
        }

        return listaViaturas;
    }

    public List<Viaturas> getViaturasApoio() throws SQLException {

        ResultSet resultSet = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT codigo,CONCAT(denominacao,' - ',placa) FROM `cad_viatura_apoio` where ativo").executeQuery();

        List<Viaturas> listaViaturas = new ArrayList<>();

        while (resultSet.next()) {
            listaViaturas.add(
                    new Viaturas(resultSet.getInt(1), resultSet.getString(2))
            );
        }

        return listaViaturas;
    }

    public void empenharViatura(int idrgo, int tipoData, String data, List<Object> viaturas, int tipoEmpenho) throws SQLException {

        boolean atualizarRGO = verificarRGO(idrgo);

        String dataHora;
        switch (tipoData) {
            default:
                dataHora = "CURRENT_TIMESTAMP";
                data = "CURRENT_DATE";
                break;
            case 2:
                data = transformarStringData(data, "dd/MM/yyyy", "yyyyMMdd");
                dataHora = data + "080000";                
                break;
            case 1:
                dataHora = buscarDataOcorrencia(idrgo);
                data = dataHora.substring(0,8);
                break;

        }

        for (Object viatura : viaturas) {
            TabelaEmpenharViatura empenharViatura;
            switch (tipoEmpenho) {
                default:
                    empenharViatura = (TabelaEmpenharViatura) viatura;
                    break;
                case 1:
                    empenharViatura = buscarInformacoesOBM(((Viaturas) viatura).getIdViatura(), dataHora);
                    break;
                case 2:
                    empenharViatura = buscarInformacoesApoio(((Viaturas) viatura));
                    break;
            }

            int contagemSaidas = contarSaidas(empenharViatura.getIdViatura(), idrgo, empenharViatura.getViaturaCentral(), data);

            PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO `rgo_viaturas` (`idsigmavi_viaturas`, `rgo_idrgo`, `nome_viatura`, `tipo_viatura`, `cidade`, `data`, `indexador_saidas`) VALUES (?,?,?,?,?,"+data+",?)");

            preparedStatement.setInt(1, empenharViatura.getIdViatura());
            preparedStatement.setInt(2, idrgo);
            preparedStatement.setString(3, empenharViatura.getNomeViatura());
            preparedStatement.setInt(4, empenharViatura.getViaturaCentral());
            preparedStatement.setInt(5, empenharViatura.getIdMunicipio());
            
            preparedStatement.setInt(6, contagemSaidas);

            System.out.println("rgo_viaturas: "+preparedStatement.toString());
            preparedStatement.execute();

            preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO rgo_horarios (h_saidaQ,idsigmavi_viatura,nome_viatura,rgo_idrgo,tipo_viatura,data,indexador_saidas) VALUES ("+dataHora+",?,?,?,?,"+data+",?);");

            preparedStatement.setInt(1, empenharViatura.getIdViatura());
            preparedStatement.setString(2, empenharViatura.getNomeViatura());
            preparedStatement.setInt(3, idrgo);
            preparedStatement.setInt(4, empenharViatura.getViaturaCentral());
            preparedStatement.setInt(5, contagemSaidas);

            System.out.println("rgo_horarios: "+preparedStatement.toString());
            preparedStatement.execute();
            
            int ultimoID = buscarUltimoID(empenharViatura.getIdViatura(), empenharViatura.getViaturaCentral(), data, contagemSaidas, idrgo);
            
            
            preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO rgo_empenhoefetivo"
                    + " (idefetivo,nomeefetivo,rgo_idrgo,id_viatura,tipo_viatura,data,indexador_saidas) "
                    + " SELECT "
                    + "     cad_escala.id_efetivo, "
                    + "     cad_escala.nome_efetivo, "
                    + "     "+idrgo+", "
                    + "     "+empenharViatura.getIdViatura()+", "
                    + "     "+empenharViatura.getViaturaCentral()+", "
                    + "     "+data+", "
                    + "     "+contagemSaidas+" "
                    + " FROM `cad_escala` "
                    + "WHERE "
                    + "     `id_viatura` = ? "
                    + "     AND `viaturaorcentral` = ? "
                    + "     AND `horario_entrada` <= "+dataHora+" "
                    + "     AND `horario_saida` >= "+dataHora+";");
            
            preparedStatement.setInt(1, empenharViatura.getIdViatura());
            preparedStatement.setInt(2, empenharViatura.getViaturaCentral());
            
            System.out.println("rgo_empenhoefetivo: "+preparedStatement.toString());
            
            preparedStatement.execute();
            
            
            if (atualizarRGO) {
                
                atualizarRGO = false;
                
                preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo SET "
                        + "     sb='"+empenharViatura.getNomePosto()+"',"
                        + "     fk_id_posto='"+empenharViatura.getIdPosto()+"',"
                        + "     id_obm_designada='"+empenharViatura.getIdObm()+"' "
                        + " WHERE idrgo = "+idrgo+";");
                
                System.out.println("Atualizar rgo" + preparedStatement.toString());
                
                preparedStatement.executeUpdate();
                
            }
            
            inserirLog(idrgo, "Empenhou a VTR "+empenharViatura.getNomeViatura());
            
            preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                    + "     idrgo_horarios, "
                    + "     nome_ultimo_evento, "
                    + "     rgo_idrgo "
                    + "FROM rgo_horarios "
                    + "WHERE "
                    + "     idsigmavi_viatura = "+empenharViatura.getIdViatura()+" "
                    + "     AND tipo_viatura = "+empenharViatura.getViaturaCentral()+" "
                    + "     AND h_saidaQ < "+dataHora+" "
                    + "     AND IFNULL(nome_ultimo_evento,0) < 7 "
                    + "     AND idrgo_horarios <> "+ultimoID);
            
            System.out.println("Ultimos horarios: "+preparedStatement.toString());
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                
                String camposAtualizar = "";
                switch(resultSet.getInt(2)){
                    
                    case 0:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_chegadaL = "+dataHora;
                    case 1:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_saidaL = "+dataHora;
                    case 2:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_chegadaQ = "+dataHora;
                    case 3:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_chegadaH = "+dataHora;
                    case 4:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_saidaH = "+dataHora;
                    default:
                        camposAtualizar += (camposAtualizar.equals("") ? "" : ",") + "h_chegadaQ = "+dataHora;
                      
                }
                
                preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("UPDATE rgo_horarios SET "+camposAtualizar+",nome_ultimo_evento = 7 WHERE idrgo_horarios = "+resultSet.getInt(1));
                
                System.out.println("Atualizar horarios: "+preparedStatement.toString());
                
                preparedStatement.executeUpdate();
                
                inserirLog(idrgo, "Realocou a viatura "+empenharViatura.getNomeViatura()+" em outra ocorrencia");
            }
            
        }

    }

    private TabelaEmpenharViatura buscarInformacoesOBM(int idViatura, String dataHora) throws SQLException {
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "       sigmavi.tab_viatura.prefixobm, "
                + "       sigmavi.tab_viatura.prefixopm, "
                + "       sigmavi.tab_viatura.fk_municipio, "
                + "       IFNULL(cad_escala.cad_postobm_id_cadpostobm,sigmavi.tab_imoveis.id_imovel) as id_posto, "
                + "       IFNULL(cad_escala.fk_id_obm,sigmavi.tab_viatura.fk_id_obm) as id_obm, "
                + "       IFNULL(cad_escala.nome_postobm,sigmavi.tab_imoveis.descricao_imovel) as nome_posto "
                + "   FROM "
                + "       sigmavi.tab_viatura "
                + "   LEFT JOIN cad_escala ON cad_escala.id_viatura = sigmavi.tab_viatura.id_vtr AND cad_escala.horario_entrada <= " + dataHora + " AND cad_escala.horario_saida >= " + dataHora + " "
                + "   INNER JOIN sigmavi.tab_imoveis ON sigmavi.tab_imoveis.id_municipio = sigmavi.tab_viatura.fk_municipio "
                + "   WHERE id_vtr = " + idViatura + " "
                + "   LIMIT 1");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return new TabelaEmpenharViatura(
                    idViatura,
                    resultSet.getString(1) + " " + resultSet.getString(2),
                    resultSet.getInt(4),
                    resultSet.getString(6),
                    "",
                    "",
                    "",
                    resultSet.getInt(3),
                    0,
                    resultSet.getInt(5),
                    0
            );
        }

        return null;
    }

    private TabelaEmpenharViatura buscarInformacoesApoio(Viaturas viatura) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "       cad_viatura_apoio.id_cidade "
                + "   FROM `cad_viatura_apoio` "
                + "   WHERE "
                + "       cad_viatura_apoio.codigo = ?");

        preparedStatement.setInt(1, viatura.getIdViatura());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            return new TabelaEmpenharViatura(
                    viatura.getIdViatura(),
                    viatura.getNome_viatura(),
                    0,
                    "",
                    "",
                    "",
                    "",
                    resultSet.getInt(1),
                    3,
                    0,
                    0
            );

        }

        return null;
    }

    private boolean verificarRGO(int idrgo) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT rgo_idrgo FROM `rgo_viaturas` WHERE `rgo_idrgo` = ? LIMIT 1");
        preparedStatement.setInt(1, idrgo);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return false;
        }

        return true;

    }

    private int contarSaidas(int idViatura, int idrgo, int tipoViatura, String data) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT idsigmavi_viaturas FROM `rgo_viaturas` WHERE `idsigmavi_viaturas` = ? AND `rgo_idrgo` = ? AND `tipo_viatura` = ? AND `data` = "+data+";");

        preparedStatement.setInt(1, idViatura);
        preparedStatement.setInt(2, idrgo);
        preparedStatement.setInt(3, tipoViatura);
        

        System.out.println("Contar saidas: " + preparedStatement.toString());
        
        ResultSet resultSet = preparedStatement.executeQuery();

        int tamanho = 0;
        if(resultSet.last())
            tamanho = resultSet.getRow();
        
        return tamanho + 1;

    }

    private int buscarUltimoID(int idViatura, int tipoViatura, String data, int contagemSaidas, int idrgo) throws SQLException {

        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("SELECT "
                + "       rgo_horarios.idrgo_horarios "
                + "   FROM "
                + "       rgo_horarios "
                + "   WHERE "
                + "       idsigmavi_viatura = ? AND "
                + "       tipo_viatura = ? AND "
                + "       `data` = "+data+" AND "
                + "       indexador_saidas = ? AND "
                + "       rgo_idrgo = ?");

        preparedStatement.setInt(1, idViatura);
        preparedStatement.setInt(2, tipoViatura);
        
        preparedStatement.setInt(3, contagemSaidas);
        preparedStatement.setInt(4, idrgo);
        
        System.out.println("Ultimo ID: "+preparedStatement.toString());
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return 0;
    }

    
    private void inserirLog(int idrgo, String texto) throws SQLException{
        PreparedStatement preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement(""
                + "INSERT INTO `cad_log` (`datahora`, `acao`, `responsavel`,`rg`,`idrgo`) "
                + "VALUES ("
                + "CURRENT_TIMESTAMP, "
                + "'"+texto+"', "
                + "'"+Usuario.getNome_efetivo()+" "+Usuario.getRG()+"',"
                + "'"+Usuario.getRG()+"',"
                + "'"+idrgo+"');");
        
        System.out.println("Log: "+preparedStatement.toString());
        
        preparedStatement.execute();
    }
}

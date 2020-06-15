/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinicius Teider
 */
public class AnotacoesDespacho {
 
    private int idAnotacoes;
    private String idrgo;
    private String descritivo;
    private Timestamp datahora;
    private String rgResponsavel;
    private String nomeResponsavel;

    public AnotacoesDespacho(int idAnotacoes, String idrgo, String descritivo, Timestamp datahora, String rgResponsavel, String nomeResponsavel) {
        this.idAnotacoes = idAnotacoes;
        this.idrgo = idrgo;
        this.descritivo = descritivo;
        this.datahora = datahora;
        this.rgResponsavel = rgResponsavel;
        this.nomeResponsavel = nomeResponsavel;
    }

    public int getIdAnotacoes() {
        return idAnotacoes;
    }

    public void setIdAnotacoes(int idAnotacoes) {
        this.idAnotacoes = idAnotacoes;
    }

    public String getIdrgo() {
        return idrgo;
    }

    public void setIdrgo(String idrgo) {
        this.idrgo = idrgo;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public String getDatahora() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        return dateFormat.format(datahora);
        //return datahora;
    }

    public void setDatahora(Timestamp datahora) {
        this.datahora = datahora;
    }

    public String getRgResponsavel() {
        return rgResponsavel;
    }

    public void setRgResponsavel(String rgResponsavel) {
        this.rgResponsavel = rgResponsavel;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }
    
    
    
}

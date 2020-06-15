/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Vinicius Teider
 */
public class ListaRGO {
    
    private int idrgo;
    private String obm,municipio,bairro,natureza;
    private String data;
    private List<Viaturas> viaturasEmpenhadas = new ArrayList<>();
    private int tipoNotificacao;
    private int idNotificacao;

    public int getIdrgo() {
        return idrgo;
    }

    public void setIdrgo(int idrgo) {
        this.idrgo = idrgo;
    }

    public String getObm() {
        return obm;
    }

    public void setObm(String obm) {
        this.obm = obm;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Viaturas> getViaturas() {
        return viaturasEmpenhadas;
    }
    
    public String getViaturasEmpenhadas() {
        String viaturas = "";
        for (Viaturas viaturasEmpenhada : viaturasEmpenhadas) {
            viaturas += "- "+viaturasEmpenhada.toString()+"\n";
        }
        return viaturas;
    }

    public void setViaturasEmpenhadas(List<Viaturas> viaturasEmpenhadas) {
        this.viaturasEmpenhadas = viaturasEmpenhadas;
    }

    public ListaRGO(int idrgo, String obm, String municipio, String bairro, String natureza, String data, int tipoNotificacao, int  idNotificacao) {
        this.idrgo = idrgo;
        this.obm = obm;
        this.municipio = municipio;
        this.bairro = bairro;
        this.natureza = natureza;
        this.data = data;
        this.tipoNotificacao = tipoNotificacao;
        this.idNotificacao = idNotificacao;
    }
    
    public StringProperty getDespachar(){
        return new SimpleStringProperty(tipoNotificacao+"");
    }

    public int getIdNotificacao() {
        return idNotificacao;
    }
    
    
    
    
    
    
    
}

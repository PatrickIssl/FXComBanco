/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class RGO {
    
    private int idrgo;
    private String nr_rgo;
    private Timestamp datahora_recebimento;
    private int idmeio_aviso;
    private String nome_solicitante, telefone_solicitante;
    private int idNatureza, idSubnatureza;
    private String urgente;
    private String descritivo;
    private String perimetro, municipio, bairro, endereco, tipo_num;
    private int endereco_numero;
    private String esquina, ponto_referencia;
    private String atendente;
    private int idAtendente;
    private String sb;
    private String lat, lng;
    private int id_posto;
    private int id_municipio, id_obm_designada;
    private int id_obm, id_subunidade;
    private Natureza natureza;
    private Subnatureza subnatureza;

    public RGO() {
    }

    public int getIdrgo() {
        return idrgo;
    }

    public void setIdrgo(int idrgo) {
        this.idrgo = idrgo;
    }

    public String getNr_rgo() {
        return nr_rgo;
    }

    public void setNr_rgo(String nr_rgo) {
        this.nr_rgo = nr_rgo;
    }

    public Timestamp getDatahora_recebimento() {
        return datahora_recebimento;
    }

    public void setDatahora_recebimento(Timestamp datahora_recebimento) {
        this.datahora_recebimento = datahora_recebimento;
    }

    public int getIdmeio_aviso() {
        return idmeio_aviso;
    }

    public void setIdmeio_aviso(int idmeio_aviso) {
        this.idmeio_aviso = idmeio_aviso;
    }

    public String getNome_solicitante() {
        return nome_solicitante;
    }

    public void setNome_solicitante(String nome_solicitante) {
        this.nome_solicitante = nome_solicitante;
    }

    public String getTelefone_solicitante() {
        return telefone_solicitante;
    }

    public void setTelefone_solicitante(String telefone_solicitante) {
        this.telefone_solicitante = telefone_solicitante;
    }

    public int getIdNatureza() {
        return idNatureza;
    }

    public void setIdNatureza(int idNatureza) {
        this.idNatureza = idNatureza;
    }

    public int getIdSubnatureza() {
        return idSubnatureza;
    }

    public void setIdSubnatureza(int idSubnatureza) {
        this.idSubnatureza = idSubnatureza;
    }

    public String getUrgente() {
        return urgente;
    }

    public void setUrgente(String urgente) {
        this.urgente = urgente;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public String getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(String perimetro) {
        this.perimetro = perimetro;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo_num() {
        return tipo_num;
    }

    public void setTipo_num(String tipo_num) {
        this.tipo_num = tipo_num;
    }

    public int getEndereco_numero() {
        return endereco_numero;
    }

    public void setEndereco_numero(int endereco_numero) {
        this.endereco_numero = endereco_numero;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(String esquina) {
        this.esquina = esquina;
    }

    public String getPonto_referencia() {
        return ponto_referencia;
    }

    public void setPonto_referencia(String ponto_referencia) {
        this.ponto_referencia = ponto_referencia;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public int getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(int idAtendente) {
        this.idAtendente = idAtendente;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getId_posto() {
        return id_posto;
    }

    public void setId_posto(int id_posto) {
        this.id_posto = id_posto;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getId_obm_designada() {
        return id_obm_designada;
    }

    public void setId_obm_designada(int id_obm_designada) {
        this.id_obm_designada = id_obm_designada;
    }

    public int getId_obm() {
        return id_obm;
    }

    public void setId_obm(int id_obm) {
        this.id_obm = id_obm;
    }

    public int getId_subunidade() {
        return id_subunidade;
    }

    public void setId_subunidade(int id_subunidade) {
        this.id_subunidade = id_subunidade;
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

    public Subnatureza getSubnatureza() {
        return subnatureza;
    }

    public void setSubnatureza(Subnatureza subnatureza) {
        this.subnatureza = subnatureza;
    }

    public RGO(int idrgo, String nr_rgo, Timestamp datahora_recebimento, int idmeio_aviso, String nome_solicitante, String telefone_solicitante, int idNatureza, int idSubnatureza, String urgente, String descritivo, String perimetro, String municipio, String bairro, String endereco, String tipo_num, int endereco_numero, String esquina, String ponto_referencia, String atendente, int idAtendente, String sb, String lat, String lng, int id_posto, int id_municipio, int id_obm_designada, int id_obm, int id_subunidade, Natureza natureza, Subnatureza subnatureza) {
        this.idrgo = idrgo;
        this.nr_rgo = nr_rgo;
        this.datahora_recebimento = datahora_recebimento;
        this.idmeio_aviso = idmeio_aviso;
        this.nome_solicitante = nome_solicitante;
        this.telefone_solicitante = telefone_solicitante;
        this.idNatureza = idNatureza;
        this.idSubnatureza = idSubnatureza;
        this.urgente = urgente;
        this.descritivo = descritivo;
        this.perimetro = perimetro;
        this.municipio = municipio;
        this.bairro = bairro;
        this.endereco = endereco;
        this.tipo_num = tipo_num;
        this.endereco_numero = endereco_numero;
        this.esquina = esquina;
        this.ponto_referencia = ponto_referencia;
        this.atendente = atendente;
        this.idAtendente = idAtendente;
        this.sb = sb;
        this.lat = lat;
        this.lng = lng;
        this.id_posto = id_posto;
        this.id_municipio = id_municipio;
        this.id_obm_designada = id_obm_designada;
        this.id_obm = id_obm;
        this.id_subunidade = id_subunidade;
        this.natureza = natureza;
        this.subnatureza = subnatureza;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Vinicius Teider
 */
public class Usuario {
    
    private static int idefetivo;
    private static String nome_efetivo, RG, email, graduacao, cpf;
    private static int obm_idobm, subunidade_idsubunidade, municipio_presta_servico;
    private static boolean militarADC;
    private static String nomeCidade;

    public static int getIdefetivo() {
        return idefetivo;
    }

    public static String getNome_efetivo() {
        return nome_efetivo;
    }

    public static String getRG() {
        return RG;
    }

    public static String getEmail() {
        return email;
    }

    public static String getGraduacao() {
        return graduacao;
    }

    public static String getCpf() {
        return cpf;
    }

    public static int getObm_idobm() {
        return obm_idobm;
    }

    public static int getSubunidade_idsubunidade() {
        return subunidade_idsubunidade;
    }

    public static int getMunicipio_presta_servico() {
        return municipio_presta_servico;
    }

    public static void setIdefetivo(int idefetivo) {
        Usuario.idefetivo = idefetivo;
    }

    public static void setNome_efetivo(String nome_efetivo) {
        Usuario.nome_efetivo = nome_efetivo;
    }

    public static void setRG(String RG) {
        Usuario.RG = RG;
    }

    public static void setEmail(String email) {
        Usuario.email = email;
    }

    public static void setGraduacao(String graduacao) {
        militarADC = !graduacao.equals("ADC");
        Usuario.graduacao = graduacao;
    }

    public static void setCpf(String cpf) {
        Usuario.cpf = cpf;
    }

    public static void setObm_idobm(int obm_idobm) {
        Usuario.obm_idobm = obm_idobm;
    }

    public static void setSubunidade_idsubunidade(int subunidade_idsubunidade) {
        Usuario.subunidade_idsubunidade = subunidade_idsubunidade;
    }

    public static void setMunicipio_presta_servico(int municipio_presta_servico) {
        Usuario.municipio_presta_servico = municipio_presta_servico;
    }

    public static boolean isMilitarADC() {
        return militarADC;
    }

    public static String getNomeCidade() {
        return nomeCidade;
    }

    public static void setNomeCidade(String nomeCidade) {
        Usuario.nomeCidade = nomeCidade;
    }


    
    
    
    
    
}

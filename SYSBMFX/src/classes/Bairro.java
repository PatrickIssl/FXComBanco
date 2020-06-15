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
public class Bairro {
    
    private int idcad_bairro;
    private String bairro;
    private int cad_cidade;
    
    public int getcidade(){
        return cad_cidade;
    }
    
    public int getidBairro() {
        return idcad_bairro;
    }

    public String getBairro() {
        return bairro;
    }

    @Override
    public String toString() {
        return bairro;
    }

    public Bairro(int idcad_bairro, String bairro, int cad_cidade) {
        this.idcad_bairro = idcad_bairro;
        this.bairro = bairro;
        this.cad_cidade = cad_cidade;
    }

    
    
    
}

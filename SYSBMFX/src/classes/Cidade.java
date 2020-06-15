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
public class Cidade {
    
    private int idcidade;
    private String cidade;

    public int getIdcidade() {
        return idcidade;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public String toString() {
        return cidade;
    }

    public Cidade(int idcidade, String cidade) {
        this.idcidade = idcidade;
        this.cidade = cidade;
    }
    
    
}

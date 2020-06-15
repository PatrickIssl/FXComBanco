/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author Administrador
 */
public class Viaturas {
    private int idViatura;
    private String nome_viatura;
    private boolean selecionado;

    public int getIdViatura() {
        return idViatura;
    }

    public String getNome_viatura() {
        return nome_viatura;
    }

    public Viaturas(int idViatura, String nome_viatura) {
        this.idViatura = idViatura;
        this.nome_viatura = nome_viatura;
    }

    @Override
    public String toString() {
        return nome_viatura;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    
    
    
    
}

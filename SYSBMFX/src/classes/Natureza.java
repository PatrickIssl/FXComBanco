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
public class Natureza {
    private int idnatureza;
    private String natureza;

    public int getIdnatureza() {
        return idnatureza;
    }

    public String getNatureza() {
        return natureza;
    }

    public Natureza(int idnatureza, String natureza) {
        this.idnatureza = idnatureza;
        this.natureza = natureza;
    }

    @Override
    public String toString() {
        return this.natureza;
    }
    
    
    
}

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
public class Subnatureza {
    
    private int idsubnatureza;
    private String subnatureza;
    private int idnatureza;

    public int getIdsubnatureza() {
        return idsubnatureza;
    }

    public String getSubnatureza() {
        return subnatureza;
    }

    public int getIdnatureza() {
        return idnatureza;
    }

    public Subnatureza(int idsubnatureza, String subnatureza, int idnatureza) {
        this.idsubnatureza = idsubnatureza;
        this.subnatureza = subnatureza;
        this.idnatureza = idnatureza;
    }

    @Override
    public String toString() {
        return subnatureza;
    }
    
    
    
}
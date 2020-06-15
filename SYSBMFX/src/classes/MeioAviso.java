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
public class MeioAviso {
    private int idts_meioaviso;
    private String aviso;

    public int getIdts_meioaviso() {
        return idts_meioaviso;
    }

    public String getAviso() {
        return aviso;
    }
    
    public MeioAviso(int idts_meioaviso, String aviso) {
        this.idts_meioaviso = idts_meioaviso;
        this.aviso = aviso;
    }

    @Override
    public String toString() {
        return this.aviso;
    }
    
    
    
}

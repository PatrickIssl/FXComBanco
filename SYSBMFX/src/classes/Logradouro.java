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
public class Logradouro {
    
    private int idcad_logradouro;
    private String logradouro;
    private int cad_municipio_idcad_municipio;

    public int getIdcad_logradouro() {
        return idcad_logradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public int getCad_municipio_idcad_municipio() {
        return cad_municipio_idcad_municipio;
    }
    
  
    @Override
    public String toString() {
        return logradouro;
    }

    public Logradouro(int idcad_logradouro, String logradouro, int cad_municipio_idcad_municipio) {
        this.idcad_logradouro = idcad_logradouro;
        this.logradouro = logradouro;
        this.cad_municipio_idcad_municipio = cad_municipio_idcad_municipio;
    }

    
    
    
}

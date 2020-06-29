/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;

/**
 *
 * @author Vinicius Teider
 */
public class Configuracoes implements Serializable{
    
    private String ipServidor;
    private String enderecoProxy;
    private String portaProxy;
    private String enderecoGeo;

    public Configuracoes(String ipServidor, String enderecoProxy, String portaProxy, String enderecoGeo) {
        this.ipServidor = ipServidor;
        this.enderecoProxy = enderecoProxy;
        this.portaProxy = portaProxy;
        this.enderecoGeo = enderecoGeo;
    }

    public Configuracoes() {
    }
    
    
    
    
    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public String getEnderecoProxy() {
        return enderecoProxy;
    }

    public void setEnderecoProxy(String enderecoProxy) {
        this.enderecoProxy = enderecoProxy;
    }

    public String getPortaProxy() {
        return portaProxy;
    }

    public void setPortaProxy(String portaProxy) {
        this.portaProxy = portaProxy;
    }

    public String getEnderecoGeo() {
        return enderecoGeo;
    }

    public void setEnderecoGeo(String enderecoGeo) {
        this.enderecoGeo = enderecoGeo;
    }
    
    
    
}



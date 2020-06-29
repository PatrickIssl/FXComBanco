/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import classes.Configuracoes;
import classes.Log;
import classes.Serializacao;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Administrador
 */
public class SYSBMFX extends Application {
    
    public static Stage stage;
    public static String LOCALCONFIGURACOES = "configuracao.dat";
    
    public static String ipServidor;
    public static String enderecoProxy;
    public static String portaProxy;
    public static String enderecoGeocoding;
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Log.gravar(this.getClass(),"Aplicativo Iniciado!");
        
        ArrayList<Object> configuracao = Serializacao.lerArquivoBinario(LOCALCONFIGURACOES);
       
        
        
        if(configuracao.isEmpty()){

            Configuracoes configuracoes = new Configuracoes("10.22.7.200", "", "", "http://www.bombeiroscascavel.com.br/sysbmnew/georeferenciar_v3/?retornoFormulario=formulario_abertura&idrgo=");
        
            configuracao.add(configuracoes);
            
            Serializacao.gravarArquivoBinario(configuracao, LOCALCONFIGURACOES);
            
            SYSBMFX.ipServidor = configuracoes.getIpServidor();
            SYSBMFX.enderecoProxy = configuracoes.getEnderecoProxy();
            SYSBMFX.portaProxy = configuracoes.getPortaProxy();
            SYSBMFX.enderecoGeocoding = configuracoes.getEnderecoGeo();
            
        }else{
            
            SYSBMFX.ipServidor = ((Configuracoes)configuracao.get(0)).getIpServidor();
            SYSBMFX.enderecoProxy = ((Configuracoes)configuracao.get(0)).getEnderecoProxy();
            SYSBMFX.portaProxy = ((Configuracoes)configuracao.get(0)).getPortaProxy();
            SYSBMFX.enderecoGeocoding = ((Configuracoes)configuracao.get(0)).getEnderecoGeo();
            
        }
        

        Parent root = FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));        
        Scene scene = new Scene(root);
       
        this.stage = stage;
        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

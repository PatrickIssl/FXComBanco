/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import classes.Configuracoes;
import classes.Serializacao;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static sysbmfx.SYSBMFX.LOCALCONFIGURACOES;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class FormularioConfiguracaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        radioCelepar.setUserData(1);
        radioHomologacao.setUserData(2);
        radioOutro.setUserData(3);
        
        
        grupoServidor = new ToggleGroup();
        
        radioCelepar.setToggleGroup(grupoServidor);
        radioHomologacao.setToggleGroup(grupoServidor);
        radioOutro.setToggleGroup(grupoServidor);
        
        
        
        switch(SYSBMFX.ipServidor){
            case "10.22.7.200":
                radioCelepar.setSelected(true);
            break;
            case "www.bombeiroscascavel.com.br:2792":
                radioHomologacao.setSelected(true);
            break;
            default: 
                txtOutro.setText(SYSBMFX.ipServidor);
            break;   
        }
        
        txtPEndereco.setText(SYSBMFX.enderecoProxy);
        txtPPorta.setText(SYSBMFX.portaProxy);
        txtGeocoding.setText(SYSBMFX.enderecoGeocoding);
        
    }    
    
    @FXML
    private TextField txtOutro;
    
    @FXML
    private TextField txtPEndereco;
    
    @FXML
    private TextField txtPPorta;
    
    @FXML
    private TextField txtGeocoding;
    
    @FXML
    private RadioButton radioCelepar;
    
    @FXML
    private RadioButton radioHomologacao;
    
    @FXML
    private RadioButton radioOutro;
    
    private ToggleGroup grupoServidor;
    
    
    
    
    @FXML
    private void onSalvar(ActionEvent event){
        
        Configuracoes  configuracoes = new Configuracoes();
        
        switch((int)grupoServidor.getSelectedToggle().getUserData()){
            case 1:
                configuracoes.setIpServidor("10.22.7.200");
                break;
            case 2:
                configuracoes.setIpServidor("www.bombeiroscascavel.com.br:2792");
                break;
            case 3:
                configuracoes.setIpServidor(txtOutro.getText());
                break;
        }
        
        configuracoes.setEnderecoProxy(txtPEndereco.getText());        
        configuracoes.setPortaProxy(txtPPorta.getText());
        configuracoes.setEnderecoGeo(txtGeocoding.getText());
        
        
        ArrayList<Object> configuracao = new ArrayList<>();
        
        configuracao.add(configuracoes);
        
        Serializacao.gravarArquivoBinario(configuracao, LOCALCONFIGURACOES);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Salvo com sucesso!");
        alert.show();
        
    }

    public void start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FormularioConfiguracao.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        
        stage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.show();
    }
    
}

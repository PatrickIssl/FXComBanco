/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import bancodados.ConexaoMysql;
import bancodados.Login;
import classes.Log;
import com.sun.deploy.ui.AboutDialog;
import java.awt.Dialog;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author Administrador
 */
public class TelaLogin implements Initializable {
    
    @FXML
    TextField txtUsuario;
    
    @FXML
    PasswordField txtSenha;

    @FXML
    private void logar(ActionEvent event){
       login();
    }
    
    private void login(){
        
        Log.gravar(this.getClass(),"Clicado no botão login");
        
        try {
            if( new Login().realizarLogin(txtUsuario.getText(), txtSenha.getText()) ){
                
                Log.gravar(this.getClass(),"Login com sucesso!");
                
                MenuController mc = new MenuController();
                mc.start();
                
            }else{
                
                Log.gravar(this.getClass(),"Usuario invalido!");
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login");
                alert.setHeaderText("Usuário ou senha incorretaa!");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            Log.gravar(this.getClass(),ex.getMessage());
        }
    }
    
    @FXML
    private void onConfigurar(ActionEvent event){
        
        FormularioConfiguracaoController configuracaoController = new FormularioConfiguracaoController();
        try {
            configuracaoController.start();
        } catch (IOException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtSenha.setOnKeyPressed(k ->{
           final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
            if (ENTER.match(k)) {
               login(); 
            }
        });
        
        
    }    
    
}

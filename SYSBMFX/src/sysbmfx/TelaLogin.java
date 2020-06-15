/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import bancodados.ConexaoMysql;
import bancodados.Login;
import com.sun.deploy.ui.AboutDialog;
import java.awt.Dialog;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
                
        try {
            //if( new Login().realizarLogin(txtUsuario.getText(), txtSenha.getText()) ){
            if( new Login().realizarLogin("105997043","123456") ){
                MenuController mc = new MenuController();
                mc.start();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login");
                alert.setHeaderText("Usuário ou senha incorretaa!");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    
}

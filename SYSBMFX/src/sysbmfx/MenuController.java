package sysbmfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
public class MenuController implements Initializable{
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    public void start() throws IOException{
        
        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();
        
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root);

        
        
        SYSBMFX.stage.setScene(scene);
        
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        SYSBMFX.stage.setX(SYSBMFX.stage.getX());
        SYSBMFX.stage.setY(SYSBMFX.stage.getY());
        SYSBMFX.stage.setWidth(width);
        SYSBMFX.stage.setHeight(height);
        
    }
    
    @FXML
    private void abrirCadastroRGO(ActionEvent event) throws IOException{
        TelaCadastro cadastro = new TelaCadastro();
        cadastro.start();
        
    }
    
    @FXML
    private void abrirDespacho(ActionEvent event) throws IOException{
        DespachoTabela despachoTabela = new DespachoTabela();
        despachoTabela.start();
    }
    
    
    
}

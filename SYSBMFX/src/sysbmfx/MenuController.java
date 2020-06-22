package sysbmfx;

import classes.Usuario;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    
    @FXML
    Label lbUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbUsuario.setText(Usuario.getNome_efetivo() + " | " + Usuario.getRG());
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
        FormularioAberturaController cadastro = new FormularioAberturaController();
        cadastro.start();
        
    }
    
    @FXML
    private void abrirDespacho(ActionEvent event) throws IOException{
        DashboardDespachoController despachoTabela = new DashboardDespachoController();
        despachoTabela.start();
        
        /*URI link;
        try {
            link = new URI("www.google.com");
            Desktop.getDesktop().browse(link);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
    
    
}

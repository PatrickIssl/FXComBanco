/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import bancodados.CamposFiltroDao;
import classes.Combo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class BuscarOcorrenciaController implements Initializable {

    
    @FXML
    TextField txtRgo;    
    
    @FXML
    TextField txtEndereco;    
    
    @FXML
    TextField txtVitima;
    
    @FXML
    TextField txtData1;
    
    @FXML
    TextField txtDate2;
    
    @FXML
    ComboBox comboFracao;
    
    @FXML
    ComboBox comboObm;
    
    @FXML
    ComboBox comboMunicipio;
    
    @FXML
    ComboBox comboPosto;
    
    @FXML
    ComboBox comboNatureza;
    
    @FXML
    ComboBox comboSubnatureza;
    
    @FXML
    ComboBox comboOficial;
    
    @FXML
    TableView txtDados;
    
    @FXML
    HBox hbStatus;
    
    ToggleGroup grupoStatus;
    
    ObservableList<Combo> obsFracao = FXCollections.observableArrayList();
    ObservableList<Combo> obsMunicipio = FXCollections.observableArrayList();
    ObservableList<Combo> obsPosto = FXCollections.observableArrayList();
    
    ObservableList<Combo> obsSubnatureza = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            carregarFiltro();
        } catch (SQLException ex) {
            Logger.getLogger(BuscarOcorrenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }   
    
    public void start() throws IOException{
    
        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();

        Parent root = FXMLLoader.load(getClass().getResource("BuscarOcorrencia.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        
        stage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(SYSBMFX.stage.getX());
        stage.setY(SYSBMFX.stage.getY());
        stage.setWidth(width);
        stage.setHeight(height);
        
        stage.show();
    
    }

    private void carregarFiltro() throws SQLException {
        
        
        
        comboObm.setItems(FXCollections.observableArrayList(new CamposFiltroDao(0,"Todas").getOBM()));
        comboObm.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int idObm = ((Combo)newValue).getId();
                
                try {
                    
                    obsFracao.clear();
                    obsFracao = FXCollections.observableArrayList(new CamposFiltroDao(0,"Todas").getFracao( idObm ));
                    comboFracao.setItems(obsFracao);
                    
                    obsMunicipio.clear();
                    obsMunicipio = FXCollections.observableArrayList(new CamposFiltroDao(0,"Todos").getCidade(idObm, 0));
                    comboMunicipio.setItems(obsMunicipio);
                    
                    obsPosto.clear();
                    obsPosto = FXCollections.observableArrayList(new CamposFiltroDao(0,"Todos").getPosto(idObm, 0));
                    comboPosto.setItems(obsPosto);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BuscarOcorrenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        comboFracao.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int idObm = ((Combo)comboObm.getSelectionModel().getSelectedItem()).getId();
                int idFracao = ((Combo)newValue).getId();
                
                try {
                    obsMunicipio.clear();
                    obsMunicipio = FXCollections.observableArrayList(new CamposFiltroDao(0,"Todos").getCidade(idObm, idFracao));
                    comboMunicipio.setItems(obsMunicipio);
                    
                    obsPosto.clear();
                    obsPosto = FXCollections.observableArrayList(new CamposFiltroDao(0,"Todos").getPosto(idObm, idFracao));
                    comboPosto.setItems(obsPosto);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BuscarOcorrenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        comboNatureza.setItems(FXCollections.observableArrayList(new CamposFiltroDao(0,"Todas").getNaturezas()));
        comboNatureza.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int idNatureza = ((Combo)newValue).getId();
                
                try {
                    
                    comboFracao.setItems(FXCollections.observableArrayList(new CamposFiltroDao(0,"Todas").getSubnaturezas(idNatureza)));
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BuscarOcorrenciaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
    }
    
    @FXML
    private void onPesquisar(ActionEvent event){
    
    }
    
    
    
    
}

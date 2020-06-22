/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import bancodados.DespachoDAO;
import bancodados.RgoDAO;
import classes.Combo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class FormularioQTAController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static int idHorarioStatico;
    private static Stage stage;
    private static Consumer method;
    
    private int idHorario;

    @FXML
    ComboBox<Combo> comboIntervencao;

    @FXML
    TextArea txtDescritivo;

    private ObservableList<Combo> obsInterversao;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        idHorario = idHorarioStatico;

        new RgoDAO().pegarNumRgo();
        try {
            obsInterversao = FXCollections.observableArrayList(new RgoDAO().getIntervencao());
        } catch (SQLException ex) {
            Logger.getLogger(FormularioCancelamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        comboIntervencao.setItems(obsInterversao);
        
    }  
    
    public void start(int idHorario, Consumer method) {
        this.idHorarioStatico = idHorario;
        this.method = method;

        try {

            Parent root = FXMLLoader.load(getClass().getResource("FormularioQTA.fxml"));
            Scene scene = new Scene(root);

            this.stage = new Stage();

            this.stage.setScene(scene);
            
            this.stage.show();

        } catch (IOException ex) {
            Logger.getLogger(EmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarAlerta(String titulo, String corpo) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(corpo);
        alert.showAndWait();

    }
    
    
    @FXML
    private void onSalvar(ActionEvent event) {

        if (comboIntervencao.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta("Erro", "Preencha a intervenção!");
            return;
        }
        
        if (txtDescritivo.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha o descritivo!");
            return;
        }
        
        
        
        try {
            
            new DespachoDAO().registrarQTA(idHorario,comboIntervencao.getSelectionModel().getSelectedItem().getId(),txtDescritivo.getText());
            method.accept(new Object());
            this.stage.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FormularioCancelamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

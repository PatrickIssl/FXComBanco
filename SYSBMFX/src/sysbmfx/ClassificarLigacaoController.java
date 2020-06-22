/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sysbmfx;

import bancodados.ConexaoMysql;
import classes.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patrick
 */


public class ClassificarLigacaoController implements Initializable {

    @FXML
    ToggleGroup grupoClassificação;
    @FXML
    RadioButton RadioLigacaoMuda;
    @FXML
    RadioButton RadioTroteCrianca;
    @FXML
    RadioButton RadioEngano;
    @FXML
    RadioButton RadioOrientacao;
    @FXML
    RadioButton RadioComplemento;
    @FXML
    RadioButton RadioTrote;
    @FXML
    RadioButton RadioInsulto;
    @FXML
    RadioButton RadioLigacaoCaiu;
    @FXML
    RadioButton RadioOutrosOrgaos;
    @FXML
    RadioButton RadioOutros;
    @FXML
    private Label fieldData;
            

  public void start() throws IOException {

        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();

        Parent root = FXMLLoader.load(getClass().getResource("classificarligacao.fxml"));
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
  
    @FXML
    private void onSalvar(ActionEvent e){
       
    
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String data = (dateFormat.format(date));
            
            
            
            PreparedStatement preparedStatement;
            preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO `sisbm_novo`.`cad_classificacao_ligacao`(`datahora`, `id_efetivo`, `codigo_classificacao`, `idobm`)VALUES (?,?, ?, ?);");
            preparedStatement.setString(1, data);
            preparedStatement.setInt(2, Usuario.getIdefetivo());
            preparedStatement.setInt(3, (int) grupoClassificação.getSelectedToggle().getUserData());
            preparedStatement.setInt(4, Usuario.getObm_idobm());
  
            
            
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClassificarLigacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     
    }
  
    public void initialize(URL url, ResourceBundle rb) {
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String mudardata = (dateFormat.format(date));
            fieldData.setText(mudardata);
            
            grupoClassificação = new ToggleGroup();
            RadioLigacaoMuda.setToggleGroup(grupoClassificação);
            RadioTrote.setToggleGroup(grupoClassificação);
            RadioTroteCrianca.setToggleGroup(grupoClassificação);
            RadioInsulto.setToggleGroup(grupoClassificação);
            RadioEngano.setToggleGroup(grupoClassificação);
            RadioLigacaoCaiu.setToggleGroup(grupoClassificação);
            RadioOrientacao.setToggleGroup(grupoClassificação);
            RadioOutrosOrgaos.setToggleGroup(grupoClassificação);
            RadioOutros.setToggleGroup(grupoClassificação);
            RadioComplemento.setToggleGroup(grupoClassificação);
            
            
            
            RadioLigacaoMuda.setUserData(1);
            RadioTrote.setUserData(2);
            RadioTroteCrianca.setUserData(3);
            RadioInsulto.setUserData(4);
            RadioEngano.setUserData(5);
            RadioLigacaoCaiu.setUserData(6);
            RadioOrientacao.setUserData(7);
            RadioOutrosOrgaos.setUserData(8);
            RadioOutros.setUserData(9);
            RadioComplemento.setUserData(10);
    }    
    
}

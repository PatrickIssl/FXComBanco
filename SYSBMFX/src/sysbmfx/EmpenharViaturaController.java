package sysbmfx;

import bancodados.AnotacoesDespachoDAO;
import bancodados.DespachoDAO;
import bancodados.RgoDAO;
import bancodados.TabelaViaturasDespachoDAO;
import classes.AnotacoesDespacho;
import classes.TabelaViaturasDespacho;
import classes.RGO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius Teider
 */
public class EmpenharViaturaController implements Initializable{
    
    private static int idrgoStatico;
    private int idrgo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.idrgo = idrgoStatico;
        
        popularInfoGerais();
        
        construirTabelaComplementos();
        popularTabelaComplementos();
        
        construirTabelaViaturas();
    } 
    
    @FXML
    Label lbRGO;
    @FXML
    Label lbNatureza;
    @FXML
    Label lbSubnatureza;
    @FXML
    Label lbMunicipio;
    @FXML
    Label lbBairro;
    @FXML
    Label lbEndereco;
    @FXML
    Label lbReferencia;
    @FXML
    Label lbDescritivo;
    @FXML
    Label lbSolicitante;
    @FXML
    Label lbTelefone;
    @FXML
    Label lbAtendente;
    
    @FXML
    TableView tbComplemento;
    
    @FXML
    TableView tbViaturas;
    
    
    @FXML
    TextArea txtDescricaoComplemento;
    
    @FXML
    Button btnCancelamento;
    
    @FXML
    Button btnEncerrar;
    
     
    private ObservableList<AnotacoesDespacho> obsComplemento;
    private ObservableList<TabelaViaturasDespacho> obsViaturas;
    
    private void popularInfoGerais(){
    
        try {
            RGO rgo = new RgoDAO().getRGO(idrgo);
            
            if(rgo == null){
                return;
            }
            
            lbRGO.setText(rgo.getNr_rgo());
            lbNatureza.setText(rgo.getNatureza().getNatureza());
            lbSubnatureza.setText(rgo.getSubnatureza().getSubnatureza());
            lbMunicipio.setText(rgo.getMunicipio());
            lbBairro.setText(rgo.getBairro());
            lbEndereco.setText(rgo.getEndereco() + rgo.getTipo_num() + (rgo.getTipo_num().equals("Esquina") ? rgo.getEsquina() : rgo.getEndereco_numero()));
            lbReferencia.setText(rgo.getPonto_referencia());
            lbDescritivo.setText(rgo.getDescritivo());
            lbSolicitante.setText(rgo.getNome_solicitante());
            lbTelefone.setText(rgo.getTelefone_solicitante());
            lbAtendente.setText(rgo.getAtendente());
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void start(int idrgo, int idNotificacao) throws IOException{
        
        try {
            new DespachoDAO().atualizarNotificacao(idNotificacao);
        } catch (SQLException ex) {
            Logger.getLogger(EmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        start(idrgo);
    }
     
    public void start(int idrgo) throws IOException{
        
        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();
        
        EmpenharViaturaController.idrgoStatico = idrgo;
        
        Parent root = FXMLLoader.load(getClass().getResource("EmpenharViatura.fxml"));
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

    private void construirTabelaComplementos() {
        
        TableColumn<AnotacoesDespacho, String> dataColuna = new TableColumn<>("Data e Hora");
        dataColuna.setCellValueFactory(new PropertyValueFactory<>("datahora"));
        
        TableColumn<AnotacoesDespacho, String> respColuna = new TableColumn<>("Responsável");
        respColuna.setCellValueFactory(new PropertyValueFactory<>("nomeResponsavel"));
        
        TableColumn<AnotacoesDespacho, String> descColuna = new TableColumn<>("Descritivo");
        descColuna.setCellValueFactory(new PropertyValueFactory<>("descritivo"));
        
        
        tbComplemento.getColumns().addAll(dataColuna, respColuna, descColuna);
        
        tbComplemento.setItems(obsComplemento);
        
        try {
            obsComplemento = FXCollections.observableArrayList(new AnotacoesDespachoDAO().getAnotacoes(idrgo));
            tbComplemento.setItems(obsComplemento);
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void popularTabelaComplementos() {
        try {
            List<AnotacoesDespacho> auxAnotacoes = new AnotacoesDespachoDAO().getAnotacoes(idrgo);
            obsComplemento.clear();
            obsComplemento.addAll(auxAnotacoes);
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void inserirComplementoAA(ActionEvent event){
        
        try {            
            new AnotacoesDespachoDAO().insert(idrgo, "Despachar AA para atendimento.",1,1 );
            popularTabelaComplementos();                        
        } catch (SQLException ex) {            
            System.out.println("Erro ao inserir no banco!");            
        }
        
    }
    
    @FXML
    private void inserirComplementoMedico(ActionEvent event){
        
        try {            
            new AnotacoesDespachoDAO().insert(idrgo, "Despachar Médico para atendimento.",2,1 );
            popularTabelaComplementos();                        
        } catch (SQLException ex) {            
            System.out.println("Erro ao inserir no banco!");            
        }
        
    }
    
    @FXML
    private void inserirComplementoABTR(ActionEvent event){
        
        try {            
            new AnotacoesDespachoDAO().insert(idrgo, "Despachar ABTR para atendimento.",3,1 );
            popularTabelaComplementos();                        
        } catch (SQLException ex) {            
            System.out.println("Erro ao inserir no banco!");            
        }
        
    }
    
    @FXML
    private void inserirComplemento(ActionEvent event){
        
        try {            
            new AnotacoesDespachoDAO().insert(idrgo, txtDescricaoComplemento.getText(),0,0 );
            popularTabelaComplementos();                        
        } catch (SQLException ex) {            
            System.out.println("Erro ao inserir no banco!");            
        }
        
    }
    
    @FXML
    private void empenharViatura(ActionEvent event){
        
        try {
            new TelaEmpenharViaturaController().start(idrgo);
        } catch (IOException ex) {
            Logger.getLogger(EmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    @FXML
    private void onVoltar(ActionEvent event){
        try {
            new DashboardDespachoController().start();
        } catch (IOException ex) {
            Logger.getLogger(TelaEmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onCancelar(ActionEvent event){
                
        new FormularioCancelamentoController().start(idrgo, this::onFecharCancelamento);
        
    }
    
    @FXML
    private void onEncerrar(ActionEvent event){
        try {
            new RgoDAO().encerrarOcorrencia(idrgo);
            mostrarAlerta("Encerrada com sucesso!", Alert.AlertType.CONFIRMATION);
        } catch (SQLException ex) {
            mostrarAlerta("Ocorreu um erro ao encerrar a ocorrência!", Alert.AlertType.ERROR);
        }
    }
    
    
    
    private void construirTabelaViaturas() {
        
        TableColumn<TabelaViaturasDespacho, Object> viaturaColuna = new TableColumn<>("Viatura");
        viaturaColuna.setCellValueFactory(funcaoViatura -> funcaoViatura.getValue().getNomeViatura());
        
        TableColumn<TabelaViaturasDespacho, Object> funcaoColuna = new TableColumn<>("#");
        funcaoColuna.setCellValueFactory(funcaoValor -> funcaoValor.getValue().getBtnFuncao(this::popularTabelaViaturas));
        
        TableColumn<TabelaViaturasDespacho, Object> qtaColuna = new TableColumn<>("QTA");
        qtaColuna.setCellValueFactory(funcaoQTA -> funcaoQTA.getValue().getQTABtn());
        
        TableColumn<TabelaViaturasDespacho, Object> saidaQuartelColuna = new TableColumn<>("Hr. Saida Quartel");
        saidaQuartelColuna.setCellValueFactory(saidaQuartelValor -> saidaQuartelValor.getValue().getBtnSaidaQuartel());
        
        TableColumn<TabelaViaturasDespacho, Object> chegadaLocalColuna = new TableColumn<>("Hr. Cheg. Local");
        chegadaLocalColuna.setCellValueFactory(chegadaLocalValor -> chegadaLocalValor.getValue().getBtnChegadaLocal());
        
        TableColumn<TabelaViaturasDespacho, Object> saidaLocalColuna = new TableColumn<>("Hr. Saida Local");
        saidaLocalColuna.setCellValueFactory(saidaLocalValor -> saidaLocalValor.getValue().getBtnSaidaLocal());
        
        TableColumn<TabelaViaturasDespacho, Object> chegadaHospColuna = new TableColumn<>("Hr. Cheg. Hosp.");
        chegadaHospColuna.setCellValueFactory(chegadaHospValor -> chegadaHospValor.getValue().getBtnChegadaHosp());
        
        TableColumn<TabelaViaturasDespacho, Object> saidaHospColuna = new TableColumn<>("Hr. Saida Hosp.");
        saidaHospColuna.setCellValueFactory(saidaHospValor -> saidaHospValor.getValue().getBtnSaidaHosp());
        
        TableColumn<TabelaViaturasDespacho, Object> chegadaQuartelColuna = new TableColumn<>("Hr. Cheg. Quartel");
        chegadaQuartelColuna.setCellValueFactory(chegadaHospValor -> chegadaHospValor.getValue().getBtnChegadaQuartel());
        
        

        
        tbViaturas.getColumns().addAll(funcaoColuna, qtaColuna, viaturaColuna, saidaQuartelColuna, chegadaLocalColuna, saidaLocalColuna , chegadaHospColuna, saidaHospColuna, chegadaQuartelColuna);
        
        tbViaturas.setItems(obsViaturas);
        
        try {
            obsViaturas = FXCollections.observableArrayList( new TabelaViaturasDespachoDAO().getViaturasDespachos(idrgo));
            tbViaturas.setItems(obsViaturas);
            
            if(obsViaturas.size() > 0){
                btnCancelamento.setVisible(false);
                btnEncerrar.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void popularTabelaViaturas(Object ob){
        try {
            obsViaturas.clear();
            obsViaturas.addAll(new TabelaViaturasDespachoDAO().getViaturasDespachos(idrgo));
            
            if(obsViaturas.size() > 0){
                btnCancelamento.setVisible(false);
                btnEncerrar.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDespachoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void onFecharCancelamento(Object ob){
        
        try {
            new DashboardDespachoController().start();
        } catch (IOException ex) {
            Logger.getLogger(TelaEmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void mostrarAlerta(String corpo, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(corpo);
        alert.showAndWait();
    }
    
    
}

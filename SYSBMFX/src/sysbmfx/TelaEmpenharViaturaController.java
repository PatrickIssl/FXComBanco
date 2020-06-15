package sysbmfx;

import bancodados.EmpenharViaturaDAO;
import classes.TabelaEmpenharViatura;
import classes.Viaturas;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vinicius Teider
 */
public class TelaEmpenharViaturaController implements Initializable {

    private static int idrgoStatico;
    private int idrgo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.idrgo = TelaEmpenharViaturaController.idrgoStatico;
        
        construirTabela();

        grupoData = new ToggleGroup();
        radioDataOcorrencia.setToggleGroup(grupoData);
        radioDataAtual.setToggleGroup(grupoData);
        radioDataDigitada.setToggleGroup(grupoData);

        radioDataOcorrencia.setUserData(1);
        radioDataAtual.setUserData(0);
        radioDataDigitada.setUserData(2);

        criarMascara(txtData);
        
        configurarAutocomplete();
        
        //aqui
        txtVtrEscala.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                obsTabela.clear();
                obsTabela.addAll(obsTabelaClone);
                
                
                Iterator<TabelaEmpenharViatura> it = obsTabela.iterator();
                
                while (it.hasNext()) {
                    TabelaEmpenharViatura empenharViatura = it.next();
                    if(!empenharViatura.isSelecionado() && !empenharViatura.getNomeViatura().toUpperCase().contains(txtVtrEscala.getText().toUpperCase())){
                        it.remove();
                    }
                }
                
            }
        });
    }
    
    

    @FXML
    ToggleGroup grupoData;

    @FXML
    RadioButton radioDataOcorrencia;

    @FXML
    RadioButton radioDataAtual;

    @FXML
    RadioButton radioDataDigitada;

    @FXML
    TextField txtData;

    @FXML
    TextField txtVtrOBM;

    @FXML
    TextField txtVtrOrgao;

    @FXML
    TextField txtVtrEscala;

    @FXML
    TableView tabelaViaturas;

    private List<Viaturas> viaturas = new ArrayList<>();
    private List<Viaturas> viaturasApoio = new ArrayList<>();

    private ObservableList<TabelaEmpenharViatura> obsTabela;
    private ObservableList<TabelaEmpenharViatura> obsTabelaClone;

    public void start(int idrgo) throws IOException {

        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();
        
        TelaEmpenharViaturaController.idrgoStatico = idrgo;

        Parent root = FXMLLoader.load(getClass().getResource("TelaEmpenharViatura.fxml"));
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
    private void onEmpenharEscala(ActionEvent event){
        
        List<Object> lista = new ArrayList<>();
        
        for (TabelaEmpenharViatura tabelaEmpenharViatura : obsTabela) {
            if(tabelaEmpenharViatura.isSelecionado()){
                lista.add(tabelaEmpenharViatura);
            }
        }

        if(lista.isEmpty()){
            
            mostrarAlerta("Erro!", "Selecione pelo menos uma viatura!");

            return;
        }
        
        
        try {
            new EmpenharViaturaDAO().empenharViatura(
                    idrgo,
                    (int)grupoData.getSelectedToggle().getUserData(),
                    txtData.getText(),
                    lista,
                    0);
            
            mostrarSucesso("Viaturas empenhadas com sucesso!");
            
        } catch (SQLException ex) {
            mostrarAlerta("Erro!", "Erro ao inserir viatura!");
        }
    }
    
    
    @FXML
    private void onEmpenharOBM(ActionEvent event){
        
        List<Object> lista = new ArrayList<>();
        
        if(viaturaOBM == null){
            
            mostrarAlerta("Erro!", "Selecione pelo menos uma viatura!");

            return;
        }
        
        lista.add(viaturaOBM);
        
        
        try {
            new EmpenharViaturaDAO().empenharViatura(
                    idrgo,
                    (int)grupoData.getSelectedToggle().getUserData(),
                    txtData.getText(),
                    lista,
                    1);
            
            mostrarSucesso("Viaturas empenhadas com sucesso!");
            
        } catch (SQLException ex) {
            mostrarAlerta("Erro!", "Erro ao inserir viatura!");
        }
    }
    
    @FXML
    private void onEmpenharApoio(ActionEvent event){
        
        List<Object> lista = new ArrayList<>();
        
        if(viaturaApoio == null){
            
            mostrarAlerta("Erro!", "Selecione pelo menos uma viatura!");

            return;
        }
        
        lista.add(viaturaApoio);
        
        try {
            new EmpenharViaturaDAO().empenharViatura(
                    idrgo,
                    (int)grupoData.getSelectedToggle().getUserData(),
                    txtData.getText(),
                    lista,
                    2);
            
            mostrarSucesso("Viaturas empenhadas com sucesso!");
            
        } catch (SQLException ex) {
            mostrarAlerta("Erro!", "Erro ao inserir viatura!");
        }
    }
    
    @FXML
    private void onVoltar(ActionEvent event){
        try {
            new EmpenharViaturaController().start(idrgo);
        } catch (IOException ex) {
            Logger.getLogger(TelaEmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onFiltrarViaturas(ActionEvent event) {
        if ((int) grupoData.getSelectedToggle().getUserData() == 2) {
            if (!validarCampoData(txtData)) {
                return;
            }
        }

        try {
            obsTabela.clear();
            obsTabelaClone.clear();
            
            List<TabelaEmpenharViatura> aux = new EmpenharViaturaDAO().getViaturas(
                            (int) grupoData.getSelectedToggle().getUserData(),
                            idrgo,
                            txtData.getText(),
                            0
                    );
            
            obsTabela.addAll( aux );
            obsTabelaClone.addAll( aux );
        } catch (SQLException ex) {
            Logger.getLogger(DespachoTabela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void construirTabela() {

        TableColumn<TabelaEmpenharViatura, Object> checkColuna = new TableColumn<>("#");
        checkColuna.setCellValueFactory(dados -> dados.getValue().getCheckbox(this::atualizarObj));

        TableColumn<TabelaEmpenharViatura, Object> postoColuna = new TableColumn<>("Posto");
        postoColuna.setCellValueFactory(dados -> dados.getValue().getPosto());

        TableColumn<TabelaEmpenharViatura, Object> prefixoColuna = new TableColumn<>("Prefixo da Viatura");
        prefixoColuna.setCellValueFactory(dados -> dados.getValue().getPrefixo());

        TableColumn<TabelaEmpenharViatura, Object> chefeColuna = new TableColumn<>("Chefe da Guarnição");
        chefeColuna.setCellValueFactory(dados -> dados.getValue().getChefe());

        TableColumn<TabelaEmpenharViatura, Object> statusColuna = new TableColumn<>("Status da Viatura");
        statusColuna.setCellValueFactory(dados -> dados.getValue().getStatus());

        tabelaViaturas.getColumns().addAll(checkColuna, postoColuna, prefixoColuna, chefeColuna, statusColuna);

        tabelaViaturas.setItems(obsTabela);

        try {
            
            List<TabelaEmpenharViatura> aux = new EmpenharViaturaDAO().getViaturas(0, idrgo, "", 0);
            
            obsTabela = FXCollections.observableArrayList(aux);
            obsTabelaClone = FXCollections.observableArrayList(aux);
            
            tabelaViaturas.setItems(obsTabela);

        } catch (SQLException ex) {
            Logger.getLogger(DespachoTabela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void criarMascara(TextField textField) {

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    String value = textField.getText();
                    value = value.replaceAll("[^0-9]", "");
                    value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                    value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");

                    value = value.substring(0, (value.length() < 10 ? value.length() : 10));

                    textField.setText(value);
                    textField.positionCaret(textField.getText().length());
                } catch (Exception e) {
                }
            }
        });

    }

    private boolean validarCampoData(TextField textField) {

        if (!textField.getText().isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                Date data = dateFormat.parse(textField.getText());
                textField.setStyle("-fx-border-color: none");

                return true;
            } catch (ParseException ex) {
                textField.setStyle("-fx-border-color: #F00");
                mostrarAlerta("Erro!", "Campo com data invalida!");
                return false;
            }
        } else {
            mostrarAlerta("Erro!", "Informe uma data!");
        }

        return false;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
    
    private void mostrarSucesso( String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }

    private Viaturas viaturaOBM = null;
    private Viaturas viaturaApoio = null;
    
    private void configurarAutocomplete() {

        SuggestionProvider sugestoesViaturas = SuggestionProvider.create(new ArrayList());
        new AutoCompletionTextFieldBinding<>(txtVtrOBM, sugestoesViaturas);

        try {
            viaturas = new EmpenharViaturaDAO().getTodasViaturas();
        } catch (SQLException ex) {
            Logger.getLogger(TelaEmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sugestoesViaturas.clearSuggestions();
        sugestoesViaturas.addPossibleSuggestions(viaturas);
        
        txtVtrOBM.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                viaturaOBM = null;
                if((newValue.length() - oldValue.length()) > 1 || newValue.contains("Placa: ") ){
                    for (Viaturas viatura : viaturas) {
                        if(viatura.getNome_viatura().equals(newValue)){
                            viaturaOBM = viatura;
                            System.out.println("OK!" + viatura.getIdViatura());
                            return;
                        }   
                    }   
                }
            }
        });
        
        
        
        
        SuggestionProvider sugestoesViaturasApoio = SuggestionProvider.create(new ArrayList());
        new AutoCompletionTextFieldBinding<>(txtVtrOrgao, sugestoesViaturasApoio);

        try {
            viaturasApoio = new EmpenharViaturaDAO().getViaturasApoio();
        } catch (SQLException ex) {
            Logger.getLogger(TelaEmpenharViaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sugestoesViaturasApoio.clearSuggestions();
        sugestoesViaturasApoio.addPossibleSuggestions(viaturasApoio);

        
        txtVtrOrgao.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                viaturaApoio = null;
                if((newValue.length() - oldValue.length()) > 1 || newValue.contains(" - ") ){
                    for (Viaturas viatura : viaturasApoio) {
                        if(viatura.getNome_viatura().equals(newValue)){
                            viaturaApoio = viatura;
                            System.out.println("OK!" + viatura.getIdViatura());
                            return;
                        }   
                    }   
                }
            }
        });
    }
    
    
    public void atualizarObj(Object obj){
        
        Viaturas aux = (Viaturas)obj;
        
        for (TabelaEmpenharViatura tabelaEmpenharViatura : obsTabelaClone) {
            if( tabelaEmpenharViatura.getIdViatura() == aux.getIdViatura() ){
                tabelaEmpenharViatura.setSelecionado(aux.isSelecionado());
            }
        }
        
    }
        
}

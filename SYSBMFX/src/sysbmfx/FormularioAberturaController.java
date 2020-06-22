package sysbmfx;

import bancodados.ConexaoMysql;
import bancodados.EnderecoDAO;
import bancodados.NaturezaDAO;
import bancodados.RgoDAO;
import classes.Bairro;
import classes.Cidade;
import classes.Logradouro;
import classes.MeioAviso;
import classes.Natureza;
import classes.Subnatureza;
import classes.Usuario;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrador
 */
public class FormularioAberturaController implements Initializable {

    @FXML
    private TextArea areaDescritivo;

    @FXML
    private RadioButton radioN;

    @FXML
    private RadioButton radioKm;
    @FXML
    private RadioButton radioCruzamento;
    @FXML
    private RadioButton radioUrbano;
    @FXML
    private RadioButton radioRural;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtLogradouro;
    @FXML
    private TextField txtEsquina;
    @FXML
    private TextField txtPontoReferencia;
    @FXML
    private ComboBox<Natureza> comboNatureza;
    @FXML
    private ComboBox<Subnatureza> comboSubnatureza;
    @FXML
    private TextField txtMunicipio;
    @FXML
    private TextField txtnumouKm;
    @FXML
    private ComboBox<MeioAviso> comboMeioAviso;
    @FXML
    private Label labelAtendente;
    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnIncluirSemDespacho;
    @FXML
    private Button btnClassificarLigacao;
    @FXML
    private Button btnIncluirEDespachar;
    @FXML
    private TextField datefield;
    @FXML
    private TextField fieldNomeSolicitante;
    @FXML
    private TextField fieldNumSolicitante;

    private List<Natureza> naturezas = new ArrayList<>();
    private ObservableList<Natureza> obsNatureza;

    private List<MeioAviso> meioAvisos = new ArrayList<>();
    private ObservableList<MeioAviso> obsMeioAviso;

    private List<Subnatureza> subNaturezas = new ArrayList<>();
    private ObservableList<Subnatureza> obsSubnaturezas;

    //Lista cidades para o autocomplete
    private List<Cidade> cidades = new ArrayList<>();
    //variavel global
    private int idMunicipio = 0;

    //Lista cidades para o autocomplete
    private List<Logradouro> logradouros = new ArrayList<>();
    //variavel global
    private int idLogradouro = 0;

    private List<Bairro> bairros = new ArrayList<>();
    private int idBairro = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCampos();
    }

    public void start() throws IOException {

        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();

        Parent root = FXMLLoader.load(getClass().getResource("FormularioAbertura.fxml"));
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

    private void inicializarCampos() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        datefield.setText(dateFormat.format(date));
        
        datefield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String value = datefield.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})\\/(\\d{4})(\\d)", "$1/$2/$3 $4");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})\\/(\\d{4})\\s(\\d{2})(\\d)", "$1/$2/$3 $4:$5");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})\\/(\\d{4})\\s(\\d{2}):(\\d{2})(\\d)", "$1/$2/$3 $4:$5:$6");
                value = value.substring(0, (value.length() < 19 ? value.length() : 19));

                datefield.setText(value);
                datefield.positionCaret(datefield.getText().length());

            }
        });

        fieldNumSolicitante.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String value = fieldNumSolicitante.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d)(\\d)", "($1$2)");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "($1$2)$3");

                value = value.substring(0, (value.length() < 13 ? value.length() : 13));

                fieldNumSolicitante.setText(value);
                fieldNumSolicitante.positionCaret(fieldNumSolicitante.getText().length());

            }
        });

        txtEsquina.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtnumouKm.setEditable(false);
                radioCruzamento.setSelected(true);
            }
        });

        txtnumouKm.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtEsquina.setEditable(false);
                radioN.setSelected(true);
            }
        });

        try {
            obsNatureza = FXCollections.observableArrayList(new NaturezaDAO().getNaturezas());
            comboNatureza.setItems(obsNatureza);

            comboNatureza.valueProperty().addListener(new ChangeListener<Natureza>() {
                @Override
                public void changed(ObservableValue<? extends Natureza> observable, Natureza oldValue, Natureza newValue) {

                    try {
                        obsSubnaturezas = FXCollections.observableArrayList(new NaturezaDAO().getSubnaturezas(newValue.getIdnatureza()));
                        comboSubnatureza.setItems(obsSubnaturezas);
                        comboSubnatureza.getSelectionModel().select(0);
                    } catch (SQLException ex) {
                        mostrarErroBD();
                    } catch (NullPointerException ex) {
                        mostrarErroBD();
                    }

                }
            });
            
            comboNatureza.getSelectionModel().select(0);

            obsSubnaturezas = FXCollections.observableArrayList(new NaturezaDAO().getSubnaturezas(0));
            comboSubnatureza.setItems(obsSubnaturezas);
            comboSubnatureza.getSelectionModel().select(0);
            
            
            labelAtendente.setText(Usuario.getNome_efetivo() + " " + Usuario.getRG());

            //meio aviso
            obsMeioAviso = FXCollections.observableArrayList(new RgoDAO().getMeioAviso());
            comboMeioAviso.setItems(obsMeioAviso);
            comboMeioAviso.getSelectionModel().select(0);

            //fimmeioaviso
            //Popula lista com as cidades do banco de dados
            cidades = new EnderecoDAO().getCidades();

            //Cria campo autocomplete parametros 1º Campo textfield e 2º lista
            TextFields.bindAutoCompletion(txtMunicipio, cidades);

            SuggestionProvider sugestoesBairros = SuggestionProvider.create(new ArrayList());
            new AutoCompletionTextFieldBinding<>(txtBairro, sugestoesBairros);

            //sugestoes logradouros
            SuggestionProvider sugestoesLogradouros = SuggestionProvider.create(new ArrayList());
            new AutoCompletionTextFieldBinding<>(txtLogradouro, sugestoesLogradouros);

            new AutoCompletionTextFieldBinding<>(txtEsquina, sugestoesLogradouros);

            //Adciona evento para verificar quando sair do campo para pegar o id da cidade selecionada
            txtMunicipio.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean onFocus) {
                    //quando o onfocus for falso é percorrido a lista de cidades e compara com o texto do textfield, se achar ele grava o id na variavel global e para o for
                    if (!onFocus) {
                        for (Cidade cidade : cidades) {
                            if (cidade.getCidade().equals(txtMunicipio.getText())) {
                                idMunicipio = cidade.getIdcidade();

                                try {

                                    //Popula lista com as cidades do banco de dados
                                    bairros = new EnderecoDAO().getBairro(idMunicipio);

                                    sugestoesBairros.clearSuggestions();
                                    sugestoesBairros.addPossibleSuggestions(bairros);

                                    logradouros = new EnderecoDAO().getLogradouro(idMunicipio);
                                    sugestoesLogradouros.clearSuggestions();
                                    sugestoesLogradouros.addPossibleSuggestions(logradouros);

                                } catch (SQLException ex) {
                                    mostrarErroBD();
                                }

                                break;
                            }
                        }
                    }
                }
            });
            
            
            txtMunicipio.setText(Usuario.getNomeCidade());
            idMunicipio = Usuario.getMunicipio_presta_servico();
        } catch (SQLException ex) {
            mostrarErroBD();
        }

    }
    @FXML
    private void onClickLigacao(ActionEvent event) throws IOException{
        ClassificarLigacaoController ligacao = new ClassificarLigacaoController();
        ligacao.start();
        
        
    }
    
    private void mostrarErroBD() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Não foi possivel carregar informações do banco!");
        alert.showAndWait();
    }

    @FXML
    private void radioCheck(ActionEvent event) {
        if (radioN.isSelected()) {
            txtEsquina.setText("");
            txtEsquina.setEditable(false);
            txtnumouKm.setEditable(true);
            radioN.setSelected(true);
        } else if (radioKm.isSelected()) {
            txtEsquina.setText("");
            txtEsquina.setEditable(false);
            txtnumouKm.setEditable(true);
            radioKm.setSelected(true);
        } else if (radioCruzamento.isSelected()) {
            txtnumouKm.setEditable(false);
            txtnumouKm.setText("");
            txtEsquina.setEditable(true);
            radioCruzamento.setSelected(true);
        } else {

        }
    }

    @FXML
    private void onclikAtualizarDataeHora(ActionEvent event) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        datefield.setText(dateFormat.format(date));
    }

    @FXML
    private void onclickIncluirSemDespacho(ActionEvent event) throws SQLException {
        incluir(0,false);
    }

    @FXML
    private void onclickIncluirEDespachar(ActionEvent event) throws IOException, SQLException {
        incluir(1,true);        
    }
    
    @FXML
    private void onclickCancelar(ActionEvent event) throws IOException, SQLException {
        MenuController menu = new MenuController();
        menu.start();
    }

    @FXML
    private void onclickIncluir(ActionEvent event) throws SQLException {
        incluir(1,false);
    }

    private void incluir(int i, boolean abrirDespacho) throws SQLException {

        if (datefield.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher a data corretamente");
            alert.showAndWait();
        } else if (fieldNumSolicitante.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o número do solicitante corretamente");
            alert.showAndWait();
        } else if (fieldNomeSolicitante.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o nome do solicitante corretamente");
            alert.showAndWait();
        } else if (txtMunicipio.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o municipio corretamente");
            alert.showAndWait();
        } else if (txtLogradouro.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o logradouro corretamente");
            alert.showAndWait();
        } else if (txtnumouKm.getText().equals("") && txtEsquina.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o campo numero ou KM ou Esquina corretamente");
            alert.showAndWait();
        } else if (areaDescritivo.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Preencher o descritivo corretamente");
            alert.showAndWait();
        } else if (comboNatureza.getSelectionModel().getSelectedIndex() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione uma natureza do evento");
            alert.showAndWait();
        } else if (comboSubnatureza.getSelectionModel().getSelectedIndex() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione uma Subnatureza do Evento");
            alert.showAndWait();
        } else {

            try {
                String numrgo = new RgoDAO().pegarNumRgo();
                String date = datefield.getText();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date data = new Date();
                data = dateFormat.parse(date);
                dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                System.out.println(dateFormat.format(data));
                date = dateFormat.format(data);
                int meioAviso = comboMeioAviso.getSelectionModel().getSelectedItem().getIdts_meioaviso();
                String nomeSolicitante = fieldNomeSolicitante.getText();
                String numeroSolicitante = fieldNumSolicitante.getText();
                int natureza = comboNatureza.getSelectionModel().getSelectedItem().getIdnatureza();
                int naturezanova = comboNatureza.getSelectionModel().getSelectedItem().getIdnatureza();
                int subNatureza = comboSubnatureza.getSelectionModel().getSelectedItem().getIdsubnatureza();
                int subnaturezanova = comboSubnatureza.getSelectionModel().getSelectedItem().getIdsubnatureza();
                String empenhada_samu, indicio_crime = "", divulgar_imprensa = "", apoio = "", vitimas = "", veiculos_envolvidos = "", prod_perigoso = "", metodogeo = "";
                int id_postopgv = 0;
                int tabela_despacho = i;
                int fk_idobm = Usuario.getObm_idobm();
                int fk_idunidade = Usuario.getSubunidade_idsubunidade();
                int status_basico = 1;
                int intervensao = 0;
                String municipio = txtMunicipio.getText();
                String bairro = txtBairro.getText();
                String esquina = txtEsquina.getText();
                String logradouro = txtLogradouro.getText();
                String pontoReferencia = txtPontoReferencia.getText();
                int numouKm;
                if (txtnumouKm.getText().equals("")) {
                    numouKm = 0;
                } else {
                    numouKm = Integer.parseInt(txtnumouKm.getText());

                }

                String nmKmCruzamento;
                if (radioN.isSelected()) {
                    nmKmCruzamento = "n°";
                } else if (radioKm.isSelected()) {
                    nmKmCruzamento = "km";
                } else if (radioCruzamento.isSelected()) {
                    nmKmCruzamento = "Esquina";
                } else {
                    nmKmCruzamento = "";
                }

                String perimetro;
                if (radioUrbano.isSelected()) {
                    perimetro = "Urbano";
                } else if (radioRural.isSelected()) {
                    perimetro = "Rural";
                } else {
                    perimetro = "";
                }

                String Descritivo = areaDescritivo.getText();
                int id_municipio = Usuario.getMunicipio_presta_servico();
                int obm_designada = Usuario.getObm_idobm();

                PreparedStatement preparedStatement;
                preparedStatement = ConexaoMysql.getConexaoMySQL().prepareStatement("INSERT INTO `sisbm_novo`.`rgo`(`nr_rgo`, `datahora_recebimento`, `ts_meioaviso_idts_meioaviso`, `solicitante_nome`, `solicitante_telefone`, `ts_nateventos_idts_nateventos`, `ts_subnateventos_idts_subnateventos`, `descritivo`, `perimetro`, `municipio`, `bairro`, `endereco_solicitacao`, `tipo_num`, `endereco_num`, `esquina`,`realizados`, `ponto_referencia`, `descricao_bensatingidos`, `descricao_bensrecolhidos`, `bens_entregue`, `fk_id_obm`, `fk_id_unidade`, `sb`, `acidente_trabalho`, `divulgar_imprensa`, `apoio`, `vitimas`, `veiculos_envol`, `prod_perigoso`, `ts_intervencao_idts_intervencao`,`ts_statusbasico_idts_statusbasico`,`id_natureza_nova`,`id_subnatureza_nova`,`fk_id_posto`,`metodoGeo`,`tabela_despacho`,`indicio_crime`,`id_postopgv`,`geocoding_trusted`,`empenhada_samu`,`id_municipio`,`id_obm_designada`,`urgente`,atendente, id_atendente)VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, numrgo);
                preparedStatement.setString(2, date);
                preparedStatement.setInt(3, meioAviso);
                preparedStatement.setString(4, nomeSolicitante);
                preparedStatement.setString(5, numeroSolicitante);
                preparedStatement.setInt(6, natureza);
                preparedStatement.setInt(7, subNatureza);
                preparedStatement.setString(8, Descritivo);
                preparedStatement.setString(9, perimetro);
                preparedStatement.setString(10, municipio);
                preparedStatement.setString(11, bairro);
                preparedStatement.setString(12, logradouro);
                preparedStatement.setString(13, nmKmCruzamento);
                preparedStatement.setInt(14, numouKm);
                preparedStatement.setString(15, esquina);
                preparedStatement.setString(16, "");
                preparedStatement.setString(17, pontoReferencia);
                preparedStatement.setString(18, "");
                preparedStatement.setString(19, "");
                preparedStatement.setString(20, "NÃO HOUVE");
                preparedStatement.setInt(21, Usuario.getObm_idobm());
                preparedStatement.setInt(22, Usuario.getSubunidade_idsubunidade());
                preparedStatement.setString(23, "");
                preparedStatement.setString(24, "");
                preparedStatement.setString(25, divulgar_imprensa);
                preparedStatement.setString(26, apoio);
                preparedStatement.setString(27, vitimas);
                preparedStatement.setString(28, veiculos_envolvidos);
                preparedStatement.setString(29, prod_perigoso);
                preparedStatement.setInt(30, intervensao);
                preparedStatement.setInt(31, status_basico);
                preparedStatement.setInt(32, naturezanova);
                preparedStatement.setInt(33, subnaturezanova);
                preparedStatement.setInt(34, 0);
                preparedStatement.setString(35, metodogeo);
                preparedStatement.setInt(36, tabela_despacho);
                preparedStatement.setString(37, indicio_crime);
                preparedStatement.setInt(38, id_postopgv);
                preparedStatement.setInt(39, 0);
                preparedStatement.setInt(40, 0);
                preparedStatement.setInt(41, id_municipio);
                preparedStatement.setInt(42, obm_designada);
                preparedStatement.setString(43, "Sim");
                preparedStatement.setString(44, Usuario.getNome_efetivo());
                preparedStatement.setString(45, Usuario.getRG());
                
                preparedStatement.execute();
                
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                
                while (resultSet.next()) {                    
                    String comando = "curl -X GET http://www.bombeiroscascavel.com.br/sysbmnew/georeferenciar_v3/?idrgo="+resultSet.getInt(1)+"&retornoFormulario=formulario_abertura";
                    System.out.println(comando);
                    Process process;
                    try {
                        process = Runtime.getRuntime().exec(comando);
                        process.getInputStream();
                    } catch (IOException ex) {
                        System.out.println("erro geo");
                        Logger.getLogger(FormularioAberturaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Alert a =  new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("Ocorrência registrada com sucesso!");
                    a.showAndWait();

                    if(abrirDespacho){
                        try {
                            new EmpenharViaturaController().start(resultSet.getInt(1));
                        } catch (IOException ex) {
                            Logger.getLogger(FormularioAberturaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
                
                
                
                limparCampos();

            } catch (ParseException ex) {
                Logger.getLogger(FormularioAberturaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
    private void limparCampos(){
    
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        datefield.setText(dateFormat.format(date));
        
        fieldNumSolicitante.setText("");
        fieldNomeSolicitante.setText("");
        txtBairro.setText("");
        txtLogradouro.setText("");
        txtEsquina.setText("");
        txtPontoReferencia.setText("");
        txtnumouKm.setText("");
        radioN.selectedProperty().set(true);
        radioKm.selectedProperty().set(false);
        radioCruzamento.selectedProperty().set(false);
        
        radioUrbano.selectedProperty().set(true);
        radioRural.selectedProperty().set(false);
        
        comboNatureza.getSelectionModel().select(0);
        comboSubnatureza.getSelectionModel().select(0);
        
        comboMeioAviso.getSelectionModel().select(0);
    
        areaDescritivo.setText("");
        
    }

}

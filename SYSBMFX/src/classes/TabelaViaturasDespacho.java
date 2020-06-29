/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import bancodados.TabelaViaturasDespachoDAO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import sysbmfx.FormularioQTAController;

/**
 *
 * @author Vinicius Teider
 */
public class TabelaViaturasDespacho {

    private final int idHorarios;

    private Button btnSaidaQuartel;
    private Button btnChegadaLocal;
    private Button btnSaidaLocal;
    private Button btnSaidaLocalQTA;
    private Button btnChegadaHosp;
    private Button btnSaidaHosp;
    private Button btnSaidaHospQTA;
    private Button btnChegadaQuartel;

    private Button btnQTA;
    private final Timestamp hrSaidaQuartel;
    private final Timestamp hrChegadaLocal;
    private final Timestamp hrSaidaLocal;
    private final Timestamp hrChegadaHosp;
    private final Timestamp hrSaidaHosp;
    private final Timestamp hrChegadaQuartel;

    private final String nomeViatura;
    private final int QTA;
    private final int UltimoEvento;

    private TextField txtSaidaQuartel;
    private TextField txtChegadaLocal;
    private TextField txtSaidaLocal;
    private TextField txtChegadaHosp;
    private TextField txtSaidaHosp;
    private TextField txtChegadaQuartel;

    private Label lbSaidaQuartel;
    private Label lbChegadaLocal;
    private Label lbSaidaLocal;
    private Label lbChegadaHosp;
    private Label lbSaidaHosp;
    private Label lbChegadaQuartel;

    private Consumer funcaoAtualizar;

    public ObjectProperty getNomeViatura() {
        return new SimpleObjectProperty(nomeViatura);
    }

    public TabelaViaturasDespacho(int idHorarios, Timestamp hrSaidaQuartel, Timestamp hrChegadaLocal, Timestamp hrSaidaLocal, Timestamp hrChegadaHosp, Timestamp hrSaidaHosp, Timestamp hrChegadaQuartel, String nomeViatura, int QTA, int UltimoEvento) {
        this.hrSaidaQuartel = hrSaidaQuartel;
        this.hrChegadaLocal = hrChegadaLocal;
        this.hrSaidaLocal = hrSaidaLocal;
        this.hrChegadaHosp = hrChegadaHosp;
        this.hrSaidaHosp = hrSaidaHosp;
        this.hrChegadaQuartel = hrChegadaQuartel;
        this.nomeViatura = nomeViatura;
        this.QTA = QTA;
        this.UltimoEvento = UltimoEvento;
        this.idHorarios = idHorarios;
    }

    public ObjectProperty getQTABtn() {

        btnQTA = new Button("QTA");
        
        btnQTA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                new FormularioQTAController().start(idHorarios, funcaoAtualizar);
                
            }
        });

        return new SimpleObjectProperty((UltimoEvento == 7 ? "" : (QTA == 0 ? btnQTA : "QTA")));
    }

    private void criarFuncaoBtn(Button button, String campo, int ultimoEvento) {

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Log.gravar(this.getClass(),"Clicado no botao "+campo+" | idHorarios: "+idHorarios);
                
                try {
                    if (new TabelaViaturasDespachoDAO().atualizarHorario(idHorarios, campo, ultimoEvento)) {
                        
                        Log.gravar(this.getClass(),"Horario atualizado com sucesso | "+campo+" | idHorarios: "+idHorarios);
                        funcaoAtualizar.accept(new Object());
                        
                    }
                } catch (SQLException ex) {
                    Log.gravar(this.getClass(),"Erro atualizar horarios "+campo+" | IdHorario: "+idHorarios+" | Erro: "+ex.getMessage());
                }

            }
        });

    }

    public ObjectProperty getBtnSaidaQuartel() {

        btnSaidaQuartel = new Button("Saida Quartel");
        txtSaidaQuartel = new TextField();
        lbSaidaQuartel = new Label();

        return configurarCampos(btnSaidaQuartel, txtSaidaQuartel, lbSaidaQuartel, hrSaidaQuartel, Arrays.asList(-1));

    }

    public ObjectProperty getBtnChegadaLocal() {
        btnChegadaLocal = new Button("Chegada Local");
        txtChegadaLocal = new TextField();
        lbChegadaLocal = new Label();

        criarFuncaoBtn(btnChegadaLocal, "h_chegadaL", 1);

        return configurarCampos(btnChegadaLocal, txtChegadaLocal, lbChegadaLocal, hrChegadaLocal, Arrays.asList(0));
    }

    public ObjectProperty getBtnSaidaLocal() {
        btnSaidaLocal = new Button("Saida Local");
        btnSaidaLocalQTA = new Button("Saida Local(QTA)");
        txtSaidaLocal = new TextField();
        lbSaidaLocal = new Label();

        criarFuncaoBtn(btnSaidaLocal, "h_saidaL", 2);
        criarFuncaoBtn(btnSaidaLocalQTA, "h_saidaL", 3);

        return configurarCampos(btnSaidaLocal, btnSaidaLocalQTA, txtSaidaLocal, lbSaidaLocal, hrSaidaLocal, Arrays.asList(1));
    }

    public ObjectProperty getBtnChegadaHosp() {
        btnChegadaHosp = new Button("Cheg. Hosp.");
        txtChegadaHosp = new TextField();
        lbChegadaHosp = new Label();

        criarFuncaoBtn(btnChegadaHosp, "h_chegadaH", 4);

        return configurarCampos(btnChegadaHosp, txtChegadaHosp, lbChegadaHosp, hrChegadaHosp, Arrays.asList(2));
    }

    public ObjectProperty getBtnSaidaHosp() {
        btnSaidaHosp = new Button("Saida Hosp.");
        btnSaidaHospQTA = new Button("Saida Hosp.(QTA)");
        txtSaidaHosp = new TextField();
        lbSaidaHosp = new Label();

        criarFuncaoBtn(btnSaidaHosp, "h_saidaH", 5);
        criarFuncaoBtn(btnSaidaHospQTA, "h_saidaH", 6);

        return configurarCampos(btnSaidaHosp, btnSaidaHospQTA, txtSaidaHosp, lbSaidaHosp, hrSaidaHosp, Arrays.asList(4));
    }

    public ObjectProperty getBtnChegadaQuartel() {
        btnChegadaQuartel = new Button("Cheg. Quartel");
        txtChegadaQuartel = new TextField();
        lbChegadaQuartel = new Label();

        criarFuncaoBtn(btnChegadaQuartel, "h_chegadaQ", 7);

        return configurarCampos(btnChegadaQuartel, txtChegadaQuartel, lbChegadaQuartel, hrChegadaQuartel, Arrays.asList(5, 6, 2, 3));
    }

    public ObjectProperty getBtnFuncao(Consumer method) {

        this.funcaoAtualizar = method;

        HBox hBox = new HBox();

        Button btnFuncao = new Button("Editar");

        FontAwesomeIconView iconSalvar = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
        iconSalvar.setFill(Color.web("#43a047"));
        iconSalvar.setSize("16,0");
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setGraphic(iconSalvar);
        btnSalvar.setVisible(false);

        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String querySQL = "";
                int ultimoEvento = 0;
                
                String dadosSQL = validarCampoData(txtSaidaQuartel, "h_saidaQ");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 0;
                }
                
                dadosSQL = validarCampoData(txtChegadaLocal, "h_chegadaL");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 1;
                }
                
                dadosSQL = validarCampoData(txtSaidaLocal, "h_saidaL");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 2;
                }
                
                dadosSQL = validarCampoData(txtChegadaHosp, "h_chegadaH");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 4;
                }
                
                dadosSQL = validarCampoData(txtSaidaHosp, "h_saidaH");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 5;
                }
                
                dadosSQL = validarCampoData(txtChegadaQuartel, "h_chegadaQ");
                if(dadosSQL.equals("!")){
                    return;
                }else if(!dadosSQL.equals("")){
                    querySQL += (!querySQL.equals("")?",":"")+dadosSQL;
                    ultimoEvento = 7;
                }
                    
                try{
                    if(validarHorarios()){
                        if(new TabelaViaturasDespachoDAO().atualizarHorarioString(idHorarios, querySQL, ultimoEvento)){
                            funcaoAtualizar.accept(new Object());
                        }
                    }
                } catch (SQLException ex) {
                    mostrarAlerta("Erro!", "Erro ao atualizar!");
                } catch(Exception e){
                    mostrarAlerta("Erro!", "Há inconsistencias com os horarios informados!");
                }
                
            }
        });

        FontAwesomeIconView iconCancel = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        iconCancel.setFill(Color.web("#e53935"));
        iconCancel.setSize("16,0");
        Button btnCancel = new Button("Cancelar");
        btnCancel.setGraphic(iconCancel);
        btnCancel.setVisible(false);

        btnFuncao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                mudarVisibilidadeCampos(true);

                btnSalvar.setVisible(true);
                btnCancel.setVisible(true);

                btnFuncao.setVisible(false);
            }
        });

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                mudarVisibilidadeCampos(false);

                btnSalvar.setVisible(false);
                btnCancel.setVisible(false);

                btnFuncao.setVisible(true);

            }
        });

        hBox.getChildren().add(btnFuncao);
        hBox.getChildren().add(btnSalvar);
        hBox.getChildren().add(btnCancel);

        return new SimpleObjectProperty((UltimoEvento == 7 ? "" : hBox));
    }

    private ObjectProperty configurarCampos(Button button, TextField textField, Label label, Timestamp timestamp, List list) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String datahora = (timestamp != null ? dateFormat.format(timestamp) : "");

        AnchorPane anchorPane = new AnchorPane();

        textField.setVisible(false);
        textField.setText(datahora);

        criarMascara(textField);

        label.setText(datahora);
        label.setVisible((!list.contains(UltimoEvento)) || QTA == 1);

        if (list.size() > 1) {
            button.setVisible(list.contains(UltimoEvento));
        } else {
            button.setVisible(list.contains(UltimoEvento) && QTA == 0);
        }

        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(label);

        return new SimpleObjectProperty((true ? anchorPane : datahora));

    }

    private ObjectProperty configurarCampos(Button button, Button button1, TextField textField, Label label, Timestamp timestamp, List list) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String datahora = (timestamp != null ? dateFormat.format(timestamp) : "");

        AnchorPane anchorPane = new AnchorPane();
        HBox hbox = new HBox();

        textField.setVisible(false);
        textField.setText(datahora);

        criarMascara(textField);

        label.setText(datahora);
        label.setVisible(!list.contains(UltimoEvento) || QTA == 1);

        button.setVisible(list.contains(UltimoEvento) && QTA == 0);
        button1.setVisible(list.contains(UltimoEvento) && QTA == 0);

        hbox.getChildren().add(button);
        hbox.getChildren().add(button1);

        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(hbox);
        anchorPane.getChildren().add(label);

        return new SimpleObjectProperty((true ? anchorPane : datahora));

    }

    private void mudarVisibilidadeCampos(boolean visibilidade) {
        if (lbSaidaQuartel.getText().equals("")) {
            btnSaidaQuartel.setVisible(!visibilidade);
        } else {
            lbSaidaQuartel.setVisible(!visibilidade);
        }
        txtSaidaQuartel.setVisible(visibilidade);

        if (UltimoEvento == 0) {
            btnChegadaLocal.setVisible(!visibilidade);
        } else {
            lbChegadaLocal.setVisible(!visibilidade);
        }
        txtChegadaLocal.setVisible(visibilidade);

        if (UltimoEvento == 1) {
            btnSaidaLocal.setVisible(!visibilidade);
            btnSaidaLocalQTA.setVisible(!visibilidade);
        } else {
            lbSaidaLocal.setVisible(!visibilidade);
        }
        txtSaidaLocal.setVisible(visibilidade);

        if (UltimoEvento == 2) {
            btnChegadaHosp.setVisible(!visibilidade);
        } else {
            lbChegadaHosp.setVisible(!visibilidade);
        }
        txtChegadaHosp.setVisible(visibilidade);

        if (UltimoEvento == 4) {
            btnSaidaHosp.setVisible(!visibilidade);
            btnSaidaHospQTA.setVisible(!visibilidade);
        } else {
            lbSaidaHosp.setVisible(!visibilidade);
        }
        txtSaidaHosp.setVisible(visibilidade);

        if (UltimoEvento == 5 || UltimoEvento == 6 || UltimoEvento == 2 || UltimoEvento == 3) {
            btnChegadaQuartel.setVisible(!visibilidade);
        } else {
            lbChegadaHosp.setVisible(!visibilidade);
        }
        txtChegadaQuartel.setVisible(visibilidade);
    }

    private void criarMascara(TextField textField) {

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})\\/(\\d{4})(\\d)", "$1/$2/$3 $4");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})\\/(\\d{4})\\s(\\d{2})(\\d)", "$1/$2/$3 $4:$5");

                value = value.substring(0, (value.length() < 16 ? value.length() : 16));

                textField.setText(value);
                textField.positionCaret(textField.getText().length());

            }
        });

    }

    private String validarCampoData(TextField textField, String nomeCampo) {
        
        if (!textField.getText().isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dateFormat.setLenient(false);
            try {
                Date data = dateFormat.parse(textField.getText());
                textField.setStyle("-fx-border-color: none");
                String update = nomeCampo+" = '"+ (new SimpleDateFormat("yyyyMMddHHmm").format(data)) +"00' ";
                
                return update;
            } catch (ParseException ex) {
                textField.setStyle("-fx-border-color: #F00");
                mostrarAlerta("Erro!", "Campo com data invalida!");
                return "!";
            }
        }

        return "";
    }
    
    private boolean validarHorarios(){
        
        Date saidaQ = transformarStringData(txtSaidaQuartel.getText());
        Date chegadaL = transformarStringData(txtChegadaLocal.getText());
        Date saidaL = transformarStringData(txtSaidaLocal.getText());
        Date chegadaH = transformarStringData(txtChegadaHosp.getText());
        Date saidaH = transformarStringData(txtSaidaHosp.getText());
        Date chegadaQ = transformarStringData(txtChegadaQuartel.getText());
        
        if(chegadaQ != null 
                && (saidaQ == null
                || chegadaL == null
                || saidaL == null)){
            mostrarAlerta("Erro!", "Preencha os campos (Saida quartel, Chegada no local e Saida do local) corretamente!");
            return false;
        }
        
        if(chegadaQ != null && (chegadaH == null ^ saidaH ==  null) ){
            mostrarAlerta("Erro!", "Preencha os campos (Chegada hospital e Saida Hospital) corretamente!");
            return false;
        }
        
        if( (saidaH == null? false : chegadaQ.before(saidaH)) || (chegadaQ == null ? false :chegadaQ.before(saidaL)) ){
            mostrarAlerta("Erro!", "Chegada no quartel deve ser maior que a saida do local e do hospital!");
            return false;
        } 
        
        if( (saidaH == null ? false : saidaH.before(chegadaH)) ){
            mostrarAlerta("Erro!", "Saida do hospital deve ser maior que a chegada no hospital!");
            return false;
        } 
        
        if( (chegadaH == null ? false : chegadaH.before(saidaL)) ){
            mostrarAlerta("Erro!", "Chegada no hospital deve ser maior que a saida do local!");
            return false;
        }
        
        
        if((saidaL == null ? false :saidaL.before(chegadaL))){
            mostrarAlerta("Erro!", "Saida do local deve ser maior que a chegada do local");
            return false;
        }
        
        if((chegadaL == null? false : chegadaL.before(saidaQ))){
            mostrarAlerta("Erro!", "Chegada no local deve ser maior que a saida do local");
            return false;
        }
        
        return true;
    }
    
    private Date transformarStringData(String dataS){
        if (!dataS.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dateFormat.setLenient(false);
            try {
                Date data = dateFormat.parse(dataS);
                return data;
            } catch (ParseException ex) {
                return null;
            }
        }
        
        return null;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }

    
    
}

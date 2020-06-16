package sysbmfx;

import bancodados.DespachoDAO;
import classes.ListaRGO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrador
 */
public class DespachoTabelaController implements Initializable {

    @FXML
    TableView tbDespachar;

    @FXML
    TableView tbDespachadas;

    @FXML
    public void start() throws IOException {

        double height = SYSBMFX.stage.getHeight();
        double width = SYSBMFX.stage.getWidth();

        Parent root = FXMLLoader.load(getClass().getResource("DespachoTabela.fxml"));
        Scene scene = new Scene(root);

        SYSBMFX.stage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        SYSBMFX.stage.setX(SYSBMFX.stage.getX());
        SYSBMFX.stage.setY(SYSBMFX.stage.getY());
        SYSBMFX.stage.setWidth(width);
        SYSBMFX.stage.setHeight(height);

    }

    private ObservableList<ListaRGO> obsDespachar;
    private ObservableList<ListaRGO> obsDespachadas;
    private int cidadesCapitoes;

    Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn<ListaRGO, String> obmColuna = new TableColumn<>("OBM");
        obmColuna.setCellValueFactory(new PropertyValueFactory<>("obm"));

        TableColumn<ListaRGO, String> municipioColuna = new TableColumn<>("Municipio");
        municipioColuna.setCellValueFactory(new PropertyValueFactory<>("municipio"));

        TableColumn<ListaRGO, String> bairroColuna = new TableColumn<>("Bairro");
        bairroColuna.setCellValueFactory(new PropertyValueFactory<>("bairro"));

        TableColumn<ListaRGO, String> naturezaColuna = new TableColumn<>("Natureza");
        naturezaColuna.setCellValueFactory(new PropertyValueFactory<>("natureza"));

        TableColumn<ListaRGO, String> dataColuna = new TableColumn<>("Data");
        dataColuna.setCellValueFactory(new PropertyValueFactory<>("data"));

        try {
            cidadesCapitoes = new DespachoDAO().getCidadeCapitoes();
        } catch (SQLException ex) {
            Logger.getLogger(DespachoTabelaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        TableColumn<ListaRGO, String> funcaoColuna = new TableColumn<>("Despachar");
        customiseFactory(funcaoColuna);
        funcaoColuna.setCellValueFactory(dados -> dados.getValue().getDespachar());

        tbDespachar.getColumns().addAll(obmColuna, municipioColuna, bairroColuna, naturezaColuna, dataColuna, funcaoColuna);

        List<ListaRGO> lista = new ArrayList<>();
        obsDespachar = FXCollections.observableArrayList(lista);
        tbDespachar.setItems(obsDespachar);

        TableColumn<ListaRGO, String> obmColunaDespachadas = new TableColumn<>("OBM");
        obmColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("obm"));

        TableColumn<ListaRGO, String> municipioColunaDespachadas = new TableColumn<>("Municipio");
        municipioColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("municipio"));

        TableColumn<ListaRGO, String> bairroColunaDespachadas = new TableColumn<>("Bairro");
        bairroColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("bairro"));

        TableColumn<ListaRGO, String> naturezaColunaDespachadas = new TableColumn<>("Natureza");
        naturezaColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("natureza"));

        TableColumn<ListaRGO, String> dataColunaDespachadas = new TableColumn<>("Data");
        dataColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<ListaRGO, String> viaturaColunaDespachadas = new TableColumn<>("Viaturas");
        viaturaColunaDespachadas.setCellValueFactory(new PropertyValueFactory<>("viaturasEmpenhadas"));

        TableColumn<ListaRGO, String> funcaoColunaDespachadas = new TableColumn<>("Despachar");
        customiseFactory(funcaoColunaDespachadas);
        funcaoColunaDespachadas.setCellValueFactory(dados -> dados.getValue().getDespachar());

        tbDespachadas.getColumns().addAll(
                viaturaColunaDespachadas,
                obmColunaDespachadas,
                municipioColunaDespachadas,
                bairroColunaDespachadas,
                naturezaColunaDespachadas,
                dataColunaDespachadas,
                funcaoColunaDespachadas
        );

        obsDespachadas = FXCollections.observableArrayList(lista);
        tbDespachadas.setItems(obsDespachadas);

        tbDespachar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (tbDespachar.getSelectionModel().getSelectedItem() != null) {

                    ListaRGO item = (ListaRGO) tbDespachar.getSelectionModel().getSelectedItem();

                    try {
                        timer.cancel();
                        new EmpenharViaturaController().start(item.getIdrgo(), item.getIdNotificacao());
                    } catch (IOException ex) {
                        Logger.getLogger(DespachoTabelaController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });

        tbDespachadas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                if (tbDespachadas.getSelectionModel().getSelectedItem() != null) {

                    ListaRGO item = (ListaRGO) tbDespachadas.getSelectionModel().getSelectedItem();

                    try {
                        timer.cancel();
                        new EmpenharViaturaController().start(item.getIdrgo());
                    } catch (IOException ex) {
                        Logger.getLogger(DespachoTabelaController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        });

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                try {
                    //obsDespachar = FXCollections.observableArrayList(new DespachoDAO().getDespachar(cidadesCapitoes));
                    List<ListaRGO> auxDespachar = new DespachoDAO().getDespachar(cidadesCapitoes);
                    obsDespachar.clear();
                    obsDespachar.addAll(auxDespachar);

                    List<ListaRGO> auxDespachadas = new DespachoDAO().getDespachadas(cidadesCapitoes);
                    obsDespachadas.clear();
                    obsDespachadas.addAll(auxDespachadas);
                } catch (SQLException ex) {
                    Logger.getLogger(DespachoTabelaController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };

        final long SEGUNDOS = 1000 * 15;

        timer.schedule(task, 0, SEGUNDOS);

        SYSBMFX.stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::onTelaFechar);

    }

    private void onTelaFechar(WindowEvent event) {
        timer.cancel();
    }

    private void customiseFactory(TableColumn<ListaRGO, String> calltypel) {
        calltypel.setCellFactory(column -> {
            return new TableCell<ListaRGO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    try {

                        TableRow<ListaRGO> currentRow = getTableRow();

                        switch (item) {
                            case "1":
                                currentRow.setStyle("-fx-background-color: #d76868");
                                setText("AA");
                                break;
                            case "2":
                                currentRow.setStyle("-fx-background-color: #93cacf");
                                setText("Médico");
                                break;
                            case "3":
                                currentRow.setStyle("-fx-background-color: #b48dcc");
                                setText("ABTR");
                                break;
                        }
                    } catch (Exception e) {
                    }
                }
            };
        });
    }

}

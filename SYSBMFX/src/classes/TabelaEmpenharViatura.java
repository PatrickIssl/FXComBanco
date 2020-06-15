/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Vinicius Teider
 */
public class TabelaEmpenharViatura {
    
    
    private int idViatura;
    private String nomeViatura;
    private int idPosto;
    private String nomePosto;
    private String nomeEfetivo;
    private String nomeEvento;
    private String OBM;
    private int idMunicipio;
    private int viaturaCentral;
    private int idObm;
    private int idrgo;
    private boolean selecionado = false;
    
    private CheckBox checkBox;
    
    private Consumer funcaoAtualizar;

    public TabelaEmpenharViatura(int idViatura, String nomeViatura, int idPosto, String nomePosto, String nomeEfetivo, String nomeEvento, String OBM, int idMunicipio, int viaturaCentral, int idObm, int idrgo) {
        this.idViatura = idViatura;
        this.nomeViatura = nomeViatura;
        this.idPosto = idPosto;
        this.nomePosto = nomePosto;
        this.nomeEfetivo = nomeEfetivo;
        this.nomeEvento = nomeEvento;
        this.OBM = OBM;
        this.idMunicipio = idMunicipio;
        this.viaturaCentral = viaturaCentral;
        this.idObm = idObm;
        this.idrgo = idrgo;
    }
    
    public ObjectProperty getCheckbox(Consumer method){
        
        funcaoAtualizar = method;
        
        checkBox = new CheckBox();

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                selecionado = newValue;
                
                Viaturas viatura = new Viaturas(idViatura, nomeViatura);
                viatura.setSelecionado(selecionado);
                
                funcaoAtualizar.accept(viatura);
            }
            
        });
        
        checkBox.setSelected(selecionado);
        
        return new SimpleObjectProperty(checkBox);
        
    }
    
    public ObjectProperty getPosto(){
        return new SimpleObjectProperty(nomePosto);
    }
    
    public ObjectProperty getPrefixo(){
        return new SimpleObjectProperty(nomeViatura);
    }
    
    
    public ObjectProperty getChefe(){
        return new SimpleObjectProperty(nomeEfetivo);
    }
    
    public ObjectProperty getStatus(){
        return new SimpleObjectProperty(nomeEvento);
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public int getIdViatura() {
        return idViatura;
    }

    public String getNomeViatura() {
        return nomeViatura;
    }

    public int getIdPosto() {
        return idPosto;
    }

    public String getNomePosto() {
        return nomePosto;
    }

    public String getNomeEfetivo() {
        return nomeEfetivo;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public String getOBM() {
        return OBM;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public int getViaturaCentral() {
        return viaturaCentral;
    }

    public int getIdObm() {
        return idObm;
    }

    public int getIdrgo() {
        return idrgo;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
        
        checkBox.setSelected(selecionado);
    }
    
    
    
    
}

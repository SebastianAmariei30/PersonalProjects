package ro.ubbcluj.map.demogui.utils.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SendMessageController implements Observer {
    public TextField textField;
    Service service;
    Utilizator utilizator;
    Stage dialogStage;
    ObservableList<Utilizator> model= FXCollections.observableArrayList();
    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,Long> tableColumnId;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;
    public void setService(Service s, Stage st, Utilizator u){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=u;
        service.addObserver(this);
        initModel();
    }
    public void initialize(){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Utilizator,Long>("id"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tableView.setItems(model);
    }
    private void initModel(){
        Iterable<Utilizator> utilizators=service.obtinePrieteni(utilizator.getId());
        List<Utilizator> l=new ArrayList<>();
        utilizators.forEach(l::add);
        model.setAll(l);
    }
    @Override
    public void update() {
        initModel();
    }

    public void handleSendMessage(){
        ObservableList<Utilizator> selectedUsers = tableView.getSelectionModel().getSelectedItems();
        String mesaj=textField.getText();
        List<Long> ids=new ArrayList<>();
        selectedUsers.forEach(x->ids.add(x.getId()));
        if(!ids.isEmpty()){
            try {
                service.adaugaMesaj(utilizator.getId(),mesaj,null, LocalDateTime.now(),ids);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Trimitere mesaj","Mesajul a fost trimis");
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null,e.getMessage());
            }
        }
        else
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator");
    }
    public void handleCancel(){
        dialogStage.close();
    }
}

package ro.ubbcluj.map.demogui.utils.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddFriendshipController {
    @FXML
    private TextField textFieldUtilizator1;
    @FXML
    private TextField textFieldUtilizator2;
    @FXML
    private TextField textFieldData;
    @FXML
    private TextField textFieldStatus;
    private Service service;
    Stage dialogStage;

    @FXML
    private void initialize(){
    }

    public void setService(Service s,Stage st){
        this.service=s;
        this.dialogStage=st;
    }
    @FXML
    private void handleAdd(){
        String id1=textFieldUtilizator1.getText();
        String id2=textFieldUtilizator2.getText();
        String date=textFieldData.getText();
        String status=textFieldStatus.getText();
        String[] d =date.split(",");
        String data=d[0];
        String ora=d[1];
        date=data+" "+ora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        addFriendship(Long.parseLong(id1),Long.parseLong(id2),dateTime,status);
    }

    private void addFriendship(Long id1, Long id2, LocalDateTime l,String status){
        try {
            service.adaugaPrietenie(id1,id2,l,status);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adaugare prietenie","Prietenia a fost adaugata");
        } catch (IllegalArgumentException | ValidationException  e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

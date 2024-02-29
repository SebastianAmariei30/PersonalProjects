package ro.ubbcluj.map.demogui.utils.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

public class AddUserController {
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldPassword;
    @FXML
    private TextField textFieldPrenume;
    @FXML
    private TextField textFieldNume;
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
        String firstName=textFieldPrenume.getText();
        String lastName=textFieldNume.getText();
        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        addUser(firstName,lastName,username,password);
    }

    private void addUser(String firstName, String lastName,String username,String password){
        try {
            password=service.sha256(password);
            service.adaugaUtilizator(firstName,lastName,username,password);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adaugare user","Utilizatorul a fost adaugat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

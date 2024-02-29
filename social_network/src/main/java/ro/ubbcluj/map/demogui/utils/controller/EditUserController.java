package ro.ubbcluj.map.demogui.utils.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

public class EditUserController {
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldPassword;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldPrenume;
    @FXML
    private TextField textFieldNume;
    private Service service;
    Stage dialogStage;
    Utilizator utilizator;

    @FXML
    private void initialize(){
    }

    public void setService(Service s,Stage st,Utilizator u){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=u;
        if(null!=u){
            setFields(u);
            textFieldId.setEditable(false);
        }
    }
    @FXML
    private void handleEdit(){
        Long id =Long.parseLong(textFieldId.getText());
        String firstName=textFieldPrenume.getText();
        String lastName=textFieldNume.getText();
        String username=textFieldUsername.getText();
        String password=textFieldPassword.getText();
        updateUser(id,firstName,lastName,username,password);
    }

    private void updateUser(Long id, String firstName, String lastName,String username,String password){
        try {
            service.modificaUtilizator(id,firstName,lastName,username,password);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare utilizator","Utilizatorul a fost modificat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }
    private void setFields(Utilizator u){
        textFieldId.setText(u.getId().toString());
        textFieldPrenume.setText(u.getFirstName());
        textFieldNume.setText(u.getLastName());
    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}

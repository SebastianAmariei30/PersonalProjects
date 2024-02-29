package ro.ubbcluj.map.demogui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.UserOptionsController;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;

import java.io.IOException;

public class LoginDialogController {
    @FXML
    public TextField textFieldUserEmail;
    @FXML
    public TextField textFieldPassword;
    private Service service;
    Stage dialogStage;

    public void setService(Service s,Stage st){
        this.service=s;
        this.dialogStage=st;
    }
    public void showUserOptionsDialog(){
        try{
            if(service.obtineUtilizatorUP(textFieldUserEmail.getText(),service.sha256(textFieldPassword.getText())).isEmpty()){
                MessageAlert.showErrorMessage(null, "Nume de utilizator / parola incorecte");
                return;}
            Utilizator selected=service.obtineUtilizatorUP(textFieldUserEmail.getText(),service.sha256(textFieldPassword.getText())).get();
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("useroptions-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("User Options");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            UserOptionsController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,selected);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleLogIn() {
        showUserOptionsDialog();
    }

    public void handleCancel() {
        dialogStage.close();
    }
}

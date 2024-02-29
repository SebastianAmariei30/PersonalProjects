package ro.ubbcluj.map.demogui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.AddUserController;

import java.io.IOException;

public class LoginController {
    Service service;
    public void setService(Service service){
        this.service=service;
    }
    public void showLoginDialog(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login-dialog-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            LoginDialogController addUserController = loader.getController();
            addUserController.setService(service, dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showUserAddDialog(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("adduser-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddUserController addUserController = loader.getController();
            addUserController.setService(service, dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void handleLogIn() {
        showLoginDialog();
    }

    public void handleSignUp() {
        showUserAddDialog();
    }
}

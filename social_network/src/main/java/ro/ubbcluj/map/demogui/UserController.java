package ro.ubbcluj.map.demogui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.repository.paging.Pageable;
import ro.ubbcluj.map.demogui.repository.paging.PageableI;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.AddUserController;
import ro.ubbcluj.map.demogui.utils.controller.ConvUserController;
import ro.ubbcluj.map.demogui.utils.controller.EditUserController;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController implements Observer {
    @FXML
    public TextField textPageNumber;
    @FXML
    public TextField textPageSize;
    int currentPageNumber;
    int currentPageSize;
    Service service;

    ObservableList<Utilizator> model= FXCollections.observableArrayList();
    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,Long> tableColumnId;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;

    public void setService(Service service){
        this.service=service;
        service.addObserver(this);
        initModel();
    }
    public void initialize(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Utilizator,Long>("id"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tableView.setItems(model);
    }
    private void initModel(){
        Pageable p=new PageableI(currentPageNumber,currentPageSize);
        Iterable<Utilizator> utilizators=service.obtineUtilizatoriPages(p);
        List<Utilizator> l=new ArrayList<>();
        utilizators.forEach(l::add);
        model.setAll(l);
    }
    public void handleUpdatePage() {
        currentPageNumber=Integer.parseInt(textPageNumber.getText());
        currentPageSize=Integer.parseInt(textPageSize.getText());
        initModel();
    }
    public void handleDeleteUser() {
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.stergeUtilizator(selected.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Utilizatorul a fost sters cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator!");
    }
    @Override
    public void update() {
        initModel();
    }

    public void handleUpdateUser(){
    Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
        showUserEditDialog(selected);
    } else
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator");
    }
    public void handleFriendships(){
        showFriendshipsDialog();
    }
    public void handleAddUser() {
        showUserAddDialog();
    }
    public void handleUserOptions(){
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showUserOptionsDialog(selected);
        } else
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator");
    }
    public void showUserOptionsDialog(Utilizator selected){
        try{
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
    public void showFriendshipsDialog(){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("friendship-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Friendships");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            FriendshipController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service);
            stage1.show();
        }catch (IOException e) {
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

    public void showUserEditDialog(Utilizator utilizator) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("edituser-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUserController editUserController = loader.getController();
            editUserController.setService(service, dialogStage, utilizator);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConvUsers() {
        showConvUsersDialog();
    }
    public void showConvUsersDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("conv-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Conversations");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            ConvUserController editUserController = loader.getController();
            editUserController.setService(service, dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
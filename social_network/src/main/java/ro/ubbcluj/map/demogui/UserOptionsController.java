package ro.ubbcluj.map.demogui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.*;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserOptionsController implements Observer {
    private Service service;
    Stage dialogStage;
    Utilizator utilizator;
    ObservableList<Utilizator> model= FXCollections.observableArrayList();
    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,Long> tableColumnId;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastName;
    @FXML
    private void initialize(){
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
    public void setService(Service s,Stage st,Utilizator utilizator){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=utilizator;
        service.addObserver(this);
        initModel();
    }
    public void handleFriendRequest(){
        showFriendRequestDialog();
    }
    public void showFriendRequestDialog(){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("friendrequest-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Friend Request");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            FriendRequestController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,utilizator);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        initModel();
    }

    public void handlePendingRequests() {
        showPendingRequestsDialog();
    }

    public void showPendingRequestsDialog(){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("pendingrequests-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Pending Requests");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            PendingRequestsController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,utilizator);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSendMessage() {
        showSendMessageDialog();
    }
    public void showSendMessageDialog(){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("sendmessage-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Send Message");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            SendMessageController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,utilizator);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleReceivedMessages() {
        showReceivedMessages();
    }
    public void showReceivedMessages(){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("receivedmessages-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Received messages");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            ReceivedMessagesController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,utilizator);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showConvUserI(Utilizator selected){
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("conv-user-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("Conversations");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            ConvUserIController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,selected,utilizator);
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleConversations() {
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showConvUserI(selected);
        } else
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator. Selecteaza un utilizator pentru a vedea converastiile cu acesta");
    }
}

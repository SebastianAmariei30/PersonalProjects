package ro.ubbcluj.map.demogui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.UserOptionsController;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;
import ro.ubbcluj.map.demogui.utils.controller.ReplyMessageController;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceivedMessagesController implements Observer {
    Service service;
    Utilizator utilizator;
    Stage dialogStage;
    ObservableList<Tuple<Utilizator,Long>> model= FXCollections.observableArrayList();
    @FXML
    TableView<Tuple<Utilizator,Long>> tableView;
    @FXML
    TableColumn<Tuple<Utilizator,Long>,Long> tableColumnId;
    @FXML
    TableColumn<Tuple<Utilizator,Long>,String> tableColumnFirstName;
    @FXML
    TableColumn<Tuple<Utilizator,Long>,String> tableColumnLastName;
    @FXML
    TableColumn<Tuple<Utilizator,Long>,Long> tableColumnMessageId;
    public void setService(Service s, Stage st, Utilizator u){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=u;
        service.addObserver(this);
        initModel();
    }
    public void initialize(){
        tableColumnId.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLeft().getId()));
        tableColumnFirstName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLeft().getFirstName()));
        tableColumnLastName.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLeft().getLastName()));
        tableColumnMessageId.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getRight()));
        tableView.setItems(model);
    }
    private void initModel(){
        Iterable<Tuple<Utilizator,Long>> utilizators=service.obtineUtilizatoriMesaje(utilizator.getId());
        utilizators.forEach(System.out::println);
        List<Tuple<Utilizator,Long>> l=new ArrayList<>();
        utilizators.forEach(l::add);
        model.setAll(l);
    }
    @Override
    public void update() {
        initModel();
    }

    public void handleViewMessage(){
        Utilizator selected = tableView.getSelectionModel().getSelectedItem().getLeft();
        Long mId=tableView.getSelectionModel().getSelectedItem().getRight();
        if (selected != null) {
            showViewMessage(selected,mId);
        } else
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici un utilizator");
    }

    private void showViewMessage(Utilizator selected,Long messageId) {
        try{
            FXMLLoader fmxlLoader1=new FXMLLoader();
            fmxlLoader1.setLocation(getClass().getResource("viewmessage-view.fxml"));
            AnchorPane friendshipLayout = fmxlLoader1.load();
            Stage stage1=new Stage();
            stage1.setTitle("View Message");
            stage1.initModality(Modality.WINDOW_MODAL);
            stage1.setScene(new Scene(friendshipLayout));
            ReplyMessageController friendshipController = fmxlLoader1.getController();
            friendshipController.setService(service,stage1,selected,messageId,utilizator.getId());
            stage1.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCancel(){
        dialogStage.close();
    }
}

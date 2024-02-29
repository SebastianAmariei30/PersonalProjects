package ro.ubbcluj.map.demogui.utils.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.repository.paging.Pageable;
import ro.ubbcluj.map.demogui.repository.paging.PageableI;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PendingRequestsController implements Observer {
    @FXML
    public TextField textPageNumber;
    @FXML
    public TextField textPageSize;
    int currentPageNumber;
    int currentPageSize;
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

    public void setService(Service s,Stage st,Utilizator u){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=u;
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
        Iterable<Utilizator> utilizators=service.obtineCereriPages(p,utilizator.getId());
        List<Utilizator> l=new ArrayList<>();
        utilizators.forEach(l::add);
        model.setAll(l);
    }

    public void handleAccept(){
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.acceptaPrietenie(selected.getId(),utilizator.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend Request", "Cererea a fost acceptata cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nicio cerere!");
    }
    public void handleReject(){
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.stergePrietenie(selected.getId(),utilizator.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Friend Request", "Cererea a fost respinsa cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nicio cerere!");
    }
    public void handleCancel(){
        dialogStage.close();
    }

    @Override
    public void update() {
        initModel();
    }

    public void handleUpdatePage(ActionEvent actionEvent) {
        currentPageNumber=Integer.parseInt(textPageNumber.getText());
        currentPageSize=Integer.parseInt(textPageSize.getText());
        initModel();
    }
}

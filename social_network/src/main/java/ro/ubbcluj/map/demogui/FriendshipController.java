package ro.ubbcluj.map.demogui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.repository.paging.Pageable;
import ro.ubbcluj.map.demogui.repository.paging.PageableI;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.controller.AddFriendshipController;
import ro.ubbcluj.map.demogui.utils.controller.AddUserController;
import ro.ubbcluj.map.demogui.utils.controller.EditUserController;
import ro.ubbcluj.map.demogui.utils.controller.MessageAlert;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipController implements Observer {
    @FXML
    public TextField textPageNumber;
    @FXML
    public TextField textPageSize;
    int currentPageNumber;
    int currentPageSize;
    Service service;

    ObservableList<Prietenie> model= FXCollections.observableArrayList();
    @FXML
    TableView<Prietenie> tableView;
    @FXML
    TableColumn<Prietenie, Long> tableColumnId1;
    @FXML
    TableColumn<Prietenie, Long> tableColumnId2;

    public void setService(Service service){
        this.service=service;
        service.addObserver(this);
        initModel();
    }
    public void initialize(){
        tableColumnId1.setCellValueFactory(cellData -> {
            Tuple<Long, Long> tuple = cellData.getValue().getId();
            return new SimpleObjectProperty<>(tuple.getRight());
        });
        tableColumnId2.setCellValueFactory(cellData -> {
            Tuple<Long, Long> tuple = cellData.getValue().getId();
            return new SimpleObjectProperty<>(tuple.getLeft());
        });
        tableView.setItems(model);
    }
    private void initModel(){
        Pageable p=new PageableI(currentPageNumber,currentPageSize);
        Iterable<Prietenie> prietenii=service.obtinePrieteniiPages(p);
        List<Prietenie> l=new ArrayList<>();
        prietenii.forEach(l::add);
        model.setAll(l);
    }
    public void handleDeleteFriendship() {
        Prietenie selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.stergePrietenie(selected.getId().getLeft(),selected.getId().getRight());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Prietenia a fost stearsa cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nicio prietenie!");
    }
    @Override
    public void update() {
        initModel();
    }
    public void handleAddFriendship() {
        showFriendshipAddDialog();
    }
    public void showFriendshipAddDialog(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addfriendship-view.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Friendship");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddFriendshipController addFriendshipController = loader.getController();
            addFriendshipController.setService(service, dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleUpdatePage() {
        currentPageNumber=Integer.parseInt(textPageNumber.getText());
        currentPageSize=Integer.parseInt(textPageSize.getText());
        initModel();
    }
}

package ro.ubbcluj.map.demogui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.MessageValidator;
import ro.ubbcluj.map.demogui.domain.validators.PrietenieValidator;
import ro.ubbcluj.map.demogui.domain.validators.UtilizatorValidator;
import ro.ubbcluj.map.demogui.repository.db.FriendshipsDBRepoPaging;
import ro.ubbcluj.map.demogui.repository.db.MessagesDPRepo;
import ro.ubbcluj.map.demogui.repository.db.UsersDBRepoPaging;
import ro.ubbcluj.map.demogui.repository.paging.PagingRepo;
import ro.ubbcluj.map.demogui.repository.paging.UsersPagingRepo;
import ro.ubbcluj.map.demogui.service.Service;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        UsersPagingRepo<Long, Utilizator> repou=new UsersDBRepoPaging("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new UtilizatorValidator());
        PagingRepo<Tuple<Long, Long>, Prietenie> repop=new FriendshipsDBRepoPaging("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new PrietenieValidator());
        MessagesDPRepo repom=new MessagesDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new MessageValidator());
        Service service=new Service(repou,repop, repom);
        AnchorPane userLayout = fxmlLoader.load();
        stage.setScene(new Scene(userLayout));
        LoginController messageTaskController = fxmlLoader.getController();
        messageTaskController.setService(service);
        stage.show();
    }

    public static void main(String[] args) {
//        //InMemoryRepository<Long, Utilizator> re1pou=new InMemoryRepository<>(new UtilizatorValidator());
//        UsersDBRepo repou=new UsersDBRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new UtilizatorValidator());
//        FriendshipsDPRepo repop=new FriendshipsDPRepo("jdbc:postgresql://localhost:5432/socialnetwork","postgres","fabian",new PrietenieValidator());
//        //InMemoryRepository<Tuple<Long,Long>, Prietenie> repop=new InMemoryRepository<>(new PrietenieValidator());
//        Service service=new Service(repou,repop);
//        UI ui=new UI(service);
//        ui.run();
        launch();
    }
}
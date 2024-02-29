package ro.ubbcluj.map.demogui.utils.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReplyMessageController {
    public TextField textField;
    public TextField textField1;

    Utilizator utilizator;
    Long fromId;
    Long messageId;

    Stage dialogStage;

    Service service;
    public void setService(Service s, Stage st, Utilizator u,Long messageId,Long fromId){
        this.service=s;
        this.dialogStage=st;
        this.utilizator=u;
        this.messageId=messageId;
        this.fromId=fromId;
        setTextField(messageId);
    }
    private void setTextField(Long mId){
        this.textField.setEditable(false);
        if(service.obtineMesaj(mId).isPresent())
            this.textField.setText(service.obtineMesaj(mId).get().getMessage());
    }

    public void handleSendMessage() {
        try {
            String text= textField1.getText();
            List<Long> l=new ArrayList<>();
            l.add(utilizator.getId());
            service.adaugaMesaj(fromId,text,messageId, LocalDateTime.now(),l);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Raspuns","Raspunsul a fost trimis cu succes");
        } catch (IllegalArgumentException | ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }

    public void handleCancel(){
        dialogStage.close();
    }

}

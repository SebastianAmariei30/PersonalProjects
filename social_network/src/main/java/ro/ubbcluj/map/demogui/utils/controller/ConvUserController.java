package ro.ubbcluj.map.demogui.utils.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Message;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.service.Service;

import java.util.List;

public class ConvUserController {
    public TextField textFieldID1;
    public TextField textFieldID2;
    public TextArea textField;
    Service service;

    Stage dialogStage;
    public void setService(Service s, Stage st){
        this.service=s;
        this.dialogStage=st;
    }
    public void handleView() {
        if(textFieldID1.getText().isEmpty()||textFieldID2.getText().isEmpty())
            MessageAlert.showErrorMessage(null, "Introdu IDurile utilizatorilor!");
        long id1=Long.parseLong(textFieldID1.getText());
        long id2=Long.parseLong(textFieldID2.getText());
        List<List<Message>> mesaje = service.obtineConverastie(id1,id2);
        String conversatie = "";
        int i =1;
        long id=id1;
        for(List<Message>l:mesaje){
            conversatie+="--Conversatie "+ i +"--"+System.lineSeparator();
            i++;
            for(Message m:l){
                conversatie+=m.getData().toString()+" "+"ID"+id+": "+m.getMessage()+System.lineSeparator();
                if(id==id1)
                    id=id2;
                else id=id1;
            }
        }
        mesaje=service.obtineConverastie(id2,id1);
        id=id2;
        for(List<Message>l:mesaje){
            conversatie+="--Conversatie "+ i +"--"+System.lineSeparator();
            i++;
            for(Message m:l){
                conversatie+=m.getData().toString()+" "+"ID"+id+": "+m.getMessage()+System.lineSeparator();
                if(id==id1)
                    id=id2;
                else id=id1;
            }
        }
        textField.clear();textField.setText(conversatie);
    }

    public void handleCancel() {
        dialogStage.close();
    }
}

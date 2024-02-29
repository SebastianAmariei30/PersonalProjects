package ro.ubbcluj.map.demogui.utils.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.map.demogui.domain.Message;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.service.Service;

import java.util.List;

public class ConvUserIController {
    public TextField textFieldID1;
    public TextField textFieldID2;
    public TextArea textField;
    Service service;
    public Utilizator utilizators;
    public Utilizator utilizatorc;

    Stage dialogStage;
    public void setService(Service s, Stage st,Utilizator u,Utilizator u1){
        this.service=s;
        this.dialogStage=st;
        this.utilizators=u;
        this.utilizatorc=u1;
        long id1=utilizatorc.getId();
        long id2=utilizators.getId();
        textFieldID1.setText(String.valueOf(id1));
        textFieldID2.setText(String.valueOf(id2));
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

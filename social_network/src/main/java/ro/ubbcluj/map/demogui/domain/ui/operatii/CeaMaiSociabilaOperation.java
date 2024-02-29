package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.service.Service;

public class CeaMaiSociabilaOperation extends Operatie {
    public CeaMaiSociabilaOperation(String[] args, Service service){
        super(Comenzi.comunitate_sociabila,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=0){
            System.out.println("numar parametri invalid!");
        }
        else{
            System.out.println("Cea mai activa comunitate este formata din:");
            service.ceaMaiSociabila().forEach(x-> System.out.println(x.getId()+" " + x.getFirstName()+" " +x.getLastName()));
        }
//        for(Utilizator utilizator: service.ceaMaiSociabila())
//            System.out.println(utilizator);
    }
}

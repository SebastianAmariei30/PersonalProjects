package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.service.Service;

public class AfiseazaUtilizatoriOperation extends Operatie {
    public AfiseazaUtilizatoriOperation(String[] args, Service service){
        super(Comenzi.afiseaza_utilizatori,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=0){
            System.out.println("numar parametri invalid!");
        }
        else
            service.obtineUtilizatori().forEach(System.out::println);
//        for (Utilizator u : service.obtineUtilizator()){
//            System.out.println(u);
//        }
    }
}

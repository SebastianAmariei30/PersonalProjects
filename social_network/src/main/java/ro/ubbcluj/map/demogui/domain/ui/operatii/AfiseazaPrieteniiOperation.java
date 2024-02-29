package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

import java.time.LocalDateTime;

public class AfiseazaPrieteniiOperation extends Operatie {
    public AfiseazaPrieteniiOperation(String[] args, Service service){
        super(Comenzi.afiseaza_prietenii,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=0){
            System.out.println("numar parametri invalid!");
        }
        else{
            service.obtinePrietenii().forEach(x->{
                Utilizator u1=null;
                Utilizator u2=null;
                if(service.obtineUtilizator(x.getId().getLeft()).isPresent()&&service.obtineUtilizator(x.getId().getRight()).isPresent()){
                    u1=service.obtineUtilizator(x.getId().getLeft()).get();
                    u2=service.obtineUtilizator(x.getId().getRight()).get();
                }
                LocalDateTime l=x.getDate();
                assert u1 != null;
                System.out.println("Utilizatorul "+u1.getFirstName()+" "+u1.getLastName()+" este prieten cu utilzatorul "+
                        u2.getFirstName()+" "+u2.getLastName()+" de la data si ora "+l.toString());
            });
        }
    }
}

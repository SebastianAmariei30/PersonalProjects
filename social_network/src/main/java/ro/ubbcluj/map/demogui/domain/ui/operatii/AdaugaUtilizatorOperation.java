package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

public class AdaugaUtilizatorOperation extends Operatie {
    public AdaugaUtilizatorOperation(String[] args, Service service){
        super(Comenzi.adauga_utilizator,args,service);
    }
    @Override
    public void executeOneOperation() {
        if(args.length!=4){
            System.out.println("numar parametri invalid!");
        }
        else{
            String nume=args[0];
            String prenume=args[1];
            String username=args[2];
            String password=args[3];
            try{
                service.adaugaUtilizator(nume,prenume,username,password);
                System.out.println("Utilizator adaugat cu succes!");
            }
            catch (IllegalArgumentException | ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

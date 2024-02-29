package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdaugaPrietenieOperation extends Operatie {
    public AdaugaPrietenieOperation(String[] args, Service service){
        super(Comenzi.adauga_prietenie,args,service);
    }

    @Override
    public void executeOneOperation() throws IllegalArgumentException, ValidationException {
        if(args.length!=4){
            System.out.println("numar parametri invalid!");
        }
        else{
            String id1=args[0];
            String id2=args[1];
            String date=args[2];
            String status=args[3];
            String[] d =date.split(",");
            String data=d[0];
            String ora=d[1];
            date=data+" "+ora;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            try{
            service.adaugaPrietenie(Long.parseLong(id1),Long.parseLong(id2),dateTime,status);
            System.out.println("Prietenie adaugata cu succes!");}
            catch (IllegalArgumentException | ValidationException  e){
                System.out.println(e.getMessage());
            }

        }
    }
}

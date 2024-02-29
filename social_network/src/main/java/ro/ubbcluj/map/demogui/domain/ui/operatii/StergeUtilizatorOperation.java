package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.service.Service;
public class StergeUtilizatorOperation extends Operatie {
    public StergeUtilizatorOperation(String[] args, Service service){
        super(Comenzi.sterge_utilizator,args,service);
    }
    @Override
    public void executeOneOperation() {
        if(args.length!=1){
            System.out.println("numar parametri invalid!");
        }
        else{
            String id=args[0];
            try{
                service.stergeUtilizator(Long.parseLong(id));
                System.out.println("Utilizator sters cu succes!");
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

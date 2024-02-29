package ro.ubbcluj.map.demogui.domain.ui.operatii;

import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.domain.validators.ValidationException;
import ro.ubbcluj.map.demogui.service.Service;

public class PrieteniLunaOperation  extends Operatie {
    public PrieteniLunaOperation(String[] args, Service service){
        super(Comenzi.prieteni_luna,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=2){
            System.out.println("numar parametri invalid!");
        }
        else {
            try{
                Long id=Long.parseLong(args[0]);
                Long luna=Long.parseLong(args[1]);
                if(service.utilizatorPrieteniiLuna(id, luna).isEmpty())
                    System.out.println("Utilizatorul dat nu are prieteni in luna data");
                else{
                    Utilizator u = null;
                    if(service.obtineUtilizator(id).isPresent())
                        u=service.obtineUtilizator(id).get();
                    assert u != null;
                    System.out.println("Utilizatorul "+ u.getFirstName() + " " + u.getLastName()+" este prieten cu:");
                    service.utilizatorPrieteniiLuna(id,luna).forEach(x-> System.out.println(x.getLeft().getFirstName()+" " +x.getLeft().getLastName()+" din "+x.getRight().toString())
                    );
                }
            }
            catch (IllegalArgumentException | ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

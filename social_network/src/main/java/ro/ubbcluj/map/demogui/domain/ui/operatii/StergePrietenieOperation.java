package ro.ubbcluj.map.demogui.domain.ui.operatii;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.ui.Comenzi;
import ro.ubbcluj.map.demogui.domain.ui.Operatie;
import ro.ubbcluj.map.demogui.service.Service;

public class StergePrietenieOperation extends Operatie {
    public StergePrietenieOperation(String[] args, Service service){
        super(Comenzi.sterge_prietenie,args,service);
    }

    @Override
    public void executeOneOperation() {
        if(args.length!=2){
            System.out.println("numar parametri invalid!");
        }
        else{
            String id1=args[0];
            String id2=args[1];
            try{
                if(service.obtinePrietenie(new Tuple<>(Long.parseLong(id1),Long.parseLong(id2))).isEmpty()&&
                        service.obtinePrietenie(new Tuple<>(Long.parseLong(id2),Long.parseLong(id1))).isEmpty())
                    System.out.println("Prietenie inexistenta!");
                else{
                service.stergePrietenie(Long.parseLong(id1),Long.parseLong(id2));
                System.out.println("Prietenie stearsa cu succes!");
                }
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

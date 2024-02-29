package ro.ubbcluj.map.demogui.domain.ui;

import ro.ubbcluj.map.demogui.domain.ui.operatii.*;
import ro.ubbcluj.map.demogui.service.Service;

public class ExpressionFactory {
    private static ExpressionFactory instance = new ExpressionFactory();
    private ExpressionFactory(){}
    public static ExpressionFactory getInstance(){
        return instance;
    }
    public Operatie createExpression(Comenzi comanda, String[] args, Service service){
        if(comanda==Comenzi.adauga_utilizator)
            return new AdaugaUtilizatorOperation(args,service);
        if(comanda==Comenzi.sterge_utilizator)
            return new StergeUtilizatorOperation(args,service);
        if(comanda==Comenzi.adauga_prietenie)
            return new AdaugaPrietenieOperation(args,service);
        if(comanda==Comenzi.sterge_prietenie)
            return new StergePrietenieOperation(args,service);
        if(comanda==Comenzi.nr_comunitati)
            return new NumarComunitatiOperation(args,service);
        if(comanda==Comenzi.comunitate_sociabila)
            return new CeaMaiSociabilaOperation(args,service);
        if(comanda==Comenzi.afiseaza_utilizatori)
            return new AfiseazaUtilizatoriOperation(args,service);
        if(comanda==Comenzi.afiseaza_prietenii)
            return new AfiseazaPrieteniiOperation(args,service);
        if(comanda==Comenzi.prieteni_luna)
            return new PrieteniLunaOperation(args,service);
        return null;
    }
}

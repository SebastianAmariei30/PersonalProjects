package ro.ubbcluj.map.demogui.domain.ui;

import ro.ubbcluj.map.demogui.service.Service;

import java.util.Objects;

public class ExpressionParser {
    private final ExpressionFactory expr;
    public ExpressionParser() {
        expr = ExpressionFactory.getInstance();
    }
    public Operatie parseExpression(String s,Service service){
        String[] str=s.split(" ");
        String[] args = new String[str.length-1];
        int ok=0;
        Comenzi cf=null;
        for(Comenzi c : Comenzi.values()){
            if(Objects.equals(str[0], c.name())){
                ok=1;
                cf=c;
            }
        }
        if(ok==0)
            return null;
        System.arraycopy(str, 1, args, 0, str.length - 1);
        return expr.createExpression(cf,args,service);
    }
}

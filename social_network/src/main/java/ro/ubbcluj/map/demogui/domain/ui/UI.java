package ro.ubbcluj.map.demogui.domain.ui;

import ro.ubbcluj.map.demogui.service.Service;

import java.util.Scanner;

public class UI {
    private Service service;

    private ExpressionParser expressionParser = new ExpressionParser();
    public UI(Service service) {
        this.service = service;
    }
    public void run()  {
        while(true){
            Scanner in= new Scanner(System.in);
            String s=in.nextLine();
            if(s.equals("exit"))
                break;
            Operatie op= expressionParser.parseExpression(s,service);
            if(op==null)
                System.out.println("Comanda invalida!");
            else
                op.execute();
        }
    }
}

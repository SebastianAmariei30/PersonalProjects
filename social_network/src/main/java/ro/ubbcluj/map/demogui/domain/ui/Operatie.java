package ro.ubbcluj.map.demogui.domain.ui;

import ro.ubbcluj.map.demogui.service.Service;

public abstract class Operatie {
    protected Comenzi comenzi;
    protected String[] args;
    protected Service service;

    public Operatie(Comenzi comenzi, String[] args,Service service) {
        this.comenzi = comenzi;
        this.args = args;
        this.service=service;
    }

    public abstract void executeOneOperation();

    public void execute(){
        this.executeOneOperation();;
    }
}

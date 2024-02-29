package ro.ubbcluj.map.demogui.service;

import ro.ubbcluj.map.demogui.domain.Message;
import ro.ubbcluj.map.demogui.domain.Prietenie;
import ro.ubbcluj.map.demogui.domain.Tuple;
import ro.ubbcluj.map.demogui.domain.Utilizator;
import ro.ubbcluj.map.demogui.graph.Graph;
import ro.ubbcluj.map.demogui.repository.Repository;
import ro.ubbcluj.map.demogui.repository.paging.Pageable;
import ro.ubbcluj.map.demogui.repository.paging.PageableI;
import ro.ubbcluj.map.demogui.repository.paging.PagingRepo;
import ro.ubbcluj.map.demogui.repository.paging.UsersPagingRepo;
import ro.ubbcluj.map.demogui.utils.observer.Observable;
import ro.ubbcluj.map.demogui.utils.observer.Observer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service implements Observable {
    private final UsersPagingRepo<Long,Utilizator> repou;
    private final PagingRepo<Tuple<Long,Long>, Prietenie>repop;
    private final Repository<Long, Message> repom;
    public Service(UsersPagingRepo<Long,Utilizator> repou, PagingRepo<Tuple<Long,Long>, Prietenie>repop, Repository<Long, Message> repom) {
        this.repou = repou;
        this.repop = repop;
        this.repom = repom;
    }
    
    public void adaugaUtilizator(String nume, String prenume,String username,String password){
        Utilizator utilizator = new Utilizator(nume,prenume,username,password);
        repou.save(utilizator);
        notifyObservers();
    }
    public void adaugaMesaj(Long from,String message, Long reply, LocalDateTime date, List<Long> to){
        Message mesaj = new Message(from,to,message,date);
        mesaj.setReply(reply);
        repom.save(mesaj);
        notifyObservers();
    }
    public void stergeUtilizator(Long id){
        //List<Long> idprieteni= new ArrayList<>();
//        for (Utilizator utilizator:repou.findOne(id).get().getFriends()){
//            idprieteni.add(utilizator.getId());
//        }
//        List<Long> l=new ArrayList<>();
//        repop.findAll().forEach(y->{
//            if(Objects.equals(y.getId().getRight(),id))
//                l.add(y.getId().getLeft());
//            if(Objects.equals(y.getId().getLeft(),id))
//                l.add(y.getId().getRight());
//        });
        //l.forEach(x->this.stergePrietenie(id,x));
//        for(Long x : idprieteni)
//            this.stergePrietenie(id,x);
        repou.delete(id);
        List<Message>l=new ArrayList<>();
        repom.findAll().forEach(l::add);
        l=l.stream().filter(x->x.getTo().contains(id))
                .collect(Collectors.toList());
        l.forEach(x->{
            List<Long>ls=x.getTo();
            ls.remove(id);
            x.setTo(ls);
            if(ls.isEmpty())
                repom.delete(x.getId());
            else repom.update(x);
                });
        notifyObservers();
    }
    public void modificaUtilizator(Long id, String prenume,String nume,String username,String password){
        Utilizator u = new Utilizator(prenume,nume,username,password);
        u.setId(id);
        repou.update(u);
        notifyObservers();
    }
    public void adaugaPrietenie(Long id1,Long id2,LocalDateTime l,String status){
        repou.findOne(id1);repou.findOne(id2);
        repop.save(new Prietenie(id1,id2, l,status));
        notifyObservers();
//        if(repou.findOne(id1).isPresent()&&repou.findOne(id2).isPresent()) {
//            repou.findOne(id1).get().getFriends().add(repou.findOne(id2).get());
//            repou.findOne(id2).get().getFriends().add(repou.findOne(id1).get());
//        }
    }
    public List<List<Message>> obtineConverastie(Long id1,Long id2){
        List<Message>l=new ArrayList<>();
        repom.findAll().forEach(l::add);
        l=l.stream().sorted(Comparator.comparing(Message::getId)).toList();
        List<Message>f=l.stream().filter(x->x.getTo().contains(id2)&& Objects.equals(x.getFrom(), id1)&&x.getReply()==-1).toList();
        List<List<Message>> conv=new ArrayList<>();
        for(Message m:f){
            Long idc=m.getId();
            List<Message>nl=new ArrayList<>();
            nl.add(m);
            for(Message m1:l){
                if(Objects.equals(m1.getReply(), idc)){
                    idc=m1.getId();
                    nl.add(m1);
                }
            }
            conv.add(nl);
        }
        return conv;
    }
    public void stergePrietenie(Long id1,Long id2){
        if(repop.findOne(new Tuple<>(id1,id2)).isPresent())
            repop.delete(new Tuple<>(id1, id2));
        if(repop.findOne(new Tuple<>(id2,id1)).isPresent())
            repop.delete(new Tuple<>(id2, id1));
        notifyObservers();
//        if(repou.findOne(id1).isPresent())
//            repou.findOne(id1).get().getFriends().removeIf(x->x.getId().equals(id2));
//        if(repou.findOne(id2).isPresent())
//            repou.findOne(id2).get().getFriends().removeIf(x->x.getId().equals(id1));
    }
    public Iterable<Utilizator> obtineUtilizatoriDisponibiliPage(Pageable p,Long id){
        Iterable<Utilizator> utilizators= repou.findAllNFriends(p,id).getContent().toList();
        return getUtilizators(utilizators);
    }
    public String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Optional<Utilizator> obtineUtilizatorUP(String username,String password){
        List<Utilizator> u=new ArrayList<>();
        repou.findAll().forEach(u::add);
        if(u.stream().filter(x-> Objects.equals(x.getUsername(), username) && Objects.equals(x.getPassword(), password)).toList().isEmpty())
            return Optional.empty();
        return Optional.of(u.stream().filter(x-> Objects.equals(x.getUsername(), username) && Objects.equals(x.getPassword(), password)).toList().get(0));
    }
    public Iterable<Utilizator> obtineUtilizatoriDisponibili(Long id){
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        List<Utilizator>u=new ArrayList<>();
        repou.findAll().forEach(u::add);
        return u.stream().filter(x->{
            Tuple<Long,Long> t=new Tuple<>(x.getId(),id);
            Tuple<Long,Long> t1=new Tuple<>(id,x.getId());
            return repop.findOne(t1).isEmpty()&&repop.findOne(t).isEmpty()&& !Objects.equals(x.getId(), id);
        }).collect(Collectors.toList());
    }
    public Iterable<Message> obtineMesaje(Long id){
        List<Message>l=new ArrayList<>();
        repom.findAll().forEach(l::add);
        return l.stream().filter(x->x.getTo().contains(id))
                .collect(Collectors.toList());
    }
    public Optional<Message> obtineMesaj(Long id){
        return repom.findOne(id);
    }
    public Iterable<Tuple<Utilizator,Long>> obtineUtilizatoriMesaje(Long id){
        List<Message>l=new ArrayList<>();
        this.obtineMesaje(id).forEach(l::add);
        return l.stream().map(x->{
            if(repou.findOne(x.getFrom()).isPresent())
                return new Tuple<>(repou.findOne(x.getFrom()).get(),x.getId());
            return null;
        }).collect(Collectors.toList());
    }
    public Iterable<Utilizator> obtinePrieteni(Long id){
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        return l.stream().filter(x-> Objects.equals(x.getId().getLeft(), id) || Objects.equals(x.getId().getRight(), id))
                .filter(x-> Objects.equals(x.getStatus(), "accepted"))
                .map(x->{
                    if(Objects.equals(x.getId().getRight(), id))
                        if(repou.findOne(x.getId().getLeft()).isPresent())
                            return repou.findOne(x.getId().getLeft()).get();
                    if(repou.findOne(x.getId().getRight()).isPresent())
                        return repou.findOne(x.getId().getRight()).get();
                    return null;
                }).collect(Collectors.toList());
    }
    public Iterable<Prietenie> obtinePrieteniiPages(Pageable p){
        return repop.findAll(p).getContent().toList();
    }
    public Iterable<Prietenie> obtinePrietenii(){
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        return l.stream().filter(x->x.getStatus().equals("accepted"))
                .collect(Collectors.toList());
    }
    public void acceptaPrietenie(Long id1, Long id2){
        if(repop.findOne(new Tuple<>(id1,id2)).isPresent()){
            Prietenie p =repop.findOne(new Tuple<>(id1,id2)).get();
            repop.update(new Prietenie(id1,id2,p.getDate(),"accepted"));
        }
        if(repop.findOne(new Tuple<>(id2,id1)).isPresent()){
            Prietenie p =repop.findOne(new Tuple<>(id2,id1)).get();
            repop.update(new Prietenie(id2,id1,p.getDate(),"accepted"));
        }
        notifyObservers();
    }
    public Iterable<Utilizator> obtineCereriPages(Pageable p,Long id){
        Iterable<Utilizator> utilizators= repou.findAllPFriends(p,id).getContent().toList();
        return getUtilizators(utilizators);
    }
    public Iterable<Utilizator> obtineCereri(Long id){
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        return l.stream().filter(x-> Objects.equals(x.getId().getLeft(), id) || Objects.equals(x.getId().getRight(), id))
                .filter(x-> Objects.equals(x.getStatus(), "pending"))
                .map(x->{
                    if(Objects.equals(x.getId().getRight(), id))
                        if(repou.findOne(x.getId().getLeft()).isPresent())
                            return repou.findOne(x.getId().getLeft()).get();
                    if(repou.findOne(x.getId().getRight()).isPresent())
                        return repou.findOne(x.getId().getRight()).get();
                    return null;
                }).collect(Collectors.toList());
    }
    public Optional<Prietenie> obtinePrietenie(Tuple<Long,Long> p){return  repop.findOne(p);}
    public Optional<Utilizator> obtineUtilizator(Long id){
        return repou.findOne(id);
    }
    public List<Tuple<Utilizator,LocalDateTime>> utilizatorPrieteniiLuna(Long id,Long luna){
        repou.findOne(id);
//        List<Tuple<Utilizator,LocalDateTime>> f=new ArrayList<>();
        List<Prietenie> l= new ArrayList<>();
        repop.findAll().forEach(l::add);
        return l.stream().filter(x->x.getDate().getMonth().getValue()==luna)
                .filter(x->x.getId().getLeft().equals(id)||x.getId().getRight().equals(id))
                .map(x->{
                    if(Objects.equals(x.getId().getRight(), id))
                        if(repou.findOne(x.getId().getLeft()).isPresent())
                            return new Tuple<>(repou.findOne(x.getId().getLeft()).get(),x.getDate());
                    if(repou.findOne(x.getId().getRight()).isPresent())
                        return new Tuple<>(repou.findOne(x.getId().getRight()).get(),x.getDate());
                    return null;
                })
                .collect(Collectors.toList());

    }
    public Iterable<Utilizator> obtineUtilizatoriPages(Pageable p){
        Iterable<Utilizator> utilizators= repou.findAll(p).getContent().toList();
        return getUtilizators(utilizators);
    }

    public Iterable<Utilizator> obtineUtilizatori(){
        Iterable<Utilizator> utilizators= repou.findAll();
        return getUtilizators(utilizators);
    }
    private Iterable<Utilizator> getUtilizators(Iterable<Utilizator> utilizators) {
        utilizators.forEach(x->{
            List<Prietenie> f= new ArrayList<>();
            repop.findAll().forEach(f::add);
            List<Utilizator> l=
                    f.stream()
                            .filter(z->z.getId().getLeft().equals(x.getId())||z.getId().getRight().equals(x.getId()))
                            .map(y->{
                                if(Objects.equals(y.getId().getRight(), x.getId()))
                                    if(repou.findOne(y.getId().getLeft()).isPresent())
                                        return repou.findOne(y.getId().getLeft()).get();
                                if(repou.findOne(y.getId().getRight()).isPresent())
                                    return repou.findOne(y.getId().getRight()).get();
                                return null;
                            })
                            .collect(Collectors.toList());
            x.setFriends(l);
        });
        return utilizators;
    }

    public List<Tuple<Long,Long>> obtineLegaturi(){
        List<Tuple<Long,Long>> leg=new ArrayList<>();
        repop.findAll().forEach(p-> {
            leg.add(p.getId());
            Tuple<Long,Long> t=new Tuple<>(p.getId().getRight(),p.getId().getLeft());
            leg.add(t);
        });
//        for(Prietenie p: repop.findAll()){
//            leg.add(p.getId());
//        }
        return leg;
    }
    public int nrComunitati(){
        Graph graph=new Graph(obtineLegaturi());
        return graph.numarComponenteConexe();
    }
    public List<Utilizator> ceaMaiSociabila(){
        Graph graph=new Graph(obtineLegaturi());
        List<Utilizator> users= new ArrayList<>();
        if(graph.drumLungMax()!=null) {
            graph.drumLungMax()
                    //.filter(id -> repou.findOne(id).isPresent())
                    .forEach(id ->{
                        if(repou.findOne(id).isPresent())
                            users.add(repou.findOne(id).get());
                    });
        }
//        for(Long id: graph.drumLungMax())
//            if (repou.findOne(id).isPresent())
//                users.add(repou.findOne(id).get());
        return users;
    }
    private List<Observer> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}

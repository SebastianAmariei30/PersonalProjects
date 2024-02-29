package ro.ubbcluj.map.demogui.graph;

import ro.ubbcluj.map.demogui.domain.Tuple;

import java.util.*;

public class Graph {
    private Map<Long, Integer> mapareNoduri;
    private final List<Tuple<Long, Long>> muchii;
    private List<List<Integer>> listaAdiacenta;

    public Graph(List<Tuple<Long, Long>> muchii) {
        this.muchii = muchii;
        initializareMapareNoduri();
        initializareListaAdiacenta();
    }
    private void initializareMapareNoduri() {
        mapareNoduri = new HashMap<>();
        //int index=0;
        int[] index ={0};
        muchii.forEach(tuplu->{
        if (!mapareNoduri.containsKey(tuplu.getLeft())) {
            mapareNoduri.put(tuplu.getLeft(), index[0]++);
        }
        if (!mapareNoduri.containsKey(tuplu.getRight())) {
            mapareNoduri.put(tuplu.getRight(), index[0]++);
        }
        }
        );
//        for (Tuple<Long, Long> tuplu : muchii) {
//            if (!mapareNoduri.containsKey(tuplu.getLeft())) {
//                mapareNoduri.put(tuplu.getLeft(), index++);
//            }
//            if (!mapareNoduri.containsKey(tuplu.getRight())) {
//                mapareNoduri.put(tuplu.getRight(), index++);
//            }
//        }
    }
    private void initializareListaAdiacenta() {
        listaAdiacenta = new ArrayList<>();
        for (int i=0;i<mapareNoduri.size();i++) {
            listaAdiacenta.add(new ArrayList<>());
        }
        muchii.forEach(tuplu->{
            int nodSursa =mapareNoduri.get(tuplu.getLeft());
            int nodDestinatie =mapareNoduri.get(tuplu.getRight());
            listaAdiacenta.get(nodSursa).add(nodDestinatie);
        });
//        for (Tuple<Long, Long> tuplu : muchii) {
//            int nodSursa =mapareNoduri.get(tuplu.getLeft());
//            int nodDestinatie =mapareNoduri.get(tuplu.getRight());
//            listaAdiacenta.get(nodSursa).add(nodDestinatie);
//        }
    }
    public List<List<Integer>>drumuriGraf() {
        boolean[] vizitat = new boolean[mapareNoduri.size()];
        List<List<Integer>> drumuri=new ArrayList<>();
        for (int nod=0;nod<mapareNoduri.size();nod++) {
            if (!vizitat[nod]) {
                List<Integer> drumCurent = new ArrayList<>();
                dfs(nod, vizitat,drumCurent);
                drumuri.add(drumCurent);
            }
        }
        return drumuri;
    }
    public List<List<Integer>>drumuriGraf_cmld() {
        boolean[] vizitat = new boolean[mapareNoduri.size()];
        List<List<Integer>> drumuri=new ArrayList<>();
        for (int nod=0;nod<mapareNoduri.size();nod++) {
            if (!vizitat[nod]) {
                List<Integer> drumCurent = new ArrayList<>();
                cel_mai_lung_drum(nod, vizitat,drumCurent);
                drumuri.add(drumCurent);
            }
        }
        return drumuri;
    }
    public int numarComponenteConexe(){
        return drumuriGraf().size();
    }
    public List<Long> drumLungMax(){
        int lungMax=0;
        List<Integer> drum=new ArrayList<>();
        for(List<Integer> l : drumuriGraf_cmld()){
            if(l.size()>lungMax){
                lungMax=l.size();
                drum=l;
            }
        }
        if(!drum.isEmpty()) {
            Integer id = drum.get(0);
            for (List<Integer> l : drumuriGraf()) {
                for (int x : l) {
                    if (id == x) {
                        drum = l;
                        break;
                    }
                }
            }
            List<Long> drumFinal = new ArrayList<>();
            drum.forEach(i -> {
                mapareNoduri.keySet().stream()
                        .filter(key -> Objects.equals(mapareNoduri.get(key), i))
                        .forEach(drumFinal::add);
            });
            return drumFinal;
        }
//        for (int i:drum){
//            for(long key:mapareNoduri.keySet())
//                if(mapareNoduri.get(key)==i)
//                    drumFinal.add(key);
//        }
        return null;
    }
    private void cel_mai_lung_drum(int nod, boolean[] vizitat,List<Integer> drumCurent){
        Stack<Integer> stiva = new Stack<>();
        stiva.push(nod);
        vizitat[nod] = true;
        while (!stiva.isEmpty()) {
            int nodCurent = stiva.pop();
            drumCurent.add(nodCurent);
            for (int vecin : listaAdiacenta.get(nodCurent)) {
                if (!vizitat[vecin]) {
                    stiva.push(vecin);
                    vizitat[vecin] = true;
                    break;
                }
            }
        }
    }
    private void dfs(int nod, boolean[] vizitat,List<Integer> drumCurent) {
        Stack<Integer> stiva = new Stack<>();
        stiva.push(nod);
        vizitat[nod] = true;

        while (!stiva.isEmpty()) {
            int nodCurent = stiva.pop();
            drumCurent.add(nodCurent);
            for (int vecin : listaAdiacenta.get(nodCurent)) {
                if (!vizitat[vecin]) {
                    stiva.push(vecin);
                    vizitat[vecin] = true;
                }
            }
        }
    }
}

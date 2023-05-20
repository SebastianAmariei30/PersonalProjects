from alg.baza_intermediara import baza_intermediara
from alg.impartiri_succesive import impartiri_succesive
from alg.substitutie import substitutie
from alg.conv_rapide import conv_rapide
from alg.impartire import impartire
from alg.adunare import adunare
from alg.scadere import scadere
class UI(object):
    def __init__(self):
        self._comenzi={"help":self.__ui_help,
                       "1":self.__ui_imp_succ,
                       "2":self.__ui_subst,
                       "3":self.__ui_baza_int,
                       "4":self.__ui_conv_rapida,
                       "5":self.__ui_ad_scadere}
    
    def __ui_help(self):
            print("Alege o comanda:")
            print("1:Conversie prin impartiri succesive")
            print("2:Conversie prin substitutie")
            print("3:Conversie utilizand o baza intermediara")
            print("4:Conversie rapida(intre bazele de numeratie putere a lui 2)")
            print("5:Suma si diferenta(intr-o baza data) a doua numere care sunt in baze diferite(si diferite fata de baza operatiilor)")
    def run(self):
        print("Aplicatie realizata de Amariei Sebastian-Andrei")
        print("Scrie <help> pentru a vedea comenzile aplicatiei")
        while True:
            comanda=input(">>>")
            if comanda in self._comenzi:
                self._comenzi[comanda]()
    def __ui_baza_int(self):
        nr=input("Numarl de transformat:")
        nr=str(nr)
        p=int(input("Baza initiala:"))
        q=int(input("Baza finala:"))
        nr1=baza_intermediara(nr,p,q)
        print(nr1)    
    def __ui_imp_succ(self):
        nr=input("Numarl de transformat:")
        nr=str(nr)
        p=int(input("Baza initiala:"))
        q=int(input("Baza finala:"))
        nr1=impartiri_succesive(nr,p,q)
        print(nr1)         
    def __ui_subst(self):
        nr=input("Numarl de transformat:")
        nr=str(nr)
        p=int(input("Baza initiala:"))
        q=int(input("Baza finala:"))
        nr1=substitutie(nr,p,q)
        print(nr1)     
    def __ui_conv_rapida(self):
        nr=input("Numarl de transformat:")
        nr=str(nr)
        p=int(input("Baza initiala:"))
        q=int(input("Baza finala:"))
        nr1=conv_rapide(nr,p,q)
        print(nr1)  
    def __ui_ad_scadere(self):
        x=input("Primul numar este:")
        p=int(input("Baza primului numar este:"))
        y=input("Al doilea numar este:") 
        q=int(input("Baza celui de-al doilea numar este:"))
        z=int(input("Baza in care se efectueaza operatiile este:"))
        if p<z:
            x=substitutie(x, p, z)
        else:
            x=impartiri_succesive(x, p, z)
        if q<z:
            y=substitutie(y, p, z)
        else:
            y=impartiri_succesive(y, p, z)
        nr1=adunare(x,y,z)
        nr2=scadere(x,y,z)
        print(nr1,nr2)
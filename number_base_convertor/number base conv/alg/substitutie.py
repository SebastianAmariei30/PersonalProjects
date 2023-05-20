'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
from alg.inmultire import inmultire
from alg.adunare import adunare
def substitutie(a,p,q):
    '''transformarea numarului a din baza p in baza q
    prin substitutie(p<q)'''
    ind=0
    suma=""
    p=str(p)
    while a:
        c=a[-1]
        a=a[:-1]
        i=ind
        while i!=0:
            c=inmultire(c,p,q)
            c=str(c)
            i=i-1
        suma=adunare(suma,c,q)
        ind=ind+1
    return str(suma)
    
            
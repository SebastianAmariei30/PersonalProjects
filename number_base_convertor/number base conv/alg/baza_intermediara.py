'''
Created on Feb 16, 2023

@author: Amariei Sebastian Andrei
'''
def baza_intermediara(a,p,q):
    '''conversia numarului a din baza p in baza q 
    folosind o baza intermediara(10)'''
    
    '''transformam numarul a din baza p in baza 10 prin substitutie'''
    ind=0
    baza10a=0
    while a:
        i=ind
        c=int(a[-1])
        a=a[:-1]
        while i:
            c=c*p
            i=i-1
        ind=ind+1
        baza10a=baza10a+c
    '''tranformam numarul a din baza 10 in baza q prin impartiri succesive'''
    cat=int(baza10a/q)
    rest=int(baza10a%q)
    rezultat=""
    rezultat=str(rest)+rezultat
    while cat!=0:
        rest=int(cat%q)
        cat=int(cat/q)
        rezultat=str(rest)+rezultat
    return rezultat
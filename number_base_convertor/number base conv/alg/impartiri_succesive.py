'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
from alg.impartire import impartire
def impartiri_succesive(a,p,q):
    '''conversia numarului a din baza p in baza q
    folosind impartiri succesive(p>q)'''
    rezultat_final=""
    q=str(q)
    rezultat_imp=impartire(a,q,p)
    rest=rezultat_imp[1]
    cat=rezultat_imp[0]
    if rest>=10:
            if rest==10:
                rest="A"
            if rest==11:
                rest="B"
            if rest==12:
                rest="C"
            if rest==13:
                rest="D"
            if rest==14:
                rest="E"
            if rest==15:
                rest="F"
    rezultat_final=str(rest)+rezultat_final
    cat=int(cat,p)
    while cat!=0:
        if cat>=10:
                if cat==10:
                    cat="A"
                if cat==11:
                    cat="B"
                if cat==12:
                    cat="C"
                if cat==13:
                    cat="D"
                if cat==14:
                    cat="E"
                if cat==15:
                    cat="F"
        cat=str(cat)
        rezultat_imp=impartire(cat,q,p)
        rest=rezultat_imp[1]
        cat=rezultat_imp[0]
        if rest>=10:
            if rest==10:
                rest="A"
            if rest==11:
                rest="B"
            if rest==12:
                rest="C"
            if rest==13:
                rest="D"
            if rest==14:
                rest="E"
            if rest==15:
                rest="F"
        rezultat_final=str(rest)+rezultat_final
        cat=int(cat,p)
    return rezultat_final
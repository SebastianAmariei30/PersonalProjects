'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
def inmultire(a,c,b):
    '''inmultirea numarului a cu cifra c in baza b
    return rezultat'''
    l=len(a)
    transport=0
    rezultat=""
    c=int(c,b)
    while l:
        if a!="":
            c1=int(a[-1],b)
            a=a[:-1]
        else:
            c1=0
        rest=int((c1*c+transport)%b)
        transport=int((c1*c+transport)/b)
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
        rezultat=str(rest)+rezultat
        l=l-1
    rest=transport
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
    rezultat=str(rest)+rezultat
    return rezultat
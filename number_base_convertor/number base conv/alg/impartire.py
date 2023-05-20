'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
def impartire(a,c,b):
    '''impartirea numarului a la cifra c in baza b
    return rezultat+rest(ultimul transport)'''
    l=len(a)
    transport=0
    rezultat=""
    c=int(c,b)
    while l:
        c1=int(a[0],b)
        a=a[1:]
        rez=int((transport*b+c1)/c)
        transport=int((transport*b+c1)%c)
        if rez>=10:
                if rez==10:
                    rez="A"
                if rez==11:
                    rez="B"
                if rez==12:
                    rez="C"
                if rez==13:
                    rez="D"
                if rez==14:
                    rez="E"
                if rez==15:
                    rez="F"
        rezultat=rezultat+str(rez)
        l=l-1
    return (rezultat,transport)
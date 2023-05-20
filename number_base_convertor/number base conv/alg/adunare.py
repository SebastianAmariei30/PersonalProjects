'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
def adunare(x,y,b):
    '''
    x= primul numar
    y=al doilea numar
    b=baza in care se face adunarea
    return rezultat= suma nr x si y in baza b'''
    transport=0
    rezultat=""
    len1=len(x)
    len2=len(y)
    l=max(len1,len2)+1
    while l:
        if x!="":
            c1=int(x[-1],b)
            x=x[:-1]
        else:
            c1=0
        if y!="":
            c2=int(y[-1],b)
            y=y[:-1]
        else:
            c2=0
        suma=c1+c2+transport
        transport=int(suma/b)
        rest=int(suma%b)
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
    return rezultat
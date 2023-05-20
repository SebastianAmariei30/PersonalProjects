'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
def scadere(x,y,b):
    '''
    x= primul numar
    y=al doilea numar
    b=baza in care se face scaderea
    return rezultat= diferenta nr x si y in baza b'''
    transport=0
    rezultat=""
    len1=len(x)
    while len1:
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
        if c1+transport>=c2:
            d=c1+transport-c2
            if d>=10:
                if d==10:
                    d="A"
                if d==11:
                    d="B"
                if d==12:
                    d="C"
                if d==13:
                    d="D"
                if d==14:
                    d="E"
                if d==15:
                    d="F"
            rezultat=str(d)+rezultat
            transport=0
        else:
            d=b+c1+transport-c2
            if d>=10:
                if d==10:
                    d="A"
                if d==11:
                    d="B"
                if d==12:
                    d="C"
                if d==13:
                    d="D"
                if d==14:
                    d="E"
                if d==15:
                    d="F"
            rezultat=str(d)+rezultat
            transport=-1
        len1=len1-1
    return rezultat
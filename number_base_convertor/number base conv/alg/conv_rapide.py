'''
Created on Feb 16, 2023

@author: Amariei Sebastian-Andrei
'''
def conv_rapide(a,p,q):
    '''conversie rapida a de la o putere a lui 2 la alta
    p,q sunt puteri ale lui 2'''
    tabel4={
        "00":"0","01":"1","10":"2","11":"3"}
    tabel8={"000":"0","001":"1","010":"2","011":"3",
            "100":"4","101":"5","110":"6","111":"7"}
    tabel16={"0000":"0","0001":"1","0010":"2","0011":"3",
            "0100":"4","0101":"5","0110":"6","0111":"7",
            "1000":"8","1001":"9","1010":"A","1011":"B",
            "1100":"C","1101":"D","1110":"E","1111":"F"}
    nr_b2=""
    if p==2:
        nr_b2=a
    if p==4:
        while a:
            c=a[-1]
            a=a[:-1]
            for nr in tabel4:
                if tabel4[nr]==c:
                    nr_b2=nr+nr_b2
    if p==8:
        while a:
            c=a[-1]
            a=a[:-1]
            for nr in tabel8:
                if tabel8[nr]==c:
                    nr_b2=nr+nr_b2
    if p==16:
        while a:
            c=a[-1]
            a=a[:-1]
            for nr in tabel16:
                if tabel16[nr]==c:
                    nr_b2=nr+nr_b2
    rezultat=""
    if q==2:
        return nr_b2
    if q==4:
        if len(nr_b2)%2!=0:
            nr_b2="0"*(2-len(nr_b2)%2)+nr_b2
        while nr_b2:
            c=nr_b2[-2:]
            nr_b2=nr_b2[:-2]
            for nr in tabel4:
                if nr==c:
                    rezultat=tabel4[nr]+rezultat
    if q==8:
        if len(nr_b2)%3!=0:
            nr_b2="0"*(3-len(nr_b2)%3)+nr_b2
        while nr_b2:
            c=nr_b2[-3:]
            nr_b2=nr_b2[:-3]
            for nr in tabel8:
                if nr==c:
                    rezultat=tabel8[nr]+rezultat
    if q==16:
        if len(nr_b2)%4!=0:
            nr_b2="0"*(4-len(nr_b2)%4)+nr_b2
        while nr_b2:
            c=nr_b2[-4:]
            nr_b2=nr_b2[:-4]
            for nr in tabel16:
                if nr==c:
                    rezultat=tabel16[nr]+rezultat
    return rezultat

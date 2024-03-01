#pragma once
#include<string>
#include<vector>
using std::string;
using std::vector;

class Joc {
private:
	int id, dim;
	string tabla, juc, stare;
public:
	Joc(int id, const string t,const string j,const string s,int d):id{id},tabla{t},juc{j},stare{s},dim{d}{}
	Joc(const Joc& ot) :id{ ot.id }, tabla{ ot.tabla }, juc{ ot.juc }, stare{ ot.stare }, dim{ ot.dim } {}
	int getId() const;
	string getTabla() const;
	string getJucator() const;
	string getStare() const;
	int getDim() const;
	void setTabla(string t) ;
	void setJucator(string j) ;
	void setStare(string s);
	void setDim(int d);
};
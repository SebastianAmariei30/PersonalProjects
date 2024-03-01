#pragma once
#include"joc.h"
#include<ostream>
using std::ostream;
class Repo {
private:
	vector<Joc> all;
	string fName;
	void citeste();
	void scrie();
public:
	Repo(string s) :fName{ s } { citeste(); }
	void adauga(const Joc& j);
	void sterge(int id);
	void mod(const Joc& j, int idn);
	vector<Joc>& getall();
};

class JocRepoException {
private:
	string msg;
public:
	JocRepoException(string m):msg{m}{}
	friend ostream& operator<<(ostream& out, const JocRepoException& ex);
};
ostream& operator<<(ostream& out, const JocRepoException& ex);
#include"teste.h"
#include"joc.h"
#include"repo.h"
#include<sstream>
#include<assert.h>
#include"validator.h"
#include"service.h"
void testDom() {
	Joc j{ 1,"X-OXO-XOO","X","Neinceput",3 };
	assert(j.getDim() == 3);
	assert(j.getId() == 1);
	assert(j.getJucator() == "X");
	assert(j.getTabla() == "X-OXO-XOO");
	assert(j.getStare() == "Neinceput");
	j.setDim(4);
	j.setJucator("O");
	j.setStare("Terminat");
	j.setTabla("X-OXO-XOO--XXX-O");
	assert(j.getDim() == 4);
	assert(j.getId() == 1);
	assert(j.getJucator() == "O");
	assert(j.getTabla() == "X-OXO-XOO--XXX-O");
	assert(j.getStare() == "Terminat");
	Joc j1{ j };
	assert(j1.getDim() == 4);
	assert(j1.getId() == 1);
	assert(j1.getJucator() == "O");
	assert(j1.getTabla() == "X-OXO-XOO--XXX-O");
	assert(j1.getStare() == "Terminat");
}

void testRepo() {
	Repo r{ "C:/Users/andre/source/repos/pr6/pr6/teste.txt" };
	assert(r.getall().size() == 1);
	Joc j{ 1,"X-OXO-XOO","X","Neinceput",3 };
	try {
		r.adauga(j);
	}
	catch (JocRepoException& ex) {
		std::stringstream sout;
		sout << ex;
		string m = sout.str();
		assert(m.find("existent") > 0);
	}
	Joc j1{ 2,"X-OXO-XOO","X","Neinceput",3 };
	r.adauga(j1);
	assert(r.getall().size() == 2);
	Joc j2{ 2,"X-OXO-XOX","Y","InDerulare",3 };
	r.mod(j2, 2);
	assert(r.getall().at(0).getStare() == "InDerulare");
	r.sterge(2);
	assert(r.getall().size() == 1);
}

void testServ(){
	Repo r{ "C:/Users/andre/source/repos/pr6/pr6/teste.txt" };
	JocValidator v;
	Service s{ r,v };
	assert(s.getall().size() == 1);
	try {
		s.adauga(2, "XXF", "K", "x", 6);
	}
	catch (ValidatorException& ex) {
		std::stringstream sout;
		sout << ex;
		string m = sout.str();
		assert(m.find("tabla") > 0);
		assert(m.find("dimensiune") > 0);
		assert(m.find("stare") > 0);
		assert(m.find("jucator") > 0);
	}
	try {
		s.adauga(1, "XXX-XX-OO", "X", "Terminat", 3);
	}
	catch (JocRepoException& ex) {
		std::stringstream sout;
		sout << ex;
		string m = sout.str();
		assert(m.find("existent") > 0);
	}
	s.adauga(2, "XXX-XX-OO", "X", "Terminat", 3);
	assert(s.getall().size() == 2);
	s.mod(2, "XX-OO-OXO", "O", "Terminat", 3);
	assert(s.getall().at(1).getJucator() == "O");
	r.sterge(2);
	assert(s.getall().size() == 1);
}
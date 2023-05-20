#include "Repo.h"

#include<iostream>
#include<sstream>
#include <assert.h>

using std::ostream;
using std::stringstream;
void ActivitateRepo::store(const Activitate& a)
{
	if (exist(a)) {
		throw ActivitateRepoException("exista deja o activitate cu titlul:" + a.getTitlu() + " si cu descrierea:" + a.getDescriere());
	}
	all.push_back(a);
}
void ActivitateRepo::sterge(string titlu,string descriere){
	if (!exist(Activitate{titlu,descriere,"x",1}))
		{
			throw ActivitateRepoException("nu exista o activitate cu titlul:" + titlu+ " si cu descrierea:" + descriere);
		}
	int i = 0;
	while(i<all.size())
	{
		if (all[i].getTitlu() == titlu && all[i].getDescriere() == descriere)
		{
			all.erase(all.begin()+i);
			return;
		}
		i++;
	}
}
void ActivitateRepo::modifica(const Activitate& ac, string titlu, string descriere) {
	if (!exist(Activitate{ titlu,descriere,"x",1 }))
	{
		throw ActivitateRepoException("nu exista o activitate cu titlul:" + titlu + " si cu descrierea:" + descriere);
	}
	int i = 0;
	while (i < all.size())
	{
		if (all[i].getTitlu() == titlu && all[i].getDescriere() == descriere)
		{
			all[i] = ac;
			return;
		}
		i++;
	}
}
bool ActivitateRepo::exist(const Activitate& a) {
	try {
		find(a.getTitlu(), a.getDescriere());
		return true;
	}
	catch (ActivitateRepoException&) {
		return false;
	}
}

const Activitate& ActivitateRepo::find(string titlu, string descriere) {
	std::vector<Activitate>::iterator it =std::find_if(all.begin(), all.end(), [&](const Activitate& a) {return a.getTitlu() == titlu && a.getDescriere() == descriere;});
	if(it==all.end())
		throw ActivitateRepoException("nu exista o activitate cu titlul:" + titlu + " si cu descrierea:" + descriere);
	return *it;
}

vector<Activitate>& ActivitateRepo::getall() noexcept {
	return all;
}

ostream& operator<<(ostream& out, const ActivitateRepoException& ex) {
	out << ex.msg;
	return out;
}

void testMetodeRepo(){
	ActivitateRepo repo;
	repo.store(Activitate{"Fotbal","Sport","abc",60 });
	assert(repo.getall().size() == 1);
	assert(repo.find("Fotbal", "Sport").getTitlu() == "Fotbal");
	repo.store(Activitate{ "Curs","Facultate","bcd",120 });
	assert(repo.getall().size() == 2);
	try {
		repo.store(Activitate{ "Fotbal","Sport","abc",60 });
	}
	catch (const ActivitateRepoException&){
		assert(true);
	}
	try {
		repo.find("c", "c");
	}
	catch (const ActivitateRepoException& e) {
		assert(true);
		stringstream os;
		os << e;
		assert(os.str().find("nu") >= 0);
	}
	try {
		repo.sterge("ian","ian");
	}
	catch (const ActivitateRepoException& e) {
		assert(true);
		stringstream os;
		os << e;
		assert(os.str().find("nu") >= 0);
	}
	repo.sterge("Curs", "Facultate");
	assert(repo.getall().size() == 1);
	repo.store(Activitate{ "x","x","abc",60 });
	repo.modifica(Activitate{ "a","a","a",1 }, "x", "x");
	assert(repo.getall().size() == 2);
	assert(repo.find("a", "a").getTitlu() == "a");
	try {
		repo.modifica(Activitate{ "a","a","a",1 }, "x", "x");
	}
	catch (const ActivitateRepoException& e) {
		assert(true);
		stringstream os;
		os << e;
		assert(os.str().find("nu") >= 0);
	}
	repo.sterge("a", "a");
	repo.sterge("Fotbal", "Sport");
	assert(repo.getall().size() == 0);
}


void testeRepo()
{
	testMetodeRepo();
}
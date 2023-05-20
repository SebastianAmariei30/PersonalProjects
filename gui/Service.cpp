#include "Service.h"

#include <functional>
#include <algorithm>
#include <assert.h>
#include <random>
#include<fstream>
#include <chrono>
using std::sort;

const Activitate& ActivitateService::cauta(string titlu, string descriere) const  {
	return repo.find(titlu, descriere);
}

void ActivitateService::modificaAct(const string& titlu, const string& descriere, const string& titlun, const string& descn, const string& tipn, int durn) {
	Activitate m = repo.find(titlu, descriere);
	Activitate a{ titlun, descn, tipn,durn };
	if (repo.exist(a)&&a.getTitlu()!=titlu&&a.getDescriere()!=descriere) {
		throw ActivitateRepoException("exista deja o activitate cu titlul:" + a.getTitlu() + " si cu descrierea:" + a.getDescriere());
	}
	val.valideaza(a);
	repo.modifica(a, titlu, descriere);
	undoActions.push_back(new UndoModifica{ repo,m,titlun,descn });
}
void ActivitateService::adaugaAct(const string& titlu, const string& descriere, const string& tip, int durata)
{
	Activitate a{ titlu,descriere,tip,durata };
	val.valideaza(a);
	repo.store(a);
	undoActions.push_back(new UndoAdauga{ repo,a });
}

void ActivitateService::stergeAct(const string& titlu, const string& descriere)
{
	Activitate a{titlu,descriere,"a",1 };
	val.valideaza(a);
	Activitate m = repo.find(titlu, descriere);
	repo.sterge(titlu, descriere);
	undoActions.push_back(new UndoSterge{ repo,m });
}
vector<Activitate> ActivitateService::filtruAct(string crit,string text)
{
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	vector<Activitate> x;
	if (crit == "descriere"){
		std::copy_if(a.begin(), a.end(), std::back_inserter(x), [text](const Activitate& x) {return x.getDescriere() == text;});
	}
	if (crit == "tip") {
		std::copy_if(a.begin(), a.end(), std::back_inserter(x), [text](const Activitate& x) {return x.getTip() == text;});
	}
	return x;
}

vector<Activitate> ActivitateService::sortActTip() {
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	sort(a.begin(), a.end(), [](const Activitate& a1, const Activitate& a2) {return a1.getTip()<a2.getTip();});
	return a;
}
vector<Activitate> ActivitateService::sortActTitlu() {
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	sort(a.begin(), a.end(), [](const Activitate& a1, const Activitate& a2) {return a1.getTitlu() < a2.getTitlu();});
	return a;
}
vector<Activitate> ActivitateService::sortActDurata() {
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	sort(a.begin(), a.end(), [](const Activitate& a1, const Activitate& a2) noexcept{return a1.getDurata() < a2.getDurata();});
	return a;
}
vector<Activitate> ActivitateService::sortActDesc() {
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	sort(a.begin(), a.end(), [](const Activitate& a1, const Activitate& a2) {return a1.getDescriere() < a2.getDescriere();});
	return a;
}

void ActivitateService::adaugaActC(string titlu) {
	vector<Activitate>& b = getall();
	vector<Activitate> a = b;
	vector<Activitate> x;
	std::copy_if(a.begin(), a.end(), std::back_inserter(x), [titlu](const Activitate& x) {return x.getTitlu() == titlu;});
	for (const auto& ac : x)
		lac.store(ac);
}
void ActivitateService::golesteActC() {
	vector<Activitate>& b= getallC();
	vector<Activitate> a = b;
	for (const auto& ac : a)
		lac.sterge(ac.getTitlu(), ac.getDescriere());
}
int ActivitateService::importarandomC(int randomnr) {
	if (randomnr > getall().size() - getallC().size())
		return 0;
	vector<Activitate>& a = getall();
	vector<Activitate> b = a;
	vector<Activitate> x;
	std::copy_if(b.begin(), b.end(), std::back_inserter(x), [this](const Activitate& x) {
		try
		{
			lac.find(x.getTitlu(), x.getDescriere()); return 0;
		}
		catch (const ActivitateRepoException&) {
			return 1;
		}}
	);
	std::random_device rd;
	std::mt19937 g(rd());
	std::shuffle(x.begin(), x.end(),g);
	int i = 0;
	while (i < randomnr) {
			lac.store(x[i]);
		i++;
	}
	return 1;
}
map<string, int> ActivitateService::fct() {
	vector<Activitate>& b = getall();
	vector<Activitate>a = b;
	map<string, int> m;
	for (const auto& a1 : a) {
		m[a1.getTip()] ++;
	}
	return m;
}

void ActivitateService::undo() {
	if (undoActions.empty()) {
		throw ActivitateRepoException("nu mai exista operatii");
	}
	ActiuneUndo* act = undoActions.back();
	act->doUndo();
	undoActions.pop_back();
	delete act;
}
void testMetodeService() {
	ActivitateRepoFile repo("C:/Users/andre/source/repos/gui/gui/test1.txt");
	ActivitateValidator val;
	ActivitateRepo lac;
	vector<ActiuneUndo*> v;
	ActivitateService serv{ repo,val,lac,v };
	try {
		serv.undo();
	}
	catch (ActivitateRepoException&) {
		assert(true);
	}
	serv.adaugaAct("a", "a", "a", 6);
	assert(serv.getall().size() == 2);
	serv.stergeAct("x", "x");
	try {
		serv.adaugaAct("", "", "", -1);
	}
	catch (ValidatorException&)
	{
		assert(true);
	}
	try {
		serv.adaugaAct("a", "a", "a", 6);
	}
	catch (ActivitateRepoException&)
	{
		assert(true);
	}
	try {
		serv.stergeAct("b", "b");
	}
	catch (ActivitateRepoException&)
	{
		assert(true);
	}
	try {
		serv.stergeAct("", "");
	}
	catch (ValidatorException&)
	{
		assert(true);
	}
	serv.stergeAct("a", "a");
	assert(serv.getall().size() == 0);
	serv.adaugaAct("a", "a", "a", 6);
	serv.modificaAct("a", "a", "x", "x", "x", 1);
	for (auto& x : serv.getall()) {
		assert(repo.exist(x)==true);
	}
	assert(repo.exist(Activitate{ "x","x","n",1 }) == true);
	try {
		serv.modificaAct("x", "x", "", "", "", -1);
	}
	catch (ValidatorException&)
	{
		assert(true);
	}
	try {
		serv.modificaAct("n", "n", "a", "a", "a", 1);
	}
	catch (ActivitateRepoException&)
	{
		assert(true);
	}
	try {
		serv.modificaAct("x", "x", "x", "x", "x", 1);
	}
	catch (ActivitateRepoException&)
	{
		assert(true);
	}
	assert(serv.cauta("x", "x").getTitlu() == "x");
	serv.stergeAct("x", "x");
	assert(serv.getall().size() == 0);
	serv.adaugaAct("a", "a", "a", 6);
	serv.adaugaAct("b", "b", "b", 7);
	assert(serv.getall().size() == 2);
	assert(serv.cauta("b", "b").getTitlu() == "b");
	vector<Activitate> a = serv.filtruAct("descriere", "a");
	assert(a.size() == 1);
	assert(serv.getall().size() == 2);
	assert(a[0].getDescriere() == "a");
	vector<Activitate> b = serv.filtruAct("tip", "b");
	assert(b[0].getDescriere() == "b");
	serv.stergeAct("a", "a");
	serv.stergeAct("b", "b");
	serv.adaugaAct("a", "c", "d", 2);
	serv.adaugaAct("c", "a", "b", 1);
	serv.adaugaAct("b", "b", "a", 3);
	serv.adaugaAct("d", "d", "c", 4);
	vector<Activitate> c = serv.sortActTip();
	assert(c[0].getDurata() == 3);
	c = serv.sortActDesc();
	assert(c[0].getDurata() == 1);
	c = serv.sortActDurata();
	assert(c[0].getDurata() == 1);
	c = serv.sortActTitlu();
	assert(c[0].getDurata() == 2);
	serv.adaugaAct("x", "b", "a", 3);
	serv.adaugaAct("y", "d", "c", 4);
	serv.adaugaAct("z", "b", "a", 3);
	serv.adaugaAct("t", "d", "c", 4);
	serv.adaugaActC("x");
	assert(serv.getallC().size() == 1);
	serv.adaugaActC("y");
	assert(serv.getallC().size() == 2);
	serv.golesteActC();
	assert(serv.getallC().size() == 0);
	serv.importarandomC(3);
	assert(serv.getallC().size() == 3);
	assert(serv.importarandomC(10) == 0);
	assert(serv.getall().size() == 8);
	serv.adaugaAct("aa", "aa", "aa", 4);
	serv.adaugaAct("bb", "bb", "bb", 3);
	serv.undo();
	assert(serv.getall().size() == 9);
	serv.stergeAct("aa", "aa");
	serv.undo();
	Activitate x{ "aa","aa","aa",4 };
	assert(serv.getall().size() == 9);
	serv.modificaAct("aa", "aa", "m", "m", "m", 10);
	serv.undo();
	assert(repo.exist(x) == true);
	serv.stergeAct("a", "c");
	serv.stergeAct("c", "a");
	serv.stergeAct("b", "b");
	serv.stergeAct("d", "d");
	serv.stergeAct("x", "b");
	serv.stergeAct("y", "d");
	serv.stergeAct("z", "b");
	serv.stergeAct("t", "d");
	serv.stergeAct("aa", "aa");
	assert(serv.getall().size() == 0);
	serv.adaugaAct("x", "x", "x", 3);
}


void testService() {
	testMetodeService();
}  
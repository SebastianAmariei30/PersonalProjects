#pragma once
#include "Activitate.h"
#include "Repo.h"
#include <string>
#include <map>
#include <vector>
#include <functional>
#include "validator.h"
#include "undo.cpp"
#include"repof.h"
using std::vector;
using std::function;
using std::string;
using std::map;

class ActivitateService {
	ActivitateRepoFile& repo;
	ActivitateValidator& val;
	ActivitateRepo& lac;
	vector<ActiuneUndo*> undoActions;

public:
	typedef int(*fctcmp)(const Activitate& a1, const Activitate& a2);
	ActivitateService(ActivitateRepoFile& repo, ActivitateValidator& val, ActivitateRepo& lac, vector<ActiuneUndo*> undoActions) noexcept : repo{ repo }, val{ val }, lac{ lac }, undoActions{ undoActions } {}
	ActivitateService(const ActivitateService& ot) = delete;
	vector<Activitate>& getall() noexcept {
		return repo.getall();
	}
	const Activitate& cauta(string titlu, string descriere) const ;
	void adaugaAct(const string& titlu, const string& descriere, const string& tip, int durata);
	void stergeAct(const string & titlu, const string & descriere);
	void modificaAct(const string& titlu, const string& descriere, const string& titlun, const string& descn, const string& tipn, int durn);
	vector<Activitate> filtruAct(string crit,string text) ;
	vector<Activitate> sortActTip();
	vector<Activitate> sortActTitlu();
	vector<Activitate> sortActDurata();
	vector<Activitate> sortActDesc();
	vector<Activitate>& getallC() noexcept {
		return lac.getall();
	}
	void adaugaActC(string titlu);
	void golesteActC();
	int importarandomC(int randomnr);
	map<string, int> fct();
	void undo();
	~ActivitateService() {
		while (!undoActions.empty())
		{
			ActiuneUndo* x = undoActions.back();
			delete x;
			undoActions.pop_back();
		}
	}
};

void testService();
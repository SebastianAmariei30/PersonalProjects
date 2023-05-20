#pragma once
#include"Repo.h"

class ActivitateRepoFile : public ActivitateRepo {
private:
	std::string fName;
	void scrieinfisier();
	void citestedinfisier();
public:
	ActivitateRepoFile(std::string fName) :ActivitateRepo(), fName{ fName } {
		citestedinfisier();
	}
	void store(const Activitate& a) override {
		ActivitateRepo::store(a);
		scrieinfisier();
	}
	void sterge(std::string titlu, std::string descriere) override {
		ActivitateRepo::sterge(titlu, descriere);
		scrieinfisier();
	}
	void modifica(const Activitate& a, std::string titlu, std::string descriere)override {
		ActivitateRepo::modifica(a, titlu, descriere);
		scrieinfisier();
	}
	vector<Activitate>& getall() noexcept override {
		return ActivitateRepo::getall();
	}
	const Activitate& find(string titlu, string descriere)override {
		return ActivitateRepo::find(titlu, descriere);
	}
	bool exist(const Activitate& a) override  {
		return ActivitateRepo::exist(a);
	}
};
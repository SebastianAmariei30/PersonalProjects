#pragma once
#include "Activitate.h"
#include "VectorDin.h"
#include <string>
#include <ostream>
#include <vector>

using std::string;
using std::ostream;
using std::vector;

class ActivitateRepo {
	vector<Activitate> all;

public:


	 ActivitateRepo() = default;

	 virtual bool exist(const Activitate& a);

	virtual void store(const Activitate& a);

	virtual void modifica(const Activitate& a, string titlu, string descriere);
	
	virtual void sterge(string titlu, string descriere);


	virtual const Activitate& find(string titlu, string descriere);

	virtual vector<Activitate>& getall() noexcept;

};

class ActivitateRepoException {
	string msg;
public:
	ActivitateRepoException(string m) :msg{ m } {}

	friend ostream& operator<<(ostream& out, const ActivitateRepoException& ex);

};

ostream& operator<<(ostream& out, const ActivitateRepoException& ex);

void testeRepo();
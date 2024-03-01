#pragma once
#include "repo.h"
#include "validator.h"

class Service {
private:
	Repo& r;
	JocValidator& v;
public:
	Service(Repo&r, JocValidator& v): r{r},v{v}{}
	Service(Service& ot) = delete;
	vector<Joc>& getall();
	void adauga(int id, const string& t, const string& j, const string& s, int d);
	void mod(int id, const string& tn, const string& jn, const string& sn, int dn);
};

#include"service.h"


vector<Joc>& Service::getall() {
	return r.getall();
}
void Service::adauga(int id, const string& t, const string& j, const string& s, int d) {
	Joc j1{ id,t,j,s,d };
	v.valideaza(j1);
	r.adauga(j1);
}
void Service::mod(int id, const string& tn, const string& jn, const string& sn, int dn) {
	Joc j1{ id,tn,jn,sn,dn };
	v.valideaza(j1);
	r.mod(j1, id);
}
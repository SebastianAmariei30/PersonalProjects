#include"joc.h"


int Joc::getId() const {
	return id;
}
string Joc::getTabla() const {
	return tabla;
}
string Joc::getJucator() const {
	return juc;
}
string Joc::getStare() const {
	return stare;
}
int Joc::getDim() const {
	return dim;
}
void Joc::setTabla(string t) {
	this->tabla = t;
}
void Joc::setJucator(string j) {
	this->juc = j;
}
void Joc::setStare(string s) {
	this->stare = s;
}
void Joc::setDim(int d) {
	this->dim = d;
}
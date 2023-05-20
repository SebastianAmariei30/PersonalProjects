#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;
class Activitate {
	std::string titlu;
	std::string descriere;
	std::string tip;
	int durata;

public:
	Activitate() :titlu{ "" }, descriere{ "" }, tip{ "" }, durata{ 0 } {}
	Activitate(const string t, const string d, const string ti, int du) :titlu{ t }, descriere{ d }, tip{ ti }, durata{ du } {}
	Activitate(const Activitate& ac) : titlu{ ac.titlu }, descriere{ ac.descriere }, tip{ ac.tip }, durata{ ac.durata } {};
	string getTip() const {
		return tip;
	}
	string getDescriere() const {
		return descriere;
	}
	string getTitlu() const {
		return titlu;
	}
	int getDurata() const noexcept {
		return durata;
	}
};

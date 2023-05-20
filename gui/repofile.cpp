#pragma once
#include<fstream>
#include"repof.h"
void ActivitateRepoFile::citestedinfisier() {
	std::ifstream fin(fName);
	if (!fin.is_open())
		throw ActivitateRepoException("Eroare deschidere: " + fName);
	while (!fin.eof()) {
		std::string titlu;
		fin >> titlu;
		if (fin.eof())
			break;
		std::string descriere;
		fin >> descriere;
		std::string tip;
		fin >> tip;
		int durata;
		fin >> durata;
		Activitate a{ titlu.c_str(),descriere.c_str(),tip.c_str(),durata};
		ActivitateRepo::store(a);
	}
	fin.close();
}

void ActivitateRepoFile::scrieinfisier(){
	std::ofstream fout(fName);
	if (!fout.is_open())
		throw ActivitateRepoException("Eroare deschidere: " + fName);
	for (auto& a : getall()) {
		fout << a.getTitlu();
		fout << std::endl;
		fout << a.getDescriere();
		fout << std::endl;
		fout << a.getTip();
		fout << std::endl;
		fout << a.getDurata();
		fout << std::endl;
	}
	fout.close();
}
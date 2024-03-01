#include"repo.h"
#include<fstream>
#include<algorithm>
void Repo::citeste() {
	std::ifstream fin(fName);
	while (!fin.eof()) {
		int i, d;
		string t, j, s;
		fin >> i;
		if (fin.eof())
			break;
		fin >> t;
		fin >> j;
		fin >> s;
		fin >> d;
		Joc j1{ i,t,j,s,d };
		all.push_back(j1);
	}
	std::sort(all.begin(), all.end(), [](const Joc& j1, const Joc& j2) {
		return j1.getStare() < j2.getStare();});
	fin.close();
}
void Repo::scrie() {
	std::ofstream fout(fName);
	for (const auto& x : all) {
		fout << x.getId() << std::endl;
		fout << x.getTabla() << std::endl;
		fout << x.getJucator() << std::endl;
		fout << x.getStare() << std::endl;
		fout << x.getDim() << std::endl;
	}
}
void Repo::adauga(const Joc& j){
	for (const auto& x : all) {
		if (j.getId() == x.getId())
			throw JocRepoException("id existent!");
	}
	all.push_back(j);
	std::sort(all.begin(), all.end(), [](const Joc& j1, const Joc& j2) {
		return j1.getStare() < j2.getStare();});
	scrie();
}
void Repo::sterge(int id) {
	int i = 0;
	for (const auto& x : all) {
		if (x.getId() == id)
		{
			all.erase(all.begin() + i);
			break;
		}
		i++;
	}
	scrie();
}
void Repo::mod(const Joc& j, int idn) {
	for (auto& x : all) {
		if (x.getId() == idn) {
			x.setDim(j.getDim());
			x.setJucator(j.getJucator());
			x.setStare(j.getStare());
			x.setTabla(j.getTabla());
			break;
		}
	}
	std::sort(all.begin(), all.end(), [](const Joc& j1, const Joc& j2) {
		return j1.getStare() < j2.getStare();});
	scrie();
}
vector<Joc>& Repo::getall() {
	return all;
}
ostream& operator<<(ostream& out, const JocRepoException& ex) {
	out << ex.msg;
	return out;
}
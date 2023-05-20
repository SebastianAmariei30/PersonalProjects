#include "UI.h"
#include "Activitate.h"

#include<iostream>
#include<string>
#include <fstream>
using std::cout;
using std::cin;
using std::endl;
void UI::cautaUI() {
	string titlu, descriere;
	cout << "Introdu titlul activitatii \n";
	cin >> titlu;
	cout << "Introdu descrierea activitatii:\n";
	cin >> descriere;
	cout << "Activitatea cautata este:\n";
	cout << serv.cauta(titlu, descriere).getTitlu() << ' ' << serv.cauta(titlu, descriere).getDescriere() << ' ' << serv.cauta(titlu, descriere).getTip() << ' ' << serv.cauta(titlu, descriere).getDurata();
}


void UI::printall() {
	for (const auto& a : serv.getall()) {
		cout << a.getTitlu() << ' ' << a.getDescriere() << ' ' << a.getTip() << ' ' << a.getDurata() << endl;
	}
}

void UI::adaugaUI()
{
	string titlu, tip, descriere;
	int durata;
	cout << "Introdu titlul activitatii:\n";
	cin >> titlu;
	cout << "Introdu descrierea activitatii:\n";
	cin >> descriere;
	cout << "Introdu tipul activitatii:\n";
	cin >> tip;
	cout << "Introdu durata activitatii:\n";
	cin >> durata;
	serv.adaugaAct(titlu, descriere, tip, durata);
	cout << "Activitate adaugata cu succes!\n";
}
void UI::stergeUI() {
	string titlu, descriere;
	cout << "Introdu titlul activitatii pe care vrei sa o stergi:\n";
	cin >> titlu;
	cout << "Introdu descrierea activitatii pe care vrei sa o stergi:\n";
	cin >> descriere;
	serv.stergeAct(titlu, descriere);
	cout << "Activitate stearsa cu succes!\n";
}
void UI::modificaUI() {
	string tit, des, titn, desn, tpn;
	int durn;
	cout << "Introdu titlul activitatii pe care vrei sa o modifici:\n";
	cin >> tit;
	cout << "Introdu descrierea activitatii pe care vrei sa o modifici:\n";
	cin >> des;
	cout << "Introdu noul titlu al activitatii:\n";
	cin >> titn;
	cout << "Introdu noua descriere a activitatii:\n";
	cin >> desn;
	cout << "Introdu noul tip al activitatii:\n";
	cin >> tpn;
	cout << "Introdu noua durata a activitatii:\n";
	cin >> durn;
	serv.modificaAct(tit, des, titn, desn, tpn, durn);
	cout << "Activitate modificata cu succes!\n";
}
void UI::filtruUI() {
	string crit, text;
	int i1;
	cout << "Atrubutul dupa care se efectueaza filtrarea:\n";
	cout << "	1.Descriere\n";
	cout << "	2.Tip\n";
	cin >> i1;
	if (i1 == 1)
	{
		crit = "descriere";
		cout << "Introdu descrierea dupa care vrei sa filtrezi:\n";
	}
	else if (i1 == 2)
	{
		crit = "tip";
		cout << "Introdu tipul dupa care vrei sa filtrezi:\n";
	}
	cin >> text;
	vector<Activitate> a = serv.filtruAct(crit, text);
	for (int i = 0;i < a.size();i++)
	{
		cout << a[i].getTitlu() << ' ' << a[i].getDescriere() << ' ' << a[i].getTip() << ' ' << a[i].getDurata() << endl;
	}
}
void UI::sortUI() {
	cout << "Introdu atributul dupa care se face sortarea:\n";
	cout << "	1.Titlu\n";
	cout << "	2.Descriere\n";
	cout << "	3.Tip\n";
	cout << "	4.Durata\n";
	int i;
	cin >> i;
	vector<Activitate> a;
	if (i == 1)
		a = serv.sortActTitlu();
	if (i == 2)
		a = serv.sortActDesc();
	if (i == 3)
		a = serv.sortActTip();
	if (i == 4)
		a = serv.sortActDurata();
	for (i = 0;i < a.size();i++)
	{
		cout << a[i].getTitlu() << ' ' << a[i].getDescriere() << ' ' << a[i].getTip() << ' ' << a[i].getDurata() << endl;
	}
}
void UI::adaugaLC() {
	cout << "Introdu titlul activitatii pe care vreo s-o adaugi:\n";
	string titlu;
	cin >> titlu;
	serv.adaugaActC(titlu);
	cout << "Activitate adaugata cu succes!\n";
}
void UI::golesteLC() {
	serv.golesteActC();
	cout << "Lista golita cu succes!\n";
}
void UI::genereazaLC() {
	cout << "Introdu numarul de activitati pe care vrei sa le generezi:\n";
	int nr;
	cin >> nr;
	const int i=serv.importarandomC(nr);
	if (i == 0)
	{
		cout << "Nu se pot adauga atat de multe activitati. Incearca un numar mai mic";
	}
	else
		cout << "Activitati adaugate cu succes!";
}
void UI::printallLC() {
	for (const auto& a : serv.getallC()) {
		cout << a.getTitlu() << ' ' << a.getDescriere() << ' ' << a.getTip() << ' ' << a.getDurata() << endl;
	}
}
void UI::exportLC() {
	string nume;
	cout << "Introdu numele fisierului in care vrei sa scrii activitatile curente:\n";
	cin >> nume;
	std::ofstream fout(nume + ".cvs");
	for (const auto& a : serv.getallC()) {
		fout << a.getTitlu() << ' ' << a.getDescriere() << ' ' << a.getTip() << ' ' << a.getDurata() << "\n";
	}
}
void UI::fctUI() {
	map<string, int> m = serv.fct();
	std::map<string, int>::iterator it = m.begin();
	while (it != m.end()) {
		cout << it->first << ' ' << it->second << endl;
		++it;
	}
}
void UI::undoUI() {
	serv.undo();
	cout << "Operatie realizata cu succes!";
}
void UI::meniuLC() {
	while (true) {
		cout << ">>> Meniu - activitati curente <<<\n";
		cout << ">>> 1 <<< Adauga activitate\n";
		cout << ">>> 2 <<< Genereaza activitati\n";
		cout << ">>> 3 <<< Goleste lista\n";
		cout << ">>> 4 <<< Afiseaza toate activitatile curente \n";
		cout << ">>> 5 <<< Export in fisier .cvs \n";
		cout << ">>> 0 <<< Revino la meniul principal\n";
		int cmd;
		cin >> cmd;
		try {
			switch (cmd) {
			case 0:
				return;
			case 1:
				adaugaLC();
				break;
			case 2:
				genereazaLC();
				break;
			case 3:
				golesteLC();
				break;
			case 4:
				printallLC();
				break;
			case 5:
				exportLC();
				break;
			default:
				break;
			}
		}
		catch (const ActivitateRepoException& ex) {
			cout << ex << endl;
		}
		catch (const ValidatorException& ex) {
			cout << ex << endl;
		}
	}
}
void UI::run() {
	while (true) {
		cout << "0.Iesire din aplicatie\n";
		cout << "1.Adauga activitate\n";
		cout << "2.Sterge o activitate\n";
		cout << "3.Modifica o activitate\n";
		cout << "4.Tipareste toate activitatile din aplicatie\n";
		cout << "5.Cauta o activitate dupa titlu si descriere\n";
		cout << "6.Filtreaza dupa un atribut\n";
		cout << "7.Sorteaza dupa un atribut\n";
		cout << "8.Meniu lista activitati curente\n";
		cout << "9.Activitati dupa tip\n";
		cout << "10.Undo\n";
		int comanda;
		cin >> comanda;
		try {
			switch (comanda) {
			case 0:
				return;
			case 1:
				adaugaUI();
				break;
			case 2:
				stergeUI();
				break;
			case 3:
				modificaUI();
				break;
			case 4:
				printall();
				break;
			case 5:
				cautaUI();
				break;
			case 6:
				filtruUI();
				break;
			case 7:
				sortUI();
				break;
			case 8:
				meniuLC();
				break;
			case 9:
				fctUI();
				break;
			case 10:
				undoUI();
				break;
			default:
				break;
			}
		}
		catch (const ActivitateRepoException& ex) {
			cout << ex << endl;
		}
		catch (const ValidatorException& ex) {
			cout << ex << endl;
		}
	}
}
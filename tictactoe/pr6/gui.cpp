#include"gui.h"
#include<sstream>
#include<qmessagebox.h>
bool isWinner(const std::string& tabla, char jucator, int dimensiune) {
	for (int i = 0; i < dimensiune; i++) {
		bool castig = true;
		for (int j = 0; j < dimensiune; j++) {
			if (tabla[i * dimensiune + j] != jucator) {
				castig = false;
				break;
			}
		}
		if (castig) {
			return true;
		}
	}

	for (int i = 0; i < dimensiune; i++) {
		bool castig = true;
		for (int j = 0; j < dimensiune; j++) {
			if (tabla[j * dimensiune + i] != jucator) {
				castig = false;
				break;
			}
		}
		if (castig) {
			return true;
		}
	}

	bool castig = true;
	for (int i = 0; i < dimensiune; i++) {
		if (tabla[i * dimensiune + i] != jucator) {
			castig = false;
			break;
		}
	}
	if (castig) {
		return true;
	}

	castig = true;
	for (int i = 0; i < dimensiune; i++) {
		if (tabla[i * dimensiune + (dimensiune - 1 - i)] != jucator) {
			castig = false;
			break;
		}
	}
	if (castig) {
		return true;
	}

	return false;
}

bool isdone(const string& s) {
	int ok = 0;
	for (auto& x : s) {
		if (x == '-')
			ok = 1;
	}
	return ok == 0;
}

void gui::init() {
	setLayout(mly);
	tw->setModel(mtm);
	mly->addWidget(tw);
	QVBoxLayout* vly = new QVBoxLayout;
	fly->addRow("Id", id);
	fly->addRow("Dimensiune", dim);
	fly->addRow("Tabla", tab);
	fly->addRow("Jucator", juc);
	fly->addRow("Stare", sta);
	vly->addLayout(fly);
	vly->addWidget(add);
	vly->addWidget(mod);
	mly->addLayout(vly);
	mly->addLayout(mlyx);
}

void gui::conn() {
	QObject::connect(add, &QPushButton::clicked, [&]() {
		int i = atoi(id->text().toStdString().c_str());
		int d = atoi(dim->text().toStdString().c_str());
		string t = tab->text().toStdString();
		string j = juc->text().toStdString();
		string st = sta->text().toStdString();
		try {
			s.adauga(i, t, j, st, d);
			mtm->setJ(s.getall());
		}
		catch (JocRepoException& ex) {
			std::stringstream sout;
			sout << ex;
			string m = sout.str();
			QString ms = QString::fromStdString(m);
			QMessageBox::information(nullptr, "Info", ms);
		}
		catch (ValidatorException& ex) {
			std::stringstream sout;
			sout << ex;
			string m = sout.str();
			QString ms = QString::fromStdString(m);
			QMessageBox::information(nullptr, "Info", ms);
		}
		});
	QObject::connect(mod, &QPushButton::clicked, [&]() {
		int i = atoi(id->text().toStdString().c_str());
		int d = atoi(dim->text().toStdString().c_str());
		string t = tab->text().toStdString();
		string j = juc->text().toStdString();
		string st = sta->text().toStdString();
		try {
			s.mod(i, t, j, st, d);
			mtm->setJ(s.getall());
		}
		catch (ValidatorException& ex) {
			std::stringstream sout;
			sout << ex;
			string m = sout.str();
			QString ms = QString::fromStdString(m);
			QMessageBox::information(nullptr, "Info", ms);
		}
		});
	QObject::connect(tw->selectionModel(), &QItemSelectionModel::selectionChanged, [&]() {
		li = tw->selectionModel()->selectedIndexes();
		if(li.size()>0)
		{
			for (int y = 0;y < vl.size();y++) {
				remove(vl[y]);
				mlyx->removeItem(vl[y]);
			}
			mly->removeItem(mlyx);
			int r = li.at(0).row();
			auto cel0 = tw->model()->index(r, 0);
			auto cel1 = tw->model()->data(cel0, Qt::DisplayRole).toString();
			int i = atoi(cel1.toStdString().c_str());
			string st;int dm;string juc;
			string tbla, stare;
			for (const auto& x : s.getall()) {
				if (x.getId() == i) {
					st = x.getTabla();
					dm = x.getDim();
					juc = x.getJucator();
					stare = x.getStare();
				}
			}
			vector<vector<QPushButton*>> vec;
			vec.resize(dm);
			int nr = 0, nr1 = 0;
			int numar = 0;
			for (int x = 0;x<st.size();x++) {
				string h = " ";
				h[0] = st[x];
				QString hh = QString::fromStdString(h);
				QPushButton* bt = new QPushButton{ hh };
				QObject::connect(bt, &QPushButton::clicked, [this,st,stare,dm,i,x,juc,r]() {
					if(st[x]=='-')
					{
						if (stare != "Terminat") {
							string str = st;
							str[x] = juc[0];
							string stn = stare;
							if (stn == "Neinceput")
								stn = "InDerulare";
							if (isWinner(str, 'X', dm) == true || isWinner(str, 'O', dm) == true||isdone(str))
								stn = "Terminat";
							qDebug() << i << str << stn << dm;
							if (juc == "X") {
								s.mod(i, str, "O", stn, dm);
							}
							else s.mod(i, str, "X", stn, dm);
							mtm->setJ(s.getall());
							tw->selectionModel()->clearSelection();
							tw->selectionModel()->select(tw->model()->index(r, 0), QItemSelectionModel::Select);
						}
					}

					});
				vec[nr].push_back(bt);
				nr1++;
				if (nr1 == dm) {
					nr++;
					nr1 = 0;
				}
				numar++;
			}
			vl.resize(dm);
			for (int x = 0;x < dm;x++)
			{
				QHBoxLayout* ly1 = new QHBoxLayout;
				vl[x]=ly1;
			}
			for (int y = 0;y < vec.size();y++)
				for (int x = 0;x < vec[y].size();x++) {
					vl[y]->addWidget(vec[y][x]);
				}
			for (int x = 0;x < vl.size();x++)
				mlyx->addLayout(vl[x]);
			mly->addLayout(mlyx);
		}
		});
}
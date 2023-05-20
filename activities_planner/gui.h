#pragma once
#include<iostream>
#include<fstream>
#include<sstream>
#include<QtWidgets/qwidget.h>
#include<QtWidgets/qlayout.h>
#include<QtWidgets/qlistwidget.h>
#include<QtWidgets/qformlayout.h>
#include<QtWidgets/qlineedit.h>
#include<QtWidgets/qpushbutton.h>
#include<QtWidgets/qdialog.h>
#include<QtWidgets/qmessagebox.h>
#include "Service.h"
#include<map>
#include "Activitate.h"
#include<QtWidgets/qtablewidget.h>
using std::map;
using std::stringstream;
class gui : public QWidget {
public:
	ActivitateService& serv;
	gui(ActivitateService& serv) :serv{ serv } {
		initGUI();
		loadData();
		initConnect();
		adaugabutoane();
	}
private:
	QVBoxLayout* lybtnd = new QVBoxLayout;
	QWidget* btnd = new QWidget;
	QTableWidget* lst = new QTableWidget(serv.getall().size(),4);
	QPushButton* btnexit = new QPushButton("&Exit");
	QPushButton* btnadd = new QPushButton("&Adauga");
	QPushButton* btndel = new QPushButton("&Sterge");
	QPushButton* btnmod = new QPushButton("&Modifica");
	QPushButton* btncau = new QPushButton("&Cauta");
	QPushButton* btnstit = new QPushButton("&Sortare dupa titlu");
	QPushButton* btnsdes = new QPushButton("&Sortare dupa descriere");
	QPushButton* btnstip = new QPushButton("&Sortare dupa tip");
	QPushButton* btnsdur = new QPushButton("&Sortare dupa durata");
	QPushButton* btnfildes= new QPushButton("&Filtrare dupa descriere");
	QPushButton* btnfiltip = new QPushButton("&Filtrare dupa tip");
	QPushButton* btncos = new QPushButton("&Activitati curente");
	QPushButton* btnundo = new QPushButton("&Undo");
	QHBoxLayout* lymain = new QHBoxLayout{};
	void remove(QLayout* layout)
	{
		QLayoutItem* child;
		while (layout->count() != 0) {
			child = layout->takeAt(0);
			if (child->layout() != 0) {
				remove(child->layout());
			}
			else if (child->widget() != 0) {
				delete child->widget();
			}

			delete child;
		}
	}
	void adaugabutoane() {
		map<string, int> m = serv.fct();
		std::map<string, int>::iterator it = m.begin();
		while (it != m.end()) {
			auto btn = new QPushButton{ QString::fromStdString(it->first) };
			lybtnd->addWidget(btn);
			string x = std::to_string(it->second);
			QString y = QString::fromStdString(x);
			QObject::connect(btn, &QPushButton::clicked, [y]() {
				QMessageBox::information(nullptr, "Info", y);
				});
			++it;
		}
		lymain->addLayout(lybtnd);
	}
	void initConnect() {
		QObject::connect(btncau, &QPushButton::clicked, [&]() {
			QDialog* c = new QDialog();
			QVBoxLayout* mai = new QVBoxLayout;
			c->setLayout(mai);
			QFormLayout* l = new QFormLayout;
			QLineEdit* tit = new QLineEdit;
			QLineEdit* des = new QLineEdit;
			QLineEdit* tip = new QLineEdit;
			QLineEdit* dur = new QLineEdit;
			l->addRow("Titlu", tit);
			l->addRow("Descriere", des);
			l->addRow("Tip", tip);
			l->addRow("Durata", dur);
			QPushButton* ca = new QPushButton("&Cauta!");
			QPushButton* ie = new QPushButton("&Iesire");
			QVBoxLayout* btl = new QVBoxLayout;
			btl->addWidget(ca);
			btl->addWidget(ie);
			mai->addLayout(l);
			mai->addLayout(btl);
			QObject::connect(ca, &QPushButton::clicked, [&]() {
				string titlu = tit->text().toStdString();
				string desc = des->text().toStdString();
				try { Activitate a = serv.cauta(titlu, desc);
				string ti = a.getTip();
				int du = a.getDurata();
				string duu = std::to_string(du);
				tip->setText(QString::fromStdString(ti));
				dur->setText(QString::fromStdString(duu));
				}
				catch (ActivitateRepoException& e) {
					stringstream os;
					os << e;
					QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
				}
				});
			QObject::connect(ie, &QPushButton::clicked, [&]() {
				c->close();
				});
			c->exec();
			});
		QObject::connect(btnexit, &QPushButton::clicked, [&]() {
			close();
			});
		QObject::connect(btnundo, &QPushButton::clicked, [&]() {
			try
			{ serv.undo();
			lst->setRowCount(serv.getall().size());
			loadData();
			}
			catch (ActivitateRepoException& e) {
				stringstream os;
				os << e;
				QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
			}
			});
		QObject::connect(btnadd, &QPushButton::clicked, [&]() {
			QDialog* addWind = new QDialog();
			addWind->setWindowTitle("Adauga Activitate");
			QVBoxLayout* mly = new QVBoxLayout(addWind);
			addWind->setLayout(mly);
			auto formly = new QFormLayout;
			QLineEdit* titluEdit = new QLineEdit;
			QLineEdit* descriereEdit = new QLineEdit;
			QLineEdit* tipEdit = new QLineEdit;
			QLineEdit* durataEdit = new QLineEdit;
			formly->addRow("Titlu", titluEdit);
			formly->addRow("Descriere",  descriereEdit);
			formly->addRow("Tip",  tipEdit);
			formly->addRow("Durata",  durataEdit);
			auto btl = new QVBoxLayout;
			QPushButton* addBtn = new QPushButton("&Adauga!");
			QPushButton* cancelBtn = new QPushButton("&Anuleaza!");
			btl->addWidget(addBtn);
			btl->addWidget(cancelBtn);
			QObject::connect(addBtn, &QPushButton::clicked, [&]() {
				std::string titlu = titluEdit->text().toStdString();
				std::string desc = descriereEdit->text().toStdString();
				std::string tip = tipEdit->text().toStdString();
				int durata = durataEdit->text().toInt();
				try {
					serv.adaugaAct(titlu, desc, tip, durata);
					lst->setRowCount(serv.getall().size());
					loadData();
					remove(lybtnd);
					lymain->removeItem(lybtnd);
					adaugabutoane();
				}
				catch (ActivitateRepoException&) {
					QMessageBox::information(nullptr, "Info", "Activitate deja existenta");
				}
				catch (ValidatorException& x) {
					stringstream os;
					os << x;
					QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
				}
				addWind->close();
				});
			QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
				addWind->close();
				});
			mly->addLayout(formly);
			mly->addLayout(btl);
			addWind->exec();
			});
		QObject::connect(btndel, &QPushButton::clicked, [&]() {
				QDialog* addWind = new QDialog();
				addWind->setWindowTitle("Sterge Activitate");
				QVBoxLayout* mly = new QVBoxLayout(addWind);
				addWind->setLayout(mly);
				auto formly = new QFormLayout;
				QLineEdit* titluEdit = new QLineEdit;
				QLineEdit* descriereEdit = new QLineEdit;
				formly->addRow("Titlu", titluEdit);
				formly->addRow("Descriere", descriereEdit);
				auto btl = new QVBoxLayout;
				QPushButton* addBtn = new QPushButton("&Sterge!");
				QPushButton* cancelBtn = new QPushButton("&Anuleaza!");
				btl->addWidget(addBtn);
				btl->addWidget(cancelBtn);
				QObject::connect(addBtn, &QPushButton::clicked, [&]() {
					std::string titlu = titluEdit->text().toStdString();
					std::string desc = descriereEdit->text().toStdString();
					try {
						serv.stergeAct(titlu, desc);
						lst->setRowCount(serv.getall().size());
						loadData();
						remove(lybtnd);
						lymain->removeItem(lybtnd);
						adaugabutoane();
					}
					catch (ActivitateRepoException&) {
						QMessageBox::information(nullptr, "Info", "Activitatea nu exista");
					}
					catch (ValidatorException& x) {
						stringstream os;
						os << x;
						QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
					}
					addWind->close();
					});
				QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
					addWind->close();
					});
				mly->addLayout(formly);
				mly->addLayout(btl);
				addWind->exec();
				});
		QObject::connect(btnmod, &QPushButton::clicked, [&]() {
			QDialog* addWind = new QDialog();
			addWind->setWindowTitle("Modifica Activitate");
			QVBoxLayout* mly = new QVBoxLayout(addWind);
			addWind->setLayout(mly);
			auto formly = new QFormLayout;
			QLineEdit* tiEdit = new QLineEdit;
			QLineEdit* diEdit = new QLineEdit;
			QLineEdit* titluEdit = new QLineEdit;
			QLineEdit* descriereEdit = new QLineEdit;
			QLineEdit* tipEdit = new QLineEdit;
			QLineEdit* durataEdit = new QLineEdit;
			formly->addRow("Titlu initial", tiEdit);
			formly->addRow("Descriere initiala", diEdit);
			formly->addRow("Titlu nou", titluEdit);
			formly->addRow("Descriere noua", descriereEdit);
			formly->addRow("Tip nou", tipEdit);
			formly->addRow("Durata noua", durataEdit);
			auto btl = new QVBoxLayout;
			QPushButton* addBtn = new QPushButton("&Modifica!");
			QPushButton* cancelBtn = new QPushButton("&Anuleaza!");
			btl->addWidget(addBtn);
			btl->addWidget(cancelBtn);
			QObject::connect(addBtn, &QPushButton::clicked, [&]() {
				std::string ti = tiEdit->text().toStdString();
				std::string di = diEdit->text().toStdString();
				std::string titlu = titluEdit->text().toStdString();
				std::string desc = descriereEdit->text().toStdString();
				std::string tip = tipEdit->text().toStdString();
				int durata = durataEdit->text().toInt();
				try {
					serv.modificaAct(ti, di, titlu, desc, tip, durata);
					remove(lybtnd);
					lymain->removeItem(lybtnd);
					adaugabutoane();
					loadData();
				}
				catch (ActivitateRepoException& x) {
					stringstream os;
					os << x;
					QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
				}
				catch (ValidatorException& x) {
					stringstream os;
					os << x;
					QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
				}
				addWind->close();
				});
			QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
				addWind->close();
				});
			mly->addLayout(formly);
			mly->addLayout(btl);
			addWind->exec();
			});
		QObject::connect(btnstit, &QPushButton::clicked, [&]() {
			lst->clear();
			int i = 0;
			for (const auto& a : serv.sortActTitlu()) {
				int dur = a.getDurata();
				string dur1 = std::to_string(dur);
				QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
				QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
				QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
				QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
				lst->setItem(i, 0, item);
				lst->setItem(i, 1, item1);
				lst->setItem(i, 2, item2);
				lst->setItem(i, 3, item3);
				i++;
			}
			});
		QObject::connect(btnsdur, &QPushButton::clicked, [&]() {
				lst->clear();
				int i = 0;
				for (const auto& a : serv.sortActDurata()) {
					int dur = a.getDurata();
					string dur1 = std::to_string(dur);
					QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
					QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
					QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
					QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
					lst->setItem(i, 0, item);
					lst->setItem(i, 1, item1);
					lst->setItem(i, 2, item2);
					lst->setItem(i, 3, item3);
					i++;
				}
				});
		QObject::connect(btnsdes, &QPushButton::clicked, [&]() {
			lst->clear();
			int i = 0;
			for (const auto& a : serv.sortActDesc()) {
				int dur = a.getDurata();
				string dur1 = std::to_string(dur);
				QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
				QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
				QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
				QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
				lst->setItem(i, 0, item);
				lst->setItem(i, 1, item1);
				lst->setItem(i, 2, item2);
				lst->setItem(i, 3, item3);
				i++;
			}
			});
		QObject::connect(btnstip, &QPushButton::clicked, [&]() {
			lst->clear();
			int i = 0;
			for (const auto& a : serv.sortActTip()) {
				int dur = a.getDurata();
				string dur1 = std::to_string(dur);
				QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
				QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
				QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
				QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
				lst->setItem(i, 0, item);
				lst->setItem(i, 1, item1);
				lst->setItem(i, 2, item2);
				lst->setItem(i, 3, item3);
				i++;
			}
			});
		/*QObject::connect(lst, &QListWidget::itemSelectionChanged, [&]() {
			auto sel = lst->selectedItems();
			if (!sel.isEmpty()) {
				auto selItem = sel.at(0);
				std::string rez = "";
				auto x = serv.cauta(selItem->text().toStdString(),selItem->data(Qt::UserRole).toString().toStdString());
				string z = std::to_string(x.getDurata());
				rez += "Activitatea selectata are titlul: " + x.getTitlu() + ", descrierea: " + x.getDescriere() + ", tipul: " + x.getTip() + " si durata: " + z;
				QMessageBox::information(nullptr, "Activitate", QString::fromStdString(rez));
			}
			});*/
		QObject::connect(btnfildes, &QPushButton::clicked, [&]() {
			QDialog* addWind = new QDialog();
			addWind->setWindowTitle("Filtru");
			QVBoxLayout* mly = new QVBoxLayout(addWind);
			addWind->setLayout(mly);
			auto formly = new QFormLayout;
			QLineEdit* titluEdit = new QLineEdit;
			formly->addRow("Descriere", titluEdit);
			QVBoxLayout* btl = new QVBoxLayout;
			QPushButton* filBtn = new QPushButton("&Filtreaza!");
			QPushButton* cancelBtn = new QPushButton("&Inchide!");
			btl->addWidget(filBtn);
			btl->addWidget(cancelBtn);
			mly->addLayout(formly);
			mly->addLayout(btl);
			QObject::connect(filBtn, &QPushButton::clicked, [&]() {
				std::string ti = titluEdit->text().toStdString();
				vector<Activitate>fil = serv.filtruAct("descriere",ti);
				vector<Activitate>all = serv.getall();
				lst->clear();
				lst->setRowCount(fil.size());
				int i = 0;
				for (const auto& a : fil) {
					int dur = a.getDurata();
					string dur1 = std::to_string(dur);
					QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
					QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
					QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
					QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
					lst->setItem(i, 0, item);
					lst->setItem(i, 1, item1);
					lst->setItem(i, 2, item2);
					lst->setItem(i, 3, item3);
					i++;
				}
				});
			QObject::connect(cancelBtn, &QPushButton::clicked,[&]() {
				lst->setRowCount(serv.getall().size());
				loadData();
				addWind->close();
			});
			addWind->exec();
			loadData();
			});
		QObject::connect(btnfiltip, &QPushButton::clicked, [&]() {
				QDialog* addWind = new QDialog();
				addWind->setWindowTitle("Filtru");
				QVBoxLayout* mly = new QVBoxLayout(addWind);
				addWind->setLayout(mly);
				auto formly = new QFormLayout;
				QLineEdit* titluEdit = new QLineEdit;
				formly->addRow("Tip", titluEdit);
				QVBoxLayout* btl = new QVBoxLayout;
				QPushButton* filBtn = new QPushButton("&Filtreaza!");
				QPushButton* cancelBtn = new QPushButton("&Inchide!");
				btl->addWidget(filBtn);
				btl->addWidget(cancelBtn);
				mly->addLayout(formly);
				mly->addLayout(btl);
				QObject::connect(filBtn, &QPushButton::clicked, [&]() {
					std::string ti = titluEdit->text().toStdString();
					vector<Activitate>fil = serv.filtruAct("tip", ti);
					lst->clear();
					lst->setRowCount(fil.size());
					int i = 0;
					for (const auto& a : fil) {
						int dur = a.getDurata();
						string dur1 = std::to_string(dur);
						QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
						QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
						QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
						QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
						lst->setItem(i, 0, item);
						lst->setItem(i, 1, item1);
						lst->setItem(i, 2, item2);
						lst->setItem(i, 3, item3);
						i++;
					}
					});
				QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
					lst->setRowCount(serv.getall().size());
					loadData();
					addWind->close();
					});
				addWind->exec();
				loadData();
				});
		QObject::connect(btncos, &QPushButton::clicked, [&]() {
			QDialog* cos = new QDialog();
			cos->setWindowTitle("Activitati curente");
			QHBoxLayout* mainly = new QHBoxLayout;
			cos->setLayout(mainly);
			QVBoxLayout* vly = new QVBoxLayout;
			QListWidget* lw = new QListWidget;
			vector<Activitate> all = serv.getallC();
			lw->clear();
			for (const auto& a : all) {
				QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(a.getTitlu()));
				item->setData(Qt::UserRole, QString::fromStdString(a.getDescriere()));
				lw->addItem(item);
			}
			QVBoxLayout* l = new QVBoxLayout;
			l->addWidget(lw);
			QPushButton* add = new QPushButton("&Adauga");
			QPushButton* gen = new QPushButton("&Genereaza random");
			QPushButton* gol = new QPushButton("&Goleste");
			QPushButton* exp = new QPushButton("Export");
			QPushButton* exit = new QPushButton("Exit");
			vly->addWidget(add);
			vly->addWidget(gen);
			vly->addWidget(gol);
			vly->addWidget(exp);
			vly->addSpacing(10);
			vly->addWidget(exit);
			mainly->addLayout(l);
			mainly->addLayout(vly);
			QObject::connect(add, &QPushButton::clicked, [&]() {
				QDialog* ad = new QDialog();
				QHBoxLayout* mly = new QHBoxLayout(ad);
				ad->setLayout(mly);
				auto formly = new QFormLayout;
				QLineEdit* titluEdit = new QLineEdit;
				formly->addRow("Titlu", titluEdit);
				auto btl = new QVBoxLayout;
				QPushButton* addBtn = new QPushButton("&Adauga!");
				QPushButton* cancelBtn = new QPushButton("&Anuleaza!");
				btl->addWidget(addBtn);
				btl->addWidget(cancelBtn);
				mly->addLayout(formly);
				mly->addLayout(btl);
				QObject::connect(addBtn, &QPushButton::clicked, [&]() {
					try {
						serv.adaugaActC(titluEdit->text().toStdString());
						vector<Activitate> all = serv.getallC();
						lw->clear();
						for (const auto& a : all) {
							QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(a.getTitlu()));
							item->setData(Qt::UserRole, QString::fromStdString(a.getDescriere()));
							lw->addItem(item);
						}
					}
					catch (ActivitateRepoException& e) {
						stringstream os;
						os << e;
						QMessageBox::information(nullptr, "Info", QString::fromStdString(os.str()));
					}
					});
				QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
					ad->close();
					});
				ad->exec();
				});
			QObject::connect(gen, &QPushButton::clicked, [&]() {
				QDialog* ge = new QDialog();
				QHBoxLayout* mly = new QHBoxLayout(ge);
				ge->setLayout(mly);
				auto formly = new QFormLayout;
				QLineEdit* nr = new QLineEdit;
				formly->addRow("&Numar", nr);
				auto btl = new QVBoxLayout;
				QPushButton* addBtn = new QPushButton("&Genereaza!");
				QPushButton* cancelBtn = new QPushButton("&Iesire!");
				btl->addWidget(addBtn);
				btl->addWidget(cancelBtn);
				mly->addLayout(formly);
				mly->addLayout(btl);
				QObject::connect(addBtn, &QPushButton::clicked, [&]() {
					int n = nr->text().toInt();
					int i = serv.importarandomC(n);
					if (i == 0) {
						QMessageBox::information(nullptr, "Info", "Ai introdus un numar prea mare, incearca un numar mai mic!");
					}
					else if (i == 1) {
						vector<Activitate> all = serv.getallC();
						lw->clear();
						for (const auto& a : all) {
							QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(a.getTitlu()));
							item->setData(Qt::UserRole, QString::fromStdString(a.getDescriere()));
							lw->addItem(item);
						}
					}
					});
				QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
					ge->close();
					});
				ge->exec();
				});
			QObject::connect(gol, &QPushButton::clicked, [&](){
				serv.golesteActC();
				lw->clear();
				});
			QObject::connect(exp, &QPushButton::clicked, [&]() {
				QDialog* expr = new QDialog();
				QHBoxLayout* mly = new QHBoxLayout(expr);
				expr->setLayout(mly);
				auto formly = new QFormLayout;
				QLineEdit* nr = new QLineEdit;
				formly->addRow("&Nume fisier", nr);
				auto btl = new QVBoxLayout;
				QPushButton* addBtn = new QPushButton("&Genereaza!");
				QPushButton* cancelBtn = new QPushButton("&Iesire!");
				btl->addWidget(addBtn);
				btl->addWidget(cancelBtn);
				mly->addLayout(formly);
				mly->addLayout(btl);
				QObject::connect(addBtn, &QPushButton::clicked, [&]() {
					std::ofstream fout(nr->text().toStdString() + ".cvs");
					for (const auto& a : serv.getallC()) {
						fout << a.getTitlu() << ' ' << a.getDescriere() << ' ' << a.getTip() << ' ' << a.getDurata() << "\n";
					}
					QMessageBox::information(nullptr, "Info", "Fisier creat cu succes!");
					});
				QObject::connect(cancelBtn, &QPushButton::clicked, [&]() {
					expr->close();
					});
				expr->exec();
				});
			QObject::connect(exit, &QPushButton::clicked, [&](){
				cos->close();
				});
			cos->exec();
			});
	}
	void loadData() {
		lst->clear();
		vector<Activitate> all = serv.getall();
		int i = 0;
		for (const auto& a : all) {
			int dur = a.getDurata();
			string dur1 = std::to_string(dur);
			QTableWidgetItem* item = new QTableWidgetItem(QString::fromStdString(a.getTitlu()));
			QTableWidgetItem* item1 = new QTableWidgetItem(QString::fromStdString(a.getDescriere()));
			QTableWidgetItem* item2 = new QTableWidgetItem(QString::fromStdString(a.getTip()));
			QTableWidgetItem* item3 = new QTableWidgetItem(QString::fromStdString(dur1));
			lst->setItem(i, 0, item);
			lst->setItem(i, 1, item1);
			lst->setItem(i, 2, item2);
			lst->setItem(i, 3, item3);
			i++;
		}
	}
	void initGUI() {
		setLayout(lymain);
		QVBoxLayout* s1 = new QVBoxLayout;
		QVBoxLayout* s2 = new QVBoxLayout;
		QHBoxLayout* sf = new QHBoxLayout;
		s1->addWidget(btnstit);
		s1->addWidget(btnsdes);
		s2->addWidget(btnstip);
		s2->addWidget(btnsdur);
		sf->addLayout(s1);
		sf->addLayout(s2);
		QVBoxLayout* sfl = new QVBoxLayout;
		sfl->addWidget(lst);
		sfl->addLayout(sf);
		lymain->addLayout(sfl);
		auto stgly = new QVBoxLayout;
		auto lybtn = new QVBoxLayout{};
		lybtn->addWidget(btnadd);
		lybtn->addWidget(btndel);
		lybtn->addWidget(btnmod);
		lybtn->addWidget(btncau);
		lybtn->addWidget(btnfildes);
		lybtn->addWidget(btnfiltip);
		lybtn->addWidget(btnundo);
		lybtn->addWidget(btncos);
		lybtn->addSpacing(10);
		lybtn->addWidget(btnexit);
		stgly->addLayout(lybtn);
		lymain->addLayout(stgly);
	}
};

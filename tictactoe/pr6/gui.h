#pragma once
#include"qtableview.h"
#include"service.h"
#include<qwidget.h>
#include"mtm.h"
#include<qformlayout.h>
#include<qlayout.h>
#include<qlineedit.h>
#include<qpushbutton.h>
class gui : public QWidget {
private:
	Service& s;
public:
	gui(Service& s) :s{ s } {
		init();
		conn();
	}
	void init();
	void conn();
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
	vector<QHBoxLayout*> vl;
	QList<QModelIndex> li;
	MyTableModel* mtm = new MyTableModel{ s.getall() };
	QVBoxLayout* mlyx = new QVBoxLayout;
	QTableView* tw = new QTableView;
	QHBoxLayout* mly = new QHBoxLayout;
	QFormLayout* fly = new QFormLayout;
	QLineEdit* id = new QLineEdit;
	QLineEdit* dim = new QLineEdit;
	QLineEdit* tab = new QLineEdit;
	QLineEdit* juc = new QLineEdit;
	QLineEdit* sta = new QLineEdit;
	QPushButton* add = new QPushButton{ "&Adauga" };
	QPushButton* mod = new QPushButton{ "&Modifica" };
};
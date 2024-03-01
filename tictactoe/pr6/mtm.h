#pragma once
#include<qabstractitemview.h>
#include"service.h"
class MyTableModel : public QAbstractTableModel{
private:
	vector<Joc> j;
public:
	MyTableModel(const vector<Joc>&j):j{j}{}
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	void setJ(const vector<Joc>& jc) {
		this->j = jc;
		emit layoutChanged();
	}
};
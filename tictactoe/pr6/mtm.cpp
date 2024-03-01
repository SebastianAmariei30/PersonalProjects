#include"mtm.h"
int MyTableModel::rowCount(const QModelIndex& parent) const {
	return j.size();
}
int MyTableModel::columnCount(const QModelIndex& parent) const {
	return 5;
}
QVariant MyTableModel::data(const QModelIndex& index, int role) const {
	if (role == Qt::DisplayRole) {
		Joc j1 = j[index.row()];
		if (index.column() == 0)
			return QString::fromStdString(std::to_string(j1.getId()));
		if (index.column() == 1)
			return QString::fromStdString(std::to_string(j1.getDim()));
		if (index.column() == 2)
			return QString::fromStdString(j1.getTabla());
		if (index.column() == 3)
			return QString::fromStdString(j1.getJucator());
		if (index.column() == 4)
			return QString::fromStdString(j1.getStare());
	}
	return QVariant();
}
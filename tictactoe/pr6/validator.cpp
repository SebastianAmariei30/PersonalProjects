#include"validator.h"

ostream& operator<<(ostream& out, const ValidatorException& ex) {
	for (const auto& m : ex.msg) {
		if (&m != nullptr) {
			out << m << " ";
		}
	}
	return out;
}

void JocValidator::valideaza(Joc& j) {
	vector<string>msg;
	if (j.getDim() != 3 && j.getDim() != 4 && j.getDim() != 5)
		msg.push_back("dimenisune invalida!");
	if (j.getTabla().size() != j.getDim() * j.getDim())
		msg.push_back("tabla invalida!");
	for (const auto& x : j.getTabla())
		if (x != 'X' && x != 'O' && x != '-')
			msg.push_back("tabla invalida!");
	if (j.getJucator() != "X" && j.getJucator() != "O")
		msg.push_back("jucator invalid!");
	if (j.getStare() != "Neinceput" && j.getStare() != "Terminat" && j.getStare() != "InDerulare")
		msg.push_back("stare invaldia!");
	if (msg.size() > 0)
		throw ValidatorException(msg);
}
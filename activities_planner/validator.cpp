#include "validator.h"

#include <assert.h>
#include <sstream>

void ActivitateValidator::valideaza(const Activitate& a) {
	vector<string> msgs;
	if (a.getDurata() <= 0) msgs.push_back("Durata invalida!");
	if (a.getTitlu().size() == 0)msgs.push_back("Titlu invalid!");
	if (a.getTip().size() == 0) msgs.push_back("Tip invalid!");
	if (a.getDescriere().size() == 0) msgs.push_back("Descriere invalida!");
	if (msgs.size() > 0)
	{
		throw ValidatorException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidatorException& ex)
{
	for (const auto& msg : ex.msgs)
	{
		if (&msg!=nullptr)
			out << msg << " ";
	}
	return out;
}

void testValidator() {
	ActivitateValidator v;
	Activitate a{"","","",-1};
	try {
		v.valideaza(a);
	}
	catch (const ValidatorException& ex) {
		std::stringstream sout;
		sout << ex;
		auto const mesaj = sout.str();
		assert(mesaj.find("invalid") >= 0);
	}
}
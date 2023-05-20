#pragma once
#include <string>
#include "activitate.h"
#include <vector>

using std::vector;
using std::string;
using std::ostream;

class ValidatorException {
	vector<string> msgs;
public:
	ValidatorException(const vector<string>& erori):msgs{erori}{}

	friend ostream& operator<<(ostream& out, const ValidatorException& ex);

};

ostream& operator<<(ostream& out, const ValidatorException& ex);

class ActivitateValidator {
public:
	void valideaza(const Activitate& a);
};

void testValidator();
#pragma once
#include"joc.h"
#include<ostream>
using std::ostream;
class ValidatorException {
private:
	vector<string> msg;
public:
	ValidatorException(const vector<string>& m):msg{m}{}
	friend ostream& operator<<(ostream& out, const ValidatorException& ex);
};
ostream& operator<<(ostream& out, const ValidatorException& ex);

class JocValidator {
public:
	void valideaza(Joc& j);
};

#include"Activitate.h"
#include"Repo.h"

class ActiuneUndo {
public:
	virtual void doUndo() = 0;
	virtual ~ActiuneUndo() {};
};

class UndoAdauga : public ActiuneUndo {
	Activitate actAdaug;
	ActivitateRepo& rep;
public:
	UndoAdauga(ActivitateRepo& rep, Activitate& actAdaug) : rep{ rep }, actAdaug{ actAdaug } {}
	void doUndo() override {
		rep.sterge(actAdaug.getTitlu(), actAdaug.getDescriere());
	}
};

class UndoSterge : public ActiuneUndo {
	Activitate actSters;
	ActivitateRepo& rep;
public:
	UndoSterge(ActivitateRepo& rep, Activitate& actSters) :rep{ rep }, actSters{ actSters } {}
	void doUndo() override {
		rep.store(actSters);
	}
};

class UndoModifica : public ActiuneUndo {
	Activitate actMod;
	ActivitateRepo& rep;
	string titlun;
	string descn;
public:
	UndoModifica(ActivitateRepo& rep, Activitate& actMod, string titlun, string descn) :rep{ rep }, actMod{ actMod }, titlun{ titlun }, descn{descn} {}
	void doUndo()override {
		rep.modifica(actMod, descn, titlun);
	}
};
#pragma once
#include "Service.h"
#include "Activitate.h"

class UI {
	ActivitateService& serv;

	void adaugaUI();
	void stergeUI();
	void modificaUI();
	void printall();
	void cautaUI();
	void filtruUI();
	void sortUI();
	void meniuLC();
	void adaugaLC();
	void golesteLC();
	void genereazaLC();
	void printallLC();
	void exportLC();
	void fctUI();
	void undoUI();

public:
	UI(ActivitateService& serv) noexcept: serv{serv}{}
	UI(const UI& ot) = delete;

	void run();
};
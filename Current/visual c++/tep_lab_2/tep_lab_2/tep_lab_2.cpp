#include "tep_lab_2.h"
#include <string>
#include <iostream>

using namespace std;

CTable::CTable()
{
	s_name = DEFAULT_NAME;
	tableLength = DEFAULT_LENGTH;
	tablica = new int[tableLength];
	s_password = DEFAULT_PASSWORD;
	cout << "bezp: " + s_name << endl;
}

CTable::CTable(string sName, int iTableLength, string sPassword) {
	s_name = sName;
	tableLength = iTableLength;
	tablica = new int[tableLength];
	if(bCheckPassword(sPassword)) s_password = sPassword;
	else {
		s_password = DEFAULT_PASSWORD;
		cout << "Obiektowi nadano haslo domyslne." << endl;
	}
	cout << "parametr: " + sName << endl;
}

CTable::CTable(CTable & pcOther)
{
	s_name = pcOther.s_name + "_copy";
	tablica = new int[pcOther.tableLength];
	for (int i = 0; i < pcOther.tableLength; i++) {
		tablica[i] = pcOther.tablica[i];
	}
	cout << "kopiuj: " + s_name << endl;
}

CTable::~CTable() {
	delete[] tablica;
	cout << "Usuwam:" + s_name << endl;
}

void CTable::vSetName(string sName)
{
	this->s_name = sName;
}

bool CTable::setTableLength(int iTableLen)
{
	if (iTableLen <= 0) cout << "niewlasciwa dlugosc tablicy!" << endl;
	int newLength = iTableLen;
	tableLength = iTableLen;
	int* newTab = new int[newLength];
	for (int n = 0; n < newLength; n++) {
		newTab[n] = tablica[n];
	}

	delete tablica;
	tablica = newTab;
	delete newTab;
	newTab = NULL;

	if (iTableLen == newLength) {
		cout << "zmiana rozmiaru tablicy " + sGetName() + " zakonczona sukcesem" << endl;
		return true;
	}
	cout << "zmiana rozmiaru tablicy " + sGetName() + " zakonczona niepowodzeniem" << endl;
	return false;
}

string CTable::getPassword()
{
	return s_password;
}

void CTable::setPassword(string sPassword)
{
	if (bCheckPassword(sPassword)) {
		s_password = sPassword;
		cout << "zmiana hasla zakonczona powodzeniem" << endl;
	}
	else cout << "nie udalo sie zmienic hasla" << endl;
}

bool CTable::bCheckPassword(string sPassword)
{
	if (sPassword.length() < 5) { 
		cout << "Haslo zbyt krotkie!" << endl;
		return false;
	}
	if (bCheckUpperCase(sPassword) == false) {
		cout << "Haslo powinno zawierac co najmniej jedna wielka litere!" << endl;
		return false;
	}
	if (bCheckLowerCase(sPassword) == false) {
		cout << "Haslo powinno zawierac co najmniej jedna mala litere!" << endl;
		return false;
	}
	if (this->s_password == sPassword) {
		cout << "Haslo nie moze byc takie samo jak poprzednie!" << endl;
		return false;
	}
	return true;
}

bool CTable::bCheckUpperCase(string sPassword)
{
	char cCurrentChar;
	for (int ii = 0; ii < sPassword.length(); ii++) {
		cCurrentChar = sPassword[ii];
		if (cCurrentChar >= 'A' && cCurrentChar <= 'Z') return true;
	}
	return false;
}
bool CTable::bCheckLowerCase(string sPassword)
{
	char cCurrentChar;
	for (int ii = 0; ii < sPassword.length(); ii++) {
		cCurrentChar = sPassword[ii];
		if (cCurrentChar >= 'a' && cCurrentChar <= 'z') return true;
	}
	return false;
}

CTable * CTable::pcClone()
{
	CTable* newTab = new CTable(this->s_name, this->tableLength, this->s_password);
	
	for (int ii = 0; ii < tableLength; ii++) {
		newTab->tablica[ii] = this->tablica[ii];
	}

	return newTab;
} 
//móg³bym to zrobiæ z u¿yciem konstruktora kopiuj¹cego, lecz nie by³by to 
//idealny klon obiektu CTable, ze wzglêdu na strukturê tego konstruktora

string CTable::sGetName()
{
	return this->s_name;
}

int * CTable::getTablica()
{
	return tablica;
}

int CTable::getTableLength()
{
	return tableLength;
}


//void CTable::v_print_Tab()
//{
//	cout << "Tab " + s_name + ":\t";
//
//	for (int ii = 0; ii < tableLength; ii++) {
//		cout << tablica[tableLength] + " ";
//	}
//}


void CTable::v_mod_tab(CTable *pcTab, int iNewSize) {
	pcTab->setTableLength(iNewSize);
	cout << pcTab->getTablica() << endl;
}
void CTable::v_mod_tab(CTable cTab, int iNewSize) {
	cTab.setTableLength(iNewSize);
	cout << cTab.getTablica() << endl;
}

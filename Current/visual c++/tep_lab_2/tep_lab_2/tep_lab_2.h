#pragma once
#include <string>
using namespace std;

const int DEFAULT_LENGTH = 5;
const string DEFAULT_NAME = "Tab";
const int DEFAULT_VALUE = 0;
const string DEFAULT_PASSWORD = "";

class CTable {
public:
	CTable();
	CTable(string s_name, int iTableLength, string sPassword);
	CTable(CTable &pcOther);
	~CTable();
	//void v_print_Tab();
	CTable* pcClone();
	
	string sGetName();
	void vSetName(string sName);
	int* getTablica();
	int getTableLength();
	bool setTableLength(int iTableLen);
	string getPassword();
	void setPassword(string sPassword);
	bool bCheckPassword(string sPassword);
	bool bCheckUpperCase(string sPassword);
	bool bCheckLowerCase(string sPassword);
	

	void v_mod_tab(CTable *pcTab, int iNewSize);
	void v_mod_tab(CTable cTab, int iNewSize);

private:
	string s_name;
	int* tablica;
	int tableLength;
	string s_password;
}

;
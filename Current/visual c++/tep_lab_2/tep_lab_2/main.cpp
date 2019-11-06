#include <iostream>
#include "tep_lab_2.h"

using namespace std;

int main() {

	CTable* table1 = new CTable();
	table1->setTableLength(10);
	table1->vSetName("tablica1");
	cout << table1->sGetName() << " " << " adres " << table1 << endl;
	cout << "\n\n";

	CTable* table2 = new CTable("Tablica2", 10, "abcdE");
	cout << "Haslo dla " << table2->sGetName() << ": " << table2->getPassword() << endl;
	table2->setPassword("argw");
	table2->setPassword("234FD");
	table2->setPassword("akfkq");
	table2->setPassword("akfkQ");
	cout << "Haslo dla " << table2->sGetName() << ": " << table2->getPassword() << endl;

	cout << "\n\n";


	CTable c_tab;
	CTable *pc_new_tab;
	CTable *pc_new_tab2;
	pc_new_tab = c_tab.pcClone();
	pc_new_tab2 = c_tab.pcClone();

	
	cout << "\nmod1 (*pc) " << endl;
	cout << "przekazuje tablice " << pc_new_tab->getTablica() << endl;
	pc_new_tab->v_mod_tab(pc_new_tab, 7);
	
	cout << "\nmod2 (c)" << endl;
	cout << "przekazuje tablice " << pc_new_tab2->getTablica() << endl;
	pc_new_tab2->v_mod_tab((*pc_new_tab2), 7);




	cin.get();
}
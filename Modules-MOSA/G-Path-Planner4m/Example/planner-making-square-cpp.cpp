//Author: Jesimar da Silva Arantes
//Date: 08/10/2018
//Last Update: 08/10/2018
//Description: Code that generates a square-shaped route in C++.
//Descricao: Código que gera uma rota em formato de quadrado em C++.

#include <iostream>
#include <fstream>

using namespace std;

int main(){
	cout << "#G-Path-Planner [C++]" << endl;
	cout << "#Planejador que faz uma rota em formato de quadrado" << endl;
	ofstream outFile;
	outFile.open("output.txt");
	outFile << "0.0 0.0" << endl;
	outFile << "0.0 2.0" << endl;
	outFile << "0.0 4.0" << endl;
	outFile << "0.0 6.0" << endl;
	outFile << "0.0 8.0" << endl;
	outFile << "0.0 10.0" << endl;
	outFile << "2.0 10.0" << endl;
	outFile << "4.0 10.0" << endl;
	outFile << "6.0 10.0" << endl;
	outFile << "8.0 10.0" << endl;
	outFile << "10.0 10.0" << endl;
	outFile << "10.0 8.0" << endl;
	outFile << "10.0 6.0" << endl;
	outFile << "10.0 4.0" << endl;
	outFile << "10.0 2.0" << endl;
	outFile << "10.0 0.0" << endl;
	outFile << "8.0 0.0" << endl;
	outFile << "6.0 0.0" << endl;
	outFile << "4.0 0.0" << endl;
	outFile << "2.0 0.0" << endl;
	outFile << "0.0 0.0" << endl;
	outFile.close();
	cout << "#Rota terminada" << endl;
	return 0;
}

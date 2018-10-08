//Author: Jesimar da Silva Arantes
//Date: 08/10/2018
//Last Update: 08/10/2018
//Description: Code that generates a square-shaped route in C.
//Descricao: Código que gera uma rota em formato de quadrado em C.

#include <stdio.h>

int main(){
	printf("#G-Path-Planner [C]\n");
	printf("#Planejador que faz uma rota em formato de quadrado\n");
	FILE* f = fopen("output.txt", "w");
	fprintf(f, "0.0 0.0\n");
	fprintf(f, "0.0 2.0\n");
	fprintf(f, "0.0 4.0\n");
	fprintf(f, "0.0 6.0\n");
	fprintf(f, "0.0 8.0\n");
	fprintf(f, "0.0 10.0\n");
	fprintf(f, "2.0 10.0\n");
	fprintf(f, "4.0 10.0\n");
	fprintf(f, "6.0 10.0\n");
	fprintf(f, "8.0 10.0\n");
	fprintf(f, "10.0 10.0\n");
	fprintf(f, "10.0 8.0\n");
	fprintf(f, "10.0 6.0\n");
	fprintf(f, "10.0 4.0\n");
	fprintf(f, "10.0 2.0\n");
	fprintf(f, "10.0 0.0\n");
	fprintf(f, "8.0 0.0\n");
	fprintf(f, "6.0 0.0\n");
	fprintf(f, "4.0 0.0\n");
	fprintf(f, "2.0 0.0\n");
	fprintf(f, "0.0 0.0\n");
	fclose(f);
	printf("#Rota terminada\n");
	return 0;
}

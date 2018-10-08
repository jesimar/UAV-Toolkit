//Author: Jesimar da Silva Arantes
//Date: 08/10/2018
//Last Update: 08/10/2018
//Description: Code that generates a line-shaped route in C.
//Descricao: Código que gera uma rota em formato de linha em C.

#include <stdio.h>

int main(){
	printf("#G-Path-Replanner [C]\n");
	printf("#Planejador que faz uma rota em formato de linha\n");
	FILE* f = fopen("output.txt", "w");
	fprintf(f, "0.0 0.0\n");
	fprintf(f, "0.0 2.0\n");
	fprintf(f, "0.0 4.0\n");
	fprintf(f, "0.0 6.0\n");
	fprintf(f, "0.0 8.0\n");
	fprintf(f, "0.0 10.0\n");
	fclose(f);
	printf("#Rota terminada\n");
	return 0;
}

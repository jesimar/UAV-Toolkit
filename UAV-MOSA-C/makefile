#************************Makefile***************************
#Program: UAV-MOSA-C
#Compiler: GCC
#Author: Jesimar da Silva Arantes
#***********************************************************

all: mini_mosa monitoring pos_analyser

mini_mosa: uav-mini-mosa.c
	gcc uav-mini-mosa.c -o mini_mosa -lcurl
	
monitoring: uav-monitoring.c
	gcc uav-monitoring.c -o monitoring -lcurl
	
pos_analyser: uav-pos-analyser.c
	gcc uav-pos-analyser.c -o pos_analyser -lcurl

clean:
	rm -rf *.o mini_mosa monitoring pos_analyser log-aircraft-mosa.csv

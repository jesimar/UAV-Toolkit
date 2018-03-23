#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 23/03/2018
#Last Update: 23/03/2018
#Description: Script that runs the path replanner MPGA4s massive times.
#Descrição: Script que executa o replanejador de rotas MPGA4s massivas vezes.

echo "=========MPGA4s========="

for i in {1..43};
do
	echo "Calc Waypoint: " $i
	java -jar Exec-MPGA4s-Massive.jar $i
	java -jar mpga4s-plot.jar > output-simulation.log
	sleep 0.25
	echo "Save Routes"
	cp 'sol_1.png' 'Massive-Routes/route'$i'.png'
	cp 'route.txt' 'Massive-Routes/route'$i'.txt'
	sleep 0.25
done

echo "==========DONE=========="

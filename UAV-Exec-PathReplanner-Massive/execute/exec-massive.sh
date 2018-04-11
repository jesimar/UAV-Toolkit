#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 23/03/2018
#Last Update: 11/04/2018
#Description: Script that runs the path replanner current massive times.
#Descrição: Script que executa o replanejador de rotas atual massivas vezes.

echo "=========Path Replanner Massive========="

#Possible values ['MPGA4s', 'GA4s', 'DE4s', 'GH4s']
REPLANNER='GH4s'

#Possible values [mpga4s-plot.jar, ga4s-plot.jar, de4s-plot.jar, gh4s-plot.jar]
JARFILE=gh4s-plot.jar

for i in {1..43};
do
	echo "Calc Waypoint: " $i
	java -jar '../dist/UAV-Exec-PathReplanner-Massive.jar' $i $REPLANNER
	cd '../../Modules-IFA/'$REPLANNER'/'
	java -jar $JARFILE > output-simulation.log
	sleep 0.25
	echo "Save Routes"
	cp '../../Modules-IFA/'$REPLANNER'/sol_1.png' '../../UAV-Exec-PathReplanner-Massive/execute/Massive-Routes/'$REPLANNER'/route'$i'.png'
	cp '../../Modules-IFA/'$REPLANNER'/route.txt' '../../UAV-Exec-PathReplanner-Massive/execute/Massive-Routes/'$REPLANNER'/route'$i'.txt'
	sleep 0.25
	cd '../../UAV-Exec-PathReplanner-Massive/execute/'
done

echo "==================DONE=================="

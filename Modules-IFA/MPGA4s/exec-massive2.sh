#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 23/03/2018
#Last Update: 23/03/2018
#Description: Script that runs the path replanner MPGA4s massive times.
#Descrição: Script que executa o replanejador de rotas MPGA4s massivas vezes.

echo "=========MPGA4s========="

fileRoute="route-fix.txt"
fileConfig="config-copy.sgl"
i=1
while read -r line
do
	j=1
	while read -r lineConfig
	do
		if [ $j -eq 8 ]
		then
			echo > 
			#echo "line8"
			#echo $lineConfig
			#echo "Calc Waypoint: " $i
			#java -jar mpga4s-plot.jar > output-simulation.log
			#sleep 0.25
			#echo "Save Routes"
			#cp 'sol_1.png' 'Massive-Routes/route'$i'.png'
			#cp 'route.txt' 'Massive-Routes/route'$i'.txt'
			#sleep 0.25
		fi
		((j+=1))
	done < $fileConfig
	((i+=1))
done < $fileRoute

echo "==========DONE=========="

#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 08/10/2018
#Last Update: 08/10/2018
#Description: Script that compiles all the C and C++ codes of the UAV-Toolkit project.
#Descrição: Script que compila todos os códigos C e C++ do projeto UAV-Toolkit.

echo "=======================Compile Codes C/C++========================"
if [ -z $1 ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-pc
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-pc
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"
	
	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-pc
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-pc
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"
	
	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-PC -lm
	echo "Compiled A-Star.c"
	
	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-PC -lm
	echo "Compiled RouteStandard4m.c"
elif [ "$1" = "PC" ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-pc
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-pc
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"

	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-pc
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-pc
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"

	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-PC -lm
	echo "Compiled A-Star.c"

	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-PC -lm
	echo "Compiled RouteStandard4m.c"
elif [ "$1" = "Edison" ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-Edison
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-Edison
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"

	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-Edison
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-Edison
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"

	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-Edison -lm
	echo "Compiled A-Star.c"

	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-Edison -lm
	echo "Compiled RouteStandard4m.c"
elif [ "$1" = "RPi" ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-RPi
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-RPi
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"

	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-RPi
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-RPi
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"

	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-RPi -lm
	echo "Compiled A-Star.c"

	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-RPi -lm
	echo "Compiled RouteStandard4m.c"
elif [ "$1" = "BBB" ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-BBB
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-BBB
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"

	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-BBB
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-BBB
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"

	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-BBB -lm
	echo "Compiled A-Star.c"

	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-BBB -lm
	echo "Compiled RouteStandard4m.c"
elif [ "$1" = "Odroid" ]
then
	cd ../Modules-IFA/G-Path-Replanner4s/Example/
	gcc replanner-making-line-c.c -o replanner-making-line-c-Odroid
	g++ replanner-making-line-cpp.cpp -o replanner-making-line-cpp-Odroid
	echo "Compiled replanner-making-line-c.c and replanner-making-line-cpp.cpp"

	cd ../../../Modules-MOSA/G-Path-Planner4m/Example/
	gcc planner-making-square-c.c -o planner-making-square-c-Odroid
	g++ planner-making-square-cpp.cpp -o planner-making-square-cpp-Odroid
	echo "Compiled planner-making-square-c.c and planner-making-square-cpp.cpp"

	cd ../../../Modules-MOSA/A-Star4m/
	gcc A-Star.c -o A-Star-Odroid -lm
	echo "Compiled A-Star.c"

	cd ../../Modules-MOSA/Route-Standard4m/
	gcc RouteStandard4m.c -o RouteStandard4m-Odroid -lm
	echo "Compiled RouteStandard4m.c"
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format:  ./compile-all-code-c-cpp.sh COMPUTER_TYPE"
	echo "    Example: ./compile-all-code-c-cpp.sh  RPi"
	echo "    COMPUTER_TYPE:"
	echo "        PC -> Personal Computer"
	echo "        Edison -> Intel Edison"
	echo "        RPi -> Raspberry Pi"
	echo "        BBB -> BeagleBone Black"
	echo "        Odroid -> Odroid"
else
	echo "Error in arg COMPUTER_TYPE"
	echo "    COMPUTER_TYPE Possibles:"
	echo "        PC -> Personal Computer"
	echo "        Edison -> Intel Edison"
	echo "        RPi -> Raspberry Pi"
	echo "        BBB -> BeagleBone Black"
	echo "        Odroid -> Odroid"
fi

echo "==============================Done================================"

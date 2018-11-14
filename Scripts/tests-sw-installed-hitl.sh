#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 02/10/2018
#Last Update: 14/11/2018
#Description: Script that tests the installed software (HITL).
#Descrição: Script que testa os softwares instalados (HITL).

echo "==============================================="

echo "-------------------Test Java-------------------"

if java -version > /dev/null 2>&1
then 
	java -version
	echo "java-ok"
elif java --version > /dev/null 2>&1
then
	java --version
	echo "java-ok"
else
	echo "java-problems"
fi

echo "-------------------Test Python-----------------"
python --version

if python --version > /dev/null 2>&1
then 
	python --version
	echo "python-ok"
else 
	echo "python-problems"
fi

echo "------------------Test Dronekit----------------"
python Test-Sw-Installed/dronekit-test.py

echo "-----------------Test MavProxy-----------------"
mavproxy.py --version
python Test-Sw-Installed/mavproxy-test.py

echo "------------------Test UAV-IFA-----------------"

if java -jar ../UAV-IFA/dist/UAV-IFA.jar --version > /dev/null 2>&1
then 
	java -jar ../UAV-IFA/dist/UAV-IFA.jar --version
	echo "uav-ifa-ok"
else 
	echo "uav-ifa-problems"
fi

echo "-----------------Test UAV-MOSA-----------------"

if java -jar ../UAV-MOSA/dist/UAV-MOSA.jar --version > /dev/null 2>&1
then 
	java -jar ../UAV-MOSA/dist/UAV-MOSA.jar --version
	echo "uav-mosa-ok"
else 
	echo "uav-mosa-problems"
fi

echo "------------------Test UAV-GCS-----------------"

if java -jar ../UAV-GCS/dist/UAV-GCS.jar --version > /dev/null 2>&1
then 
	java -jar ../UAV-GCS/dist/UAV-GCS.jar --version
	echo "uav-gcs-ok"
else 
	echo "uav-gcs-problems"
fi

echo "==============================================="

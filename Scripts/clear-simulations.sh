#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 22/02/2018
#Last Update: 22/02/2018
#Description: Script that removes (deletes) a set of files from old missions.
#Descrição: Script que remove (apaga) um conjunto de arquivos de missões antigas.

echo "===========================Clear Files============================"
echo "Clear files mav.*"
rm mav.*

echo "Clear files *.csv"
rm ../UAV-MOSA/*.csv
rm ../UAV-IFA/*.csv
rm ../UAV-Monitoring/*.csv

echo "Clear files in HGA4m [*.log route3D* routeGeo*]"
rm ../Modules-MOSA/HGA4m/*.log
rm ../Modules-MOSA/HGA4m/route3D*
rm ../Modules-MOSA/HGA4m/routeGeo*

echo "Clear files in MPGA4s [output-simulation.log route.txt routeGeo.txt *.png]"
rm ../Modules-IFA/MPGA4s/output-simulation.log
rm ../Modules-IFA/MPGA4s/route.txt
rm ../Modules-IFA/MPGA4s/routeGeo.txt
rm ../Modules-IFA/MPGA4s/*.png

echo "Clear files in GH4s [output-simulation.log route.txt routeGeo.txt *.png]"
rm ../Modules-IFA/GH4s/output-simulation.log
rm ../Modules-IFA/GH4s/route.txt
rm ../Modules-IFA/GH4s/routeGeo.txt
rm ../Modules-IFA/GH4s/*.png

echo "Clear files in GA4s [output-simulation.log route.txt routeGeo.txt *.png]"
rm ../Modules-IFA/GA4s/output-simulation.log
rm ../Modules-IFA/GA4s/route.txt
rm ../Modules-IFA/GA4s/routeGeo.txt
rm ../Modules-IFA/GA4s/*.png

echo "Clear files in DE4s [output-simulation.log route.txt routeGeo.txt *.png]"
rm ../Modules-IFA/DE4s/output-simulation.log
rm ../Modules-IFA/DE4s/route.txt
rm ../Modules-IFA/DE4s/routeGeo.txt
rm ../Modules-IFA/DE4s/*.png

echo "==============================Done================================"

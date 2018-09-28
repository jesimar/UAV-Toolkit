#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 22/02/2018
#Last Update: 27/09/2018
#Description: Script that removes (deletes) a set of files from old missions.
#Descrição: Script que remove (apaga) um conjunto de arquivos de missões antigas.

echo "===========================Clear Files============================"

echo "Clear files telemetry QGC"
sudo rm /home/jesimar/Documentos/QGroundControl/Telemetry/*.tlog 2>/dev/null

echo "Clear files mav.*"
sudo rm mav.* 2>/dev/null

echo "Clear files *.csv"
sudo rm ../UAV-MOSA/*.csv 2>/dev/null
sudo rm ../UAV-IFA/*.csv 2>/dev/null
sudo rm ../UAV-Monitoring/*.csv 2>/dev/null
sudo rm ../UAV-Tests/*.csv 2>/dev/null

echo "Clear files in HGA4m [*.png *.log route3D* routeGeo*]"
sudo rm ../Modules-MOSA/HGA4m/*.png 2>/dev/null
sudo rm ../Modules-MOSA/HGA4m/*.log 2>/dev/null
sudo rm ../Modules-MOSA/HGA4m/route3D* 2>/dev/null
sudo rm ../Modules-MOSA/HGA4m/routeGeo* 2>/dev/null
sudo rm ../Modules-MOSA/HGA4m/log_error.txt 2>/dev/null
sudo rm ../Modules-MOSA/HGA4m/instance.err 2>/dev/null

echo "Clear files in CCQSP4m [*.png *.log route3D* routeGeo* output.txt]"
sudo rm ../Modules-MOSA/CCQSP4m/*.png 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/*.log 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/route3D* 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/routeGeo* 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/log_error.txt 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/output.txt 2>/dev/null
sudo rm ../Modules-MOSA/CCQSP4m/instance.err 2>/dev/null

echo "Clear files in AStar4m [output.txt goals.txt output-simulation.log route3D* routeGeo*]"
sudo rm ../Modules-MOSA/A-Star4m/output.txt 2>/dev/null
sudo rm ../Modules-MOSA/A-Star4m/goals.txt 2>/dev/null
sudo rm ../Modules-MOSA/A-Star4m/output-simulation.log 2>/dev/null
sudo rm ../Modules-MOSA/A-Star4m/route3D* 2>/dev/null
sudo rm ../Modules-MOSA/A-Star4m/routeGeo* 2>/dev/null

echo "Clear files in MPGA4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/MPGA4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/MPGA4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/MPGA4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/MPGA4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/MPGA4s/log_error.txt 2>/dev/null

echo "Clear files in GH4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/GH4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/GH4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/GH4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/GH4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/GH4s/log_error.txt 2>/dev/null

echo "Clear files in GA4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/GA4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/GA4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/GA4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/GA4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/GA4s/log_error.txt 2>/dev/null

echo "Clear files in DE4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/DE4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/DE4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/DE4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/DE4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/DE4s/log_error.txt 2>/dev/null

echo "Clear files in MS4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/MS4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/MS4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/MS4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/MS4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/MS4s/log_error.txt 2>/dev/null

echo "Clear files in MILP4s [output-simulation.log route.txt routeGeo.txt *.png]"
sudo rm ../Modules-IFA/MILP4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/MILP4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/MILP4s/routeGeo.txt 2>/dev/null
sudo rm ../Modules-IFA/MILP4s/*.png 2>/dev/null
sudo rm ../Modules-IFA/MILP4s/log_error.txt 2>/dev/null

echo "Clear files in GA-GA-4s [output-simulation.log]"
sudo rm ../Modules-IFA/GA-GA-4s/output-simulation.log 2>/dev/null

echo "Clear files in GA-GH-4s [output-simulation.log]"
sudo rm ../Modules-IFA/GA-GH-4s/output-simulation.log 2>/dev/null

echo "Clear files in Fixed-Route4s [output-simulation.log, route.txt, routeGeo.txt]"
sudo rm ../Modules-IFA/Fixed-Route4s/output-simulation.log 2>/dev/null
sudo rm ../Modules-IFA/Fixed-Route4s/route.txt 2>/dev/null
sudo rm ../Modules-IFA/Fixed-Route4s/routeGeo.txt 2>/dev/null

echo "Clear files in Route-Simplifier [output-simplifier.txt]"
sudo rm ../Modules-Global/Route-Simplifier/output-simplifier.txt 2>/dev/null

echo "Clear files in Route-Standard4m [route-behavior.txt]"
sudo rm ../Modules-MOSA/Route-Standard4m/route-behavior.txt 2>/dev/null

echo "==============================Done================================"

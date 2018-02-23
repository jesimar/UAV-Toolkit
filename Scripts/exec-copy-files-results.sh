#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 13/02/2018
#Last Update: 13/02/2018

DIR_FILES=Results/Case1/Sim1

echo "copy file results"
cp ../UAV-IFA/*.csv ../$DIR_FILES/.
cp ../UAV-MOSA/*.csv ../$DIR_FILES/.

cd ../Modules-MOSA/HGA4m/*.png
cd ../Modules-MOSA/HGA4m/*.log
cd ../Modules-MOSA/HGA4m/*.txt

#cd ../Modules-IFA/MPGA4s/*.png
#cd ../Modules-IFA/MPGA4s/*.log
#cd ../Modules-IFA/MPGA4s/*.txt

#cd ../Modules-IFA/GH4s/*.png
#cd ../Modules-IFA/GH4s/*.log
#cd ../Modules-IFA/GH4s/*.txt

#cd ../Modules-IFA/GA4s/*.png
#cd ../Modules-IFA/GA4s/*.log
#cd ../Modules-IFA/GA4s/*.txt

#cd ../Modules-IFA/DE4s/*.png
#cd ../Modules-IFA/DE4s/*.log
#cd ../Modules-IFA/DE4s/*.txt

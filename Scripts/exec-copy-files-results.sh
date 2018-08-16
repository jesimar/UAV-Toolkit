#!/bin/bash
: '
Author: Jesimar da Silva Arantes
#Date: 13/02/2018
#Last Update: 07/03/2018
#Description: Script that copies (saves) the results files automatically.
#Descrição: Script que copia (salva) os arquivos de resultados de forma automática.
'

echo "=============copy file results============="

cd ../Results/

FILE=$(date '+%Y-%m-%d_%H-%M-%S')
mkdir $FILE

cd $FILE
mkdir MOSA
mkdir IFA

cd ../../Scripts/

DIR_FILES=Results/$FILE/

echo "copy file mavproxy systems"

cp mav.* ../$DIR_FILES/. 2>/dev/null

echo "copy file IFA and MOSA systems"

cp ../UAV-IFA/*.csv ../$DIR_FILES/. 2>/dev/null
cp ../UAV-MOSA/*.csv ../$DIR_FILES/. 2>/dev/null

echo "copy file path planning"

cp ../Modules-MOSA/HGA4m/*.log ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/HGA4m/*.png ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/HGA4m/route3D* ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/HGA4m/routeGeo* ../$DIR_FILES/MOSA/. 2>/dev/null

cp ../Modules-MOSA/CCQSP4m/*.log ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/CCQSP4m/*.png ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/CCQSP4m/route3D* ../$DIR_FILES/MOSA/. 2>/dev/null
cp ../Modules-MOSA/CCQSP4m/routeGeo* ../$DIR_FILES/MOSA/. 2>/dev/null

echo "copy file path replanning"

cp ../Modules-IFA/MPGA4s/output-simulation.log ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MPGA4s/route.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MPGA4s/routeGeo.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MPGA4s/*.png ../$DIR_FILES/IFA/. 2>/dev/null

cp ../Modules-IFA/GH4s/output-simulation.log ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GH4s/route.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GH4s/routeGeo.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GH4s/*.png ../$DIR_FILES/IFA/. 2>/dev/null

cp ../Modules-IFA/GA4s/output-simulation.log ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GA4s/route.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GA4s/routeGeo.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/GA4s/*.png ../$DIR_FILES/IFA/. 2>/dev/null

cp ../Modules-IFA/DE4s/output-simulation.log ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/DE4s/route.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/DE4s/routeGeo.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/DE4s/*.png ../$DIR_FILES/IFA/. 2>/dev/null

cp ../Modules-IFA/MS4s/output-simulation.log ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MS4s/route.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MS4s/routeGeo.txt ../$DIR_FILES/IFA/. 2>/dev/null
cp ../Modules-IFA/MS4s/*.png ../$DIR_FILES/IFA/. 2>/dev/null

echo "====================done==================="

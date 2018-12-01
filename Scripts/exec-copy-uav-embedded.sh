#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 30/11/2018
#Last Update: 30/11/2018
#Description: Script that copies (saves) all the files automatically in UAV-Embedded.
#Descrição: Script que copia (salva) todos os arquivos de forma automática em UAV-Embedded.

echo "=============copy file UAV-Embedded============="

DIR_FILES=../../UAV-Embedded2

cp ../Modules-Global/config-global.properties $DIR_FILES/Modules-Global/.
cp ../Modules-Global/config-param.properties $DIR_FILES/Modules-Global/.

cp ../Modules-Global/Files/featureMission.txt $DIR_FILES/Modules-Global/Files/.
cp ../Modules-Global/Files/geoBase.txt $DIR_FILES/Modules-Global/Files/.
cp ../Modules-Global/Files/map-full.sgl $DIR_FILES/Modules-Global/Files/.
cp ../Modules-Global/Files/map-nfz.sgl $DIR_FILES/Modules-Global/Files/.

cp ../UAV-IFA/dist/UAV-IFA.jar $DIR_FILES/UAV-IFA/dist/.
cp ../UAV-IFA/dist/lib/Lib-UAV.jar $DIR_FILES/UAV-IFA/dist/lib/.

cp ../UAV-MOSA/dist/UAV-MOSA.jar $DIR_FILES/UAV-MOSA/dist/.
cp ../UAV-MOSA/dist/lib/Lib-UAV.jar $DIR_FILES/UAV-MOSA/dist/lib/.

cp ../UAV-S2DK/*.py $DIR_FILES/UAV-S2DK/.
cp ../UAV-S2DK/*.pyc $DIR_FILES/UAV-S2DK/.

cp ../Scripts/clear-simulations.sh $DIR_FILES/Scripts/.
cp ../Scripts/compile-all-code-c-cpp.sh $DIR_FILES/Scripts/.
cp ../Scripts/dir.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-ifa.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-login-bbb.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-login-edison.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-login-rpi.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-mavproxy-hitl.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-mavproxy-real-* $DIR_FILES/Scripts/.
cp ../Scripts/exec-mosa.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-s2dk.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-swap-mission.sh $DIR_FILES/Scripts/.
cp ../Scripts/exec-turn-off-buzzer.sh $DIR_FILES/Scripts/.
cp ../Scripts/kill-all-processes.sh $DIR_FILES/Scripts/.
cp ../Scripts/list-info-hd.sh $DIR_FILES/Scripts/.
cp ../Scripts/show-all-processes.sh $DIR_FILES/Scripts/.
cp ../Scripts/show-my-ip.sh $DIR_FILES/Scripts/.
cp ../Scripts/show-my-linux.sh $DIR_FILES/Scripts/.
cp ../Scripts/test-has-internet.sh $DIR_FILES/Scripts/.
cp ../Scripts/tests-sw-installed.sh $DIR_FILES/Scripts/.
cp ../Scripts/tests-sw-installed-hitl.sh $DIR_FILES/Scripts/.

echo "====================done==================="

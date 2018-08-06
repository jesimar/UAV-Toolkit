REM #Author: Jesimar da Silva Arantes
REM #Date: 19/10/2017
REM #Last Update: 19/10/2017
REM #Description: Script that runs the MAVProxy software on the local computer for SITL tests on the PC.
REM #Descrição: Script que executa o software MAVProxy no computador local para testes SITL no PC.

IP=127.0.0.1
PORT_IN_1=5760
PORT_IN_2=5501
PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master tcp:127.0.0.1:5760 --sitl 127.0.0.1:5501 --out 127.0.0.1:14550 --out 127.0.0.1:14551

#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 03/10/2018
#Last Update: 05/10/2018
#Description: Script that runs the path planner: G-Path-Planner4m.
#Descrição: Script que executa o planejador de rotas: G-Path-Planner4m.

#Abaixo deve ser definida a forma de execução do seu código usando o shellscript.

#Por exemplo: caso seu código esteja em Java use a linha abaixo.
java -jar -Djava.library.path="/opt/ibm/ILOG/CPLEX_Studio1251/cplex/bin/x86-64_sles10_4.1" nome_path_planner4m.jar > output-simulation.log
#java -jar -Djava.library.path="/home/jesimar/Apps/cplex/cplex/bin/x86-64_sles10_4.1" nome_path_planner4m.jar > output-simulation.log

#Por exemplo: caso seu código esteja em C use a linha abaixo.
./nome_path_planner4m > output-simulation.log

#Caso seu código esteja em outra linguagem de programação diferente defina abaixo a forma de execução desse código através do terminal.

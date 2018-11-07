# UAV-MOSA

O UAV-MOSA é uma implementação para monitoramento da missão do drone através da implementação do sistema *Mission Oriented Sensor Array* (MOSA) [[Link](http://www.teses.usp.br/teses/disponiveis/55/55134/tde-12072016-102631/pt-br.php)].

O projeto foi escrito em Java usando a IDE Netbeans.

## Instalação

Não necessita de instalação, basta baixar o projeto em seu computador e usar. 
Necessita apenas ter o Java JRE instalado uma vez que é uma aplicação Java.

## Como Executar

Existem três formas diferentes de executar o sistema que são: 

* **SITL** - Software-In-The-Loop - Tem que ter o sistema apenas no seu computador. O drone e os sensores são apenas simulado.
* **HITL** - Hardware-In-The-Loop - Tem que ter o sistema no seu computador e em algum Companion Computer (CC). O drone é simulado, mas os sensores são reais.
* **REAL_FLIGH** - Voo Real - Tem que ter o sistema no seu computador, no Companion Computer (CC) e ter um drone real. O drone é real e todos os sensores.

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta /UAV-Toolkit/Scripts/):

* **Forma 1** -> Execução em SITL (Executado no PC - Personal Computer):

   1. ./exec-sitl.sh                  (PC)
   2. ./exec-mavproxy-sitl.sh         (PC)
   3. ./exec-s2dk.sh                  (PC)
   4. ./exec-ifa.sh                   (PC)
   5. ./exec-mosa.sh                  (PC)

* **Forma 2** -> Execução em HITL (Executado no PC e CC - Companion Computer):

   1. ./exec-sitl.sh                  (PC)
   2. ./exec-mavproxy-hitl.sh         (CC)
   3. ./exec-s2dk.sh                  (CC)
   4. ./exec-ifa.sh                   (CC)
   5. ./exec-mosa.sh                  (CC)

* **Forma 3** -> Execução REAL_FLIGHT (Executado no PC e CC com Drone real):

   1. ./exec-mavproxy-real-*.sh       (CC)
   2. ./exec-s2dk.sh                  (CC)
   3. ./exec-ifa.sh                   (CC)
   4. ./exec-mosa.sh                  (CC)

:warning: **OBS:** Deve-se executar cada um desses scripts em um terminal diferente.

:warning: **OBS:** Você pode abrir/executar também uma estação de controle de solo para acompanhar a execução da missão, com por exemplo, o QGroundControl, APM Planner 2.0 ou Mission Planner.

## Interface do Sistema

Esta aplicação não possui interface gráfica. Abaixo encontra-se um print da saída na linha de comando dessa aplicação contendo alguns logs importantes.

![](./Figures/exec-mosa.png)

## Arquivos de Entrada

No diretório /UAV-Toolkit/Modules-Global/ tem-se um arquivo de entrada (config-global.properties), em que se define que tipo de missão deverá ser seguida, quais métodos iremos utilizar entre outras configurações do sistema MOSA.

Abaixo estão os principais parâmetros do sistema MOSA (existem outros parâmetros usados). 

```
prop.mosa.global.system_exec=PLANNER

prop.mosa.planner.local_exec=OFFBOARD
prop.mosa.planner.method=CCQSP4m
prop.mosa.planner.cmd_exec=./exec-planner.sh

prop.mosa.planner.hga4m.local_exec_processing=GROUND
prop.mosa.planner.hga4m.file_mission=waypointsMission.txt
prop.mosa.planner.hga4m.time_exec=[4.0,4.0,4.0,4.0,4.0,6.0]
prop.mosa.planner.hga4m.delta=0.08

prop.mosa.planner.ccqsp4m.waypoints=60
prop.mosa.planner.ccqsp4m.delta=0.04

prop.mosa.fixed_route.dir=../Modules-MOSA/Fixed-Route4m/
prop.mosa.fixed_route.file_waypoints=missao-teste.txt
```

## Arquivos de Saída

O seguinte arquivo de saída (log-overhead-mosa*.csv) em formato CSV é gerado: 

```
Type-of-Method;Requisition-URL;Time-In-MilliSeconds
GET;/get-parameters/;348
GET;/get-all-sensors/;32
GET;/get-distance-to-home/;11
GET;/get-all-sensors/;30
GET;/get-distance-to-home/;17
GET;/get-all-sensors/;47
GET;/get-distance-to-home/;37
...
GET;/get-all-sensors/;37
```

Este arquivo contém basicamente três campos o tipo de comando (tipo de método), o nome do comando (nome da URL) e o tempo gasto (overhead) em milissegundos (ms) para concluir o comando.

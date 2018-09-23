# UAV-IFA

Projeto escrito em Java usando a IDE Netbeans para monitoramento da segurança em voo do drone  através da implementação do sistema *In-Flight Awareness* (IFA) [[Link](http://www.teses.usp.br/teses/disponiveis/55/55134/tde-03122015-105313/pt-br.php)].

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL (Executado no PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-sitl.sh         (PC)
3. ./exec-s2dk.sh                  (PC)
4. ./exec-ifa.sh                   (PC)

Forma 2 -> Execução em HITL (Executado no CC - Companion Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-hitl.sh         (CC)
3. ./exec-s2dk.sh                  (CC)
4. ./exec-ifa.sh                   (CC)

Forma 3 -> Execução REAL_FLIGHT (Executado no Drone no CC):

1. ./exec-mavproxy-real-*.sh       (CC)
2. ./exec-s2dk.sh                  (CC)
3. ./exec-ifa.sh                   (CC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

OBS: Para que a aeronave faça algo efetivamente deve-se executar também o sistema MOSA.

OBS: Você pode abrir/executar também uma estação de controle de solo para acompanhar a execução da missão, com por exemplo, o QGroundControl, APM Planner 2.0 ou Mission Planner.

![](./Figures/exec-ifa.png)

## Arquivos de Entrada

No diretório /Modules-Global/ tem-se um arquivo de propriedades (config-global.properties), em que se define que tipo algoritmo será executado em caso de falha crítica entre outras configurações do sistema IFA.

## Arquivos de Saída

O seguinte arquivo de saída (log-overhead-ifa*.csv) em formato CSV é gerado: 

```
Time-in-POST(ms);/set-parameter/;322
Time-in-POST(ms);/set-parameter/;232
Time-in-POST(ms);/set-parameter/;348
Time-in-GET(ms);/get-parameters/;208
Time-in-GET(ms);/get-home-location/;419
Time-in-GET(ms);/get-all-sensors/;27
Time-in-GET(ms);/get-distance-to-home/;60
...
Time-in-GET(ms);/get-all-sensors/;44
```

Este arquivo contém basicamente três campos o tipo de comando, o nome do comando e o tempo gasto (overhead) em milissegundos para concluir o comando.

Um outro arquivo de saída gerado é o log-aircraft*.csv que também utiliza o formato CSV e possui o seguinte aspecto:

```
time;lat;lng;alt_rel;alt_abs;voltage_bat;current_bat;level_bat;pitch;yaw;roll;vx;vy;vz;fixtype;satellitesvisible;eph;epv;heading;groundspeed;airspeed;mode;system-status;armed;is-armable;ekf-ok
0.0;-22.0059325;-47.8987095;0.19;870.18;12.587;0.0;100.0;0.005283987149596214;0.4842582046985626;-0.008793571032583714;0.0;-0.31;0.05;3;10;121;65535;27.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
0.5;-22.0059325;-47.8987095;0.19;870.18;12.587;0.0;100.0;0.005300106015056372;0.48441487550735474;-0.008804457262158394;0.0;-0.31;0.05;3;10;121;65535;27.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
1.0;-22.0059325;-47.8987095;0.19;870.18;12.587;0.0;100.0;0.005296911578625441;0.48446178436279297;-0.008803870528936386;0.0;-0.31;0.05;3;10;121;65535;27.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
1.5;-22.0059325;-47.8987095;0.19;870.18;12.587;0.0;100.0;0.005296911578625441;0.48446178436279297;-0.008803870528936386;0.0;-0.31;0.05;3;10;121;65535;27.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
...
9.0;-22.0059325;-47.8987095;0.19;870.18;12.587;0.0;100.0;0.005296911578625441;0.48446178436279297;-0.008803870528936386;0.0;-0.31;0.05;3;10;121;65535;27.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
```

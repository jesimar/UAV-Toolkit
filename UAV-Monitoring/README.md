# UAV-Monitoring

Projeto escrito em Java usando a IDE Netbeans para monitoramento das informações dos sensores do drone.

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL (Executado no PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-silt.sh         (PC)
3. ./exec-s2dk.sh                  (PC)
4. ./exec-monitoring.sh            (PC)

Forma 2 -> Execução em HITL (Executado no CC - Companion Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-hitl.sh         (CC)
3. ./exec-s2dk.sh                  (CC)
4. ./exec-monitoring.sh            (CC)

Forma 3 -> Execução REAL_FLIGHT (Executado no Drone no CC):

1. ./exec-mavproxy-real-*.sh       (CC)
2. ./exec-s2dk.sh                  (CC)
3. ./exec-monitoring.sh            (CC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

## Saída do Programa

A saída na tela desse programa deverá ser algo como a seguir:

```
UAV-MONITORING
0.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0016137107741087675;0.34749430418014526;9.80661134235561E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
1.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0015787805896252394;0.34754806756973267;9.81771619990468E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
2.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.001606693142093718;0.3475651741027832;9.87518928013742E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
...
3.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0015879754209890962;0.3475704491138458;9.789993055164814E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
```

Um arquivo .csv com o mesmo conteúdo do impresso em tela é gerado (nome do arquivo uav-data*.csv).

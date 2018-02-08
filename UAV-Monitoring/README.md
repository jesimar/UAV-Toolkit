# UAV-Monitoring

Projeto escrito em Java usando a IDE Netbeans para monitoramento das informações dos sensores do drone.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh
4. ./exec-monitoring.sh

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

A saída desse programa deverá ser algo como a seguir:

```
UAV-MONITORING
0.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0016137107741087675;0.34749430418014526;9.80661134235561E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
1.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0015787805896252394;0.34754806756973267;9.81771619990468E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
2.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.001606693142093718;0.3475651741027832;9.87518928013742E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
...
3.0;-22.0059325;-47.8987095;0.0;869.99;12.587;0.0;100.0;0.0015879754209890962;0.3475704491138458;9.789993055164814E-4;-0.03;0.0;0.0;3;10;121;65535;19.0;0.0;0.0;STABILIZE;STANDBY;false;true;true
```

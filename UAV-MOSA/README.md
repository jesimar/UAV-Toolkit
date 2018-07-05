# UAV-MOSA

Projeto escrito em Java usando a IDE Netbeans para monitoramento da missão do drone através da implementação do sistema *Mission Oriented Sensor Array* (MOSA) [[Link](http://www.teses.usp.br/teses/disponiveis/55/55134/tde-12072016-102631/pt-br.php)].

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-local.sh        (PC)
3. ./exec-soa-interface.sh         (PC)
4. ./exec-ifa.sh                   (PC)
5. ./exec-mosa.sh                  (PC)

Forma 2 -> Execução em SITL-CC (CC - Companion Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-cc-sitl.sh      (CC)
3. ./exec-soa-interface.sh         (CC)
4. ./exec-ifa.sh                   (CC)
5. ./exec-mosa.sh                  (CC)

Forma 3 -> Execução no Drone no CC:

1. ./exec-mavproxy-cc-real-*.sh    (CC)
2. ./exec-soa-interface.sh         (CC)
3. ./exec-ifa.sh                   (CC)
4. ./exec-mosa.sh                  (CC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

OBS: Você pode abrir/executar também uma estação de controle de solo para acompanhar a execução da missão, com por exemplo, o QGroundControl, APM Planner 2.0 ou Mission Planner.

![](./Figures/exec-mosa.png)

## Arquivos de Entrada

No diretório raiz tem-se um arquivo de entrada (config-mosa.properties), em que se define que tipo de missão deverá ser seguida, quais métodos iremos utilizar entre outras configurações do sistema MOSA.

## Arquivos de Saída

O seguinte arquivo de saída (log-overhead-mosa*.csv) em formato CSV é gerado: 

```
Time-in-GET(ms);/get-parameters/;348
Time-in-GET(ms);/get-all-sensors/;32
Time-in-GET(ms);/get-distance-to-home/;11
Time-in-GET(ms);/get-all-sensors/;30
Time-in-GET(ms);/get-distance-to-home/;17
Time-in-GET(ms);/get-all-sensors/;47
Time-in-GET(ms);/get-distance-to-home/;37
...
Time-in-GET(ms);/get-all-sensors/;37
```

Este arquivo contém basicamente três campos o tipo de comando, o nome do comando e o tempo gasto (overhead) em milissegundos para concluir o comando.

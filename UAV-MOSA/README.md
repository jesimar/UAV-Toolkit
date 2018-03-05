# UAV-MOSA

Projeto escrito em Java usando a IDE Netbeans para monitoramento da missão do drone através da implementação do sistema MOSA (Mission Oriented Sensor Array).

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-local.sh        (PC)
3. ./exec-soa-interface.sh         (PC)
4. ./exec-ifa.sh                   (PC)
5. ./exec-mosa.sh                  (PC)

Forma 2 -> Execução em SITL-EDISON:

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-edison-sitl.sh  (EDISON)
3. ./exec-soa-interface.sh         (EDISON)
4. ./exec-ifa.sh                   (EDISON)
5. ./exec-mosa.sh                  (EDISON)

Forma 3 -> Execução no Drone na EDISON:

1. ./exec-mavproxy-edison.sh       (EDISON)
2. ./exec-soa-interface.sh         (EDISON)
3. ./exec-ifa.sh                   (EDISON)
4. ./exec-mosa.sh                  (EDISON)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

![](./Figures/exec-mosa.png)

## Arquivos de Entrada

No diretório raiz tem-se um arquivo de entrada (config.properties), onde se define que tipo de missão deverá ser seguida, quais métodos iremos utilizar entre outras configurações do sistema MOSA.

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

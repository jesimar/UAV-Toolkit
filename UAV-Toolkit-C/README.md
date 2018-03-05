# UAV-Toolkit-C

Conjunto de códigos em C para monitoramento do drone.

Os seguintes códigos foram criados para validar o serviço UAV-SOA-Interface.

* **uav-pos-analyser.c** -> Código que análise a posição do drone a cada instante. Código semelhante ao UAV-PosAnalyser escrito em Java.
* **uav-monitoring.c** -> Código que monitora os sensores do drone a cada instante. Código semelhante ao UAV-Monitoring escrito em Java.
* **uav-mini-mosa.c** -> Código inicial do sistema MOSA escrito em C.Código inicial semelhante ao UAV-MOSA escrito em Java.

## Como Compilar

Para compilar os códigos digite no terminal: 

`make all`

Para apagar os códigos digite no terminal:

`make clean`

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts/comandos:

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-local.sh        (PC)
3. ./exec-soa-interface.sh         (PC)
4. ./pos_analyser ou ./monitoring ou ./mini_mosa     (executado na pasta local)            (PC)

Forma 2 -> Execução em SITL-EDISON:

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-edison-sitl.sh  (EDISON)
3. ./exec-soa-interface.sh         (EDISON)
4. ./pos_analyser ou ./monitoring ou ./mini_mosa     (executado na pasta local)            (EDISON)

Forma 3 -> Execução no Drone na EDISON:

1. ./exec-mavproxy-edison.sh       (EDISON)
2. ./exec-soa-interface.sh         (EDISON)
3. ./pos_analyser ou ./monitoring ou ./mini_mosa     (executado na pasta local)            (EDISON)

OBS: Deve-se executar cada um desses scripts/comandos em um terminal diferente.

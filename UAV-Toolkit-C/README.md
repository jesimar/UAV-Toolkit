# UAV-Toolkit-C

Conjunto de códigos em C para monitoramento do drone.

Os seguintes códigos foram criados para validar o serviço UAV-Services-Dronekit.

* **uav-pos-analyser.c** -> Código que análise a posição do drone a cada instante. Código semelhante ao UAV-PosAnalyser escrito em Java.
* **uav-monitoring.c** -> Código que monitora os sensores do drone a cada instante. Código semelhante ao UAV-Monitoring escrito em Java.
* **uav-mini-mosa.c** -> Código inicial do sistema MOSA escrito em C.Código inicial semelhante ao UAV-MOSA escrito em Java.

Para testar os códigos acima, primeiramente, deve-se executar os seguintes scripts/comandos:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh
4. ./pos_analyser ou ./monitoring ou ./mini_mosa (executado na pasta local)

OBS: Deve-se executar cada um desses scripts/comandos em um terminal diferente.

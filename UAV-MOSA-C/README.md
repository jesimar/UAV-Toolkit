# UAV-MOSA-C

Conjunto de códigos em C para monitoramento do drone.

Os seguintes códigos foram criados para validar o serviço UAV-Services-Dronekit.

* **uav-pos-analyser.c** -> Código que análise a posição do drone a cada instante. Código semelhante ao UAV-PosAnalyser escrito em Java.
* **uav-monitoring.c** -> Código que monitora os sensores do drone a cada instante. Código semelhante ao UAV-Monitoring escrito em Java.
* **uav-mini-mosa.c** -> Código inicial do sistema MOSA escrito em C.Código inicial semelhante ao UAV-MOSA escrito em Java.

Para testar os códigos acima, primeiramente, deve-se executar os seguintes scripts:

+ ./exec-sitl.sh
+ ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
+ ./exec-services-dronekit.sh

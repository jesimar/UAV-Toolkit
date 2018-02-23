# UAV-PosAnalyser

Projeto escrito em Java usando a IDE Netbeans para monitoramento da posição do drone.

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-local.sh        (PC)
3. ./exec-services-dronekit.sh     (PC)
4. ./exec-pos-analyser.sh          (PC)

Forma 2 -> Execução em SITL-EDISON:

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-edison-sitl.sh  (EDISON)
3. ./exec-services-dronekit.sh     (EDISON)
4. ./exec-pos-analyser.sh          (EDISON)

Forma 3 -> Execução no Drone na EDISON:

1. ./exec-mavproxy-edison.sh       (EDISON)
2. ./exec-services-dronekit.sh     (EDISON)
3. ./exec-pos-analyser.sh          (EDISON)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

A saída desse programa deverá ser algo como a seguir:

```
UAV-POSITIONS-ANALYSER
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.00, 870.00]
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.10, 870.10]
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.15, 870.15]
...
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 2.00, 872.00]
```

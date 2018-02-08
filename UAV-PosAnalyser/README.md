# UAV-PosAnalyser

Projeto escrito em Java usando a IDE Netbeans para monitoramento da posição do drone.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh
4. ./exec-pos-analyser.sh

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

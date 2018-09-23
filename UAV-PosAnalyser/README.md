# UAV-PosAnalyser

Projeto escrito em Java usando a IDE Netbeans para monitoramento da posição do drone.

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL (Executado no PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-sitl.sh         (PC)
3. ./exec-s2dk.sh                  (PC)
4. ./exec-pos-analyser.sh          (PC)

Forma 2 -> Execução em HITL (Executado no CC - Companion Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-hitl.sh         (CC)
3. ./exec-s2dk.sh                  (CC)
4. ./exec-pos-analyser.sh          (CC)

Forma 3 -> Execução REAL_FLIGHT (Executado no Drone no CC):

1. ./exec-mavproxy-real-*.sh       (CC)
2. ./exec-s2dk.sh                  (CC)
3. ./exec-pos-analyser.sh          (CC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

## Saída do Programa

A saída desse programa deverá ser algo como a seguir:

```
UAV-POSITIONS-ANALYSER
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.00, 870.00]
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.10, 870.10]
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 0.15, 870.15]
...
gps (lat, lng, alt_rel, alt_abs): [-22.0059325, -47.8987095, 2.00, 872.00]
```

# UAV-Tests

Projeto escrito em Java usando a IDE Netbeans para testes de funções do UAV-SOA-Interface.

## Como Executar

Para testar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

1. ./exec-sitl.sh                (PC)
2. ./exec-mavproxy-local.sh      (PC)
3. ./exec-soa-interface.sh       (PC)
4. ./exec-tests.sh               (PC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

OBS: Você pode abrir/executar também uma estação de controle de solo para acompanhar a execução da missão, com por exemplo, o QGroundControl, APM Planner 2.0 ou Mission Planner.

## Arquivos de Entrada

No diretório principal desse projeto existe um arquivo de propriedades (config.properties), em que se define qual o número do teste a ser executado. Modifique esse arquivo para o número do teste que você deseja executar.

```
prop.number_test=16
```

# UAV-Tests

Projeto escrito em Java usando a IDE Netbeans para testes de funções do UAV-SOA-Interface.

## Como Executar

Para testar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh
3. ./exec-soa-interface.sh
4. ./exec-tests.sh

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

## Arquivos de Entrada

No diretório principal desse projeto existe um arquivo de propriedades (config.properties), onde se define qual o número do teste a ser executado. Modifique esse arquivo para o número do teste que você deseja executar.

```
prop.number_test=16
```

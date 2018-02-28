# UAV-Insert-Failure

Projeto escrito em Java usando a IDE Netbeans para inserção de falhas de forma manual no drone.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh
3. ./exec-services-dronekit.sh
4. ./exec-ifa.sh
5. ./exec-insert-failure.sh
6. ./exec-mosa.sh

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

No diretório principal desse projeto existe um arquivo de propriedades (config.properties), onde se define o IP da máquina e a porta usada na comunicação.

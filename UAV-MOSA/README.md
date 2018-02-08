# UAV-MOSA

Projeto escrito em Java usando a IDE Netbeans para monitoramento da missão do drone.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh
4. ./exec-ifa.sh
5. ./exec-mosa.sh

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

No diretório raiz tem-se um arquivo de propriedades (config.properties), onde se define que tipo de missão deverá ser seguida, quais métodos iremos utilizar entre outras configurações do sistema MOSA.

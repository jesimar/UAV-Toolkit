# UAV-IFA

Projeto escrito em Java usando a IDE Netbeans para monitoramento da segurança em voo do drone.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh
4. ./exec-ifa.sh

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

No diretório raiz tem-se um arquivo de propriedades (config.properties), onde se define que tipo algoritmo será executado em caso de falha crítica entre outras configurações do sistema IFA.

Para que a aeronave faça algo efetivamente deve-se executar também o sistema MOSA.

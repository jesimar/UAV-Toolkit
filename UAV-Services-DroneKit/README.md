# UAV-Services-DroneKit

Aplicação em Python que provê serviços sobre a biblioteca Dronekit usando o protocolo HTTP com métodos GET e POST.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh ou ./exec-mavproxy-edison.sh
3. ./exec-services-dronekit.sh

Em seguida é necessário executar mais alguma aplicação que faça as requisições ao UAV-Services-Dronekit como exemplo de aplicações tem-se: 
UAV-IFA, UAV-MOSA, UAV-Tests, UAV-Monitoring, UAV-PosAnalyser, UAV-MOSA-C.

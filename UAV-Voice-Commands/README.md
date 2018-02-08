# UAV-Voice-Commands

Projeto escrito em Java usando a IDE Netbeans para controle do drone utilizando comandos de voz.

Para testar este código, primeiramente, deve-se executar os seguintes scripts:

1. ./exec-sitl.sh
2. ./exec-mavproxy-local.sh
3. ./exec-services-dronekit.sh
4. ./exec-ifa.sh
4. ./exec-mosa.sh

Antes de executar o sistema MOSA deve-se fazer as seguintes alterações: 
```
prop.global.system_exec=CONTROLLER
prop.controller.type_controller=VOICE_COMMANDS
```

OBS: Deve-se executar cada um desses scripts em um terminal diferente.

OBS: Por enquanto esse código só funciona em Software-In-The-Loop (SITL).

Os seguintes comandos de voz são suportados: 

```
takeoff
land
up
down 
left
right
forward
back
rotate
quit
```

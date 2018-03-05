# UAV-Voice-Commands

Projeto escrito em Java usando a IDE Netbeans para controle do drone utilizando comandos de voz.

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts):

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                  (PC)
2. ./exec-mavproxy-local.sh        (PC)
3. ./exec-soa-interface.sh         (PC)
4. ./exec-ifa.sh                   (PC)
5. ./exec-mosa.sh                  (PC)

OBS: Deve-se executar cada um desses scripts em um terminal diferente.
OBS: Por enquanto esse código só funciona em Software-In-The-Loop (SITL).

## Arquivos de Entrada

Antes de executar o sistema MOSA deve-se fazer as seguintes alterações no arquivo de configurações (config.properties) do MOSA: 

```
prop.global.system_exec=CONTROLLER
prop.controller.type_controller=VOICE_COMMANDS
```

## Comandos Suportados

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

# UAV-Services-DroneKit

Aplicação em Python que provê serviços sobre a biblioteca Dronekit usando o protocolo HTTP com métodos GET e POST.

## Rodar a missão da USP no Campus 2 faça o seguinte: 
Abra um terminal e execute: 
```
$ dronekit-sitl copter --home=-22.005640526891543,-47.93247463788215,870,70
```
ou
```
$ dronekit-sitl plane-3.3.0 --home=-22.005640526891543,-47.93247463788215,870,70
```

Abra outro terminal e execute:
```
$ mavproxy.py --master tcp:127.0.0.1:5760 --sitl 127.0.0.1:5501 --out 127.0.0.1:14550 --out 127.0.0.1:14551
```
No terminal do mavproxy abra o map do mavproxy para visualizar a rota (opção 1):
```
MAV> module load map
```
Abra alguma Estação de Controle de Solo (opção 2):
Testado no QGroundControl (conexão automática) e APM Planner 2 (conecte usando UDP 127.0.0.1:14550):

```
$ qgroundcontrol
```
ou
```
$ apmplanner2
```

Abra outro terminal e execute:
```
$ python mission_usp_campus2.py --connect 127.0.0.1:14551
```

## Visualizar o resultado da simulação: 

Após rodar a simulação você poderá visualizá-la usando o programa MAVExplorer.py. Caso seja necessário instale-o. 
Para visualizá-lo execute o seguinte comando no terminal passando como argumento o arquivo de log da telemetria (mav.tlog) gerado.

```
$ MAVExplorer.py mav.tlog
MAV> map
```

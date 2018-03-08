# UAV-SOA-Interface

Aplicação em Python que disponibiliza através do protocolo HTTP com métodos GET e POST um conjunto serviços sobre a biblioteca Dronekit. Estes serviços são providos através de uma Interface de Arquitetura Orientada a Serviços (SOA) do drone.

## Como Executar

Para executar este código, primeiramente, deve-se executar os seguintes scripts (localizados na pasta Scripts): 

Forma 1 -> Execução em SITL-PC (PC - Personal Computer):

1. ./exec-sitl.sh                    (PC)
2. ./exec-mavproxy-local.sh          (PC)
3. ./exec-soa-interface.sh           (PC)

Forma 2 -> Execução em SITL-EDISON:

1. ./exec-sitl.sh                    (PC)
2. ./exec-mavproxy-edison-sitl.sh    (EDISON)
3. ./exec-soa-interface.sh           (EDISON)

Forma 3 -> Execução no Drone na EDISON:

1. ./exec-mavproxy-edison.sh         (EDISON)
2. ./exec-soa-interface.sh           (EDISON)

Em seguida é necessário executar mais alguma aplicação que faça as requisições ao UAV-SOA-Interface, como exemplo, de aplicações tem-se: 
UAV-IFA, UAV-MOSA, UAV-Tests, UAV-Monitoring, UAV-PosAnalyser e UAV-Toolkit-C.

![](../Figures/exec-soa-interface.png)

## Requisições:

Métodos GET: 

* '/get-gps/'
* '/get-battery/'
* '/get-attitude/'
* '/get-velocity/'
* '/get-heading/'
* '/get-groundspeed/'
* '/get-airspeed/'
* '/get-gpsinfo/'
* '/get-mode/'
* '/get-system-status/'
* '/get-armed/'
* '/get-is-armable/'
* '/get-ekf-ok/'
* '/get-home-location/'
* '/get-parameters/'
* '/get-distance-to-home/'
* '/get-all-sensors/'

Métodos POST:

* '/set-waypoint/'
* '/append-waypoint/'
* '/set-mission/'
* '/append-mission/'
* '/set-velocity/'
* '/set-parameter/'
* '/set-heading/'
* '/set-mode/'

## Como Fazer Requisições usando o Navegador:

```
http://localhost:50000/get-gps/
http://localhost:50000/get-battery/
http://localhost:50000/get-attitude/
http://localhost:50000/get-velocity/
http://localhost:50000/get-heading/
http://localhost:50000/get-groundspeed/
http://localhost:50000/get-airspeed/
http://localhost:50000/get-gpsinfo/
http://localhost:50000/get-mode/
http://localhost:50000/get-mode/
http://localhost:50000/get-groundspeed/
http://localhost:50000/get-groundspeed/
http://localhost:50000/get-groundspeed/
```


    '/get-system-status/': views.getSystemStatus,
    '/get-armed/': views.getArmed,
    '/get-is-armable/': views.isArmable,
    '/get-ekf-ok/': views.getEkfOk,
    '/get-home-location/': views.getHomeLocation,
    '/get-parameters/': views.getParameters,
    '/get-distance-to-home/': views.getDistanceToHome,
    '/get-all-sensors/': views.getAllInfoSensors

    '/set-waypoint/': views.setWaypoint,
    '/append-waypoint/': views.appendWaypoint,
    '/set-mission/': views.setMission,
    '/append-mission/': views.appendMission,
    '/set-velocity/': views.setVelocity,
    '/set-parameter/': views.setParameter,
    '/set-heading/': views.setHeading,
    '/set-mode/': views.setMode
# UAV-Manager

O UAV-Manager é um programa para facilitar a instalação/execução do ambiente UAV-Toolkit.

O projeto foi escrito em Java usando a IDE Netbeans.

## Instalação

Não necessita de instalação, basta baixar o projeto em seu computador e usar. 
Necessita apenas ter o Java JRE instalado uma vez que é uma aplicação Java.

## Como Executar

Para executar este código deve-se executar o seguinte script (localizados na pasta /UAV-Toolkit/Scripts/):

1. ./exec-uav-manager.sh                  (PC)

## Interface do Sistema

Abaixo encontra-se três telas com a GUI do sistema em execução.

| Tela de Instalação do Sistema  | Tela de Execução do Sistema   | Tela com Sistema em Execução  |
|--------------------------------|-------------------------------|-------------------------------|
| ![](../Figures/uav-gcs1.png)   | ![](../Figures/uav-gcs2.png)  | ![](../Figures/uav-gcs3.png)  |

## Arquivos de Entrada

No diretório /UAV-Toolkit/UAV-Manager/ existe um arquivo de propriedades (manager.properties), em que se é definido alguns parâmetros de execução de forma automática (não alterar esse arquivo).
Abaixo estão os principais parâmetros que influenciam o UAV-Manager.

```
MAVPROXY_COMMAND=mavproxy.py --master tcp\:127.0.0.1\:5760 --sitl 127.0.0.1\:5501 --out 127.0.0.1\:14550 --out 127.0.0.1\:14551
GROUND_STATION_APP=QGroundControl.AppImage
GROUND_STATION_DIR=/media/jesimar/Workspace/Drone/GCS/qgroundcontrol
UAV_GCS_DIR=/media/jesimar/Workspace/Work/UAV/UAV-GCS/dist
UAV_GCS_APP=UAV-GCS.jar
UAV_MOSA_APP=UAV-MOSA.jar
UAV_MOSA_DIR=/media/jesimar/Workspace/Work/UAV/UAV-MOSA/dist
SITL_COMMAND=dronekit-sitl copter-3.3 --home\=-22.00587424417797,-47.89874454308930,870,90 --speedup\=1.0
S2DK_APP=init.py
S2DK_DIR=/media/jesimar/Workspace/Work/UAV/UAV-S2DK
UAV_IFA_DIR=/media/jesimar/Workspace/Work/UAV/UAV-IFA/dist
UAV_IFA_APP=UAV-IFA.jar
```

#Authors: Jesimar da Silva Arantes and Andre Missaglia
#Date: 01/06/2017
#Last Update: 15/03/2018
#Description: Code that defines the possible request headers.
#Descricao: Codigo que define os cabecalhos das requisicoes possiveis.

import views

GET_URLS = {
    '/get-gps/': views.getGPS,
    '/get-barometer/': views.getBarometer,
    '/get-battery/': views.getBattery,
    '/get-attitude/': views.getAttitude,
    '/get-velocity/': views.getVelocity,
    '/get-heading/': views.getHeading,
    '/get-groundspeed/': views.getGroundSpeed,
    '/get-airspeed/': views.getAirSpeed,
    '/get-gpsinfo/': views.getGPSInfo,
    '/get-mode/': views.getMode,
    '/get-system-status/': views.getSystemStatus,
    '/get-armed/': views.getArmed,
    '/get-is-armable/': views.getIsArmable,
    '/get-ekf-ok/': views.getEkfOk,
    '/get-home-location/': views.getHomeLocation,
    '/get-parameters/': views.getParameters,
    '/get-distance-to-home/': views.getDistanceToHome,
    '/get-all-sensors/': views.getAllInfoSensors
}

POST_URLS = {
    '/set-waypoint/': views.setWaypoint,
    '/append-waypoint/': views.appendWaypoint,
    '/set-mission/': views.setMission,
    '/append-mission/': views.appendMission,
    '/set-velocity/': views.setVelocity,
    '/set-parameter/': views.setParameter,
    '/set-heading/': views.setHeading,
    '/set-mode/': views.setMode
}

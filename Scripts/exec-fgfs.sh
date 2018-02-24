#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 13/02/2016
#Last Update: 13/02/2016
#Description: Script that runs the FlightGear Flight Simulator (FGFS) software.
#Descrição: Script que executa o software FlightGear Flight Simulator (FGFS).
#Aeronaves: 
    #--aircraft=c172p
    #--aircraft=Rascal110-JSBSim

/usr/games/fgfs --fg-root=/usr/share/games/flightgear --fg-scenery=/usr/share/games/flightgear/Scenery --airport=KSFO --aircraft=c172p --disable-random-objects --prop:/sim/rendering/random-vegetation=false --disable-panel --disable-hud-3d --enable-horizon-effect --enable-enhanced-lighting --disable-ai-models --disable-ai-traffic --enable-real-weather-fetch --enable-fullscreen --prop:/sim/rendering/texture-compression=off --geometry=1280x1024 --bpp=32 --disable-terrasync --disable-fgcom

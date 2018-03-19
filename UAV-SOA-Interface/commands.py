#Authors: Jesimar da Silva Arantes and Andre Missaglia
#Date: 01/06/2017
#Last Update: 15/03/2018
#Description: Code that defines the mavlink commands for drone control.
#Descricao: Codigo que define os comandos mavlink para controle do drone.

from dronekit import Command, mavutil, VehicleMode, LocationGlobalRelative
import time
import math

'''
Comando para decolar ate o waypoint de altitude z.
'''
def takeoff(vehicle, z, cmds):
    arm_and_takeoff(vehicle, z)
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, 
        mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 0, z))

'''
Comando para ir para um waypoint nas coordenadas x, y e z.
'''
def goto(x,y,z, cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, 
        mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, x, y, z))

'''
Comando para pousar no waypoint de coordenadas x e y. Pouso na horizontal.
'''
def land(x,y, cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, 
        mavutil.mavlink.MAV_CMD_NAV_LAND, 0, 0, 0, 0, 0, 0, x, y, 0))

'''
Comando para pousar no waypoint imediatamente abaixo da aeronave. Pouso na vertical.
'''
def landVertical(cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, 
        mavutil.mavlink.MAV_CMD_NAV_LAND, 0, 0, 0, 0, 0, 0, 0, 0, 0))

'''
Comando para fazer retorno para posicao de lancamento ou Return To Launch (RTL).
'''
def rtl(cmds):
    cmds.add(Command(0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, 
        mavutil.mavlink.MAV_CMD_NAV_RETURN_TO_LAUNCH, 0, 0, 0, 0, 0, 0, 0, 0, 0))

'''
Comando para comecar a missao autonoma. 
Necessario caso o drone esteja no solo.
'''
def startmission(vehicle):
    vehicle.mode = VehicleMode("GUIDED") # SE COMENTAR ESSA LINHA NAO FUNCIONA
    vehicle.mode = VehicleMode("AUTO")
    msg = vehicle.message_factory.command_long_encode(
        0, 0,     # target_system, target_component
        mavutil.mavlink.MAV_CMD_MISSION_START,  # command
        0,        # confirmation
        0,        # param 1, yaw in degrees
        0,        # param 2, yaw speed deg/s
        0,        # param 3, direction -1 ccw, 1 cw
        0,        # param 4, relative offset 1, absolute angle 0
        0, 0, 0)  # param 5 ~ 7 not used
    vehicle.send_mavlink(msg)

'''
Comando para definir uma orientacao.
'''
def defineHeading(vehicle, angle, direction, is_relative):
    msg = vehicle.message_factory.command_long_encode(
        0, 0,        # target system, target component
        mavutil.mavlink.MAV_CMD_CONDITION_YAW, #command
        0,           #confirmation
        angle,       # param 1, yaw in degrees
        0,           # param 2, yaw speed deg/s
        direction,   # param 3, direction -1 ccw, 1 cw
        is_relative, # param 4, relative offset 1, absolute angle 0
        0, 0, 0)     # param 5 ~ 7 not used
    vehicle.send_mavlink(msg)

'''    
Comando para retornar a distancia em solo em metros entre os objetos LocationGlobal.
Esta distancia nao considera a dimensao altitude em seu calculo (distancia horizontal apenas)
Este metodo eh uma aproximacao. Nao tem boa precisao para grandes distancias e para distancias proximas aos polos da Terra
'''
def getDistanceMeters(location1, location2):
    if location1 is None:
        return -1
    elif location2 is None:
        return -1
    else:
        dlat = location2.lat - location1.lat
        dlng = location2.lon - location1.lon
        return math.sqrt((dlat*dlat) + (dlng*dlng)) * 1.113195e5

'''
Comando para retornar a distancia em solo em metros para o waypoint atual (corrente).
Caso o waypoint corrente seja do tipo RTL ou LAND_VERTICAL entao a distancia retornada sera None.
Retorna None para o primeiro waypoint (Home location).
'''
def getDistanceToCurrentWaypoint(vehicle):
    nextwaypoint = vehicle.commands.next
    if nextwaypoint==0:
        return None
    if nextwaypoint > vehicle.commands.count:
        vehicle.commands.next = 0
        return None
    missionitem=vehicle.commands[nextwaypoint-1] #commands are zero indexed
    lat = missionitem.x
    lon = missionitem.y
    alt = missionitem.z
    targetLocation = LocationGlobalRelative(lat,lon,alt)
    if targetLocation.lat == 0.0 or targetLocation.lon == 0.0:
        return None
    distancetopoint = getDistanceMeters(vehicle.location.global_frame, targetLocation)
    return distancetopoint

'''
Comando para armar e decolar a aeronave ate a altitude aTargetAltitude.
'''
def arm_and_takeoff(vehicle, aTargetAltitude, wait=True):
    print "Mode: ", vehicle.mode.name
    print "GPS: ", vehicle.gps_0.fix_type
    print "Armed: ", vehicle.armed
    print "Is-Armable: ", vehicle.is_armable
    
    while vehicle.mode.name == "INITIALISING":
        print "Waiting for vehicle mode to initialise ... "
        time.sleep(1)
    
    while vehicle.gps_0.fix_type < 2:
        print "Waiting for GPS >= 2D fix ... ", vehicle.gps_0.fix_type
        time.sleep(1)

    #print "Basic pre-arm checks"
    # Don't let the user try to arm until autopilot is ready
    #while not vehicle.is_armable: #todas essas linhas abaixo desse while estavam comentadas. 
    #    print " Waiting for vehicle to initialise..."
    #    print "Mode: ", vehicle.mode.name
    #    print "GPS: ", vehicle.gps_0.fix_type
    #    time.sleep(1)

    # Copter should arm in GUIDED mode
    # Set mode to GUIDED for arming and takeoff:
    print "Change mode to GUIDED to arm the vehicle"    
    vehicle.mode = VehicleMode("GUIDED")
    while (vehicle.mode.name != "GUIDED"):
        print "Waiting for mode GUIDED ... mode = ", vehicle.mode.name
        vehicle.mode = VehicleMode("GUIDED")
        time.sleep(1)

    print "Arming motors"
    vehicle.armed = True
    while not vehicle.armed:
        print "    Waiting for arming ..."
        vehicle.armed = True
        time.sleep(1)

    print "Taking off!"
    vehicle.simple_takeoff(aTargetAltitude)

    print "Mode: ", vehicle.mode.name
    print "GPS: ", vehicle.gps_0.fix_type
    print "Armed: ", vehicle.armed
    print "Is-Armable: ", vehicle.is_armable
    
    # Wait until the vehicle reaches a safe height before processing the goto 
    # (otherwise the command after Vehicle.simple_takeoff will execute immediately).
    while wait:
        print " Altitude: ", vehicle.location.global_relative_frame.alt
        if vehicle.location.global_relative_frame.alt >= aTargetAltitude * 0.95:
            print "Reached target altitude"
            break
        time.sleep(1)
    
    print "Altitude reach: ", vehicle.location.global_relative_frame.alt

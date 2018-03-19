#Authors: Jesimar da Silva Arantes and Andre Missaglia
#Date: 01/06/2017
#Last Update: 15/03/2018
#Description: Code that initializes the main application.
#Descricao: Codigo que inicia a aplicacao principal.

from server import runServer
from dronekit import connect
from status import getStatus
from readfile import readFileParameters

print '<<<<< UAV-SOA-Interface >>>>'

print '====Read File Parameters===='
parameters = readFileParameters()
param = parameters.split(' ')
HOST_MAVPROXY = param[0]
PORT_MAVPROXY = param[1]
HOST_HTTP = param[2]
PORT_HTTP = param[3]

print 'host_mavp: %s  port_mavp: %s  host_http: %s  port_http: %s' % (HOST_MAVPROXY, PORT_MAVPROXY, HOST_HTTP, PORT_HTTP)

conn = '%s:%s' % (HOST_MAVPROXY, PORT_MAVPROXY)

print '========Connect Drone======='
vehicle = connect(conn, wait_ready=True)

print '=========Get Status========='
getStatus(vehicle)

print '=========Run Server========='
runServer(vehicle, HOST_HTTP, int(PORT_HTTP))

print '======Close Connection======'
vehicle.close()

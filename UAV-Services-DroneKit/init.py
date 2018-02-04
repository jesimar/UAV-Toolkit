from server import runServer
from dronekit import connect
from status import getStatus

print 'connectDrone'
vehicle = connect('127.0.0.1:14551', wait_ready=True)

print 'getStatus'
getStatus(vehicle)

print 'runServer'
runServer(vehicle)

print 'closeConnection'
vehicle.close()

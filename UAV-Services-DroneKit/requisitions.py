import views

GET_URLS = {
    '/get-gps/': views.getGPS,
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
    '/get-is-armable/': views.isArmable,
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

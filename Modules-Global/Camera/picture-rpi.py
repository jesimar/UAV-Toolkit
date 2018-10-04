#Author: Veronica Vannini
#Date: 20/08/2018
#Last Update: 20/08/2018
#Description: Code that take a picture.

from picamera import PiCamera
from time import gmtime, strftime

camera = PiCamera()

camera.resolution = (2592, 1944)

i = strftime("%Y-%m-%d_%H:%M:%S", gmtime())
camera.capture('picutures/picture_%s.jpg' % i)

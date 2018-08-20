#Author: Veronica Vannini
#Date: 20/08/2018
#Last Update: 20/08/2018
#Description: Code that take a picture.

from picamera import PiCamera
from time import sleep
from time import gmtime, strftime

camera = PiCamera()

camera.start_preview()
sleep(5)
i = strftime("%Y-%m-%d_%H:%M:%S", gmtime())
camera.capture('/home/pi/Desktop/picture_%s.jpg' % i)
camera.stop_preview()

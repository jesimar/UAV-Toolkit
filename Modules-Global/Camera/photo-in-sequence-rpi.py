#Author: Jesimar da Silva Arantes
#Date: 20/08/2018
#Last Update: 20/08/2018
#Description: Code that take a photo in sequence.

from picamera import PiCamera
from time import sleep
import sys

number = int(sys.argv[1])
delay = int(sys.argv[2])

camera = PiCamera()

camera.resolution = (2592, 1944)

for i in range(number):
	camera.capture('photo-in-sequence_%s.jpg' % i)
	sleep(delay)

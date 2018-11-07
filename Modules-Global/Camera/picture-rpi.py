#Author: Veronica Vannini and Jesimar da Silva Arantes
#Date: 20/08/2018
#Last Update: 07/11/2018
#Description: Code that take a picture.

from picamera import PiCamera
from time import gmtime, strftime
import sys
import picamera

lat = sys.argv[1]
lng = sys.argv[2]
alt = sys.argv[3]

camera = PiCamera()

camera.resolution = (1366, 768) #(2592, 1944)
camera.annotate_text_size = 18
camera.annotate_background = picamera.Color('black')
camera.annotate_text = 'lat: ' + lat + '   lng: ' + lng + '   alt: ' + alt

i = strftime("%Y-%m-%d_%H:%M:%S", gmtime())
camera.capture('pictures/picture_%s.jpg' % i)

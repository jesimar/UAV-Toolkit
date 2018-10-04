#Author: Jesimar da Silva Arantes
#Date: 20/08/2018
#Last Update: 20/08/2018
#Description: Code that make a video.

from picamera import PiCamera
from time import sleep
from time import gmtime, strftime
import sys

time = int(sys.argv[1])

camera = PiCamera()

camera.resolution = (1920, 1080)
camera.framerate = 15

i = strftime("%Y-%m-%d_%H:%M:%S", gmtime())

camera.start_recording('videos/video_%s.h264' % i)
sleep(time)
camera.stop_recording()

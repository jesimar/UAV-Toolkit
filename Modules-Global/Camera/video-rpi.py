#Author: Jesimar da Silva Arantes
#Date: 20/08/2018
#Last Update: 20/08/2018
#Description: Code that make a video.

from picamera import PiCamera
from time import sleep
from time import gmtime, strftime
import sys

time = int(sys.argv[1])
rate = int(sys.argv[2])

camera = PiCamera()

camera.resolution = (1366, 768)  #(1920, 1080)
camera.framerate = rate

i = strftime("%Y-%m-%d_%H:%M:%S", gmtime())

camera.start_recording('videos/video_%s.h264' % i)
sleep(time*24/rate)
camera.stop_recording()

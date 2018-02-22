import mraa
import time

buzzer = mraa.Gpio(8)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(0)

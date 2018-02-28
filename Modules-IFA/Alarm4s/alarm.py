import mraa
import time

pin = 8
alarm = mraa.Gpio(pin)
alarm.dir(mraa.DIR_OUT)

print("alarm")

while True:
    alarm.write(1)
    time.sleep(1.0)
    alarm.write(0)
    time.sleep(0.5)

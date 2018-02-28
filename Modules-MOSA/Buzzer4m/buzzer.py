import mraa
import time

pin = 8
buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

print("buzzer")

buzzer.write(1)
time.sleep(1.0)
buzzer.write(0)

import mraa
import time

buzzer = mraa.Gpio(8)
buzzer.dir(mraa.DIR_OUT)

print("buzzer")

buzzer.write(1)
time.sleep(1.0)
buzzer.write(0)

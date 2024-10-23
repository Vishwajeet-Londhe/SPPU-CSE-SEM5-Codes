import Adafruit_DHT
import RPi.GPIO as GPIO
import time


GPIO.setmode(GPIO.BOARD)
GPIO.setup(40,GPIO.OUT)

sensor=Adafruit_DHT.DHT11

gpio=3

tt=29
th=60

for i in range(10):
    T,H = Adafruit_DHT.read_retry(sensor,gpio)
    time.sleep(2)
    if H is not None and T is not None:
        print('Temp={0:0.4f}*C Humidity={1:0.04f}%'.format(T,H))
        if H>th:
            print('Humidity has crossed its limits')
            GPIO.output(40,GPIO.HIGH)
            time.sleep(2)
            GPIO.output(40,GPIO.LOW)
            time.sleep(2)
        else:
            print('Humidity within its limit')

        if T>tt:
            print('Temp has crossed its limits')

            GPIO.output(40,GPIO.HIGH)
            time.sleep(2)
            GPIO.output(40,GPIO.LOW)
            time.sleep(2)
        else:
            print('Temp within its limit')

    else:
        print('error')
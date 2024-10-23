import RPi.GPIO  as GPIO
import time
bp = 40 
count = 0 
GPIO.setmode(GPIO.BOARD) 
GPI0.setup(5, GPI0.IN) 
GPIO.setup(11, GPIO.IN) 
GPIO.setup(bp, GPIO.OUT)
while count < 10: 
    enter = GPIO.input(5) 
    exit = GPIO.input(11) 
    if enter == False:
        time.sleep(1) 
        GPIO.output(bp, GPIO.LOW) 
         time.sleep(1)     
     elif exit == False and count > 0: 
       count -= 1 
       print('Human Exited") 
       print (count str(count))   
       GPIO.output(bp, GPIO.HIGH) 
       time.sleep(1) 
       GPIO.output(bp, GPIO.LOW) 
       time.sleep(1)
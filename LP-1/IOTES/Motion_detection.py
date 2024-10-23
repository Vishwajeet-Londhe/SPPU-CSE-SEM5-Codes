import cv2
import numpy as np
from imutils.video import VideoStream
from imutils import resize
import RPi.GPIO as GPIO
import time

Buzzer_pin = 14
GPIO.setmode(GPIO.BCM)
GPIO.setup(Buzzer_pin, GPIO.OUT)

diff_threshold = 1000000

vs = VideoStream(src=0).start()

def getImage():
    im = vs.read()
    im = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY)
    im = cv2.blur(im, (20, 20))
    return im

old_image = getImage()

while True:
    new_image =getImage()
    diff = cv2.absdiff(old_image, new_image)
    print(diff)
    diff_score = np.sum(diff)
    if diff_score > diff_threshold:
        print(diff_score)
        print("Movement Detected")
        GPIO.output(Buzzer_pin, GPIO.HIGH)
        time.sleep(0.5)
        GPIO.output(Buzzer_pin, GPIO.LOW)
        time.sleep(0.5)
      
    old_image= new_image
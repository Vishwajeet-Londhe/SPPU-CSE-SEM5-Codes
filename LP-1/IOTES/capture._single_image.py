from time import sleep
from picamera import PiCamera
import cv2

camera = PiCamera()
camera.resolution = (1024, 768)
camera.start_preview()
# Camera warm-up time
sleep(2)
camera.capture('foo.jpg')
camera.stop_preview()
#Now capturing new image which we'll resize
sleep(2)
camera.start_preview()
# Camera warm-up time
sleep(2)
camera.capture('foo_resized.jpg', resize=(320, 240))
camera.stop_preview()
# to show  captured image
sleep(2)
img1 = cv2.imread("foo.jpg")
cv2.imshow("foo_tmp",img1)
cv2.waitKey(0)
cv2.destroyAllWindows()
face_cascade = cv2.CascadeClassifier('/home/pi/.local/lib/python3.5/site-packages/cv2/data/haarcascade_frontalface_default.xml')
img2 = cv2.imread("foo.jpg")
sleep(2)
gray = cv2.cvtColor(img2,cv2.COLOR_BGR2GRAY)
faces = face_cascade.detectMultiScale(gray,1.1,5)
for(x, y, w, h) in faces:
    cv2.rectangle(img2, (x,y),(x+w, y+h),(255, 0, 0),2)
    cv2.imshow('Faces', img2)
    cv2.waitKey(0)
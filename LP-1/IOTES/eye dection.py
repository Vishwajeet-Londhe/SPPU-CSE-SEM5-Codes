import cv2
import numpy as np
import picamera
import picamera.array
 
def main():
    # Load the face and eye detection cascades
    face_cascade = cv2.CascadeClassifier('/home/pi/.local/lib/python3.5/site-packages/cv2/data/haarcascade_frontalface_default.xml')
    eye_cascade = cv2.CascadeClassifier('/home/pi/.local/lib/python3.5/site-packages/cv2/data/haarcascade_eye.xml')
     # You'll need the XML file for eye detection
 
    with picamera.PiCamera() as camera:
        camera.resolution = (320, 240)
        camera.framerate = 15
 
        with picamera.array.PiRGBArray(camera, size=(320, 240)) as stream:
            for frame in camera.capture_continuous(stream, format='bgr', use_video_port=True):
                image = frame.array
 
                # Convert the image to grayscale for face and eye detection
                gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
 
                # Detect faces
                faces = face_cascade.detectMultiScale(gray, 1.1, 5)
 
                for (x, y, w, h) in faces:
                    cv2.rectangle(image, (x, y), (x+w, y+h), (255, 255, 0), 2)
 
                    # Region of interest in the grayscale image for eyes detection
                    roi_gray = gray[y:y+h, x:x+w]
                    # Region of interest in the color image for drawing rectangles around eyes
                    roi_color = image[y:y+h, x:x+w]
                    
                    # Detect eyes within the region of interest (i.e., the detected face)
                    eyes = eye_cascade.detectMultiScale(roi_gray, 1.03, 5, minSize=(30,30))
                    for (ex, ey, ew, eh) in eyes:
                        cv2.rectangle(roi_color, (ex, ey), (ex+ew, ey+eh), (0, 255, 0), 2)
 
                # Resize the frame for displaying in a larger window
                display_frame = cv2.resize(image, (640, 480))
 
                # Display the frame
                cv2.imshow('Face and Eye Detection', display_frame)
 
                # Clear the stream for the next frame
                stream.truncate(0)
 
                # Close the window when 'q' is pressed
                if cv2.waitKey(1) & 0xFF == ord('q'):
                    break
 
        cv2.destroyAllWindows()
 
if __name__ == '__main__':
    main()
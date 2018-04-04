#include <SoftwareSerial.h>

#define dir1 8
#define dir2 10
#define pwm1 9
#define pwm2 11

#define RXD 8 //아두이노에서는 7
#define TXD 7 //아두이노에서는 8
//http://www.micro4you.com/files/ElecFreaks/Bluetooth%20HC-06.pdf

SoftwareSerial bluetooth(RXD, TXD);
int pwm_value;
byte buffer[20]={0};
int bufferPosition;
char temp[20];

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);
  bufferPosition = 0;
}

void loop() {  
  // phone -> arduino
  if(bluetooth.available()){
    byte data = bluetooth.read();
    //Serial.write(data);
    buffer[bufferPosition++] = data;
    if (data == '\n'){
      buffer[bufferPosition] = '\0';
      //Serial.write(buffer, bufferPosition);
      //Serial.print((char*)buffer);
      bufferPosition = 0;
      strcpy(temp, (char*)buffer);
      Serial.println(temp);
      if (strcmp("on", temp) == true){
        Serial.println("주행");
        digitalWrite(dir1, LOW);
        digitalWrite(dir2, HIGH);
        analogWrite(pwm1, 255);
        analogWrite(pwm2, 255);
      }
    }
    //Serial.write(bluetooth.read());
  }
  // arduino -> phone
  if(Serial.available()){
    bluetooth.write(Serial.read());
  }
}

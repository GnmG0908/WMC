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
char buffer[20]={};
int bufferPosition;
int cnt =0;

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);

}

void loop() {  
  // phone -> arduino
  if(bluetooth.available()){
    char data = bluetooth.read();
    //Serial.write(data);
    buffer[cnt] = data;
    cnt++;
    }
    
      /*if(data == '\n'){
      buffer[cnt] = '\0';
      Serial.write(buffer, cnt);
      cnt = 0;*/
      if (!strcmp(buffer,"on")){
        Serial.println("주행");
        digitalWrite(dir1, LOW);
        digitalWrite(dir2, HIGH);
        analogWrite(pwm1, 255);
        analogWrite(pwm2, 255);
      }
   // arduino -> phone
  if(Serial.available()){
    bluetooth.write(Serial.read());
  }  
    
    }
    //Serial.write(bluetooth.read());
  
 

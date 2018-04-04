#include <SoftwareSerial.h>
#define RXD 8 //아두이노에서는 7
#define TXD 7 //아두이노에서는 8
//http://www.micro4you.com/files/ElecFreaks/Bluetooth%20HC-06.pdf
SoftwareSerial bluetooth(RXD, TXD);

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
}

void loop() {
  // phone -> arduino
  if(bluetooth.available()){
    Serial.write(bluetooth.read());
  }
  // arduino -> phone
  if(Serial.available()){
    bluetooth.write(Serial.read());
  }
}

#include <SoftwareSerial.h>
#define RXD 5 //아두이노에서는 6
#define TXD 6 //아두이노에서는 5
//http://www.micro4you.com/files/ElecFreaks/Bluetooth%20HC-06.pdf
SoftwareSerial bluetooth(RXD, TXD);

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);
}

void loop() {
  String str = "";
  char data;
  // phone -> arduino
  while (bluetooth.available()){
    data = bluetooth.read();
    str.concat(data);
    delay(10);
  }
  if (str != "")
    Serial.print(str);
}

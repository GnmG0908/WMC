#include <Wire.h>
#include <SoftwareSerial.h>
// 마스터
#define SLAVE 1
// 블루투스
#define RXD 5 //아두이노에서는 6
#define TXD 6 //아두이노에서는 5
SoftwareSerial bluetooth(RXD, TXD);

String readSerial(){
  String str = "";
  char data;
  while (bluetooth.available()){
    data = bluetooth.read();
    str.concat(data);
    delay(10);
  }
  if (str != ""){
    Serial.print(str);
  }
  return str;
}

void setup() {
  Wire.begin();
  Serial.begin(9600);
  bluetooth.begin(9600);
}

void loop() {
  if (bluetooth.available()){
    String str = readSerial();
    
    Wire.beginTransmission(SLAVE);
    Wire.print(str);
    Wire.endTransmission();
    
    delay(10);
    receivedata(); 
    delay(10);
  }
}

void receivedata(){
  Wire.requestFrom(SLAVE, 15);
  for (int i=0;i<15;i++){
    char e = Wire.read();
    Serial.print(e);
  }
  Serial.println();
}

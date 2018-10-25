#include <SoftwareSerial.h>
//(T, R)
SoftwareSerial bluetooth1(2,3);
//SoftwareSerial bluetooth2(4,5);
//SoftwareSerial bluetooth3(6,7);

void setup() {
  Serial.begin(9600);
  bluetooth1.begin(9600);
  //bluetooth2.begin(9600);
  //bluetooth3.begin(9600);
}

void loop() {
  while (bluetooth1.available()){
    byte data1 = bluetooth1.read();
    //byte data2 = bluetooth2.read();
    //byte data3 = bluetooth3.read();
    Serial.write(data1);
    //Serial.write(data2);
    //Serial.write(data3);
  }
  while (Serial.available()){
    byte data = Serial.read();
    bluetooth1.write(data);
    //bluetooth2.write(data);
    //bluetooth3.write(data);
  }
}

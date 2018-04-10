#include <Wire.h>

#define SLAVE 1

String readSerial(){
  String str = "";
  char data;
  while (Serial.available()){
    data = Serial.read();
    str.concat(data);
    delay(10);
  }
  if (str != ""){
    Serial.println(str);
  }
  return str;
}

void setup() {
  Wire.begin();
  Serial.begin(9600);
}

void loop() {
  if (Serial.available()){
    String str = readSerial();
    //char* charString;
    //str.toCharArray(charString, str.length());
    
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

#include <Wire.h>

#define SLAVE 1

void setup() {
  Wire.begin();
  Serial.begin(9600);
}

void loop() {
  if (Serial.available()) {
    char c = Serial.read();
    Wire.beginTransmission(SLAVE);
    Wire.write(c);
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

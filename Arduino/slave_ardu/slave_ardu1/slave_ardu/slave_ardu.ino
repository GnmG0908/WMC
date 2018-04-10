#include <Wire.h>

#define SLAVE 1

void receiveFromMaster(int bytes) {
  for (int i = 0 ; i < bytes ; i++) {
    char c = Wire.read();
    Serial.print(c);
  }
}

void sendToMaster() {
  Wire.write("SLAVE -> MASTER");
}

void setup() {
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
}

void loop () {
}


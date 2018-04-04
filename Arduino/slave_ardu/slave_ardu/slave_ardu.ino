#include <Wire.h>

#define SLAVE 1

void setup() {
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
}

void loop () {
}

void receiveFromMaster(int bytes) {
  for (int i = 0 ; i < bytes ; i++) {
    char c = Wire.read();
    Serial.print(c);
  }
  Serial.println();
}

void sendToMaster() {
  Wire.write("SLAVE -> MASTER");
}


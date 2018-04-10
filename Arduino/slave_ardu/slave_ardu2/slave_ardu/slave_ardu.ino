#include <Wire.h>

#define SLAVE 1

String str = "";

void receiveFromMaster(int bytes) {
  char c;
  for (int i = 0 ; i < bytes ; i++) {
    c = Wire.read();
    str.concat(c);
    Serial.print(c);
  }
  Serial.println();
  active();
  str = "";
}

void active(){
  if (str == "go"){
    go();
  }
}

void go(){
  Serial.println("전진 중");
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


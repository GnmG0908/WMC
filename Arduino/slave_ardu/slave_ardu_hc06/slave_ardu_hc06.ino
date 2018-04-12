#include <Wire.h>

#define SLAVE 1

#define dir1 8
#define pwm1 9
#define dir2 10
#define pwm2 11

String str = "";
int pwm_value;

void receiveFromMaster(int bytes) {
  char c;
  for (int i = 0 ; i < bytes ; i++) {
    c = Wire.read();
    str.concat(c);
  }
  Serial.print(str);
  active();
  str = "";
}

void active(){
  if (str == "F\n"){
    go();
  } else if (str == "L\n"){
    left();
  } else if (str == "R\n"){
    right();
  } else if (str == "stop\n"){
    stopm();
  }
}

void go(){
  Serial.println("직진");
  digitalWrite(dir1, LOW);
  digitalWrite(dir2, HIGH);
  analogWrite(pwm1, 255);
  analogWrite(pwm2, 255);
  delay(500);
}

void left(){
  Serial.println("좌회전");
  digitalWrite(dir1, LOW);
  digitalWrite(dir2, HIGH);
  analogWrite(pwm1, 0);
  analogWrite(pwm2, 255);
  delay(500);
}

void right(){
  Serial.println("우회전");
  digitalWrite(dir1, LOW);
  digitalWrite(dir2, HIGH);
  analogWrite(pwm1, 255);
  analogWrite(pwm2, 0);
  delay(500);
}

void stopm(){
  Serial.println("정지");
  digitalWrite(dir1, LOW);
  digitalWrite(dir2, HIGH);
  analogWrite(pwm1, 0);
  analogWrite(pwm2, 0);
  delay(500);
}

void sendToMaster() {
  Wire.write("SLAVE -> MASTER");
}

void setup() {
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);
}

void loop () {
}


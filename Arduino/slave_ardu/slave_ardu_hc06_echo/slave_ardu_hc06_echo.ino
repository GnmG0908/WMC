#include <Wire.h>
// 슬레이브
#define SLAVE 1
// 좌상 초음파
#define echoPinLU 2
#define trigPinLU 3
// 우상 초음파
#define echoPinRU 4
#define trigPinRU 5
// 좌측 모터
#define dir1 10
#define pwm1 11
// 우측 모터
#define dir2 12
#define pwm2 13

// 변수
String str = "";
long duration;
float distance;

// 마스터로부터 텍스트 수신
void receiveFromMaster(int bytes) {
  char c;
  for (int i = 0 ; i < bytes ; i++) {
    c = Wire.read();
    str.concat(c);
  }
  active();
  str = "";
}

// 좌상 초음파 측정
boolean echoLU(){
  digitalWrite(trigPinLU, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinLU, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinLU, LOW);
  duration = pulseIn(echoPinLU, HIGH);
  distance = (duration/2) / 29.1;
  if(distance < 70){
    Serial.println("LU false");
    return false;
  }
  else{
    Serial.print("LU : ");
    Serial.print(distance);
    Serial.println(" cm");
    return true;
  }
}
// 우상 초음파 측정
boolean echoRU(){
  digitalWrite(trigPinRU, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinRU, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinRU, LOW);
  duration = pulseIn(echoPinRU, HIGH);
  distance = (duration/2) / 29.1;
  if(distance < 70){
    Serial.println("RU false");
    return false;
  }
  else{
    Serial.print("RU : ");
    Serial.print(distance);
    Serial.println(" cm");
    return true;
  }
}
// 동작
void active(){
  if (str == "F\n"){
    go();
  } else if (str == "L\n"){
    left();
  } else if (str == "R\n"){
    right();
  } else if (str == "HL\n"){
    hleft();
  } else if (str == "HR\n"){
    hright();
  } else if (str == "stop\n"){
    stopm();
  }
}
// 전진
void go(){
  bool LU = echoLU();
  bool RU = echoRU();
  if((LU == true) && (RU == true)){
    Serial.println("직진");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 255);
  }
  else {
    stopm();
  }
}
// 좌회전
void left(){
  bool LU = echoLU();
  bool RU = echoRU();
  if((LU == true) && (RU == true)){
    Serial.println("좌회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 255);
  }
  else{
    stopm();
  }
}
// 우회전
void right(){
  bool LU = echoLU();
  bool RU = echoRU();
  if((LU == true) && (RU == true)){
    Serial.println("우회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 0);
  }
  else{
    stopm();
  }
}
// 수동 좌회전
void hleft(){
  bool LU = echoLU();
  bool RU = echoRU();
  if((LU == true) && (RU == true)){
    Serial.println("좌회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 255);
  }
  else{
    stopm();
  }
}
// 수동 우회전
void hright(){
  bool LU = echoLU();
  bool RU = echoRU();
  if((LU == true) && (RU == true)){
    Serial.println("우회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 0);
  }
  else{
    stopm();
  }
}
// 정지
void stopm(){
  Serial.println("정지");
  digitalWrite(dir1, LOW);
  digitalWrite(dir2, HIGH);
  analogWrite(pwm1, 0);
  analogWrite(pwm2, 0);
}

// 마스터로 텍스트 송신
void sendToMaster() {
  Wire.write("SLAVE -> MASTER");
}

void setup() {
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);
  pinMode(trigPinRU, OUTPUT);
  pinMode(echoPinRU, INPUT);
  pinMode(trigPinLU, OUTPUT);
  pinMode(echoPinLU, INPUT);
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
}

void loop () {
}


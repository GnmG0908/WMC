#include <Wire.h>
// 슬레이브
#define SLAVE 1
// 좌상 초음파
#define echoPinLU 2
#define trigPinLU 3
// 우상 초음파
#define echoPinRU 4
#define trigPinRU 5
// 좌하 초음파
#define echoPinLD 6
#define trigPinLD 7
// 우하 초음파
#define echoPinRD 8
#define trigPinRD 9
// 좌측 모터
#define dir1 10
#define pwm1 11
// 우측 모터
#define dir2 12
#define pwm2 13

// 변수
String str = "";
//int pwm_value;
long duration;
float distance;

// 마스터로부터 텍스트 수신
void receiveFromMaster(int bytes) {
  char c;
  for (int i = 0 ; i < bytes ; i++) {
    c = Wire.read();
    str.concat(c);
  }
  //Serial.print(str);
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
  if(distance < 100){
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
  if(distance < 100){
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
/*
// 좌하 초음파 측정
boolean echoLD(){
  digitalWrite(trigPinLD, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinLD, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinLD, LOW);
  duration = pulseIn(echoPinLD, HIGH);
  distance = (duration/2) / 29.1;
  if(((distance >= 10) && (distance <= 200)) || (distance >= 2800)){
    Serial.print("LD : ");
    Serial.print(distance);
    Serial.println(" cm");
    return true;
  }
  else{
    Serial.print("LD : ");
    Serial.print(distance);
    Serial.print(" cm");
    Serial.println("   LD false");
    return false;
  }
}
// 우하 초음파 측정
boolean echoRD(){
  digitalWrite(trigPinRD, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinRD, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinRD, LOW);
  duration = pulseIn(echoPinRD, HIGH);
  distance = (duration/2) / 29.1;
  if(((distance >= 10) && (distance <= 200)) || (distance >= 2800)){
    Serial.print("RD : ");
    Serial.print(distance);
    Serial.println(" cm");
    return true;
  }
  else{
    Serial.print("RD : ");
    Serial.print(distance);
    Serial.print(" cm");
    Serial.println("   RD false");
    return false;
  }
}
*/
// 동작
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
// 전진
void go(){
  //bool LD = echoLD();
  //bool RD = echoRD();
  bool LU = echoLU();
  bool RU = echoRU();
  //if ((LU == true) && (RU == true) && (LD == true) && (RD == true)){
  if((LU == true) && (RU == true)){
    Serial.println("직진");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 255);
    //delay(200); 
  }
  else {
    stopm();
  }
}
// 좌회전
void left(){
  //bool LD = echoLD();
  //bool RD = echoRD();
  bool LU = echoLU();
  bool RU = echoRU();
  //if ((LU == true) && (RU == true) && (LD == true) && (RD == true)){
  if((LU == true) && (RU == true)){
    Serial.println("좌회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 255);
    //delay(200); 
  }
  else{
    stopm();
  }
}
// 우회전
void right(){
  //bool LD = echoLD();
  //bool RD = echoRD();
  bool LU = echoLU();
  bool RU = echoRU();
  //if ((LU == true) && (RU == true) && (LD == true) && (RD == true)){
  if((LU == true) && (RU == true)){
    Serial.println("우회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 0);
    //delay(200); 
  }
  else{
    stopm();
  }
}
// 정지
void stopm(){
  //bool LU = echoLU();
  //bool RU = echoRU();
  //bool LD = echoLD();
  //bool RD = echoRD();
  //if ((LU == false) || (RU == false) || (LD == false) || (RD == false)){
    Serial.println("정지");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 0);
    //delay(200);
  //}
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
  //pinMode(trigPinRD, OUTPUT);
  //pinMode(echoPinRD, INPUT);
  //pinMode(trigPinLD, OUTPUT);
  //pinMode(echoPinLD, INPUT);
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
}

void loop () {
}


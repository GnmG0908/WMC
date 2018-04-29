#include <Wire.h>
// 슬레이브
#define SLAVE 1
// 측 모터
#define dir1 10
#define pwm1 11
// 측 모터
#define dir2 12
#define pwm2 13
// 좌상 초음파
#define echoPinLU 2
#define trigPinLU 3
// 우상 초음파
#define echoPinRU 4
#define trigPinRU 5

// 변수들
String str = "";
//int pwm_value;
long durationLU, durationRU;
float distanceLU, distanceRU;

// 마스터로부터 텍스트 수신
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
/*
// 좌상 초음파 측정
boolean echoLU(){
  digitalWrite(trigPinLU, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinLU, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinLU, LOW);
  durationLU = pulseIn(echoPinLU, HIGH);
  distanceLU = (durationLU/2) / 29.1;
  if(distanceLU < 100){
    return false;
  }
  else{
    Serial.print(distanceLU);
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
  durationRU = pulseIn(echoPinRU, HIGH);
  distanceRU = (durationRU/2) / 29.1;
  if(distanceRU < 100){
    return false;
  }
  else{
    Serial.print(distanceRU);
    Serial.println(" cm");
    return true;
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
  //bool LU = echoLU();
  //bool RU = echoRU();
  //if ((LU == true) && (RU == true)){
    Serial.println("직진");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 255);
    delay(200); 
  //}
}

// 좌회전
void left(){
  //bool LU = echoLU();
  //bool RU = echoRU();
  //if ((LU == true) && (RU == true)){
    Serial.println("좌회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 255);
    delay(200);
  //}
}

// 우회전
void right(){
  //bool LU = echoLU();
  //bool RU = echoRU();
  //if ((LU == true) && (RU == true)){
    Serial.println("우회전");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 0);
    delay(200);
  //}
}

// 정지
void stopm(){
  //bool LU = echoLU();
  //bool RU = echoRU();
  //if ((LU == false) && (RU == false)){
    Serial.println("정지");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 0);
    delay(200);
  //}
}

// 마스터로 텍스트 송신
void sendToMaster() {
  Wire.write("SLAVE -> MASTER");
}

void setup() {
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);
  //pinMode(trigPinRU, OUTPUT);
  //pinMode(echoPinRU, INPUT);
  //pinMode(trigPinLU, OUTPUT);
  //pinMode(echoPinLU, INPUT);
  Wire.begin(SLAVE);
  Wire.onReceive(receiveFromMaster);
  Wire.onRequest(sendToMaster);
  Serial.begin(9600);
}

void loop () {
}


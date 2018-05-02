#include <Wire.h>
#include <SoftwareSerial.h>
// 마스터
#define SLAVE 1
// 좌측 초음파
#define echoPinL 2
#define trigPinL 3
// 우측 초음파
#define echoPinR 4
#define trigPinR 5
// 블루투스
#define RXD 6 //아두이노에서는 7
#define TXD 7 //아두이노에서는 6
SoftwareSerial bluetooth(RXD, TXD);

// 변수
long duration;
float distance;
bool LR;

// 블루투스로부터 텍스트 수신
String readSerial(){
  String str = "";
  char data;
  while (bluetooth.available()){
    data = bluetooth.read();
    str.concat(data);
    delay(10);
  }
  /*if (str != ""){
    Serial.print(str);
  }*/
  return str;
}

// 슬레이브로부터 텍스트 수신
void receivedata(){
  Wire.requestFrom(SLAVE, 15);
  for (int i=0;i<15;i++){
    char e = Wire.read();
    Serial.print(e);
  }
  Serial.println();
}

// 좌측 초음파 측정
boolean echoL(){
  digitalWrite(trigPinL, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinL, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinL, LOW);
  duration = pulseIn(echoPinL, HIGH);
  distance = (duration/2) / 29.1;
  Serial.print("L : ");
  Serial.println(distance);
  if(distance <= 30){
    return false;
  }
  else{
    return true;
  }
}
// 우측 초음파 측정
boolean echoR(){
  digitalWrite(trigPinR, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinR, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPinR, LOW);
  duration = pulseIn(echoPinR, HIGH);
  distance = (duration/2) / 29.1;
  Serial.print("R : ");
  Serial.println(distance);
  if(distance <= 30){
    return false;
  }
  else{
    return true;
  }
}

void setup() {
  Wire.begin();
  Serial.begin(9600);
  bluetooth.begin(9600);
  pinMode(trigPinL, OUTPUT);
  pinMode(echoPinL, INPUT);
  pinMode(trigPinR, OUTPUT);
  pinMode(echoPinR, INPUT);
}

void loop() {
  if (bluetooth.available()){
    String str = readSerial();

    if(str == "L\n"){
      LR = echoL();
      if(LR == false){
        str = "F\n";
      }
    }
    else if(str == "R\n"){
      LR = echoR();
      if(LR == false){
        str = "F\n";
      }
    }
    Serial.println(str);
    Wire.beginTransmission(SLAVE);
    Wire.print(str);
    Wire.endTransmission();
    
    delay(10);
    receivedata(); 
    delay(10);
  }
}

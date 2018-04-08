//#include <SoftwareSerial.h>

#define dir1 8
//#define dir2 10
#define pwm1 9
//#define pwm2 11

//#define RXD 8 //아두이노에서는 7
//#define TXD 7 //아두이노에서는 8
//http://www.micro4you.com/files/ElecFreaks/Bluetooth%20HC-06.pdf

//SoftwareSerial bluetooth(RXD, TXD);
int pwm_value;

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
  Serial.begin(9600);
  //bluetooth.begin(9600);
  pinMode(pwm1, OUTPUT);
  //pinMode(pwm2, OUTPUT);
}

void loop(){
  String str = readSerial();
  if (str == "on"){
    Serial.println("전진");
    digitalWrite(dir1, HIGH);
    analogWrite(pwm1, 255);
    delay(5000);
    analogWrite(pwm1, 0);
  }
}
/*
void loop() {  
  // phone -> arduino
  //if(bluetooth.available()){
  if (Serial.available()){
    while ((data = Serial.read()) != '\n'){
      //char data = Serial.read();
      //char data = bluetooth.read();
      buffer[bufferPosition] = data;
      bufferPosition++;
      Serial.print(data);
    }
    //buffer[bufferPosition] = '\0';
  }
  
  if (!strcmp(buffer, "on")){
    Serial.println("주행");
    digitalWrite(dir1, LOW);
    //digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    //analogWrite(pwm2, 255);
  }
  bufferPosition = 0;
  memset(buffer, 0, sizeof(buffer));
  
  // arduino -> phone
  if(Serial.available()){
    bluetooth.write(Serial.read());
  }  
}*/

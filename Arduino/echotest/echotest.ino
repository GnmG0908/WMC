/*
#define trigPin 13
#define echoPin 12

void setup(){
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}

void loop(){
  long duration, distance;
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;

  if(distance >= 200 || distance <= 0){
    Serial.println("거리를 측정할 수 없음");
  }
  else{
    Serial.print(distance);
    Serial.println(" cm");
  }
  delay(100);
}
*/

#define trigPin1 13
#define echoPin1 12
#define trigPin2 11
#define echoPin2 10

void setup(){
  Serial.begin(9600);
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
}

void loop(){
  long duration1, duration2;
  float distance1, distance2;
  
  digitalWrite(trigPin1, LOW);
  digitalWrite(echoPin1, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin1, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin1, LOW);
  duration1 = pulseIn(echoPin1, HIGH);
  
  digitalWrite(trigPin2, LOW);
  digitalWrite(echoPin2, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin2, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin2, LOW);
  duration2 = pulseIn(echoPin2, HIGH);

  distance1 = duration1 / 29.0 / 2.0;
  distance2 = duration2 / 29.0 / 2.0;  

  if(distance1 <= 100 && distance2 <= 100){
    Serial.println("정지");
  }
  else{
    Serial.print("1번 초음파");
    Serial.print(distance1);
    Serial.println(" cm");
    Serial.print("2번 초음파");
    Serial.print(distance2);
    Serial.println(" cm");
  }
  delay(400);
}


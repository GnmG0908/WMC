#define dir2 8
#define pwm2 9
#define trigPin 13
#define echoPin 12
int pwm_value;
long duration, distance;

void setup() {
  pinMode(pwm2, OUTPUT);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  digitalWrite(dir2, HIGH);
  //analogWrite(pwm2, 200);

  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;

  if(distance >= 200 || distance <= 10){
    Serial.println("거리를 측정할 수 없음");
    analogWrite(pwm2, 0);
  }
  else{
    Serial.print(distance);
    Serial.println(" cm");
    analogWrite(pwm2, 255);
  }
  delay(500);
}

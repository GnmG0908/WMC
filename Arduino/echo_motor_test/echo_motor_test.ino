#define dir1 8
#define dir2 10
#define pwm1 9
#define pwm2 11
#define trigPin 12
#define echoPin 13
int pwm_value;
long duration, distance;

void setup() {
  pinMode(pwm1, OUTPUT);
  pinMode(pwm2, OUTPUT);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  Serial.begin(9600);
}

void loop() {
  //digitalWrite(dir1, HIGH);
  //digitalWrite(dir2, HIGH);

  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = (duration/2) / 29.1;

  if(distance <= 150){
    Serial.println("정지");
    analogWrite(pwm1, 0);
    analogWrite(pwm2, 0);
  }
  else{
    Serial.print(distance);
    Serial.println(" cm");
    digitalWrite(dir1, LOW);
    digitalWrite(dir2, HIGH);
    analogWrite(pwm1, 255);
    analogWrite(pwm2, 255);
  }
  delay(400);
}

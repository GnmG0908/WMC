#define dir2 8
#define pwm2 9
int pwm_value;

void setup() {
  pinMode(pwm2, OUTPUT);
//  pinMode(dir2, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  digitalWrite(dir2, HIGH);
  analogWrite(pwm2, 100);
  delay(3000);
  digitalWrite(dir2, LOW);
  analogWrite(pwm2, 200);
  delay(3000);
}

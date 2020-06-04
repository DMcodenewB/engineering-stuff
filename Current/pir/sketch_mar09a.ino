#include <Wire.h>
#include <LCD.h>
#include <LiquidCrystal.h>
#include <LiquidCrystal_I2C.h>


#define BUTTON_RED 2
#define BUTTON_GREEN 4

#define LED_RED 6
#define LED_GREEN 5

LiquidCrystal_I2C lcd(0x27, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

short redCount = 0;
short greenCount = 0;


void setup() {
  // put your setup code here, to run once;
  pinMode(BUTTON_RED, INPUT_PULLUP);
  pinMode(BUTTON_GREEN, INPUT_PULLUP);

  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);

  lcd.begin(16,2);
  lcd.clear();
}

void loop() {
  // put your main code here, to run repeatedly:
  lcd.print("Czerwona: ");
  lcd.print(redCount);
  lcd.setCursor(0,1);
  lcd.print("Zielona: ");
  lcd.print(greenCount);
  
  if(digitalRead(BUTTON_RED) == LOW){
    digitalWrite(LED_RED, HIGH);
    redCount += 1;
    lcd.setCursor(10,0);
    lcd.print(redCount);
  }
  else digitalWrite(LED_RED, LOW);

  if(digitalRead(BUTTON_GREEN) == LOW){
    digitalWrite(LED_GREEN, HIGH);
    greenCount += 1;
    lcd.setCursor(9,1);
    lcd.print(greenCount);
  }
  else digitalWrite(LED_GREEN, LOW);

  
  delay(200);
  lcd.clear();
}

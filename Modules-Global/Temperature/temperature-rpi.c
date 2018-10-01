/**
* Author: Jesimar da Silva Arantes
* Date: 01/07/2018
* Last Update: 30/09/2018
* Description: Code reading the temperature sensor.
* Descricao: Codigo que faz a leitura do sensor de temperatura. 
*/

#include <wiringPi.h>
#include <stdlib.h>
#include <stdio.h>

#define CLK 5  //PIN SCK
#define DBIT 6 //PIN SO
#define CS 7   //PIN CS

int Thermal_Couple_Read(void);

int main() {
	if (wiringPiSetup () == -1){
		exit(1);
	}
	pinMode(CLK, OUTPUT);
	pinMode(DBIT, INPUT);
	pinMode(CS, OUTPUT);
	digitalWrite(CS, HIGH);
	digitalWrite(CLK, LOW);
	
	while(1){
		int sensorValue = Thermal_Couple_Read();
		if (sensorValue == -1) {
			printf("NO-SENSOR-CONNECTED\n");
		} else {
			float Ctemp = sensorValue * 0.25;
			printf("%4.2f\n", Ctemp);
		}
		delay(500);
	}
	return 0;
}

int Thermal_Couple_Read() {
	int value = 0;
	// init sensor
	digitalWrite(CS, LOW);
	delay(2);
	digitalWrite(CS, HIGH);
	delay(200);

	/* Read the chip and return the raw temperature value
	Bring CS pin low to allow us to read the data from
	the conversion process */

	digitalWrite(CS, LOW);
	/* Cycle the clock for dummy bit 15 */
	digitalWrite(CLK, HIGH);
	// delay(1);
	digitalWrite(CLK, LOW);

	/*
	Read bits 14-3 from MAX6675 for the Temp.
	Loop for each bit reading
	the value and storing the final value in 'temp' */

	int i;
	for (i = 14; i >= 0; i--) {
		digitalWrite(CLK, HIGH);
		// delay(1);
		value += digitalRead(DBIT) << i;
		digitalWrite(CLK, LOW);
	}

	// check bit D2 if HIGH no sensor
	if ((value & 0x04) == 0x04) return -1;
	// shift right three places
	return value >> 3;
}

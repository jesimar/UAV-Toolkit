/**
* Author: Jesimar da Silva Arantes
* Date: 03/01/2018
* Last Update: 16/03/2018
* Description: Code monitors the drone's current position.
* Descricao: Codigo monitora a posicao atual do drone.
*/

//============================USED LIBRARIES============================

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <curl/curl.h>

//=========================STRUCTS and TYPEDEF==========================

typedef struct Data {
	char *memory;
	size_t size;
}data;

typedef struct GPS{
	double lat;
	double lng;
}gps;

typedef struct Barometer{
	double alt_rel;//altitude in m
	double alt_abs;//altitude in m
}barometer;

//=========================FUNCTION PROTOTYPES==========================

void posAnalyser(float freq_hertz, gps *vgps, barometer *vbaro);
void getGPS(gps *vgps);
void getBarometer(barometer *vbaro);
void printGPS(gps vgps, barometer vbaro);
static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp);

//==============================FUNCTIONS===============================

/**
 * Metodo principal para execucao do uav-pos-analyser.
 */
int main(){
	float freq_hertz = 1.0;	//hertz
	gps vgps;
	barometer vbaro;
	printf("UAV-POSITIONS-ANALYSER\n");
	posAnalyser(freq_hertz, &vgps, &vbaro);
	return 0;
}

/**
 * Metodo que faz a captura das informações de GPS e Barometro e imprime na tela.
 */
void posAnalyser(float freq_hertz, gps *vgps, barometer *vbaro){
	while(true){
		sleep(1.0/freq_hertz);
		getGPS(vgps);
		getBarometer(vbaro);
		printGPS(*vgps, *vbaro);
	}
}

/**
 * Faz uma requisição HTTP - GET para obter informações do GPS (lat, lng).
 */
void getGPS(gps *vgps) {
	CURL *curl;
	data chunk;
	chunk.memory = malloc(1);
	chunk.size = 0;
	CURLcode res;
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:50000/get-gps/");
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);
		res = curl_easy_perform(curl);
		if(res != CURLE_OK){
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		}
		curl_easy_cleanup(curl);
		sscanf(chunk.memory, "{\"gps\": [%lf,%lf]", &vgps->lat, &vgps->lng);
	}
	free(chunk.memory);
	curl_global_cleanup();
}

/**
 * Faz uma requisição HTTP - GET para obter informações do Barometro (alt_rel, alt_abs).
 */
void getBarometer(barometer *vbaro) {
	CURL *curl;
	data chunk;
	chunk.memory = malloc(1);
	chunk.size = 0;
	CURLcode res;
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:50000/get-barometer/");
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);
		res = curl_easy_perform(curl);
		if(res != CURLE_OK){
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		}
		curl_easy_cleanup(curl);
		sscanf(chunk.memory, "{\"barometer\": [%lf,%lf]", &vbaro->alt_rel, &vbaro->alt_abs);
	}
	free(chunk.memory);
	curl_global_cleanup();
}

/**
 * Imprime informações de GPS e Barometro de forma formatada.
 */
void printGPS(gps vgps, barometer vbaro){
	printf("      gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]\n", vgps.lat, vgps.lng, vbaro.alt_rel, vbaro.alt_abs);
}

/**
 * Funcao que aloca os dados lidos atraves do metodo HTTP GET.
 */
static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp){
	size_t realsize = size * nmemb;
	data *mem = (data *)userp;
	mem->memory = realloc(mem->memory, mem->size + realsize + 1);
	if(mem->memory == NULL) {
		printf("not enough memory (realloc returned NULL)\n");
		return 0;
	}
	memcpy(&(mem->memory[mem->size]), contents, realsize);
	mem->size += realsize;
	mem->memory[mem->size] = 0;
	return realsize;
}

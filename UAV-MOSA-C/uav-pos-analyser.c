#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <curl/curl.h>

typedef struct Data {
	char *memory;
	size_t size;
}data;

typedef struct GPS{
	double lat;
	double lng;
	double alt_rel;//altitude in m
	double alt_abs;//altitude in m
}gps;

void posAnalyser(float freq_hertz, gps *vgps);
void getGPS2(gps *vgps);
void printGPS(gps vgps);
static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp);

int main(){
	float freq_hertz = 1.0;	//hertz
	gps vgps;
	printf("UAV-POSITIONS-ANALYSER\n");
	posAnalyser(freq_hertz, &vgps);
	return 0;
}

void posAnalyser(float freq_hertz, gps *vgps){
	while(true){
		sleep(1.0/freq_hertz);
		getGPS2(vgps);
		printGPS(*vgps);
	}
}

void getGPS2(gps *vgps) {
	CURL *curl;
	data chunk;
	chunk.memory = malloc(1);
	chunk.size = 0;
	CURLcode res;
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:50000/gps/");
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);
		res = curl_easy_perform(curl);
		if(res != CURLE_OK){
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		}
		curl_easy_cleanup(curl);
		sscanf(chunk.memory, "{\"gps\": [%lf,%lf,%lf,%lf]", &vgps->lat, &vgps->lng, &vgps->alt_rel, &vgps->alt_abs);
	}
	free(chunk.memory);
	curl_global_cleanup();
}

void printGPS(gps vgps){
	printf("      gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]\n", vgps.lat, vgps.lng, vgps.alt_rel, vgps.alt_abs);
}

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

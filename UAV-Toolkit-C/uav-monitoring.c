/**
* Author: Jesimar da Silva Arantes
* Date: 03/01/2018
* Last Update: 16/03/2018
* Description: Code monitors a set of aircraft sensors.
* Descricao: Codigo monitora um conjunto de sensores da aeronave.
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

typedef struct Battery{
	double voltage;//in millivolts
	double current;//in 10 * milliamperes
	double level;  //in %
}battery;

typedef struct Attitude{
	double pitch;
	double yaw;
	double roll;
}attitude;

typedef struct Velocity{
	double vx;//velocity in m/s
	double vy;//velocity in m/s
	double vz;//velocity in m/s
}velocity;

typedef struct GPSInfo{
	int fixType;			//0-1: no fix, 2: 2D fix, 3: 3D fix
	int satellitesVisible;	//numero de satelites visiveis
	int eph;				//GPS horizontal dilution of position (HDOP)
	int epv;				//GPS vertical   dilution of position (VDOP)
}gpsinfo;

typedef struct SensorUAV{
	double heading;			//in degrees
	double groundspeed;		//in m/s
	double airspeed;		//in m/s
}sensoruav;

typedef struct StatusUAV{
	char mode[20];
	char systemStatus[20];
	bool armed;
	bool isArmable;    
	bool ekfOk;
}statusuav;

//=========================FUNCTION PROTOTYPES==========================

void monitoring(float freq_hertz, gps *vgps, barometer *vbaro, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav);
void getAllInfo(gps *vgps, barometer *vbaro, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav);
void printAllInfo(gps vgps, barometer vbaro, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav);
void fprintAllInfo(FILE *f, gps vgps, barometer vbaro, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav);
static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp);

//==============================FUNCTIONS===============================

/**
 * Metodo principal para execucao do uav-monitoring.
 */
int main(){
	float freq_hertz = 1.0;	//hertz
	gps vgps;
	barometer vbaro;
	battery vbattery;
	attitude vattitude;
	velocity vvelocity;
	gpsinfo vgpsinfo;
	sensoruav vsensoruav;
	statusuav vstatusuav;
	printf("UAV-MONITORING\n");
	monitoring(freq_hertz, &vgps, &vbaro, &vbattery, &vattitude, &vvelocity, &vgpsinfo, &vsensoruav, &vstatusuav);
	return 0;
}

/**
 * Metodo que faz a captura das informações de diversos sensores do drone e imprime na tela e em arquivo.
 */
void monitoring(float freq_hertz, gps *vgps, barometer *vbaro, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav){
	FILE *f = fopen("log-aircraft-monitoring.csv", "w");  
	fprintf(f, "lat;lng;alt_rel;alt_abs;voltage;current;level;pitch;yaw;roll;vx;vy;vz;fixtype;satellitesvisible;eph;epv;heading;groundspeed;airspeed\n");//";mode;system-status;armed;is-armable;ekf-ok"
	while (true){
		sleep(1.0/freq_hertz);
		getAllInfo(vgps, vbaro, vbattery, vattitude, vvelocity, vgpsinfo, vsensoruav, vstatusuav);
		printAllInfo(*vgps, *vbaro, *vbattery, *vattitude, *vvelocity, *vgpsinfo, *vsensoruav, *vstatusuav);
		fprintAllInfo(f, *vgps, *vbaro, *vbattery, *vattitude, *vvelocity, *vgpsinfo, *vsensoruav, *vstatusuav);
		fflush(f);
	}
	fclose(f);
}

/**
 *  Faz uma requisição HTTP - GET para obter todas as informações dos sensores do drone.
 */
void getAllInfo(gps *vgps, barometer *vbaro, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav) {
	CURL *curl;
	data chunk;
	chunk.memory = malloc(1);
	chunk.size = 0;
	CURLcode res;
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:50000/get-all-sensors/");
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);
		res = curl_easy_perform(curl);
		if(res != CURLE_OK){
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		}
		curl_easy_cleanup(curl);
		//printf("%s\n", chunk.memory);
		sscanf(chunk.memory, 
			"{\"all-sensors\": [%lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %d, %d, %d, %d, [%lf, %lf, %lf]]}",
			&vgps->lat, &vgps->lng, &vbaro->alt_rel, &vbaro->alt_abs,
			&vbattery->voltage, &vbattery->current, &vbattery->level,
			&vattitude->pitch, &vattitude->yaw, &vattitude->roll, 
			&vsensoruav->heading, &vsensoruav->groundspeed, &vsensoruav->airspeed,
			&vgpsinfo->fixType, &vgpsinfo->satellitesVisible, &vgpsinfo->eph, &vgpsinfo->epv, 
			&vvelocity->vx, &vvelocity->vy, &vvelocity->vz
			//"next_wpt;count_wpt;dist_to_home;dist_to_current_wpt"
			//, vstatusuav->mode, vstatusuav->systemStatus, &vstatusuav->armed, &vstatusuav->isArmable, &vstatusuav->ekfOk
		);
	}
	free(chunk.memory);
	curl_global_cleanup();
}

/**
 *  Imprime as informações lidas na tela de forma formatada.
 */
void printAllInfo(gps vgps, barometer vbaro, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav){
	printf("      allinfo : [%3.7f;%3.7f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%d;%d;%d;%d;%3.2f;%3.2f;%3.2f]\n", 
		vgps.lat, vgps.lng, vbaro.alt_rel, vbaro.alt_abs, 
		vbattery.voltage, vbattery.current, vbattery.level, 
		vattitude.pitch, vattitude.yaw, vattitude.roll, 
		vvelocity.vx, vvelocity.vy, vvelocity.vz, 
		vgpsinfo.fixType, vgpsinfo.satellitesVisible, vgpsinfo.eph, vgpsinfo.epv,
		vsensoruav.heading, vsensoruav.groundspeed, vsensoruav.airspeed
	);
}

/**
 *  Imprime as informações lidas em um arquivo de forma formatada.
 */
void fprintAllInfo(FILE *f, gps vgps, barometer vbaro, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav){
	fprintf(f, "%3.7f;%3.7f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%d;%d;%d;%d;%3.2f;%3.2f;%3.2f\n", 
		vgps.lat, vgps.lng, vbaro.alt_rel, vbaro.alt_abs, 
		vbattery.voltage, vbattery.current, vbattery.level, 
		vattitude.pitch, vattitude.yaw, vattitude.roll, 
		vvelocity.vx, vvelocity.vy, vvelocity.vz, 
		vgpsinfo.fixType, vgpsinfo.satellitesVisible, vgpsinfo.eph, vgpsinfo.epv,
		vsensoruav.heading, vsensoruav.groundspeed, vsensoruav.airspeed
	);
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

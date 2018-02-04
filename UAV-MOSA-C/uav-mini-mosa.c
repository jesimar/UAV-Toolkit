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

void monitoring(float freq_hertz, gps *vgps, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav);
void posAnalyser(float freq_hertz, gps *vgps);
void getGPS2(gps *vgps);
void getAllInfo(gps *vgps, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav);
void printGPS(gps vgps);
void printAllInfo(gps vgps, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav);
void fprintAllInfo(FILE *f, gps vgps, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav);
static size_t WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp);

int main(){
	int mode = 1;			// 1 - SENSOR_ANALYSER      2 - POS_ANALYSER
	float freq_hertz = 1.0;	//hertz
	gps vgps;
	battery vbattery;
	attitude vattitude;
	velocity vvelocity;
	gpsinfo vgpsinfo;
	sensoruav vsensoruav;
	statusuav vstatusuav;
	
	printf("MOSA\n");
	if (mode == 1){
		monitoring(freq_hertz, &vgps, &vbattery, &vattitude, &vvelocity, &vgpsinfo, &vsensoruav, &vstatusuav);
	} else if (mode == 2){
		posAnalyser(freq_hertz, &vgps);
	}
	return 0;
}

void posAnalyser(float freq_hertz, gps *vgps){
	printf("    posAnalyser\n");
	while(true){
		sleep(1.0/freq_hertz);
		getGPS2(vgps);
		printGPS(*vgps);
	}
}

void monitoring(float freq_hertz, gps *vgps, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav){
	printf("    monitoring\n");
	FILE *f = fopen("log-aircraft-mosa.csv", "w");  
	fprintf(f, "lat;lng;alt_rel;alt_abs;voltage;current;level;pitch;yaw;roll;vx;vy;vz;fixtype;satellitesvisible;eph;epv;heading;groundspeed;airspeed\n");//";mode;system-status;armed;is-armable;ekf-ok"
	while (true){
		sleep(1.0/freq_hertz);
		getAllInfo(vgps, vbattery, vattitude, vvelocity, vgpsinfo, vsensoruav, vstatusuav);
		printAllInfo(*vgps, *vbattery, *vattitude, *vvelocity, *vgpsinfo, *vsensoruav, *vstatusuav);
		fprintAllInfo(f, *vgps, *vbattery, *vattitude, *vvelocity, *vgpsinfo, *vsensoruav, *vstatusuav);
		fflush(f);
	}
	fclose(f);
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

void getAllInfo(gps *vgps, battery *vbattery, attitude *vattitude, velocity *vvelocity, gpsinfo *vgpsinfo, sensoruav *vsensoruav, statusuav *vstatusuav) {
	CURL *curl;
	data chunk;
	chunk.memory = malloc(1);
	chunk.size = 0;
	CURLcode res;
	curl = curl_easy_init();
	if(curl) {
		curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:50000/allinfo/");
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&chunk);
		res = curl_easy_perform(curl);
		if(res != CURLE_OK){
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
		}
		curl_easy_cleanup(curl);
		//printf("%s\n", chunk.memory);
		sscanf(chunk.memory, 
			"{\"allinfo\": [%lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %lf, %d, %d, %d, %d, [%lf, %lf, %lf]]}",
			&vgps->lat, &vgps->lng, &vgps->alt_rel, &vgps->alt_abs,
			&vbattery->voltage, &vbattery->current, &vbattery->level,
			&vattitude->pitch, &vattitude->yaw, &vattitude->roll, 
			&vsensoruav->heading, &vsensoruav->groundspeed, &vsensoruav->airspeed,
			&vgpsinfo->fixType, &vgpsinfo->satellitesVisible, &vgpsinfo->eph, &vgpsinfo->epv, 
			&vvelocity->vx, &vvelocity->vy, &vvelocity->vz
			//, vstatusuav->mode, vstatusuav->systemStatus, &vstatusuav->armed, &vstatusuav->isArmable, &vstatusuav->ekfOk
		);
	}
	free(chunk.memory);
	curl_global_cleanup();
}

void printGPS(gps vgps){
	printf("      gps (lat, lng, alt_rel, alt_abs): [%3.7f, %3.7f, %3.2f, %3.2f]\n", vgps.lat, vgps.lng, vgps.alt_rel, vgps.alt_abs);
}

void printAllInfo(gps vgps, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav){
	printf("      allinfo : [%3.7f;%3.7f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%d;%d;%d;%d;%3.2f;%3.2f;%3.2f]\n", 
		vgps.lat, vgps.lng, vgps.alt_rel, vgps.alt_abs, 
		vbattery.voltage, vbattery.current, vbattery.level, 
		vattitude.pitch, vattitude.yaw, vattitude.roll, 
		vvelocity.vx, vvelocity.vy, vvelocity.vz, 
		vgpsinfo.fixType, vgpsinfo.satellitesVisible, vgpsinfo.eph, vgpsinfo.epv,
		vsensoruav.heading, vsensoruav.groundspeed, vsensoruav.airspeed
		//, vstatusuav.mode, vstatusuav.systemStatus, vstatusuav.armed, vstatusuav.isArmable, vstatusuav.ekfOk
	);
}

void fprintAllInfo(FILE *f, gps vgps, battery vbattery, attitude vattitude, velocity vvelocity, gpsinfo vgpsinfo, sensoruav vsensoruav, statusuav vstatusuav){
	fprintf(f, "%3.7f;%3.7f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%3.2f;%d;%d;%d;%d;%3.2f;%3.2f;%3.2f\n", 
		vgps.lat, vgps.lng, vgps.alt_rel, vgps.alt_abs, 
		vbattery.voltage, vbattery.current, vbattery.level, 
		vattitude.pitch, vattitude.yaw, vattitude.roll, 
		vvelocity.vx, vvelocity.vy, vvelocity.vz, 
		vgpsinfo.fixType, vgpsinfo.satellitesVisible, vgpsinfo.eph, vgpsinfo.epv,
		vsensoruav.heading, vsensoruav.groundspeed, vsensoruav.airspeed
	);
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

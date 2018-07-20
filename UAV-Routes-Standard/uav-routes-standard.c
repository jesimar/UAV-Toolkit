/**
* Author: Jesimar da Silva Arantes
* Date: 08/06/2016
* Last Update: 05/02/2018
* Description: Program to generate routes with standard formats for the UAV.
* Descrição: Programa para gerar rotas com formatos padrões para o VANT.
*/

//============================USED LIBRARIES============================

#include <stdio.h>
#include <math.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

#define CIRC_MERIDIONAL 40007860.0
#define CIRC_EQUATORIAL 40075017.0

//===========================GLOBAL VARIABLES===========================

int IS_PRINT = FALSE;

double POS_Z = 100.0;
double RADIUS_CIRCLE = 1000.0;
double BASE_TRIANGLE = 2000.0;
double BASE_RECTANGLE = 2000.0;

int IS_GEOGRAPHICAL = FALSE;
double GEO_BASE_LAT = 0.0;
double GEO_BASE_LNG = 0.0;

//=========================STRUCTS and TYPEDEF==========================

typedef struct pos{
	double posX;
	double posY;
	double posZ;
}position;

//=========================FUNCTION PROTOTYPES==========================

void readFileConfig(char name[]);

void createRouteCircle(int discretization, char name[]);
void createRouteTriangle(int discretization, char name[]);
void createRouteRectangle(int discretization, char name[]);

void createRouteCircleGeo(int discretization, char name[]);
void createRouteTriangleGeo(int discretization, char name[]);
void createRouteRectangleGeo(int discretization, char name[]);

double convertYtoLatitude(double latBase, double y);
double convertXtoLongitude(double lonBase, double latBase, double x);

//==============================FUNCTIONS===============================

int main(){
	readFileConfig("config.txt");
	
	if (IS_GEOGRAPHICAL == TRUE){
		createRouteCircleGeo(5, "Circle/route_circle_5wpt_geo.txt");
		createRouteCircleGeo(10, "Circle/route_circle_10wpt_geo.txt");
		createRouteCircleGeo(20, "Circle/route_circle_20wpt_geo.txt");
		createRouteCircleGeo(40, "Circle/route_circle_40wpt_geo.txt");
		createRouteCircleGeo(80, "Circle/route_circle_80wpt_geo.txt");
		createRouteCircleGeo(160, "Circle/route_circle_160wpt_geo.txt");
		createRouteCircleGeo(320, "Circle/route_circle_320wpt_geo.txt");
		createRouteCircleGeo(640, "Circle/route_circle_640wpt_geo.txt");
		createRouteCircleGeo(1280, "Circle/route_circle_1280wpt_geo.txt");
		createRouteCircleGeo(2560, "Circle/route_circle_2560wpt_geo.txt");
		createRouteCircleGeo(750, "Circle/route_circle_750wpt_geo.txt");
		createRouteCircleGeo(800, "Circle/route_circle_800wpt_geo.txt");
		
		createRouteTriangleGeo(3, "Triangle/route_triangle_3wpt_geo.txt");
		createRouteTriangleGeo(6, "Triangle/route_triangle_6wpt_geo.txt");
		createRouteTriangleGeo(12, "Triangle/route_triangle_12wpt_geo.txt");
		createRouteTriangleGeo(24, "Triangle/route_triangle_24wpt_geo.txt");
		createRouteTriangleGeo(48, "Triangle/route_triangle_48wpt_geo.txt");
		createRouteTriangleGeo(96, "Triangle/route_triangle_96wpt_geo.txt");
		createRouteTriangleGeo(192, "Triangle/route_triangle_192wpt_geo.txt");
		
		createRouteRectangleGeo(4, "Rectangle/route_rectangle_4wpt_geo.txt");
		createRouteRectangleGeo(8, "Rectangle/route_rectangle_8wpt_geo.txt");
		createRouteRectangleGeo(16, "Rectangle/route_rectangle_16wpt_geo.txt");
		createRouteRectangleGeo(32, "Rectangle/route_rectangle_32wpt_geo.txt");
		createRouteRectangleGeo(64, "Rectangle/route_rectangle_64wpt_geo.txt");
		createRouteRectangleGeo(128, "Rectangle/route_rectangle_128wpt_geo.txt");
		createRouteRectangleGeo(256, "Rectangle/route_rectangle_256wpt_geo.txt");
	} else{
		createRouteCircle(5, "Circle/route_circle_5wpt.txt");
		createRouteCircle(10, "Circle/route_circle_10wpt.txt");
		createRouteCircle(20, "Circle/route_circle_20wpt.txt");
		createRouteCircle(40, "Circle/route_circle_40wpt.txt");
		createRouteCircle(80, "Circle/route_circle_80wpt.txt");
		createRouteCircle(160, "Circle/route_circle_160wpt.txt");
		createRouteCircle(320, "Circle/route_circle_320wpt.txt");
		createRouteCircle(640, "Circle/route_circle_640wpt.txt");
		createRouteCircle(1280, "Circle/route_circle_1280wpt.txt");
		createRouteCircle(2560, "Circle/route_circle_2560wpt.txt");
		createRouteCircle(750, "Circle/route_circle_750wpt.txt");
		createRouteCircle(800, "Circle/route_circle_800wpt.txt");
		
		createRouteTriangle(3, "Triangle/route_triangle_3wpt.txt");
		createRouteTriangle(6, "Triangle/route_triangle_6wpt.txt");
		createRouteTriangle(12, "Triangle/route_triangle_12wpt.txt");
		createRouteTriangle(24, "Triangle/route_triangle_24wpt.txt");
		createRouteTriangle(48, "Triangle/route_triangle_48wpt.txt");
		createRouteTriangle(96, "Triangle/route_triangle_96wpt.txt");
		createRouteTriangle(192, "Triangle/route_triangle_192wpt.txt");
		
		createRouteRectangle(4, "Rectangle/route_rectangle_4wpt.txt");
		createRouteRectangle(8, "Rectangle/route_rectangle_8wpt.txt");
		createRouteRectangle(16, "Rectangle/route_rectangle_16wpt.txt");
		createRouteRectangle(32, "Rectangle/route_rectangle_32wpt.txt");
		createRouteRectangle(64, "Rectangle/route_rectangle_64wpt.txt");
		createRouteRectangle(128, "Rectangle/route_rectangle_128wpt.txt");
		createRouteRectangle(256, "Rectangle/route_rectangle_256wpt.txt");
	}
	return 0;
}

/**
 * Lê o arquivo de configurações contendo os parametros usados para gerar rotas.
 */
void readFileConfig(char name[]){
	FILE *file = fopen(name, "r");
	char str[50];
	if (file) {
		while (!feof(file)){
			fscanf(file, "%s", str);
			if (strcmp(str, "#IS_PRINT") == 0){
				fscanf(file, "%d", &IS_PRINT);
			}else if (strcmp(str, "#POS_Z") == 0){
				fscanf(file, "%lf", &POS_Z);
			}else if (strcmp(str, "#RADIUS_CIRCLE") == 0){
				fscanf(file, "%lf", &RADIUS_CIRCLE);
			}else if (strcmp(str, "#BASE_TRIANGLE") == 0){
				fscanf(file, "%lf", &BASE_TRIANGLE);
			}else if (strcmp(str, "#BASE_RECTANGLE") == 0){
				fscanf(file, "%lf", &BASE_RECTANGLE);
			}else if (strcmp(str, "#IS_GEOGRAPHICAL") == 0){
				fscanf(file, "%s", str);
				if (strcmp(str, "true") == 0){
					IS_GEOGRAPHICAL = TRUE;
					fscanf(file, "%lf", &GEO_BASE_LAT);
					fscanf(file, "%lf", &GEO_BASE_LNG);
				}
			}
		}
		fclose(file);
	}
}

/**
 * Função usada para criar uma rota em formato circular em coordenadas cartesianas.
 */
void createRouteCircle(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	double radius = RADIUS_CIRCLE;
	double a = RADIUS_CIRCLE;
	double b = 0.0;
	double step_angle = M_PI * (360.0/discretization) /180.0;
	for (int i = 0; i < discretization+1; i++){
		double angle = M_PI - step_angle * i;
		pos.posX = a + radius*cos(angle);
		pos.posY = b + radius*sin(angle);
		if (IS_PRINT == TRUE){
			printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
		}
		fprintf(file, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	}
	fclose(file);
}

/**
 * Função usada para criar uma rota em formato circular em coordenadas geográficas.
 */
void createRouteCircleGeo(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	double radius = RADIUS_CIRCLE;
	double a = RADIUS_CIRCLE;
	double b = 0.0;
	double step_angle = M_PI * (360.0/discretization) /180.0;
	for (int i = 0; i < discretization+1; i++){
		double angle = M_PI - step_angle * i;
		pos.posX = a + radius*cos(angle);
		pos.posY = b + radius*sin(angle);
		pos.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
		pos.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
		if (IS_PRINT == TRUE){
			printf("%2.8f %2.8f %2.2f\n", pos.posY, pos.posX, pos.posZ);
		}
		fprintf(file, "%2.8f;%2.8f;%2.2f\n", pos.posY, pos.posX, pos.posZ);
	}
	fclose(file);
}

/**
 * Função usada para criar uma rota em formato triangular em coordenadas cartesianas.
 */
void createRouteTriangle(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	double inc = 3.0*BASE_TRIANGLE/discretization;
	for (int i = 0; i < 3; i++){
		for (int j = 0; j < discretization/3; j++){
			if (IS_PRINT == TRUE){
				printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
			}
			fprintf(file, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
			if (i == 0){
				pos.posY = pos.posY + inc;
			}else if (i == 1){
				pos.posX = pos.posX + sqrt(3.0) * inc * cos(M_PI/3.0);
				pos.posY = pos.posY - inc * sin(M_PI/6.0);
			}else if (i == 2){
				pos.posX = pos.posX - sqrt(3.0) * inc * cos(M_PI/3.0);
				pos.posY = pos.posY - inc * sin(M_PI/6.0);
			}
		}
	}
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	if (IS_PRINT == TRUE){
		printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	}
	fprintf(file, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	fclose(file);
}

/**
 * Função usada para criar uma rota em formato triangular em coordenadas geográficas.
 */
void createRouteTriangleGeo(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	position posP;
	pos.posX = 0;
	pos.posY = 0;
	posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
	posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
	posP.posZ = POS_Z;
	double inc = 3.0*BASE_TRIANGLE/discretization;
	for (int i = 0; i < 3; i++){
		for (int j = 0; j < discretization/3; j++){
			if (IS_PRINT == TRUE){
				printf("%2.8f %2.8f %2.2f\n", posP.posY, posP.posX, posP.posZ);
			}
			fprintf(file, "%2.8f;%2.8f;%2.2f\n", posP.posY, posP.posX, posP.posZ);
			if (i == 0){
				pos.posY = pos.posY + inc;
			}else if (i == 1){
				pos.posX = pos.posX + sqrt(3.0) * inc * cos(M_PI/3.0);
				pos.posY = pos.posY - inc * sin(M_PI/6.0);
			}else if (i == 2){
				pos.posX = pos.posX - sqrt(3.0) * inc * cos(M_PI/3.0);
				pos.posY = pos.posY - inc * sin(M_PI/6.0);
			}
			posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
			posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
		}
	}
	pos.posX = 0;
	pos.posY = 0;
	posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
	posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
	if (IS_PRINT == TRUE){
		printf("%2.8f %2.8f %2.2f\n", posP.posY, posP.posX, posP.posZ);
	}
	fprintf(file, "%2.8f;%2.8f;%2.2f\n", posP.posY, posP.posX, posP.posZ);
	fclose(file);
}

/**
 * Função usada para criar uma rota em formato retangular (quadrado) em coordenadas cartesianas.
 */
void createRouteRectangle(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	double inc = 4.0*BASE_RECTANGLE/discretization;
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < discretization/4; j++){
			if (IS_PRINT == TRUE){
				printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
			}
			fprintf(file, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
			if (i == 0){
				pos.posY = pos.posY + inc;
			}else if (i == 1){
				pos.posX = pos.posX + inc;
			}else if (i == 2){
				pos.posY = pos.posY - inc;
			}else if (i == 3){
				pos.posX = pos.posX - inc;
			}
		}
	}
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	if (IS_PRINT == TRUE){
		printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	}
	fprintf(file, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	fclose(file);
}

/**
 * Função usada para criar uma rota em formato retangular (quadrado) em coordenadas geográficas.
 */
void createRouteRectangleGeo(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *file = fopen(name, "w+");
	position pos;
	position posP;
	pos.posX = 0;
	pos.posY = 0;
	posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
	posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
	posP.posZ = POS_Z;
	double inc = 4.0*BASE_RECTANGLE/discretization;
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < discretization/4; j++){
			if (IS_PRINT == TRUE){
				printf("%2.8f %2.8f %2.2f\n", posP.posY, posP.posX, posP.posZ);
			}
			fprintf(file, "%2.8f;%2.8f;%2.2f\n", posP.posY, posP.posX, posP.posZ);
			if (i == 0){
				pos.posY = pos.posY + inc;
			}else if (i == 1){
				pos.posX = pos.posX + inc;
			}else if (i == 2){
				pos.posY = pos.posY - inc;
			}else if (i == 3){
				pos.posX = pos.posX - inc;
			}
			posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
			posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
		}
	}
	pos.posX = 0;
	pos.posY = 0;
	posP.posX = convertXtoLongitude(GEO_BASE_LNG, GEO_BASE_LAT, pos.posX);
	posP.posY = convertYtoLatitude(GEO_BASE_LAT, pos.posY);
	posP.posZ = POS_Z;
	if (IS_PRINT == TRUE){
		printf("%2.8f %2.8f %2.2f\n", posP.posY, posP.posX, posP.posZ);
	}
	fprintf(file, "%2.8f;%2.8f;%2.2f\n", posP.posY, posP.posX, posP.posZ);
	fclose(file);
}

double convertYtoLatitude(double latBase, double y){
	return latBase + y * 360/CIRC_MERIDIONAL;
}

double convertXtoLongitude(double lonBase, double latBase, double x){
	return lonBase + x * 360/(CIRC_EQUATORIAL * cos(latBase * M_PI/180));
}

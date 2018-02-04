#include <stdio.h>
#include <math.h>

#define POS_Z 200

typedef struct pos{
	double posX;
	double posY;
	double posZ;
}position;

void createRouteCircle(int discretization, char name[]);
void createRouteRectangle(int discretization, char name[]);
void createRouteTriangle(int discretization, char name[]);

int main(){
	createRouteCircle(5, "route_circle_5.csv");
	createRouteCircle(10, "route_circle_10.csv");
	createRouteCircle(20, "route_circle_20.csv");
	createRouteCircle(40, "route_circle_40.csv");
	createRouteCircle(80, "route_circle_80.csv");
	createRouteCircle(160, "route_circle_160.csv");
	createRouteCircle(320, "route_circle_320.csv");
	//createRouteRectangle(40, "route_rect_40.csv");
	//createRouteRectangle(80, "route_rect_80.csv");
	//createRouteRectangle(160, "route_rect_160.csv");
	//createRouteRectangle(320, "route_rect_320.csv");
	//createRouteTriangle(40, "route_triang_40.csv");
	//createRouteTriangle(80, "route_triang_80.csv");
	//createRouteTriangle(160, "route_triang_160.csv");
	//createRouteTriangle(320, "route_triang_320.csv");
	return 0;
}

void createRouteCircle(int discretization, char name[]){
	printf("\n------------%s------------\n\n", name);
	FILE *arq = fopen(name, "w+");
	position pos;
	pos.posX = 0;
	pos.posY = 0;
	pos.posZ = POS_Z;
	double radius = 1000;
	double a = 1000.0;
	double b = 0.0;
	double step_angle = M_PI * (360.0/discretization) /180.0;
	for (int i = 0; i < discretization+1; i++){
		double angle = M_PI - step_angle * i;
		pos.posX = a + radius*cos(angle);
		pos.posY = b + radius*sin(angle);
		printf("%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
		fprintf(arq, "%2.2f %2.2f %2.2f\n", pos.posX, pos.posY, pos.posZ);
	}
	fclose(arq);
}

void createRouteRectangle(int discretization, char name[]){
	FILE *arq = fopen(name, "w+");
	double posX = 0;
	double posY = 0;
	double posZ = 200;
	double comp = 4000;
	double inc = comp/discretization;
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < discretization/4; j++){
			printf("%2.2f %2.2f %2.2f\n", posX, posY, posZ);
			fprintf(arq, "%2.2f %2.2f %2.2f\n", posX, posY, posZ);
			if (i == 0){
				posY = posY + inc;
			}else if (i == 1){
				posX = posX + inc;
			}else if (i == 2){
				posY = posY - inc;
			}else if (i == 3){
				posX = posX - inc;
			}
		}
	}
	fclose(arq);
}

void createRouteTriangle(int discretization, char name[]){
	FILE *arq = fopen(name, "w+");
	double posX = 0;
	double posY = 0;
	double posZ = POS_Z;
	double comp = 3000;
	double inc = comp/discretization;
	for (int i = 0; i < 3; i++){
		for (int j = 0; j < discretization/3; j++){
			printf("%2.2f %2.2f %2.2f\n", posX, posY, posZ);
			fprintf(arq, "%2.2f %2.2f %2.2f\n", posX, posY, posZ);
			if (i == 0){
				posY = posY + inc;
			}else if (i == 1){
				posX = posX + sqrt(3.0) * inc * cos(M_PI/3.0);
				posY = posY - inc * sin(M_PI/6.0);
			}else if (i == 2){
				posX = posX - sqrt(3.0) * inc * cos(M_PI/3.0);
				posY = posY - inc * sin(M_PI/6.0);
			}
		}
	}
	fclose(arq);
}

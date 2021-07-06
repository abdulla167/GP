#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <Arduino_JSON.h>
#include <iostream>   
#include <string> 
#include <Arduino.h>
#include <WiFi.h>
#include <HTTPClient.h>


// Json Variable to Hold Sensor Readings
JSONVar readings;

Adafruit_MPU6050 mpu;
sensors_event_t a, g, temp;

float gyroX, gyroY, gyroZ;
float accX, accY, accZ;
float temperature;

//Gyroscope sensor deviation
//offset
float gyroXerror = 0.07;
float gyroYerror = 0.03;
float gyroZerror = 0.01;

// GPIO where the DS18B20 is connected to
const int oneWireBus = 4;     

// Setup a oneWire instance to communicate with any OneWire devices
OneWire oneWire(oneWireBus);

// Pass our oneWire reference to Dallas Temperature sensor 
DallasTemperature sensors(&oneWire);

const char* ssid = "REPLACE_WITH_YOUR_SSID";
const char* password = "REPLACE_WITH_YOUR_PASSWORD";

// Initialize WiFi
void initWiFi() {
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.println("");
  Serial.print("Connecting to WiFi...");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);
  }
  Serial.println("");
  Serial.println(WiFi.localIP());
}

void setup(void) {
  Serial.begin(115200);
  while (!Serial)
    delay(10); // will pause Zero, Leonardo, etc until serial console opens


   sensors.begin();
   
  // Try to initialize!
  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
  Serial.println("MPU6050 Found!");
}

  String getGyroReadings(){
  mpu.getEvent(&a, &g, &temp);

  float gyroX_temp = g.gyro.x;
  if(abs(gyroX_temp) > gyroXerror)  {
    gyroX += gyroX_temp/50.00;
  }
  
  float gyroY_temp = g.gyro.y;
  if(abs(gyroY_temp) > gyroYerror) {
    gyroY += gyroY_temp/70.00;
  }

  float gyroZ_temp = g.gyro.z;
  if(abs(gyroZ_temp) > gyroZerror) {
    gyroZ += gyroZ_temp/90.00;
  }

  readings["gyroX"] = String(gyroX);
  readings["gyroY"] = String(gyroY);
  readings["gyroZ"] = String(gyroZ);

  String jsonString = JSON.stringify(readings);
  return jsonString;
}

String getAccReadings() {
  mpu.getEvent(&a, &g, &temp);
  // Get current acceleration values
  accX = a.acceleration.x;
  accY = a.acceleration.y;
  accZ = a.acceleration.z;
  readings["accX"] = String(accX);
  readings["accY"] = String(accY);
  readings["accZ"] = String(accZ);
  String accString = JSON.stringify (readings);
  return accString;
}

String getTemperature(){
  mpu.getEvent(&a, &g, &temp);
  temperature = temp.temperature;
  return String(temperature);
}

void loop() {

  sensors.requestTemperatures(); 
  float temperatureC = sensors.getTempCByIndex(0);

  Serial.print("Your Baby Temperature:");
  Serial.print(temperatureC);
  Serial.println("ºC");
  Serial.println("");
  delay(5000);

  
  /* Get new sensor events with the readings */
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  Serial.print("Room Temperature: ");
  Serial.print(temp.temperature);
  Serial.println("ºC");

  Serial.println("");
  delay(500);
  /* Print out the values */
 /*Serial.print("Acceleration X: ");
 Serial.print(a.acceleration.x);
  Serial.print(", Y: ");
  Serial.print(a.acceleration.y);
  Serial.print(", Z: ");
  Serial.print(a.acceleration.z);
  Serial.println(" m/s^2");*/
  
  Serial.println(a.acceleration.x);
  
  Serial.print("Your Baby Position: ");
  
  if((((a.acceleration.x)>= 9)&&((a.acceleration.x)<= 10)))
  {
    Serial.print("Right");
  }
  
   else if((((a.acceleration.x)>= (-10))&&((a.acceleration.x)<= (-9))))
  {
    Serial.print("Left");
  }
  
   else if((((a.acceleration.x)>= (-0.8))&&((a.acceleration.x)<= (-0.5))))
  {
    Serial.print("Back");
  }
  
   else if((((a.acceleration.x)>= 1)&&((a.acceleration.x)<= 3)))
  {
    Serial.print("Face");
  }
  else
  {
    Serial.print("Upnormal Position");
  }
  Serial.println("");
 
  if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status

 //Streaming data
 //server connection
 
  /* HTTPClient http;    //Declare object of class HTTPClient
 
   http.begin("http://172.28.134.181:8080/push");      //Specify request destination
   //http.addHeader("Content-Type", "text/plain");  //Specify content-type header
 
    int httpCode = http.POST((String(temperatureC));(getAccReadings().c_str(),"accelerometer_readings");(getTemperature().c_str(),"temperature_reading"));   //Send the request
   String payload = http.getString();                  //Get the response payload
 
   Serial.println(httpCode);   //Print HTTP return code
   Serial.println(payload);    //Print request response payload
 
   http.end();  //Close connection
 
 }else{
 
    Serial.println("Error in WiFi connection");   
 
 }
 
  delay(1000);  //Send a request every 30 seconds*/

}

  
}

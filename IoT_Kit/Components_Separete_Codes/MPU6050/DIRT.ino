#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>
#include <OneWire.h>
#include <DallasTemperature.h>

Adafruit_MPU6050 mpu;

// GPIO where the DS18B20 is connected to
const int oneWireBus = 4;     

// Setup a oneWire instance to communicate with any OneWire devices
OneWire oneWire(oneWireBus);

// Pass our oneWire reference to Dallas Temperature sensor 
DallasTemperature sensors(&oneWire);

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

  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  Serial.print("Accelerometer range set to: ");
  switch (mpu.getAccelerometerRange()) {
  case MPU6050_RANGE_2_G:
    Serial.println("+-2G");
    break;
  case MPU6050_RANGE_4_G:
    Serial.println("+-4G");
    break;
  case MPU6050_RANGE_8_G:
    Serial.println("+-8G");
    break;
  case MPU6050_RANGE_16_G:
    Serial.println("+-16G");
    break;
  }
  
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  Serial.print("Gyro range set to: ");
  switch (mpu.getGyroRange()) {
  case MPU6050_RANGE_250_DEG:
    Serial.println("+- 250 deg/s");
    break;
  case MPU6050_RANGE_500_DEG:
    Serial.println("+- 500 deg/s");
    break;
  case MPU6050_RANGE_1000_DEG:
    Serial.println("+- 1000 deg/s");
    break;
  case MPU6050_RANGE_2000_DEG:
    Serial.println("+- 2000 deg/s");
    break;
  }

  mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
  Serial.print("Filter bandwidth set to: ");
  switch (mpu.getFilterBandwidth()) {
  case MPU6050_BAND_260_HZ:
    Serial.println("260 Hz");
    break;
  case MPU6050_BAND_184_HZ:
    Serial.println("184 Hz");
    break;
  case MPU6050_BAND_94_HZ:
    Serial.println("94 Hz");
    break;
  case MPU6050_BAND_44_HZ:
    Serial.println("44 Hz");
    break;
  case MPU6050_BAND_21_HZ:
    Serial.println("21 Hz");
    break;
  case MPU6050_BAND_10_HZ:
    Serial.println("10 Hz");
    break;
  case MPU6050_BAND_5_HZ:
    Serial.println("5 Hz");
    break;
  }

  Serial.println("");
  delay(100);
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
 /* Serial.print("Acceleration X: ");
 Serial.print(a.acceleration.x);
  Serial.print(", Y: ");
  Serial.print(a.acceleration.y);
  Serial.print(", Z: ");
  Serial.print(a.acceleration.z);
  Serial.println(" m/s^2");*/

  Serial.print("Your Child Position: ");
  if((((a.acceleration.x)>= 9.2)||((a.acceleration.x)<= 9.8))&&(((a.acceleration.y)>= -1.5)||((a.acceleration.y)<= 0.75))&&(((a.acceleration.z)>= 0.68)||((a.acceleration.z)<= 2.4)))
  {
    Serial.print("Right");
  }
  else if((((a.acceleration.x)>= -9.35)||((a.acceleration.x)<= -9.8))&&(((a.acceleration.y)>= -0.89)||((a.acceleration.y)<= -0.17))&&(((a.acceleration.z)>= -1.4)||((a.acceleration.z)<= 1.79)))
  {
    Serial.print("Left");
  }
  else if((((a.acceleration.x)>= 0.75)||((a.acceleration.x)<= 1.22))&&(((a.acceleration.y)>= 0.65)||((a.acceleration.y)<= 0.81))&&(((a.acceleration.z)>= 9.31)||((a.acceleration.z)<= 9.37)))
  {
    Serial.print("Back");
  }
  else if((((a.acceleration.x)>= -0.08)||((a.acceleration.x)<= 0.36))&&(((a.acceleration.y)>= 1.01)||((a.acceleration.y)<= 1.34))&&(((a.acceleration.z)>= -10.21)||((a.acceleration.z)<= -10.36)))
  {
    Serial.print("Face");
  }
  
  Serial.println("");
 // Serial.print("out");
  /*Serial.print(g.gyro.x);
  Serial.print(", Y: ");
  Serial.print(g.gyro.y);
  Serial.print(", Z: ");
  Serial.print(g.gyro.z);
  Serial.println(" rad/s");*/

  
}

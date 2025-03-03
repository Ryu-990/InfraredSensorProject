#include <WiFi.h>
#include <HTTPClient.h>

const char* ssid = "YOUR_WIFI_SSID";
const char* password = "YOUR_WIFI_PASSWORD";
const char* serverURL = "http://yourserver.com/infrared";

const int sensorPin = 2; // センサーの接続ピン

void setup() {
  Serial.begin(115200);
  pinMode(sensorPin, INPUT);

  // WiFi接続
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("WiFi connecting...");
  }
  Serial.println("WiFi connected.");
}

void loop() {
  if (digitalRead(sensorPin) == HIGH) {
    sendAlertToServer();
    delay(5000); // 誤検知を防ぐための待機
  }
}

void sendAlertToServer() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin(serverURL);
    int httpResponseCode = http.POST("sensor=triggered");

    if (httpResponseCode > 0) {
      Serial.printf("HTTP Response code: %d\n", httpResponseCode);
    } else {
      Serial.printf("Error code: %d\n", httpResponseCode);
    }

    http.end();
  } else {
    Serial.println("WiFi not connected.");
  }
}

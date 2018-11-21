#include <SoftwareSerial.h>       


SoftwareSerial espSerial(3, 4);   //Pin 3 and 4 act as RX and TX. Connected to TX and RX of ESP8266 respectively     
SoftwareSerial blu(12,13);


int ledPin = 9; 
int ledPin2 = 8;  
char data = '0';
int led1status, led2status;


#define DEBUG true
String mySSID = "Yagami";       // WiFi SSID
String myPWD = "kpa112233"; // WiFi Password
String myAPI = "V4ZJPYWF9SRJVWR2";   // API Key
String myHOST = "api.thingspeak.com";
String myPORT = "80";
String myFIELD = "field1"; 
int sendVal;


void setup()
{
  Serial.begin(9600);
  espSerial.begin(9600);//default is 115200...baud rate is set to 9600 by AT+UART_DEF=9600,8,1,0,0
  blu.begin(9600);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);

  
  espData("AT+RST", 1000, DEBUG);                      
  espData("AT+CWMODE=1", 1000, DEBUG);                 
  espData("AT+CWJAP=\""+ mySSID +"\",\""+ myPWD +"\"", 1000, DEBUG);   
  /*while(!esp.find("OK")) 
  {          
      //Wait for connection
  }*/
  delay(1000);
  
}

 
  
  
  
  
  void loop()
  {
    
  
    if(blu.available() > 0)  // Send data only when you receive data:
  {
    //Serial.println("CIHRAG");
    data = blu.read();      //Read the incoming data and store it into variable data
    Serial.println(data);        //Print Value inside data in Serial monitor
    if(data == '1' && digitalRead(9)==HIGH){            //Checks whether value of data is equal to 1 
      digitalWrite(9, LOW);
      led1status=0;
      Serial.println("LED 1 OFF");
    }else if(data == '1' && digitalRead(9)==LOW){       
      digitalWrite(9, HIGH);
      led1status=1;
      Serial.println("LED 1 ON");
    }else if(data == '2' && digitalRead(8)==HIGH){
      digitalWrite(8, LOW);
      led2status=0;
      Serial.println("LED 2 OFF");
    }else if(data == '2' && digitalRead(8)==LOW){       
      digitalWrite(8, HIGH);
      led2status=2;
      Serial.println("LED 2 ON");
    }
  

    
    
    sendVal = led1status+led2status;

    
    String sendData = "GET /update?api_key="+ myAPI +"&"+ myFIELD +"="+String(sendVal);
    espData("AT+CIPMUX=1", 1000, DEBUG);       //Allow multiple connections
    espData("AT+CIPSTART=0,\"TCP\",\""+ myHOST +"\","+ myPORT, 1000, DEBUG);
    espData("AT+CIPSEND=0," +String(sendData.length()+4),1000,DEBUG);  
    espSerial.find(">"); 
    espSerial.println(sendData);
    Serial.print("Value to be sent: ");
    Serial.println(sendVal);
     
    espData("AT+CIPCLOSE=0",1000,DEBUG);
    delay(10000);

  }
  }





 String espData(String command, const int timeout, boolean debug)
{
  Serial.print("AT Command ==> ");
  Serial.print(command);
  Serial.println("     ");
  
  String response = "";
  espSerial.println(command);
  long int time = millis();
  while ( (time + timeout) > millis())
  {
    while (espSerial.available())
    {
      char c = espSerial.read();
      response += c;
    }
  }
  if (debug)
  {
    //Serial.print(response);
    Serial.println("Executed");
  }
  return response;
}
  

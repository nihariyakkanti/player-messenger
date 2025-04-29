@echo off

echo Building the project using Maven Wrapper...
call mvnw clean package

echo.
echo Running App.java (Same Java Process)...
java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.same.App

echo Waiting for 3 seconds before starting different process...
timeout /t 3

echo.
echo Running NetworkPlayerApp - Player1 and Player2 in separate java processes...

start cmd /k java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.separate.NetworkPlayerApp Player2 localhost 5000

timeout /t 2 

start cmd /k java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.separate.NetworkPlayerApp Player1 localhost 5000

timeout /t 10

echo Both processes have completed.
pause
#!/bin/bash

echo "Building the project using Maven Wrapper..."
./mvnw clean package

echo ""
echo "Running App.java (Same Java Process)..."
java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.same.App

echo "Waiting for 3 seconds before starting different process"
sleep 3

echo ""
echo "Running NetworkPlayerApp - Player1 and Player2 in separate java processes"

java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.separate.NetworkPlayerApp Player2 localhost 5000 &
PID2=$!
echo "Player2 started with PID $PID2"

sleep 2

java -cp target/message_exchange-1.0.0.jar com.players.message.exchange.process.separate.NetworkPlayerApp Player1 localhost 5000 &
PID1=$!
echo "Player1 started with PID $PID1"


wait $PID1
wait $PID2

echo "Both processes have completed."

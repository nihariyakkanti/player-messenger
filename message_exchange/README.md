# Message Exchange Between Players

This project executes two players communicate by exchanging messages.
- **Same Java Process**: Both players run inside the same JVM.
- **Different Java Processes**: Each player runs in separate Java process, Both Players communicate over a socket connection.

## Project Structure

- `Message.java`: Represents a message that is exchanged between players, It has a Sender, Receiver and Message.
- `Player.java`: A Player has an inbox where he receives messages. He can process messages and sends reply to other player within the same JVM.
- `App.java`: Main application class to run two players inside the same process.
- `NetworkPlayer.java`: A network Player has same behaviour as Player, but uses socket to communicate over different PID's.

## Requirements

- Java 8 or higher
- Maven (already included Maven Wrapper)


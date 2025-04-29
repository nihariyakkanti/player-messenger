package com.players.message.exchange.process.same;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main application class to run two players in the same Java process.
 * 
 * Creates two players. Manage the flow of sending and receiving messages.
 * Gracefully finalize the program using stop condition (after 10 messages
 * exchanged).
 */

public class App {
	public static void main(String[] args) {
		System.out.println("Welcome to Player Communication");

		Player player1 = new Player("Player1", new LinkedBlockingQueue<Message>(), 10, 10);
		Player player2 = new Player("Player2", new LinkedBlockingQueue<Message>(), 100, 100);

		player1.initateMessage(player2);
		while (!player1.isCompleted()) {

			player2.processMessageAndSendReply();
			player1.processMessageAndSendReply();

		}

	}
}

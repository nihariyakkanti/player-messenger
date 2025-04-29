package com.players.message.exchange.process.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.players.message.exchange.process.same.Message;
import com.players.message.exchange.process.same.Player;

class TestInitiatorPlayer {

	private Player player1;
	private Player player2;

	@BeforeEach
	void initialize() {
		player1 = new Player("Player1", new LinkedBlockingQueue<>(), 10, 10);
		player2 = new Player("Player2", new LinkedBlockingQueue<>(), 100, 100);
	}

	@Test
	void testSendMessageBeyondLimit() {
		for (int i = 0; i < 12; i++) {
			Message message = new Message(player1, player2, "Message " + i);
			player1.sendMessage(player2, message);
		}
		assertTrue(player1.getSendCounter() == player1.getSendLimit());
	}
}

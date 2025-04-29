package com.players.message.exchange.process.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.players.message.exchange.process.same.Message;
import com.players.message.exchange.process.same.Player;

class PlayerTest {

	private Player player1;
	private Player player2;

	@BeforeEach
	void initalizePlayers() {
		player1 = new Player("Player1", new LinkedBlockingQueue<>(), 10, 10);
		player2 = new Player("Player2", new LinkedBlockingQueue<>(), 100, 100);
	}

	@Test
	void testCreation() {
		assertEquals("Player1", player1.getName());
		assertNotNull(player1.getInbox());
	}

	@Test
	void testInitateMessage() throws InterruptedException {
		player1.initateMessage(player2);

		assertEquals(1, player1.getSendCounter());
		assertEquals("Hello", player2.getInbox().take().getMessage());
	}

	@Test
	void testSendMessage() throws InterruptedException {
		Message message = new Message(player1, player2, "TestMessage");
		player1.sendMessage(player2, message);

		assertEquals(1, player1.getSendCounter());
		assertEquals("TestMessage", player2.getInbox().take().getMessage());
	}

	@Test
	void testProcessMessage() {
		Message input = new Message(player1, player2, "Hello");
		player1.sendMessage(player2, input);
		player2.processMessageAndSendReply();
		assertEquals(1, player2.getSendCounter());
		try {
			assertTrue(player1.getInbox().take().getMessage().contains(String.valueOf(0)));
		} catch (InterruptedException e) {
			System.out.println(e);
		}

	}

}

package com.players.message.exchange.process.same;

/**
 * Represents a message.
 * 
 * Has sender, receiver and the content of the message
 */

public class Message {
	private Player sender;
	private Player receiver;
	private String message;

	public Message(Player sender, Player receiver, String message) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public Player getSender() {
		return sender;
	}

	public Player getReceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}

}

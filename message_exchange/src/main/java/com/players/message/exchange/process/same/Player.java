package com.players.message.exchange.process.same;

import java.util.concurrent.BlockingQueue;

/**
 * Represents a player.
 * 
 * Holds a name and message inbox. A player can send messages, receive messages
 * and reply back with a message.
 */
public class Player {
	private String name;
	private BlockingQueue<Message> inbox;
	private int sendCounter;
	private int receiveCounter;
	private int receiveLimit;
	private int sendLimit;

	public int getSendCounter() {
		return this.sendCounter;
	}

	public int getReceiveCounter() {
		return this.receiveCounter;
	}

	public int getReceiveLimit() {
		return receiveLimit;
	}

	public int getSendLimit() {
		return sendLimit;
	}

	public Player(String name, BlockingQueue<Message> inbox, int sendLimit, int receiveLimit) {
		super();
		this.name = name;
		this.inbox = inbox;
		this.sendLimit = sendLimit;
		this.receiveLimit = receiveLimit;
	}


	public String getName() {
		return this.name;
	}

	public BlockingQueue<Message> getInbox() {
		return this.inbox;
	}

	public void sendMessage(Player receiver, Message m) {
		if (!isSendLimitExceeded()) {
			try {
				this.sendCounter++;
				System.out.println(this.getName() + " sent message to " + m.getReceiver().getName());
				System.out.println("Message: " + m.getMessage());
				receiver.getInbox().put(m);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	private boolean isSendLimitExceeded() {
		if (this.sendCounter >= sendLimit) {
			return true;
		}
		return false;
	}

	public void processMessageAndSendReply() {
		try {
			Message msg = this.inbox.take();
			System.out.println(this.getName() + " received message from " + msg.getSender().getName());
			this.receiveCounter++;
			String reply = msg.getMessage() + " " + this.sendCounter;
			Message replyMessage = new Message(this, msg.getSender(), reply);
			sendMessage(msg.getSender(), replyMessage);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public boolean isCompleted() {
		if (this.sendCounter >= this.sendLimit && this.receiveCounter >= this.receiveLimit) {
			return true;
		}
		return false;
	}

	public void initateMessage(Player player) {
		Message firstMessage = new Message(this, player, "Hello");
		this.sendMessage(player, firstMessage);
	}
}

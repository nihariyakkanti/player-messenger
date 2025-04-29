package com.players.message.exchange.process.separate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkPlayer {

	private String name;
	private String address;
	private DataInputStream input;
	private DataOutputStream output;

	private int sendCounter;
	private int receiveCounter;
	private int sendLimit;
	private int receiveLimit;

	public DataInputStream getInput() {
		return input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public int getSendLimit() {
		return sendLimit;
	}

	public int getReceiveLimit() {
		return receiveLimit;
	}

	public int getSendCounter() {
		return this.sendCounter;
	}

	public int getReceiveCounter() {
		return this.receiveCounter;
	}

	public NetworkPlayer(String name, String address, int sendLimit, int receiveLimit) {
		super();
		this.name = name;
		this.address = address;
		this.sendLimit = sendLimit;
		this.receiveLimit = receiveLimit;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setInput(DataInputStream input) {
		this.input = input;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	public void sendMessage(String reply) {
		if (!isSendLimitExceeded()) {
			try {
				System.out.println(this.name + " sent message");
				System.out.println("Message: " + reply);

				this.sendCounter++;
				this.output.writeUTF(reply);
			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}

	public boolean isCompleted() {
		if (this.sendCounter >= this.sendLimit && this.receiveCounter >= this.receiveLimit) {
			return true;
		}
		return false;
	}

	public void processMessageAndSendReply() {
		try {
			String msg = this.input.readUTF();
			if (msg != null) {
				System.out.println(this.name + " received message");
				this.receiveCounter++;
				String reply = msg + " " + this.sendCounter;
				sendMessage(reply);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private boolean isSendLimitExceeded() {
		if (this.sendCounter >= sendLimit) {
			return true;
		}
		return false;
	}

	public void initateMessage() {
		String firstMessage = "Hello";
		this.sendMessage(firstMessage);
	}

	public void closeResources() {
		try {
			this.output.close();
			this.input.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}

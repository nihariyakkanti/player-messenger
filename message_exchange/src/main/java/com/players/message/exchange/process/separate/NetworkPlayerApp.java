package com.players.message.exchange.process.separate;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Represents Players running in separate java process.
 * 
 * clientPlayer starts communication between the players using socket. creates
 * server and clientPlayer Players and manages the message exchange between the
 * players.
 */
public class NetworkPlayerApp {
	

	public static void main(String[] args) {
		String player = args[0];
		String address = args[1];
		int port = Integer.parseInt(args[2]);
		if (player.equalsIgnoreCase("Player1")) {
			NetworkPlayer clientPlayer = new NetworkPlayer("Player1", address, 10, 10);
			startClientPlayer(clientPlayer, port);
		}
		if (player.equalsIgnoreCase("Player2")) {
			NetworkPlayer serverPlayer = new NetworkPlayer("Player2", address, 10, 10);
			startServerPlayer(serverPlayer, port);
		}
	}

	private static void startServerPlayer(NetworkPlayer serverPlayer, int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			Socket socket = server.accept();
			System.out.println("Server Player2 started..");
			serverPlayer.setInput(new DataInputStream(new BufferedInputStream(socket.getInputStream())));
			serverPlayer.setOutput(new DataOutputStream(socket.getOutputStream()));

			while (serverPlayer.getReceiveCounter() < serverPlayer.getReceiveLimit()) {

				serverPlayer.processMessageAndSendReply();
			}

			socket.close();
			server.close();

		} catch (UnknownHostException exp) {
			System.out.println(exp);
		} catch (IOException e1) {
			System.out.println(e1);
		} finally {
			serverPlayer.closeResources();
		}

	}

	private static void startClientPlayer(NetworkPlayer clientPlayer, int port) {
		try {
			Socket socket = new Socket(clientPlayer.getAddress(), port);
			System.out.println("Player1 socket connected to player2");
			clientPlayer.setInput(new DataInputStream(socket.getInputStream()));
			clientPlayer.setOutput(new DataOutputStream(socket.getOutputStream()));
			clientPlayer.initateMessage();
			while (!clientPlayer.isCompleted()) {

				clientPlayer.processMessageAndSendReply();

			}
			socket.close();

		} catch (UnknownHostException exp) {
			System.out.println(exp);
		} catch (IOException e1) {
			System.out.println(e1);
		} finally {
			clientPlayer.closeResources();
		}
	}

}

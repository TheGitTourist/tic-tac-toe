package com.github.ennoxhd.tictactoe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Print {
	
	private static final String YES = "yes";
	private static final String NO = "no";
	
	private Scanner input;
	private PrintStream output;
	
	private Scanner getInput() {
		return input;
	}

	private void setInput(Scanner input) {
		this.input = input;
	}

	private PrintStream getOutput() {
		return output;
	}

	private void setOutput(PrintStream output) {
		this.output = output;
	}

	public Print(InputStream in, OutputStream out) {
		setInput(new Scanner(in));
		setOutput(new PrintStream(out));
	}
	
	private String giveOptions(String... string) {
		String options = "";
		for(int i = 0; i < string.length; i++) {
			options += "'" + string[i].toString() + "'";
			if(i < string.length - 1) options += " or ";
		}
		return options;
	}
	
	public Game preGame() {
		final String options = giveOptions(YES, NO);
		getOutput().print("""
				--- Welcome to TicTacToe ---
				Do you want to give the players some names (%s)? """
				.formatted(options));
		String a = "";
		int i = 0;
		do {
			if(i != 0) {
				getOutput().print("Wrong input, please write " + options + ": ");
			}
			a = getInput().nextLine();
			i++;
		} while((!YES.equals(a)) && (!NO.equals(a)));
		if(YES.equals(a)) {
			getOutput().print("Enter the name for the first player: ");
			String firstPlayerName = "";
			firstPlayerName = getInput().next();
			getOutput().print("Enter the name for the second player: ");
			String secondPlayerName = "";
			secondPlayerName = getInput().next();
			return new Game(firstPlayerName, secondPlayerName);
		} else {
			return new Game();
		}
	}
	
	public Turn getTurnFromInput(Player currentPlayer) {
		getOutput().println();
		Turn nextTurn = null;
		while(nextTurn == null) {
			if(!currentPlayer.getPlayerName().equals("")) {
				getOutput().println("Move of " + currentPlayer.getPlayerName()
					+ " (" + currentPlayer.getSignString() + "): ");
			} else {
				getOutput().println("Move of " + currentPlayer.getSignString() + ": ");
			}
			getOutput().print("x - ");
			final int x = getInput().nextInt();
			getOutput().print("y - ");
			final int y = getInput().nextInt();
			try {
				nextTurn = new Turn(currentPlayer.getSign(), x, y);
			} catch(IllegalArgumentException e) {
				nextTurn = null;
				getOutput().println("Please enter coordinates in valid range [0;2] for x and y direction!");
			}
		}
		return nextTurn;
	}

	public void postGame(Player winner) {
		String wStr = "";
		if(!winner.getPlayerName().equals("")) {
			wStr = winner.getPlayerName();
		} else {
			wStr = winner.getSignString();
		}
		getOutput().println("\nThe winner of the game is: " + wStr);
		getOutput().println("--- --- --- --- ---");
	}
	
	public void print(String string) {
		getOutput().print(string);
	}

	public void invalidTurn(Turn nextTurn) {
		getOutput().println("The turn " + nextTurn.toString() + " is not valid!");
	}
}

import java.util.*;

public class Game{
	static Scanner sc = new Scanner(System.in);
	static Random rand = new Random();
	static Stack <String> hand = new Stack<String>(); // user's hand
	static Stack <String> stack = new Stack<String>(); // dealer's hand
	static int total = 0;
	static int dTotal = 0;
	static int count = 0;
	static int dCount = 0;
	static int last = rand.nextInt(11);
	static boolean gameOn = true;
	public static void main(String[] args) {
		// deck of cards
		String[] deck = {"1 spade", "2 spade" , "3 spade" ,"4 spade" , "5 spade" , "6 spade", "7 spade" , "8 spade", "9 spade", "10 spade" , "Jack spade" ,"Queen spade", "King spade", "Ace spade", "1 heart", "2 heart" , "3 heart" ,"4 heart" , "5 heart" , "6 heart", "7 heart" , "8 heart", "9 heart", "10 heart" , "Jack heart" ,"Queen heart", "King heart", "Ace heart","1 club", "2 club" , "3 club" ,"4 club" , "5 club" , "6 club", "7 club" , "8 club", "9 club", "10 club" , "Jack club" ,"Queen club", "King club", "Ace club", "1 diamond", "2 diamond" , "3 diamond" ,"4 diamond" , "5 diamond" , "6 diamond", "7 diamond" , "8 diamond", "9 diamond", "10 diamond" , "Jack diamond" ,"Queen diamond", "King diamond", "Ace diamond"};
		System.out.println("Enter Username...");
		user user = new user(sc.nextLine(), 0); // user object 
		System.out.println("Press 1 to Draw and 2 to stick");		
		//boolean exit = false
		while(total <= 21) { //only accept input if still in the game ie. total < 21
			int play = sc.nextInt();
			if(play(play, deck)) { 
				System.out.println(hand.pop()); //pop card from stack and print what it is
				System.out.println("Your total " + total);// print total
				
			}
			else if(play == 2) { // stick call
				System.out.println("Your final total " + total); // print total and break
				break;
				}
		}
		
		System.out.println();
		System.out.println("***************"); //spacers
		System.out.println("Dealer will play now: "); 
		
		stall(2); //  wait 2 seconds 
		while(dealerPlay(deck)) {
				System.out.println(stack.pop()); // pop and show card
				if(dTotal > total) break;
				else if(total > 21) break;
				stall(3); // wait 2 seconds after each draw for suspense

		}
		stall(0.5);
		System.out.println("Dealer's final total "+ dTotal);
		stall(0.5);
		winnerCheck(); //checks for winner, all conditions
		System.out.println();		
		user.det();
		System.out.println("You have one more chance to lower your score!");
		System.out.println("Would you like to play higher or lower?... 1 for 'yes' 2 for 'no'");
		
		
		if(sc.nextInt() == 1) {
			while(gameOn){	
				hl();
			}
			System.out.println("Game over");
			System.out.println(user.name + " ended with a total of " + user.showScore() + " points"); // print end score
		}
		else{
			System.out.println("Game over");
			System.out.println(user.name + " ended with a total of " + user.showScore() + " points"); // print end score, exit
			System.out.println();
			System.out.println("Thanks for playing");
			System.exit(0); //exit program
		}
	
	}

	public static String shuffle(String[] deck) {
		
		// iterate through the array and swap it with another RANDOM element
		for (int i = 0; i < deck.length; i++) {
			int num = rand.nextInt(deck.length);
			String temp = deck[num];
			deck[num] = deck[i]; // swap elements
			deck[i] = temp;
		}
		return Arrays.toString(deck); 
	}
	
	public static String draw(String[] deck) {
		int i = rand.nextInt(deck.length);
		String card = deck[i];
		// make s substring from beginning to first space
		// extracts value of card
		for(int j = 0; j < card.length(); j ++) {
			if(Character.isWhitespace(card.charAt(j))) {
				String val = card.substring(0, j);
				if(val.equals("Jack") || val.equals("Queen") || val.equals("King")) { // jack queen king = 10 in value
					 val = "10";
					 total += Integer.parseInt(val);
					count++; //increase card count in hand
				}
				else if(val.equals("Ace")) { // if ace give value 11
					val = "11";
					total += Integer.parseInt(val);
					count++;

				}
				else {
					total += Integer.parseInt(val); 
					count++;
				}
				
			}
		}
		return card;
	}
	
	public static boolean play(int one, String[] deck){
		if(one == 1) {
			hand.add(draw(deck)); // add pulled card to stack
			return true;
		}
		else {
			return false; // break
		}
	}
	
	public static String dealerDraw(String[] deck) {
		int a = rand.nextInt(deck.length); // random int
		String card = deck[a]; // element at random index
		
		// make s substring from beginning to first space
		// extracts value of card
		for(int j = 0; j < card.length(); j ++) {
			if(Character.isWhitespace(card.charAt(j))) { 
				String value = card.substring(0, j);
				if(value.equals("Jack") || value.equals("Queen") || value.equals("King")) {
					value = "10";
					dTotal +=  Integer.parseInt(value); // parse the value as string to integer and add to total
					dCount++;
				}
				else if(value.equals("Ace")) {
					value = "11";
					dTotal += Integer.parseInt(value);
					dCount++;

				}
				else dTotal += Integer.parseInt(value); dCount++;

				
			}
		}
		return card;
	}
	
	public static boolean dealerPlay(String[] deck){
		
		if(total >= 20 ) { // if player gets over 20 
			
			if(dTotal < 22) { // and dealer's total 
				stack.add(dealerDraw(deck)); 
				return true;
			}
			else {
				return false;
			}
		}
		
		if(dTotal <21) { // if player gets under 21
			stack.add(dealerDraw(deck)); 
			return true;
		}

		else if(dTotal ==21){
			return false;
		}
		else return false;
	}
	
	public static void winnerCheck() {
		
		if(dTotal <= 21 && total <=21) { //both dealer and player still in game
			if(dTotal > total) { // dealer outscores
				System.out.println("Dealer wins!"); 
				user.addScore(8); //add 8
			}
			else if(dTotal == total) { // same total 
				if(dCount > count) { // dealer holds more cards
					System.out.println(user.name + "wins due to smaller hand!");
					user.addScore(count); // and a point
				}
				else if(dCount < count) { // you hold more cards
					System.out.println("Dealer wins on smaller hand!");
					user.addScore(8);
				}
				else{
					System.out.println("draw..."); // both hold same amount of cards
					user.addScore(5);
				}
			}
			
			else {
				System.out.println(user.name + " wins!");
				user.addScore(count);
			}
		}
		else if(dTotal > 21 && total > 21) System.out.println("Nobody wins..."); // both of you have score over
		else if(dTotal <= 21 && total > 21) { // dealer is under or at 21 and player is over
			System.out.println("Dealer wins"); 
			user.addScore(10); // add 10 points
		}

		else {
			System.out.println(user.name + " wins!"); 
			user.addScore(count);
		}
	}
	
	public static void stall(double sec)
	{
	    try {
	        Thread.sleep((long) (sec * 1000)); // stall execution for sec * 1000 milliseconds
	    } catch (InterruptedException e) {} // if it is interrupted return exception e
	}
	
	public static void hl() {
		int num = rand.nextInt(11); // random number between 1 and 11
		int temp = num;
		System.out.println("Value is " + last);
		int in = sc.nextInt(); // read input from user
		if(in == 1){ // 1 = higher
			if( num > last) { // check to see if the random num is higher than shown
				last = num; // set previous number to currently shown number
				user.remScore(); // dock a point
				gameOn = true; // continue playing
			}
			else if(num < last) { // got it wrong
				gameOn = false; // end game
			}
		}
		else if(in == 2) { // 2 = lower
			if(num < last) { // check to see if the random num is lower than shown 
				last = num; // set previous number to currently shown
				user.remScore(); // dock a point
				gameOn = true; // continue playing
			}
			else if(num > last) { // got it wrong
				gameOn = false; // end game
			}

		}
		else System.out.println("Invalid"); // input invalid
	}
}


package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() 
	{
		// COMPLETE THIS METHOD
	  CardNode s1=deckRear.next; 
	  int nextValue=0; 
	  do
	   {
		 if(s1.cardValue==27)
		 {
			 nextValue=s1.next.cardValue; 
			 break; 
		 }
		 s1=s1.next;
		 
	   }
	   while(s1!=deckRear.next);
	
	  s1.next.cardValue=s1.cardValue;
	  s1.cardValue=nextValue; 
	  
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		int switchCard=0;
		CardNode ptr=deckRear.next; 
		CardNode ptr2=deckRear.next;
		do
		{
			if(ptr.cardValue==28)
			{
				ptr2=ptr; 
				switchCard=ptr.next.cardValue;
				break;
			}
		    ptr=ptr.next;
		}
		while(ptr!=deckRear.next);
		
		//Switches the values for Joker B 
		for(int count=0;count<2;count++)
		{
			ptr2.next.cardValue=ptr2.cardValue; 
			ptr2.cardValue=switchCard; 
			ptr2=ptr2.next; 
			switchCard=ptr2.next.cardValue;
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */ 
	void tripleCut() 
	{ 
		// COMPLETE THIS METHOD
		CardNode frontPtr=deckRear.next; //our pointer to the front 
		CardNode firstJoker=deckRear.next; //our first joker 
		int numFJ=0;
		if(firstJoker.cardValue==27||firstJoker.cardValue==28)
		{
			 //does nothing 
			numFJ=firstJoker.cardValue;
			
		}
		else
		{	//loop to find the first joker (either 27 or 28) if not in the front  
		   while(firstJoker.next.cardValue<=26)
		   {
			   firstJoker=firstJoker.next; 
		   }
		   numFJ=firstJoker.next.cardValue; 
		}
		
		//loop to find the second joker (either 27 or 28) 
		CardNode secondJoker=firstJoker.next;  
		if(secondJoker.cardValue>26&&secondJoker.cardValue!=numFJ)
		{
			//does nothing
		}
		else
		{
			//We stop at the second card and then we go to the next card to get the beginning of what we are bringing towards the front 
			secondJoker=secondJoker.next;
			while(secondJoker.cardValue<=26)
			{
				secondJoker=secondJoker.next;
			}
		
		}
			
		//Cases
		if((deckRear.cardValue==28&&deckRear.next.cardValue==27)||(deckRear.cardValue==27&&deckRear.next.cardValue==28)) //If the first and last are jokers (5) 
		{
			//do nothing
		}
		
		else if(firstJoker==frontPtr&&frontPtr.cardValue>26) //if the first card in the deck is a joker
		{
			if(secondJoker==frontPtr.next) //if the first two cards in the deck are jokers  (2)
			{
				deckRear=secondJoker; 
			}
			
			else if(secondJoker!=deckRear) //if the first joker is in the front and the other joker is in the middle (4)
			{
			CardNode holder=secondJoker.next;
			deckRear=secondJoker; 
			deckRear.next=holder; 
			} 
		}
		
		else if(firstJoker==frontPtr&&frontPtr.cardValue<27) //if the first joker is in the front but it's value is not 27 or 28 but it's .next is 
		{
			if(secondJoker==deckRear) //if the  first joker is in the front but the second one is in the end (6)
			{
				deckRear=firstJoker; 
			}
			else if(secondJoker!=deckRear) //if the first joker is in the front and the second joker is not in the end (1) 
			{
				CardNode tempHold=secondJoker.next;
				secondJoker.next=frontPtr;	
			    deckRear.next=firstJoker.next; 
			    deckRear=firstJoker; 
			    deckRear.next=tempHold; 
			}
			else if(secondJoker==firstJoker.next.next) //if the second joker 28 or 27 is after the first joker (27 or 28) 
			{
				CardNode tempHold=secondJoker.next; 
				secondJoker.next=firstJoker; 
				deckRear.next=firstJoker.next; 
				deckRear=firstJoker; 
				deckRear.next=tempHold;  
			}
		}
		
		else if(firstJoker!=frontPtr) //if the first card in a deck is not a joker 
		{
			if(secondJoker==deckRear) //if the first joker is not in the front but the second one is in the end (6)
			{
		       deckRear=firstJoker; 
			}
		
			else if(secondJoker!=deckRear) //every case where the first joker is not in the front and the second one is not next to the first (so we have something to switch over
			{
			  //Case (1) 
			   CardNode tempHold=secondJoker.next;
			   secondJoker.next=frontPtr;	
			   deckRear.next=firstJoker.next; 
			   deckRear=firstJoker; 
			   deckRear.next=tempHold; 
			}
	
			else if(firstJoker.next==secondJoker&&secondJoker.next==frontPtr) //if the jokers are in the end of the deck (3)
			{
				CardNode tempRearPtr=null; 
				for(tempRearPtr=deckRear.next;tempRearPtr.next!=firstJoker;tempRearPtr=tempRearPtr.next);
				deckRear=tempRearPtr;
				deckRear.next=firstJoker;
			}
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() 
	{		
		CardNode frontPtr=deckRear.next; 
		// COMPLETE THIS METHOD
		int howManyCards=deckRear.cardValue; 
		CardNode countCard=deckRear.next;
		
		if(howManyCards==28)
		{
			howManyCards=27; 
		}
		
		//If we are only moving one card 
		if(howManyCards==1)
		{
			countCard=deckRear.next;
			CardNode lastPointer=null;
		
			//lastPointer is a pointer to the second to last card
			for(lastPointer=deckRear.next;lastPointer.next!=deckRear;lastPointer=lastPointer.next);
		
			//makes the deckRear.next equal to the second to last CardNode 
			deckRear.next=countCard.next; 
			
			//We set the first card's .next equal to the last card 
			countCard.next=deckRear; 
			
			//We make the second to last card .next equal to the new card
			lastPointer.next=countCard; 
		}
	 
		else
		{
			//Gives us a pointer to the last card in the set we are moving
			for(int x=0;x<howManyCards-1;x++)
			{
			countCard=countCard.next;
			} 
			
			//Gives us a pointer before the deckRear card 
			CardNode lastPointer=null; 
			for(lastPointer=deckRear.next;lastPointer.next!=deckRear;lastPointer=lastPointer.next); 
		
			if(howManyCards!=27) 
			{  
				deckRear.next=countCard.next; 
				lastPointer.next=frontPtr;
				countCard.next=deckRear; 
			}
			else  
			{
				//do nothing 
			}
	   }
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey()
	{
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	
		jokerA(); 
		jokerB(); 
		tripleCut();
		countCut(); 

		
		CardNode front=deckRear.next;
		CardNode ptr=front; 
		int count=deckRear.next.cardValue; 
		int key=0;
		if(count==28)
		{
			count=27;
		}
		for(int x=0;x<count-1;x++)
		{ 
			ptr=ptr.next;  
		} 
		
		if(ptr.next.cardValue<27)
		{
			key=ptr.next.cardValue;
			return key;
		}
		else
		{
		   return getKey(); 
		} 
		
	 
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) 
	{	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    
		//fixedMessage is going to be the string without punctuation's 
		String fixedMessage="";  
		
		
		for(int count=0;count<message.length();count++)
		{
			//checking the current ASCII values between 65 (A) and 90 (Z) 
			if(message.charAt(count)>=65&&message.charAt(count)<=90)
			{
				fixedMessage=fixedMessage+message.charAt(count); 
			}
		}
				
		//Going to hold the alphabet values of the certain character in the string
	    int alphabetValues[]=new int[fixedMessage.length()]; 
	    for(int x=0;x<fixedMessage.length();x++)
	    {
	    	int c = fixedMessage.charAt(x)-'A'+1; 
	    	alphabetValues[x]=c; 
	    }
		
		
		//Going to hold the keyStream values 
		int keyStreamValues[]=new int[fixedMessage.length()]; 
		for(int x=0;x<fixedMessage.length();x++)
		{
			keyStreamValues[x]=getKey(); 
		}
		
		//keyStream of numbers with the alphabet position and keyStream values added
		int keyStream[]=new int[fixedMessage.length()];
		for(int x=0; x<fixedMessage.length();x++)
		{
			keyStream[x]=alphabetValues[x]+keyStreamValues[x]; 
			if(keyStream[x]>26)
			{
				keyStream[x]=keyStream[x]-26; 
			}
		}
		
		String encryptedMessage=""; 
		for(int x=0;x<fixedMessage.length();x++)
		{
			encryptedMessage=encryptedMessage+(char)(keyStream[x]-1+'A');  
		}
		
		 
		return encryptedMessage;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) 
	{	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    
		String result=""; 
		message.toUpperCase(); 
		
		//Storing all of the numbers into an array with the abc value
		int code[]=new int[message.length()];
		for(int x=0;x<message.length();x++)
		{
			code[x]=message.charAt(x)-'A'+1; 
		}
		
		//The keystream for this encrypted message
		int keyStream[]=new int[message.length()];
		for(int x=0; x<message.length();x++)
		{
			keyStream[x]=getKey(); 
		}
		
		int resultArray[]=new int[message.length()];
		for(int x=0;x<message.length();x++)
		{
			resultArray[x]=code[x]-keyStream[x]; 
			if(resultArray[x]<=0)
			{
				resultArray[x]=resultArray[x]+26; 
			}
		}
		
		for(int x=0;x<resultArray.length;x++)
		{
			result=result+(char)(resultArray[x]-1+'A');
		}
	
		return result;  
	}
}

import java.util.ArrayList;


public class Counter {

	private PowerSet<Card> cardps;
	private Card starter;

	//initialize variables 
	int maxRun = 0;
	int knobsPoint = 0;
	int flushPoints = 0;

	//initialize arrayLists
	private ArrayList<Card> pairList = new ArrayList<Card>();
	private ArrayList<Integer> runList = new ArrayList<Integer>();
	private ArrayList<Integer> flushList = new ArrayList<Integer>();

	
	
	//initialize starter card and generate power set of all 5 cards
	public Counter(Card[] hand, Card starter) {
	    this.starter = starter;
	    this.cardps = new PowerSet<>(hand);
	   
	}

	


  public int countPoints() {
    int points = 0;
    for(int i = 0; i < cardps.getLength(); i++) {
    	Set<Card> set = cardps.getSet(i); 
    	
    	//for each card combination, check for fifteens, pairs, runs, flush, and knobs
    	if (cardps.getSet(i).getLength() > 0) {
    		points += countFifteens(set);    
    		points += countPairs(set);
    		countRuns(set);   
		    countFlush(set); 
		    countKnobs(set);
    	}
    }
    
    //only count the run as points if it is of equal length to the max run length
    for(int i = 0; i < runList.size(); i++) {
    	if(runList.get(i) == maxRun) {
    		points = points + runList.get(i);
    	}
    }
    //add knobs point to points
    points = points + knobsPoint;
    
    //for each card combination, I printed the amount of same suits that exist in for each of the 4 suits, and looked at the 
    //pattern of these numbers and realized that the third-last number accurately shows if the cards are a flush of length 4, flush of length 5, or not a flush.
    if (flushList.get(flushList.size() - 3) >= 4) {
    	
        points = points + flushList.get(flushList.size() - 3);
    }
    
    return points;
  }
  
  
  
  private int countFifteens(Set<Card> set) {
	    int points = 0;
	    int sum = 0;
	    
	    //sum all elements in the current card combination
	    //increase points by 2 if all cards in this card combination sum to 15
	    for(int i = 0; i < set.getLength(); i++) {
	    	sum += set.getElement(i).getFifteenRank();
	    }
	    
		if (sum == 15) {
			points = points + 2;

	    }
	    return points;
	  }
	  
  
  private void countFlush(Set<Card> set) {
	  
	  	if (set.getLength() >= 4) {
	  		
	  		String[] suits = {"S", "H", "D", "C"};
		    int count = 0;  
		    
		    
		    for (String suit : suits) {
		    	
		    	//for each of the suits S, H, D, C, check if the i-th card in the current card combo has same suit as the it
		    	//if so, then increase count and add count to arrayList called flushList
		    	//then, in the countPoints method above, I will analyze these count values and find a pattern that solves the problem
		    	for(int i = 0; i < set.getLength(); i++) {
		    		
		    		
			    		if(suit.equals(set.getElement(i).getSuit())) {
			    			count++;
			    			
			    		}
		        	
		    	}
		    	flushList.add(count);
		    	
		    
		    }
	  	}
	  
	    
	
  	}
	    
	  
  
 
 //helper function for countFifteens member function
 //returns numeric representation of each card's label
  private int getFifteenRank(Card card) {
	  String label = card.getLabel();
	  if (label == "A") {
	  
		  return 1;
  	  } else {
  		if (label == "J" || label == "Q" || label == "K") {
		  return 10;
  		  } else {
  			  return Integer.parseInt(label);
  		  }
	  }
  }

  

  private void countRuns(Set<Card> set) {
	  
	  boolean runOrNot = isRun(set);
	  
	  //if current card combo is a run of 3 or more consecutive cards then add run length to an arrayList
  	  if (runOrNot == true) {
  		  runList.add(set.getLength());
  		  
  		  //update maxRun variable to store length of longest consecutive run
  		  if(set.getLength() > maxRun)
  		  		maxRun = set.getLength();
  	  }

  }
  
 
  private int countPairs(Set<Card> set) {
	  
	  int points = 0;
	    
	  //look at every possible pair of cards in the current card combination
	  //if current pair shares the same label, then if both cards do not exist in the arrayList pairList,
	  //then add both cards to pairList and increase points by 2
	  //this is to prevent the same pair of cards in other card combinations from being counted as points again
	    for(int i = 0; i < set.getLength(); i++) {
		    for(int j = i+1; j < set.getLength(); j++) {
		    	if (set.getElement(i).getLabel() == set.getElement(j).getLabel()) {
		    		boolean containi = pairList.contains(set.getElement(i));
		    		boolean containj = pairList.contains(set.getElement(j));
		    		
		    		if(!(containi == true && containj == true)) {
			    		points = points + 2;
			    		pairList.add(set.getElement(i));
			    		pairList.add(set.getElement(j));
		    		}
		    	}
		    	
	    	}
	    }
	    return points;
  }
	  

  
 

  private void countKnobs(Set<Card> set) {
	  
	  //look at each card in the current card combination
	    for(int i = 0; i < set.getLength(); i++) {

	    	//if the i-th card's label is J, and if this card is not the starter card, then...
	    	if(set.getElement(i).getLabel() == "J" && !(set.getElement(i).getSuit() == starter.getSuit() && set.getElement(i).getLabel() == starter.getLabel())) {
	    			//...if the starter card's suit is equal to the i-th card's suit, then make knobs point equal to one
	    			if(starter.getSuit() == set.getElement(i).getSuit()) {
	    				knobsPoint = 1;
	    			}
	    		}
	  
	    
	    }

	    

  }
  
  
//helper function given to us
  
	private boolean isRun (Set<Card> set) {
		// In this method, we are going through the given set to check if it constitutes a run of 3 or more
		// consecutive cards. To do this, we are going to create an array of 13 cells to represent the
		// range of card ranks from 1 to 13. We go through each card and increment the cell corresponding to
		// each card's rank. For example, an Ace (rank 1) would cause the first (index 0) cell to increment.
		// An 8 would cause the 8th (index 7) cell to increment. When this loop is done, the array will
		// contain 5 or less cells with values of 1 or more to represent the number of cards with each rank.
		// Then we can use this array to search for 3 or more consecutive non-zero values to represent a run.
		
		int n = set.getLength();
		
		if (n <= 2) return false; // Run must be at least 3 in length.
		
		int[] rankArr = new int[13];
		for (int i = 0; i < 13; i++) rankArr[i] = 0; // Ensure the default values are all 0.
		
		for (int i = 0; i < n; i++) {
			rankArr[set.getElement(i).getRunRank()-1] += 1;
		}
	
		// Now search in the array for a sequence of n consecutive 1's.
		int streak = 0;
		int maxStreak = 0;
		for (int i = 0; i < 13; i++) {
			if (rankArr[i] == 1) {
				streak++;
				if (streak > maxStreak) maxStreak = streak;
			} else {
				streak = 0;
			}
		}
		if (maxStreak == n) { // Check if this is the maximum streak.
				return true;
			} else {
				return false;
			}
		
		}
	  
}


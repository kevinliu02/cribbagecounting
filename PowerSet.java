
public class PowerSet <T>{

	private Set<T>[] set;
	
	public PowerSet(T[] elements) {
		//length of power set is 2^n where n is number of elements in set
        int powerSetSize = (int)(Math.pow(2, elements.length));
        
        
		set = new Set[powerSetSize];
		

        for (int i = 0; i < powerSetSize; i++) {

        	
        	//convert current i value into binary, as suggested by assignment instructions
            String bin = Integer.toBinaryString(i);
            
            String binPadded = "";
            Set<T> currentSet = new Set<>();   
            //pad binary representation with zeroes (left-padded)
            for(int j = 0; j < Math.abs(bin.length() - elements.length); j++) {
            	binPadded = binPadded + "0";
            }
            binPadded  = binPadded + bin;
            
         
            int j = 0;
            //add j-th element to the current subset if the element corresponds to 1 in binary representation
            while(j < binPadded.length()) {
                if(binPadded.charAt(j) == '0') {
                    
                } else {
                	currentSet.add(elements[j]);
                	
                }
                
                j++;
            }
            
            set[i] = currentSet;
        }
   
	}
	
	public int getLength() {
		return set.length;
	}
	
	
	//get the i-th card combination stored in the array set
	public Set<T> getSet(int i) {
		return set[i];
	}
	

}

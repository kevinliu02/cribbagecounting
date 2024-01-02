
public class Set <T>{
	LinearNode<T> setStart;
	
	
	public Set() {
		setStart = null;
	}
	
	//add new node to front of linked list
	public void add(T element) {
		LinearNode<T> newNode = new LinearNode<>(element);
		
		newNode.setNext(setStart);
		setStart = newNode; //setStart always points at front, so now it points to newNode
	}
	
	//traverses each node in linked list and increments a counter by 1 to get length of linked list
	public int getLength() {
		int count = 0;
		LinearNode<T> current = setStart;
		
		while(current != null) {
			
			current = current.getNext();
			count++;
		}
		return count;
	}
	
	
	//traverses the linked list and returns the value of i-th node
	public T getElement(int i){
		
        LinearNode<T> current = setStart;
        if(i >= this.getLength()){
            return null;
            
        } else {
        	int k = 0;
        	 while(k < i) {
                 current= current.getNext();
                 k++;
             }
        }
        
 
        return current.getElement();
        
    }
	
	
	
	//traverses linked list and checks if each element is equal to target element
	public boolean contains(T element) {
		
		LinearNode<T> current = setStart;
		
		while (current != null) {
			if (current.getElement().equals(element)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
		
	}
	
	
	//traverses linked list and prints every element of Set separated by space
	public String toString() {
		  String str = "";
		    LinearNode<T> current = setStart;

		    while (current != null) {
		        str = str + current.getElement();
		        
		        //this prevents an unnecessary space after last element
		        if (current.getNext() != null) {
		            str += " ";
		        } 
		        current = current.getNext();
		    }

		    return str;
	}

}

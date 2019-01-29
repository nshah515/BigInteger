package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		integer=integer.trim(); // no whitespace
		BigInteger parsed=new BigInteger(); // return statement
		if (integer.isEmpty()) {
			
			parsed.front= new DigitNode(0, parsed.front);
			return parsed;
		}
		if (!Character.isDigit(integer.charAt(0))) {
		if (integer.charAt(0)=='+') {
			parsed.negative=false;
		}
		else if (integer.charAt(0)=='-') {
			parsed.negative=true;
		}
		else {
			throw new IllegalArgumentException();
		}
		integer=integer.substring(1);
		}
		// 0's should be removed if in the LL
		while (integer.length()>1) {
			if(integer.charAt(0)=='0') {
				integer=integer.substring(1);
			}
			else {
				break;
			}
		}
		
		
		// go through LL and check for neg/pos, if the char!=digit, and add to DigitNode
		
		for (int i=0; i<integer.length(); i++) {
			if (!Character.isDigit(integer.charAt(i)) && i==0) { // if i=0, check for pos/neg, if i!=0, illegal
				if (integer.charAt(0)=='+') {
					parsed.negative=false;
				}
				else if (integer.charAt(0)=='-') {
					parsed.negative=true;
				}
				else {
					throw new IllegalArgumentException();
				}
				integer=integer.substring(1); // remove neg or pos sign
			}
			else if(!Character.isDigit(integer.charAt(i)) && i!=0) {
				throw new IllegalArgumentException();
			}
			if(Character.isDigit(integer.charAt(i))){ // if it is a digit, add it
				parsed.numDigits++; //increment size by one
				char a=integer.charAt(i); // stores the value of the index as a char
				int place=Character.getNumericValue(a); // converts to numeric value
				parsed.front= new DigitNode(place, parsed.front);
			}
		
		}
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return parsed; 
	}
	
	
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
	
			if(first.numDigits == 0 && second.numDigits == 0) return new BigInteger();
			
			BigInteger added = new BigInteger();
			
			if(second.numDigits == 0){
				added= first;
				System.out.println("here");
				return added;
			}			
			if(first.numDigits == 0){
				added=second;
				return added;
			}
		
			
			
			int small;
			int large;
			int nDig = 0;
			int carry = 0;
			int sum = 0;
			DigitNode current = null;

			// neg +neg or pos+pos --> addition both ways
			if((first.negative==false && second.negative== false) || (first.negative==true && second.negative==true)){
				DigitNode firstPtr=null;
				DigitNode secondPtr=null;
				if (first.numDigits > second.numDigits){
					large = first.numDigits;
					small = second.numDigits;
					firstPtr=first.front;
					secondPtr=second.front;
				}else{
					large = second.numDigits;
					small = first.numDigits;
					firstPtr=second.front;
					secondPtr=first.front;
				}
				
				while(firstPtr != null){
					if(secondPtr == null){
						carry = carry + firstPtr.digit;
						int res = (int)(carry/10.0);
						sum = (int)(carry - (int)(res*10));
						carry = (int)(res);
					}else{
						
						carry = carry + firstPtr.digit + secondPtr.digit;
						int res = (int)(carry/10.0);
						sum = (int)(carry - (int)(res*10));
						carry = (int)(res);
					}
					current = addAfter(sum, current);
					if (added.front==null) {
						added.front=current;
						
				
					}
					firstPtr=firstPtr.next;
					if (secondPtr!=null) {
						secondPtr=secondPtr.next;
					}
					
					
				}
				if (carry!=0) {
					current=addAfter(carry, current);
				}
			}
	
		else if ((first.negative==true && second.negative==false) || (first.negative==false && second.negative==true)) {
		
			DigitNode firstPtr=null;
			DigitNode secondPtr=null;
			boolean sign=false;
			if(vals(first, second)) {
				sign=second.negative;
				firstPtr=second.front;
				secondPtr=first.front;
			}
			else {
				sign=first.negative;
				firstPtr=first.front;
				secondPtr=second.front;
			}
			int borrow=0;
			int diff=0;
			
			while(firstPtr != null){
				if(secondPtr == null){
					if (borrow!=0) {
						if (firstPtr.digit==0) {
							diff=0;
						}
						else {		
							diff=firstPtr.digit-borrow;
							borrow=0;
						}
					}
					else {
						diff=firstPtr.digit;
					}
				}
				
				else{
					if (firstPtr.digit-borrow>=secondPtr.digit) {
						diff=firstPtr.digit-secondPtr.digit-borrow;
						borrow=0;
					}
					else {
						
						diff=firstPtr.digit-borrow+10-secondPtr.digit;
						borrow=1;
						
					}
				}
				current = addAfter(diff, current);
				if (added.front==null) {
					added.front=current;
					
			
				}
				firstPtr=firstPtr.next;
				if (secondPtr!=null) {
					secondPtr=secondPtr.next;
				}
				
				
			}
			added.negative=sign;
			
	
		}
			
		if (first.negative==true && second.negative==true) {
			added.negative=true;	
		}
		else if (first.negative==false && second.negative==false) {
			added.negative=false;
		}
		
		added=BigInteger.parse(""+ added);	
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return added; 
	}
	
	private static boolean vals(BigInteger first, BigInteger second) {
		boolean large=false;
		if (first.numDigits > second.numDigits) {
			large=false;
		}
		else if (first.numDigits < second.numDigits) {
			large=true;
		}
		else {
			BigInteger fnew=new BigInteger();
			BigInteger snew=new BigInteger();
			DigitNode fptr=first.front;
			DigitNode sptr=second.front;
			while (fptr!=null) {
				addBefore(fptr, fnew);
				fptr=fptr.next;
			}
			while (sptr!=null) {
				addBefore(sptr, snew);
				sptr=sptr.next;
			}
			// 6789, 6688
			DigitNode f=fnew.front, s=snew.front;
			while (f!=null && s!=null) {
				
				if (s.digit > f.digit) {
					large=true;
					break;
				}
				f=f.next;
				s=s.next;
			}
			
		}
		return large;
	}
	


	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		
		int aMultiplier=1;
		int bMultiplier=1;
		
		DigitNode a=first.front;
		DigitNode b=second.front;
		
		int temp=0;
		while(a!=null) {
			while (b!=null) {
				temp=temp+(a.digit*b.digit*aMultiplier*bMultiplier);
				b=b.next;
				bMultiplier=bMultiplier*10;
			}
			bMultiplier =1;
			aMultiplier=aMultiplier*10;
			a=a.next;
			b=b.next;
		}
		
		BigInteger result= parse(Integer.toString(temp));
		
		result.negative=sign(first,second);
		return result;
		
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		
		
	}
	private static boolean sign(BigInteger first, BigInteger second) {
	
		if (first.negative==false && second.negative==true) {
			return true;
		}
		if (first.negative==true && second.negative==false) {
			return true;
		}
		return false;
	}
	
	private static DigitNode addAfter(int x, DigitNode a) {
		if (a!=null) {
			DigitNode pointer=a;
			if(pointer.next!=null) {
				pointer=pointer.next;
			}
			pointer.next=new DigitNode(x, null);
			return pointer.next;
		}else {
			return new DigitNode (x, a);
		}
	}
	
	private static BigInteger addBefore(DigitNode val, BigInteger x) {
		if (x.front==null) {
			x.front=new DigitNode(val.digit, null);
		
		}else {
			DigitNode temp=x.front;
			x.front=new DigitNode(val.digit, temp);
				
		}
		return x;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
} // finish
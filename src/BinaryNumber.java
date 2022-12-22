
import java.util.Iterator;

public class BinaryNumber implements Comparable<BinaryNumber>{
    private static final BinaryNumber ZERO = new BinaryNumber(0);
    private static final BinaryNumber ONE  = new BinaryNumber(1);
    private BitList bits;

    // Copy constructor
    //Do not change this constructor
    public BinaryNumber(BinaryNumber number) {
        bits = new BitList(number.bits);
    }

    //Do not change this constructor
    private BinaryNumber(int i) {
        bits = new BitList();
        bits.addFirst(Bit.ZERO);
        if (i == 1)
            bits.addFirst(Bit.ONE);
        else if (i != 0)
            throw new IllegalArgumentException("This Constructor may only get either zero or one.");
    }

    //Do not change this method
    public int length() {
        return bits.size();
    }

    //Do not change this method
    public boolean isLegal() {
        return bits.isNumber() & bits.isReduced();
    }

    //----------write your code BELOW this line only!!!---------------------------------------------------------

    //--------------------helpers--------------------------------------------------------------------------------
    public BinaryNumber(BitList num){
        this.bits = new BitList(num);
    }
    public void compareSizes(BinaryNumber B){
       this.bits.padding(B.bits.size());
       B.bits.padding(this.bits.size());
    }
    public void compareSizes(BitList B){
        this.bits.padding(B.size());
        B.padding(this.bits.size());
    }
    //-------------------end of helpers------------------------------------------------------------------------

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.1 ================================================
    public static int toInt(char c) {
        return "0123456789".indexOf(c);
    }
    public BinaryNumber(char c) {
        this.bits = new BitList();
        String binary = Integer.toBinaryString(toInt(c));
        for(int i = binary.length() - 1;i>=0;i--){
            this.bits.addLast(new Bit(binary.charAt(i) - '0'));
        }
        //add zero if there is one at the beginning
        Bit LastBit = this.bits.getLast();
        if(LastBit.toInt() == 1){
            this.bits.addLast(new Bit(0));
        }
        else{
            this.bits.addLast(LastBit);
        }
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.2 ================================================
    @Override
    public String toString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line

        String ans = "";
        Iterator<Bit> iter = this.bits.iterator();
        while(iter.hasNext())
            ans = ans.concat(iter.next().toString());
        return ans;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.3 ================================================
    public boolean equals(Object other) {
        if(! (other instanceof BinaryNumber))
            return false;

    	Iterator<Bit> ThisIter = this.bits.iterator();
        Iterator<Bit> OtherIter =((BinaryNumber) other).bits.iterator();

        if(ThisIter.hasNext() & OtherIter.hasNext()){
            if(ThisIter.next().toInt() != OtherIter.next().toInt()){
                return false;
            }
        }
        //they are the same until the point one of them end
        // 001 0011111111 for example
        return ThisIter.hasNext() == OtherIter.hasNext();
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.4 ================================================
    public BinaryNumber add(BinaryNumber addMe) {

        if(addMe.signum() == 0)
            //in case the number is zero
            return this;

        boolean countCarry = true;

        if(addMe.signum() == -1)
            //in case the number is negative
            countCarry = false;


        Bit A;
        Bit B ;
        Bit Cin = new Bit(0);

        compareSizes(addMe);

        BitList res = new BitList();
        BitList copyBits;
        copyBits = this.bits;
        while (addMe.bits.size() > 0){
            A = this.bits.removeLast();
            B = addMe.bits.removeLast();
            res.addLast(Bit.fullAdderSum(A,B,Cin));
            Cin = Bit.fullAdderCarry(A,B,Cin);
        }
        res.reduce();
        if(Cin.toInt() == 1 & countCarry) {
            //that means we have a carry
            res.addFirst(Cin);
            res.addFirst(new Bit(0));
        }
            this.bits = copyBits;

        return new BinaryNumber(res);
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.5 ================================================
    public BinaryNumber negate() {
        this.bits.reduce();
        BitList negBits = new BitList();
        Iterator<Bit> thisIter = this.bits.iterator();
        Bit temp;
        while(thisIter.hasNext()) {
            temp = thisIter.next().negate();
            negBits.addLast(temp);
        }

        //represent the number 1
        //and then we will combine
        BinaryNumber addOne = new BinaryNumber(1);

        BinaryNumber res = (add(addOne));

        return res;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.6 ================================================
    public BinaryNumber subtract(BinaryNumber subtractMe) {
        //if(!subtractMe.isLegal())
         //   throw new IllegalArgumentException("this is not a number");
        subtractMe = subtractMe.negate();
        return (add(subtractMe));
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.7 ================================================
    public int signum() {
        if(this.toString().equals("0"))
            //number is zero
            return 0;
        if(this.bits.getFirst().toInt() == 1)
            return -1;
        return 1;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.8 ================================================
    public int compareTo(BinaryNumber other) {
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.9 ================================================
    public int toInt() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.10 ================================================
    // Do not change this method
    public BinaryNumber multiply(BinaryNumber multiplyMe) {
    	throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    private BinaryNumber multiplyPositive(BinaryNumber multiplyMe) {
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.11 ================================================
    // Do not change this method
    public BinaryNumber divide(BinaryNumber divisor) {
    	// Do not remove or change the next two lines
    	if (divisor.equals(ZERO)) // Do not change this line
            throw new RuntimeException("Cannot divide by zero."); // Do not change this line
    	//
    	throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    private BinaryNumber dividePositive(BinaryNumber divisor) {
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.12 ================================================
    public BinaryNumber(String s) {
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.13 ================================================
    public String toIntString() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line
        //
        throw new UnsupportedOperationException("Delete this line and implement the method.");
    }

//--------------write your code ABOVE this line only!!!---------------------------------------------------------

    // Returns this * 2
    // Do not change this method
    public BinaryNumber multiplyBy2() {
        BinaryNumber output = new BinaryNumber(this);
        output.bits.shiftLeft();
        output.bits.reduce();
        return output;
    }

    // Returns this / 2
    // Do not change this method
    public BinaryNumber divideBy2() {
        BinaryNumber output = new BinaryNumber(this);
        if (!equals(ZERO)) {
            if (signum() == -1) {
                output.negate();
                output.bits.shiftRight();
                output.negate();
            } else output.bits.shiftRight();
        }
        return output;
    }
 
}




import java.util.Iterator;

public class BinaryNumber implements Comparable<BinaryNumber> {
    private static final BinaryNumber ZERO = new BinaryNumber(0);
    private static final BinaryNumber ONE = new BinaryNumber(1);
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
    public BinaryNumber(BitList num) {
        this.bits = new BitList(num);
    }

    public void compareSizes(BinaryNumber B) {
        this.bits.padding(B.bits.size());
        B.bits.padding(this.bits.size());
    }

    public void t() {
        this.bits.removeFirst();
        this.bits.addFirst(Bit.ONE);
        this.bits.addFirst(Bit.ONE);
        this.bits.addLast(Bit.ONE);
    }

    //-------------------end of helpers------------------------------------------------------------------------

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.1 ================================================
    public static int toInt(char c) {
        return "0123456789".indexOf(c);
    }

    public BinaryNumber(char c) {
        this.bits = new BitList();
        String binary = Integer.toBinaryString(toInt(c));
        for (int i = binary.length() - 1; i >= 0; i--) {
            this.bits.addLast(new Bit(binary.charAt(i) - '0'));
        }
        //add zero if there is one at the beginning
        Bit LastBit = this.bits.getLast();
        if (LastBit.toInt() == 1) {
            this.bits.addLast(new Bit(0));
        } else {
            this.bits.addLast(LastBit);
        }
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.2 ================================================
    @Override
    public String toString() {
        // Do not remove or change the next two lines
        // if (!isLegal()) // Do not change this line
        //   throw new RuntimeException("I am illegal.");// Do not change this line

        String ans = "";
        Iterator<Bit> iter = this.bits.iterator();
        while (iter.hasNext())
            ans = ans.concat(iter.next().toString());
        return ans;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.3 ================================================
    public boolean equals(Object other) {
        if (!(other instanceof BinaryNumber))
            return false;

        Iterator<Bit> ThisIter = this.bits.iterator();
        Iterator<Bit> OtherIter = ((BinaryNumber) other).bits.iterator();

        if (ThisIter.hasNext() & OtherIter.hasNext()) {
            if (ThisIter.next().toInt() != OtherIter.next().toInt()) {
                return false;
            }
        }
        //they are the same until the point one of them end
        // 001 0011111111 for example
        return ThisIter.hasNext() == OtherIter.hasNext();
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.4 ================================================
    public BinaryNumber add(BinaryNumber addMe) {

        if (this.signum() == 0)
            //trying to add zero
            return this;
        boolean carry = !(this.signum() == -1 | addMe.signum() == -1);

        Bit A, B;
        Bit sum = new Bit(0);
        Bit Cin = new Bit(0);
        BinaryNumber ret = new BinaryNumber(0);
        ret.bits.removeFirst();

        compareSizes(addMe);

        BitList thisBits = new BitList(this.bits);
        BitList otherBits = new BitList(addMe.bits);

        if (this.bits.isNumber())
            this.bits.reduce();

        while (thisBits.size() > 0) {

            A = thisBits.removeFirst();
            B = otherBits.removeFirst();

            sum = Bit.fullAdderSum(A, B, Cin);
            Cin = Bit.fullAdderCarry(A, B, Cin);
            ret.bits.addLast(sum);
        }
        if (carry & Cin.toInt() == 1) {
            ret.bits.addLast(Bit.ZERO);
            ret.bits.addLast(Bit.ONE);
        } else if (sum.toInt() == 1 & !carry)
            ret.bits.addLast(Bit.ONE);

            //numbers are positive, we will add zero at the end
            // if it's not nessery reduce will handle
        else if (carry)
            ret.bits.addLast(Bit.ZERO);
        ret.bits.reduce();
        return ret;

    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.5 ================================================
    public BinaryNumber negate() {
        //this.bits.reduce();
        BitList negBits = new BitList();
        Iterator<Bit> thisIter = this.bits.iterator();
        Bit temp;
        while (thisIter.hasNext()) {
            temp = thisIter.next().negate();
            negBits.addFirst(temp);
        }

        //represent the number 1
        //and then we will combine
        BinaryNumber addOne = new BinaryNumber(1);
        BinaryNumber ret = new BinaryNumber(negBits);
        BinaryNumber t = new BinaryNumber(ret.add(addOne));
        return t;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.6 ================================================
    public BinaryNumber subtract(BinaryNumber subtractMe) {
        if (!subtractMe.isLegal())
            throw new IllegalArgumentException("this is not a number");
        subtractMe = subtractMe.negate();

        return add(subtractMe);
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.7 ================================================
    public int signum() {
        if (this.toString().equals("0"))
            //number is zero
            return 0;
        if (this.bits.getFirst().toInt() == 1)
            return -1;
        return 1;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.8 ================================================
    public int compareTo(BinaryNumber other) {

        if (this.toInt() > other.toInt())
            return 1;
        if (this.toInt() < other.toInt())
            return -1;
        return 0;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.9 ================================================
    public int toInt() {
        // Do not remove or change the next two lines
        if (!isLegal()) // Do not change this line
            throw new RuntimeException("I am illegal.");// Do not change this line

        BitList thisBits = new BitList(this.bits);

        boolean neg = thisBits.removeLast().toInt() == 1;
        int index = 1;
        int num = 0;
        int temp;
        while (thisBits.size() > 0) {
            temp = index * thisBits.removeFirst().toInt();
            if (index > (Integer.MAX_VALUE / 2) + 1)
                throw new RuntimeException("number is to big to be represented");
            index *= 2;
            if (num > ((Integer.MAX_VALUE / 2) + 1 - temp))
                throw new RuntimeException("number is to big to be represented");
            num += temp;
        }
        if (neg)
            return -1 * num;
        return num;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 3.10 ================================================
    // Do not change this method
    public BinaryNumber multiply(BinaryNumber multiplyMe) {

        if (this.signum() == 0 | multiplyMe.signum() == 0) {
            return new BinaryNumber(0);
        }

        boolean minus = (this.signum() * multiplyMe.signum()) < 0;

        BitList thisBits = new BitList(this.bits);
        BitList otherBits = new BitList(multiplyMe.bits);

        BinaryNumber otherBinaryNumber = new BinaryNumber(otherBits);
        BinaryNumber thisBinaryNumber = new BinaryNumber(thisBits);

        if (otherBinaryNumber.signum() < 0)
            otherBinaryNumber = otherBinaryNumber.negate();

        if (thisBinaryNumber.signum() < 0)
            thisBinaryNumber = thisBinaryNumber.negate();


        BinaryNumber ans = thisBinaryNumber.multiplyPositive(otherBinaryNumber);
        if(minus)
            ans.bits.addLast(Bit.ONE);

        return ans;

    }

    private BinaryNumber multiplyPositive(BinaryNumber multiplyMe) {
        int mult = multiplyMe.toInt();
        this.compareSizes(multiplyMe);
        return this.recMultiplyPositive (new BinaryNumber(this), new BinaryNumber(this), 1, mult);
    }

    private BinaryNumber recMultiplyPositive(BinaryNumber ans, BinaryNumber target, int mults, int total) {
        /*******************************
         * ans - the answer
         * target - the number we multiply
         * mults - how many multiplys we have made so far
         * remain - how many multiplays we still need to do
         ******************************/
        if (total == mults)
            return ans;
        if (mults*2  <= total)
            return recMultiplyPositive(ans.multiplyBy2(), target, mults * 2, total);

        if (mults * 2 > total) {
            BinaryNumber temp = recMultiplyPositive(target, target, 1, total-mults);
            return ans.add(temp);
        }
        return ans;
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




import java.util.LinkedList;
import java.util.Iterator;


public class BitList extends LinkedList<Bit> {
    private int numberOfOnes;

    // Do not change the constructor
    public BitList() {
        numberOfOnes = 0;
    }

    // Do not change the method
    public int getNumberOfOnes() {
        return numberOfOnes;
    }

//----------write your code BELOW this line only!!!---------------------------------------------------------

//=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.1 ================================================

    public void addLast(Bit element) {
        if (element == null)
            throw new IllegalArgumentException("dont try to insert null.");
        if (element.toInt() == 1)
            this.numberOfOnes += 1;
        super.addFirst(element);

    }

    public void addFirst(Bit element) {
        if (element == null)
            throw new IllegalArgumentException("dont try to insert null.");
        if (element.toInt() == 1)
            this.numberOfOnes += 1;
        super.addLast(element);
    }

    public Bit removeLast() {
        Bit temp;
        if (super.size() > 0) {
            temp = super.removeFirst();
            if (temp.toInt() == 1)
                this.numberOfOnes -= 1;
            return temp;
        }
        throw new IllegalArgumentException("list is empty");
    }

    public Bit removeFirst() {
        Bit temp;
        if (super.size() > 0) {
            temp = super.removeLast();
            if (temp.toInt() == 1)
                this.numberOfOnes -= 1;
            return temp;
        }
        throw new IllegalArgumentException("list is empty");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.2 ================================================
    public String toString() {
        String ans = "";
        if (super.size() != 0) {
            Iterator<Bit> iter = this.iterator();
            ans += "<";
            while(iter.hasNext())
                ans += iter.next().toString();
            ans +=">";
        }
        return ans;
    }


    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.3 ================================================
    public BitList(BitList other) {
        if (other == null)
            throw new IllegalArgumentException("dont try to insert null.");
        Iterator<Bit> iter = other.iterator();
        while (iter.hasNext())
            this.addFirst(iter.next());
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.4 ================================================
    public boolean isNumber() {
        if (this.size() >= 1 && (this.numberOfOnes > 0 | this.getLast().toInt() == 0)) {
            if (this.numberOfOnes == 1 & this.getFirst().toInt() == 1)
                return false;
            return true;
        }
        return false;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.5 ================================================
    public boolean isReduced() {
        Bit last;
        Bit prevLast;
        boolean ans = false;
        if(!isNumber())
            throw new IllegalArgumentException("dont try to insert null.");

        if (this.toString().equals( "<0>") | this.toString().equals( "<01>") | this.toString().equals("<10>"))
            return true;
        if (this.size() > 2) {
            last = this.removeLast();
            prevLast = this.removeLast();
            if ((last.toInt() == 1 & prevLast.toInt() == 0) | (last.toInt() == 0 & prevLast.toInt() == 1)) {
                ans =  true;
            }
            if(this.numberOfOnes == 0){
                if ((last.toInt() == 1) & (prevLast.toInt() == 1)) {
                    ans = true;
                }
            }
            this.addLast(prevLast);
            this.addLast(last);
        }
        return ans;
    }

    public void reduce() {
        if(this.isNumber()) {
            while (!this.isReduced())
                this.removeLast();
            return;
        }
        throw  new  IllegalArgumentException("not a number");
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.6 ================================================
    public BitList complement() {
        if(!isNumber())
            throw new IllegalArgumentException("dont try to insert null.");
        BitList temp = new BitList();
        Iterator<Bit> iter = this.iterator();
        while (iter.hasNext())
            temp.addFirst(iter.next().negate());
        return temp;
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.7 ================================================
    public Bit shiftRight() {
        if(!isNumber())
            throw new IllegalArgumentException("dont try to insert null.");
        if(this.size()> 0){
            Bit temp = this.removeFirst();
            return temp;
        }
        return null;
    }

    public void shiftLeft() {
        if(!isNumber())
            throw new IllegalArgumentException("dont try to insert null.");
       this.addFirst(new Bit(0));
    }

    //=========================== Intro2CS 2022/3, ASSIGNMENT 4, TASK 2.8 ================================================
    public void padding(int newLength) {
        if(!isNumber())
            throw new IllegalArgumentException("dont try to insert null.");
        if(this.size() == 0)
            throw new IllegalArgumentException("the BitList is empty cannot pad");
        if(this.size() >= newLength)
            return;

        Bit pad = this.getFirst();
        //this is the lbs due to the flip of the list

        while(this.size() != newLength)
            this.addLast(new Bit(pad.toInt()));
        return;
    }


//----------write your code ABOVE this line only!!!---------------------------------------------------------

    //----------------------------------------------------------------------------------------------------------
    // The following overriding methods must not be changed.
    //----------------------------------------------------------------------------------------------------------
    public boolean add(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public void add(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit remove(int index) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offer(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerFirst(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean offerLast(Bit e) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public Bit set(int index, Bit element) {
        throw new UnsupportedOperationException("Do not use this method!");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Do not use this method!");
    }
}

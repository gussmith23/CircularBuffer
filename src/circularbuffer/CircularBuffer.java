/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circularbuffer;


/**
 *
 * @author hfs5022
 */
public class CircularBuffer implements  Buffer {

    private final int[] buffer = {-1,-1,-1};
    
    private int occupiedCells = 0;
    private int writeIndex = 0;
    private int readIndex = 0;

    @Override
    public synchronized void set(int value) throws InterruptedException {
        while (occupiedCells == buffer.length) {
            System.out.println("Buffer is full; producer waiting.");
            wait();
        }
        
        buffer[writeIndex] = value;
        
        writeIndex = (writeIndex + 1) % buffer.length;
        
        ++occupiedCells;
        displayState("Producer writes " + value);
        notifyAll();
    }

    @Override
    public synchronized int get() throws InterruptedException {
        while (occupiedCells == 0) {
            System.out.println("Buffer is empty; consumer waiting.");
            wait();
        }
        
        int readValue = buffer[readIndex];
        
        readIndex = (readIndex + 1) % buffer.length;
        
        --occupiedCells;
        displayState("Consumer reads " + readValue);
        notifyAll();
        
        return readValue;
    }
    
    public void displayState(String operation) {
        
        System.out.printf("%s%s%d)\n%s", operation,
                " (buffer cells occupied: ", occupiedCells, "buffer cells:  ");
    
        for(int value : buffer)
            System.out.printf(" %2d  ", value);
        
        System.out.print("\n               ");
        
        for (int i = 0; i < buffer.length; i++) 
            System.out.print("---- ");
        
        System.out.print("\n               ");
        
        for(int i = 0; i < buffer.length; i++) {
            if (i == writeIndex && i == readIndex)
                System.out.print(" WR  ");
            else if (i == writeIndex) 
                System.out.print(" W   ");
            else if (i == readIndex) 
                System.out.print("  R  ");
            else 
                System.out.print("     ");
        }
                 
        System.out.println("\n");
        
    }
    
    
}

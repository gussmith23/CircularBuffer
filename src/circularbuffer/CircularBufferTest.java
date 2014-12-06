/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circularbuffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author hfs5022
 */
public class CircularBufferTest {
    
    public static void main(String[] args) {
        
        ExecutorService application = Executors.newCachedThreadPool();
        
        CircularBuffer sharedLocation = new CircularBuffer();
        
        sharedLocation.displayState("Initial State");
        
        application.execute(new Producer(sharedLocation));
        application.execute(new Consumer(sharedLocation));
        
        application.shutdown();
    }
    
}

// Student: Vinh Le
// Class: COEN 283
import java.util.LinkedList;

public class ProducerConsumer {
    static final int N = 5; //buffer size
    static Producer p = new Producer();
    static Consumer c = new Consumer();
    static OurMonitor monitor = new OurMonitor();

    public static void main(String[] args) throws InterruptedException {
        p.start();
        c.start();
    }

    static class Producer extends Thread {
        public void run() {
            int item = 0;
            while (true) {
                try {
                    monitor.insert(item++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            while (true) {
                try {
                    monitor.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class OurMonitor {
        LinkedList<Integer> buffer = new LinkedList<>();

        public synchronized void insert(int val) throws InterruptedException {
            if (buffer.size() == N) go_to_sleep(); //buffer full, sleep
            buffer.add(val);
            System.out.println("Procedure produce value: " + val);
            notify(); // if consumer sleep, wake it up
            Thread.sleep(1000); //easy to check
        }

        public synchronized void remove() throws InterruptedException {
            int val;
            if (buffer.size() == 0) go_to_sleep(); //buffer empty, sleep
            val = buffer.removeFirst();  //fetch item from buffer
            System.out.println("Consumer consumed value: " + val);
            notify(); // if consumer sleep, wake it up
            Thread.sleep(1000); //easy to check
        }

        public void go_to_sleep() {
            try {wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

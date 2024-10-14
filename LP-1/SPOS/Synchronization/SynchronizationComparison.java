package Synchronization;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class ProducerConsumerWithMutex {
    LinkedList<Integer> buffer = new LinkedList<>();
    int capacity = 5;
    final Object mutex = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (mutex) {
                while (buffer.size() == capacity) {
                    mutex.wait(); // Wait if buffer is full
                }
                buffer.add(value);
                System.out.println("Producer produced: " + value);
                value++;
                mutex.notify(); // Notify consumer
            }
            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (mutex) {
                while (buffer.isEmpty()) {
                    mutex.wait(); // Wait if buffer is empty
                }
                int val = buffer.removeFirst();
                System.out.println("Consumer consumed: " + val);
                mutex.notify(); // Notify producer
            }
            Thread.sleep(1000);
        }
    }
}


class ProducerConsumerWithSemaphore {
    LinkedList<Integer> buffer = new LinkedList<>();
    int capacity = 5;
    Semaphore empty = new Semaphore(5); // Semaphore for empty slots
    Semaphore full = new Semaphore(0);  // Semaphore for full slots
    Semaphore mutex = new Semaphore(1); // Semaphore for mutual exclusion

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            empty.acquire(); // Wait for an empty slot
            mutex.acquire(); // Lock the buffer
            buffer.add(value);
            System.out.println("Producer produced: " + value);
            value++;
            mutex.release(); // Unlock the buffer
            full.release();  // Signal that buffer is not empty
            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            full.acquire();  // Wait for a full slot
            mutex.acquire(); // Lock the buffer
            int val = buffer.removeFirst();
            System.out.println("Consumer consumed: " + val);
            mutex.release(); // Unlock the buffer
            empty.release(); // Signal that buffer is not full
            Thread.sleep(1000);
        }
    }
}

public class SynchronizationComparison {

    public static void main(String[] args) throws InterruptedException {
        // Test for Mutex
        ProducerConsumerWithMutex pcMutex = new ProducerConsumerWithMutex();
        Thread producerMutex = new Thread(() -> {
            try {
                pcMutex.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerMutex = new Thread(() -> {
            try {
                pcMutex.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Test for Semaphore
        ProducerConsumerWithSemaphore pcSemaphore = new ProducerConsumerWithSemaphore();
        Thread producerSemaphore = new Thread(() -> {
            try {
                pcSemaphore.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerSemaphore = new Thread(() -> {
            try {
                pcSemaphore.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Running Producer-Consumer with Mutex...");
        producerMutex.start();
        consumerMutex.start();
        Thread.sleep(10000);

        System.out.println("Running Producer-Consumer with Semaphore...");
        producerSemaphore.start();
        consumerSemaphore.start();
        Thread.sleep(10000);
    }
}
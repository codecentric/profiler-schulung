package de.codecentric.training.javaprofiling.deadlock;

class DeadlockThread extends Thread {
	private final int id;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	public DeadlockThread(int id) {
		super("DeadlockThread" + id);
		this.id = id;
		start();
	}

	private void acquireLock1Then2() {
		synchronized (lock1) {
			try {
				sleep(500);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			synchronized (lock2) {
				System.out.println("Thread " + id + " got both locks!");
			}
		}
	}

	private void acquireLock2Then1() {
		synchronized (lock2) {
			synchronized (lock1) {
				System.out.println("Thread " + id + " got both locks!");
			}
		}
	}

	@Override
	public void run() {
		switch (id) {
		case 3:
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			acquireLock1Then2();
			break;
		case 2:
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			acquireLock2Then1();
			break;
		}
	}
}

public class DeadlockTest {
	public static void main(String[] s) throws InterruptedException {
		new DeadlockThread(1);
		new DeadlockThread(2);

		new DeadlockThread(3);
	}
}
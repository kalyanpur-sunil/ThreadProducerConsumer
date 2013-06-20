package org.sunil.threads;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class CarAssemblyLine{
	//This follows FIFO
	//Creates an ArrayBlockingQueue with the given (fixed) capacity 
	private BlockingQueue<Integer> line = new ArrayBlockingQueue<Integer>(10);

	Random random = new Random(100);

	public void producer(){
		try{
			for(int j=0; j<500; j++){
				int i = random.nextInt();
				this.line.put(i);
				System.out.println("Put "+ i);
				Thread.sleep(500);
			}

		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}

	public void consumer(){
		try{
			
			while(!line.isEmpty()){
				//Retrieves and removes the head of this queue, 
				//waiting if necessary until an element becomes available.
				int i = this.line.take();
				System.out.println("took " + i);

				Thread.sleep(5000);
			}
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}

}

class ProducerWorker implements Runnable{
	CarAssemblyLine assemblyLine;

	public ProducerWorker(CarAssemblyLine line){
		this.assemblyLine = line;
	}

	public void run(){
		assemblyLine.producer();
	}
}

class ConsumerWorker implements Runnable{
	CarAssemblyLine assemblyLine;

	public ConsumerWorker(CarAssemblyLine line){
		this.assemblyLine = line;
	}

	public void run(){
		assemblyLine.consumer();
	}
}

public class Application1 {

	public static void main(String []args){
		CarAssemblyLine line = new CarAssemblyLine();
		
		Thread producerThread = new Thread(new ProducerWorker(line));

		Thread consumerThread = new Thread(new ConsumerWorker(line));

		producerThread.start();

		consumerThread.start();

		try{
			producerThread.join();
			consumerThread.join();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

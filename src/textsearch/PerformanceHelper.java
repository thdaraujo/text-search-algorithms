package textsearch;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class PerformanceHelper {
	private long startTime;
	private long stopTime;
	private String algorithm;
	private String fileName;
	private boolean running = false;
	
	public PerformanceHelper(String algorithm, String fileName){
		this.algorithm = algorithm;
		this.fileName = fileName;
	}
	
	public PerformanceHelper(String fileName){
		this.fileName = fileName;
	}
	
	public void setAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}
	
	public void start(){
		this.running = true;
		this.startTime = System.nanoTime();
	}
	
	public void stop(){
		this.running = false;
		this.stopTime = System.nanoTime();
	}
	
	public void reset(){
		this.startTime = 0;
		this.stopTime = 0;
		this.running = false;
	}
	
	public long getRunningTime(){
		return this.stopTime - this.startTime;
	}
	
	public String getRunningTimeString(){
		long elapsedTime = this.getRunningTime();
		double ms = (double)elapsedTime / 1000000;
		return String.format("%.3fms", ms);
	}
	
	public void write(){
		try{
			FileWriter fw = new FileWriter(this.fileName, true);
			fw.write(this.toCSVString() + System.lineSeparator());
			fw.close();
		}
		catch(IOException ex){
			System.err.println("IOException" + ex.getMessage());
		}
	}
	
	public void print(){
		System.out.println(this.toCSVString());
	}
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	private String toCSVString(){
		String now = new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime());
		return MessageFormat.format("{0};{1};{2};{3};{4};{5}", 
				now, 
				this.algorithm, 
				this.startTime, 
				this.stopTime, 
				this.getRunningTime(), 
				this.getRunningTimeString());
	}
	
	public String toString(){
		String now = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Calendar.getInstance().getTime());
		return MessageFormat.format("{0} | Algorithm: {1} | Running Time: {2}", 
				now, 
				this.algorithm, 
				this.getRunningTimeString());
	}
}

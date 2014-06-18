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
	private String inputFileName;
	private String outputFileName;
	private String pattern;
	private boolean running = false;
	private boolean found = false;
	
	
	public PerformanceHelper(String inputFileName, String pattern, String outputFileName){
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.pattern = pattern;
	}
	
	public void setAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}
	
	public void start(){
		this.running = true;
		this.startTime = System.nanoTime();
	}
	
	public void stop(boolean found){
		this.running = false;
		this.stopTime = System.nanoTime();
		this.found = found;
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
			FileWriter fw = new FileWriter(this.outputFileName, true);
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
		String now = new SimpleDateFormat("yyyyMMdd HHmmss").format(Calendar.getInstance().getTime());
		return MessageFormat.format("{0};{1};{2};{3};{4};{5};{6};{7};{8}", 
				now, 
				this.algorithm,
				this.inputFileName,
				this.pattern, 
				this.found,
				this.startTime, 
				this.stopTime, 
				this.getRunningTime(), 
				this.getRunningTimeString());
	}
	
	public String toString(){
		String now = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(Calendar.getInstance().getTime());
		return MessageFormat.format("{0} | Algorithm: {1} | File: {2} | Pattern: {3} | Found: {4} | Running Time: {5}", 
				now, 
				this.algorithm, 
				this.inputFileName,
				this.pattern,
				this.found,
				this.getRunningTimeString());
	}
}

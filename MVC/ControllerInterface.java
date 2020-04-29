
  
public interface ControllerInterface {//Controller策略模式，把Controller插進View讓View聰明些
	void start(); //View能夠調用的Controller都在這
	void stop();
	void increaseBPM();
	void decreaseBPM();
 	void setBPM(int bpm);
}

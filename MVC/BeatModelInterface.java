
  
public interface BeatModelInterface {
	void initialize();//BeatModel初始化
  
	void on();
  
	void off();
  
        void setBPM(int bpm);//此方法設定BPM，調用此方法後BPM頻率馬上改變
  
	int getBPM();
  
	void registerObserver(BeatObserver o);
  
	void removeObserver(BeatObserver o);
  
	void registerObserver(BPMObserver o);
  
	void removeObserver(BPMObserver o);
}




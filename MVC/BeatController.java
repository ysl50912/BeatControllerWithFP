
  
public class BeatController implements ControllerInterface {
	BeatModelInterface model; //Controller必須同時接觸View and model
	DJView view;
   
	public BeatController(BeatModelInterface model) {
		this.model = model;
		view = new DJView(this, model);  //把Controller當成參數傳進View的建構式中
        view.createView();
        view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}
  
	public void start() {                   //選單選擇Start時，Controller調用model的on()
		model.on();
		view.disableStartMenuItem();    // 介面改變
		view.enableStopMenuItem();
	}
  
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}
    
	public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
	}
    
	public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
  	}
  
 	public void setBPM(int bpm) {   //使用者介面設定任意BPM值，Controller直接把值設定給model
		model.setBPM(bpm);
	}
}




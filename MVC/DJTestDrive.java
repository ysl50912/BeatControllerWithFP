
  
public class DJTestDrive {

    public static void main (String[] args) {
        BeatModelInterface model = new BeatModel();
	ControllerInterface controller = new BeatController(model);  
        //建立Controller傳給Model
    }
}

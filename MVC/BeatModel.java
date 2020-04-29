

import javax.sound.midi.*;

import java.util.*;

public class BeatModel implements BeatModelInterface, MetaEventListener {
	Sequencer sequencer;//Sequencer 知道如何產生真實節拍
	ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
        //ArrayList持有兩位觀察者
	ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	int bpm = 90;
	Sequence sequence;
	Track track;

	public void initialize() {
		setUpMidi(); //設定MIDI
		buildTrackAndStart();//設定節拍音軌
	}

	public void on() {
		System.out.println("Starting the sequencer");
		sequencer.start();
		setBPM(90);
	}

	public void off() {
		setBPM(0);
		sequencer.stop(); 
	}

	public void setBPM(int bpm) {//Controller用此方法處理節拍
		this.bpm = bpm;   //設定bpm實體變數
		sequencer.setTempoInBPM(getBPM()); //要求定序器改變BPM
		notifyBPMObservers();  //通知所有BPM的觀察者，告知BPM改變
	}

	public int getBPM() {
		return bpm;
	}

	void beatEvent() {
		notifyBeatObservers();
	}

        //註冊觀察者，通知觀察者
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void notifyBeatObservers() {
	beatObservers.forEach(BeatObserver::updateBeat);	
            //for(int i = 0; i < beatObservers.size(); i++) {
			//BeatObserver observer = (BeatObserver)beatObservers.get(i);
			//observer.updateBeat();
		//}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
	beatObservers.forEach(BeatObserver::updateBeat);	
            //for(int i = 0; i < bpmObservers.size(); i++) {
			//BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			//observer.updateBPM();
		//}
	}


	public void removeObserver(BeatObserver o) {
		beatObservers.remove(o);
            //int i = beatObservers.indexOf(o);
		//if (i >= 0) {
			//beatObservers.remove(i);
		//}
	}



	public void removeObserver(BPMObserver o) {
		bpmObservers.remove(o);
            //int i = bpmObservers.indexOf(o);
		//if (i >= 0) {
			//bpmObservers.remove(i);
		//}
	}


	public void meta(MetaMessage message) {
		if (message.getType() == 47) {
			beatEvent();
			sequencer.start();
			setBPM(getBPM());
		}
	}
        //處理MIDI
       
	public void setUpMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public void buildTrackAndStart() {
		int[] trackList = {35, 0, 46, 0};

		sequence.deleteTrack(null);
		track = sequence.createTrack();

		makeTracks(trackList);
		track.add(makeEvent(192,9,1,0,4));      
		try {
			sequencer.setSequence(sequence);                    
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

	public void makeTracks(int[] list) {        

		for (int i = 0; i < list.length; i++) {
			int key = list[i];

			if (key != 0) {
				track.add(makeEvent(144,9,key, 100, i));
				track.add(makeEvent(128,9,key, 100, i+1));
			}
		}
	}

	public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);

		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return event;
	}
        }




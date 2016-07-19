import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MiniMusicPlayer3{
	static JFrame f=new JFrame("My First Music Video");
	static MyDrawPanel ml;
	public static void main(String[] args) {
		MiniMusicPlayer3 mini=new MiniMusicPlayer3();
		mini.go();
	}
	
	public void setUpGui(){
		ml=new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(30,30,300,300);
		f.setVisible(true);
	}
	
	public void go() {
		// TODO Auto-generated method stub
		setUpGui();
		
		try {
			Sequencer sequencer=MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(ml, new int[] {127});
			Sequence seq=new Sequence(Sequence.PPQ, 4);
			Track track=seq.createTrack();
			
			int r= 0;
			for(int i= 0; i< 60;i+= 4){
				r=(int)((Math.random()*50)+1);
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, r, 100, i+2));
			}
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		// TODO Auto-generated method stub
		MidiEvent event=null;
		try {
			ShortMessage aMessage=new ShortMessage();
			aMessage.setMessage(comd,chan,one,two);
			event=new MidiEvent(aMessage, tick);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return event;
	}
	public class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg=false;
		public void  controlChange(ShortMessage event) {
			msg=true;
			repaint();
		}
		public void paintComponent(Graphics graphics) {
			if (msg) {
				Graphics2D graphics2d=(Graphics2D)graphics;
				
				int r=(int)(Math.random()*255);
				int g=(int)(Math.random()*255);
				int b=(int)(Math.random()*255);
				graphics.setColor(new Color(r, g, b));
				
				int ht=(int)((Math.random()*120)+10);
				int width=(int)((Math.random()*120)+10);
				int x=(int)((Math.random()*40)+10);
				int y=(int)((Math.random()*40)+10);
				graphics.fillRect(x, y, width, ht);
				msg=false;
			}
		}
	}

}

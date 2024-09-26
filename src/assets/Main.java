package assets;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main {
	public static void main(String[] args) {
		
		try {
			// aplicamos el tema Flatlaf
			UIManager.setLookAndFeel(new FlatIntelliJLaf());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new MeteoAppGUI();
				
				
//				debug statements
//				System.out.println(MeteoApp.getLocationData("Ribeira"));
//				System.out.println(MeteoApp.getCurrentTime());
				
			}
			
		});
	}

}

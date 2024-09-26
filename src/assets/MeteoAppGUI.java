package assets;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

public class MeteoAppGUI extends JFrame {

	private static final long serialVersionUID = -5187127477523315495L;
	private JSONObject meteoData;

	public MeteoAppGUI() {
		super("MeteoApp");
		this.setSize(450, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setResizable(false);
		try {
			Image icono = ImageIO.read(getClass().getResource("/MeteoApp.png"));
			this.setIconImage(icono);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//añadir los diferentes componentes al marco
		addComponentesGUI();
		this.setVisible(true);

	}

	private void addComponentesGUI() {
		JTextField searchBox = new JTextField();
		// search box
		searchBox.setBounds(15, 15, 351, 45);
		searchBox.setFont(new Font("Dialog", Font.PLAIN, 24));
		getContentPane().add(searchBox);

		// Imagen de la meteorologia
		JLabel imagenMeteo = new JLabel(loadImage("/cloudy.png"));
		imagenMeteo.setBounds(0, 125, 450, 217);
		getContentPane().add(imagenMeteo);

		// etiqueta para mostrar temperatura
		JLabel textoTemp = new JLabel("10ºC");
		textoTemp.setBounds(0, 350, 450, 54);
		textoTemp.setFont(new Font("Dialog", Font.BOLD, 48));

		// Centrar etiqueta temperatura
		textoTemp.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(textoTemp);

		// descripción de las condiciones meteorológicas
		JLabel meteoDescrip = new JLabel("Cloudy");
		meteoDescrip.setBounds(0, 405, 450, 36);
		meteoDescrip.setFont(new Font("Dialog", Font.PLAIN, 32));
		meteoDescrip.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(meteoDescrip);

		// imagen nivel humedad
		JLabel imagenHumedad = new JLabel(loadImage("/humidity.png"));
		imagenHumedad.setBounds(15, 500, 74, 66);
		getContentPane().add(imagenHumedad);

		// descripcion de la humedad
		JLabel textoHumedad = new JLabel("<html><b>Humedad:</b><br>100%</html>");
		textoHumedad.setBounds(90, 500, 85, 55);
		textoHumedad.setFont(new Font("Dialog", Font.PLAIN, 16));
		getContentPane().add(textoHumedad);

		// imagen de la velocidad del viento
		JLabel imagenViento = new JLabel(loadImage("/windspeed.png"));
		imagenViento.setBounds(243, 500, 74, 66);
		getContentPane().add(imagenViento);

		// texto descriptivo sobre el viento
		JLabel textoViento = new JLabel("<html><b>Viento:</b><br>15km/h</html>");
		textoViento.setBounds(327, 500, 85, 55);
		textoViento.setFont(new Font("Dialog", Font.PLAIN, 16));
		getContentPane().add(textoViento);

		// search Button
		JButton searchButton = new JButton(loadImage("/search.png"));
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userInput = searchBox.getText();

				if (userInput.replaceAll("\\s", "").length() <= 0) {
					return;
				}

				meteoData = MeteoApp.getWeatherData(userInput);

				// actualizar condiciones Meteorologicas
				String condMeteo = (String) meteoData.get("weather_condition");

				switch (condMeteo) {
				case "Despejado":
					imagenMeteo.setIcon(loadImage("/clear.png"));
					break;
				case "Nublado":
					imagenMeteo.setIcon(loadImage("/cloudy.png"));
					break;
				case "Lluvia":
					imagenMeteo.setIcon(loadImage("/rain.png"));
					break;
				case "Nieve":
					imagenMeteo.setIcon(loadImage("/snow.png"));
					

				}
				
				//actualizar datos temperatura
				
				double temp = (double) meteoData.get("temperature");
				textoTemp.setText(temp + "ºC");
				
				//actualizar texto condiciones meteo
				meteoDescrip.setText(condMeteo);
				
				//actualizar datos humedad
				long humedad = (long) meteoData.get("humidity");
				textoHumedad.setText("<html><b>Humedad: "+humedad+"</b>%</html");
				
				//actualizar datos viento
				double viento = (double) meteoData.get("windspeed");
				textoViento.setText("<html><b>Viento:</b><br> "+viento+"km/h</html");
				

			}

		});
		// cambiar cursor cuando "Hovering" sobre boton
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);

		getContentPane().add(searchButton);
	}

	// metodo para crear imagen en boton
	private ImageIcon loadImage(String resourcePath) {
		try {
			BufferedImage image = ImageIO.read(getClass().getResource(resourcePath));

			return new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}

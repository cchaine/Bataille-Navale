package graphics;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Display {
	
	public static JFrame frame;
	public static JWindow loading;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	private JProgressBar progressBar;
	
	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		loadingWindow();
	}
	
	private void loadingWindow()
	{
		loading = new JWindow();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loading.setSize(width / 2, height / 2);
		loading.setLocationRelativeTo(null);
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		Dimension prefSize = progressBar.getPreferredSize();
		prefSize.height = height;
		progressBar.setPreferredSize(prefSize);
		progressBar.setStringPainted(true);
		loading.add(progressBar);
		loading.setVisible(true);
	}
	
	public void createDisplay()
	{
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)  ;
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public JProgressBar getProgressBar()
	{
		return progressBar;
	}
	
	public JWindow loading()
	{
		return loading;
	}
	
	
}

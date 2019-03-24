package Gridding;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class ImageDisplay extends JFrame
{
	BufferedImage image;
	private JPanel contentPane;
	private final int width = 1024;
	private final int height = 1024;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ImageDisplay frame = new ImageDisplay();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImageDisplay()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1041, 1053);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void generateImage()
	{
		/*
		 * Add code to load in image
		 */
		int[] data = new int[height*width];
		for (int i : data)
		{
			
		}
	}
	
	private void mapColour(float value, float min, float max)
	{
		
		value
	}
}

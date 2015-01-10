package com.benoitbranchard.bpr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Frame extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private final JTextField	jtf					= new JTextField()
														{
															private static final long	serialVersionUID	= 1L;
															{
																setPreferredSize(new Dimension(100, 26));
																addMouseListener(new MouseListener()
																	{
																		public void mouseClicked(MouseEvent e)
																		{
																		}
																		
																		public void mousePressed(MouseEvent e)
																		{
																			setText("");
																		}
																		
																		public void mouseReleased(MouseEvent e)
																		{
																		}
																		
																		public void mouseEntered(MouseEvent e)
																		{
																		}
																		
																		public void mouseExited(MouseEvent e)
																		{
																		}
																	});
															}
														};
	private final JButton		jbtn				= new JButton("Valider");
	private final java.awt.List	awtList				= new java.awt.List();
	private final WordList		wd					= new WordList("liste_francais.txt");
	private final Clipboard		cp					= Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public Frame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setTitle("BombPartyResolver");
		this.setLayout(new BorderLayout());
		this.getContentPane().add(jtf, BorderLayout.WEST);
		this.getContentPane().add(jbtn, BorderLayout.EAST);
		this.getContentPane().add(awtList, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(jbtn);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		jbtn.addActionListener(this);
		awtList.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent ie)
				{
					setToClipboard(awtList.getSelectedItem());
				}
			});
	}
	
	public void setToClipboard(String s)
	{
		cp.setContents(new StringSelection(s), new ClipboardOwner()
			{
				@Override
				public void lostOwnership(Clipboard clipboard, Transferable contents)
				{
					// TODO Auto-generated method stub
				}
			});
		wd.remove(s);// moves s at the end
		wd.add(s);
		System.out.println(s + " copied to clipboard.");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == jbtn)
		{
			String tx = WordList.removeAccent(jtf.getText().toLowerCase());
			if(tx.length() > 1)
			{
				awtList.removeAll();
				for(String t : wd)
				{
					if(t.contains(tx))
					{
						awtList.add(t);
					}
				}
				if(awtList.getItemCount() > 0)
				{
					setToClipboard(awtList.getItem(0));
				}
			}
		}
	}
}

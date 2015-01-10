package com.benoitbranchard.bpr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;

public class WordList extends ArrayList<String>
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public WordList(String path)
	{
		super();
		try
		{
			InputStream ips = Main.class.getClassLoader().getResourceAsStream(path);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			while((line = br.readLine()) != null)
			{
				this.add(removeAccent(line.toLowerCase()));
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		Collections.sort(this, new StringComparator(""));
	}
	
	public static String removeAccent(String source)
	{
		return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
	}
}
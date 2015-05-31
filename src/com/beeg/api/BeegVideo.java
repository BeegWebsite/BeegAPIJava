package com.beeg.api;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class BeegVideo {
	
	public enum BeegQuality{Best, Good, Fast}
	
	private String ID, source;
	
	public BeegVideo(String ID){
		this.ID = ID;
		Scanner sc = null;
		try {
			sc = new Scanner(new URL("http://www.beeg.com/" + ID).openStream(), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		source = sc.useDelimiter("\\A").next();
		sc.close();
	}
	
	public String getTitle(){
		
		Matcher matcher = Pattern.compile("<title>(.+)</title>").matcher(source);
		matcher.find();
		String title = matcher.group(1);
		return title.substring(0, title.length() - 8);
	}
	
	public String getDescription(){
		Pattern p = Pattern.compile("<td class=\"synopsis more\" colspan=\"2\">(.*)</td>");
		Matcher matcher = p.matcher(source);
		matcher.find();
		String description = matcher.group(1);
		return description;
	}
	
	public Image getThumbnail() throws MalformedURLException, IOException{
		BufferedImage img;
		img = ImageIO.read(new URL("http://img.beeg.com/236x177/" + ID + ".jpg"));
		return img;
	}
	
	public Image getThumbnail(int width, int height) throws MalformedURLException, IOException{
		BufferedImage img;
		img = ImageIO.read(new URL("http://img.beeg.com/" + width + "x" + height + "/" + ID + ".jpg"));
		return img;
	}
	
	public String getThumbnailURL(){
		return "http://img.beeg.com/236x177/" + ID + ".jpg";
	}
	
	public String getThumbnailURL(int width, int height){
		return "http://img.beeg.com/" + width + "x" + height + "/" + ID + ".jpg";
	}
	
	public BeegQuality getBestQuality(){
		Matcher matcher = Pattern.compile("'720p': '(.*)'").matcher(source);
		if(matcher.find())
			return BeegQuality.Best;
		matcher = Pattern.compile("'480p': '(.*)'").matcher(source);
		if(matcher.find())
			return BeegQuality.Good;
		return BeegQuality.Fast;
	}
	
	public int getBestQualityInPixels(){
		BeegQuality bq = getBestQuality();
		return ((bq == BeegQuality.Best)?720:((bq == BeegQuality.Good)?480:240));
	}
	
	public String getURL(BeegQuality quality){
		String url = "";
		if(quality == BeegQuality.Best){
	       	Matcher matcher = Pattern.compile("'720p': '(.*)'").matcher(source);
	       	if(matcher.find())
	      		url = matcher.group(1);
		}
		if(quality == BeegQuality.Good || (url == "" && quality == BeegQuality.Best)){
    		Matcher matcher = Pattern.compile("'480p': '(.*)'").matcher(source);
    		if(matcher.find())
    			url = matcher.group(1);
        }
        if(quality == BeegQuality.Fast || url == ""){
        	Matcher matcher = Pattern.compile("'240p': '(.*)'").matcher(source);
    		matcher.find();
    		url = matcher.group(1);
        }
        return url;
	}

	public String getCasting(){
		Pattern p = Pattern.compile("<th>Cast</th>\\s*<td>(.*)</td>");
		Matcher matcher = p.matcher(source);
		matcher.find();
		String cast = matcher.group(1);
		return cast;
	}
	
	public String getPublishedDate(){
		Pattern p = Pattern.compile("<th>Published</th>\\s*<td>(.*)</td>");
		Matcher matcher = p.matcher(source);
		matcher.find();
		String date = matcher.group(1);
		return date;
	}
		
	public String getID()
    {
        return ID;
    }
	
	
}

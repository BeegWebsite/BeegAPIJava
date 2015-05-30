package com.beeg.api;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class BeegVideo {
	
	public enum BeegQuality{Best, Good, Fast}
	
	private String ID, source;
	private float percentage;
	
	public BeegVideo(String ID) throws Exception{
		this.ID = ID;
		Scanner sc = new Scanner(new URL("http://www.beeg.com/" + ID).openStream(), "UTF-8");
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
	
	public String getURLThumbnail(){
		return "http://img.beeg.com/236x177/" + ID + ".jpg";
	}
	
	public String getURLThumbnail(int width, int height){
		return "http://img.beeg.com/" + width + "x" + height + "/" + ID + ".jpg";
	}
	
	
	public BeegQuality getBestQuality(){
		Matcher matcher = Pattern.compile("'720p': '(?<Video>[\\s\\S]*?)'").matcher(source);
		if(matcher.find())
			return BeegQuality.Best;
		matcher = Pattern.compile("'480p': '(?<Video>[\\s\\S]*?)'").matcher(source);
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
	       	Matcher matcher = Pattern.compile("'720p': '(?<Video>[\\s\\S]*?)'").matcher(source);
	       	if(matcher.find())
	      		url = matcher.group(1);
		}
		if(quality == BeegQuality.Good || (url == "" && quality == BeegQuality.Best)){
    		Matcher matcher = Pattern.compile("'480p': '(?<Video>[\\s\\S]*?)'").matcher(source);
    		if(matcher.find())
    			url = matcher.group(1);
        }
        if(quality == BeegQuality.Fast || url == ""){
        	Matcher matcher = Pattern.compile("'240p': '(?<Video>[\\s\\S]*?)'").matcher(source);
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
	
	public float getDownloadPercentage(){
		return percentage;
	}
	
	public void download(String path, BeegQuality quality, StandardCopyOption option) throws Exception {
		downloadUsingStream(getURL(quality), path + "/" + ID + ".mp4");
	}
	
	public void download(String path, String name, BeegQuality quality, StandardCopyOption option) throws Exception{
		downloadUsingStream(getURL(quality), path + "/" + name + ".mp4");
	}
	
	
	private void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
}

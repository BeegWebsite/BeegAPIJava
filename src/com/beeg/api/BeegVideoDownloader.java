package com.beeg.api;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.beeg.api.BeegVideo.BeegQuality;

public class BeegVideoDownloader {

	private String ID;
	
	private BeegVideo video;
	
	private DownloadEventListener listener;
	
	public BeegVideoDownloader(String ID){
		this.ID = ID;
		video = new BeegVideo(ID);
	}
	
	public BeegVideo getVideo(){
		return video;
	}
	
	public void addDownloadEventListener(final DownloadEventListener listener){
		this.listener = listener;
	}
	
	public void download(String path, BeegQuality quality) throws Exception {
		downloadFromLink(video.getURL(quality), path + "/" + ID + ".mp4");
	}
	
	public void download(String path, String name, BeegQuality quality) throws Exception{
		downloadFromLink(video.getURL(quality), path + "/" + name + ".mp4");
	}
	
	private void downloadFromLink(String urlStr, String file) throws IOException{
		    BufferedInputStream in = null;
		    FileOutputStream out = null;
		    int percentage = 0, nperc = 0;
		    try {
		        URL url = new URL(urlStr);
		        URLConnection conn = url.openConnection();
		        int size = conn.getContentLength();
		        in = new BufferedInputStream(url.openStream());
		        out = new FileOutputStream(file);
		        byte data[] = new byte[1024];
		        int count;
		        double sumCount = 0.0;
		        while ((count = in.read(data, 0, 1024)) != -1) {
		            out.write(data, 0, count);

		            sumCount += count;
		            if (size > 0) {
		            	nperc = (int) (sumCount / size * 100.0);
		            	if(percentage != nperc)
		            		listener.ProgressChanged(nperc);
		            	percentage = nperc;
		            }
		        }
		    } catch (MalformedURLException e1) {
		        e1.printStackTrace();
		    } catch (IOException e2) {
		        e2.printStackTrace();
		    } finally {
		        if (in != null)
		        	listener.Completed();
		            try {
		                in.close();
		            } catch (IOException e3) {
		                e3.printStackTrace();
		            }
		        if (out != null)
		            try {
		                out.close();
		            } catch (IOException e4) {
		                e4.printStackTrace();
		            }
		    }		    
		}
	
}

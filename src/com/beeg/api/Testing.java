package com.beeg.api;

import java.nio.file.StandardCopyOption;

import com.beeg.api.BeegVideo.BeegQuality;

public class Testing {

	public static void main(String[] args) throws Exception{

		BeegVideo video;
		
		video = new BeegVideo("7853226");
		
		System.out.println(video.getTitle());
		
		video.download("C:/Ants/BeegAPI", BeegQuality.Fast, StandardCopyOption.REPLACE_EXISTING);

	}

}

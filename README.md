# BeegAPIJava
Beeg API for Java

## Why ?

Because Beeg is a fucking awesome website.

## How to use ?

#### • BeegVideo

```java
//Declare new BeegVideo
BeegVideo video = new BeegVideo("7241201");

//Load all information
video.load();

//Show the title
System.out.println("Title : " + video.getTitle());

//Show the description
System.out.println("Description : " + video.getDescription());

//Show the published date
System.out.println("Published date : " + video.getPublishedDate());

//Show the casting
System.out.println("Casting : " + video.getCasting());

//Show the video URL on good (480p) quality
System.out.println("Video URL : " + video.getURL(BeegVideo.BeegQuality.Good));

//Show the thumbnail URL
System.out.println("Thumbnail URL : " + video.getURLThumbnail());

//Get the thumbnail as an image
Image thumbnail = video.getThumbnail();

//Save the video as mp4
video.download("C:/downloads", BeegVideo.BeegQuality.Best, StandardCopyOption.REPLACE_EXISTING);
	
//Show the best quality available (240/360/720 or -1 if error)
System.out.println("Best quality (pixels) : " + video.getBestQualityInPixels());

//Show the best quality available (fast/good/best or null if error)
System.out.println("Best quality (BeegQuality) : " + video.getBestQuality());
```

#### • BeegVideoDownloader

```java
//Declare new BeegVideoDownloader
BeegVideoDownloader videoDownloader = new BeegVideoDownloader("7241201");
//Or
BeegVideoDownloader videoDownloader = new BeegVideoDownloader(new BeegVideo("7241201"));
	
//Add new DownloadEventListener
videoDownloader.addDownloadEventListener(new DownloadEventListener(){
	
	//Download complete
	@Override
	protected void Completed() {
		System.out.println("Download complete");
	}

	//Download progress
	@Override
	protected void ProgressChanged(int percentage) {
		System.out.println("Download progress : " + percentage + "%");
	}
	
});
		
//Start the download
videoDownloader.download("C:/downloads", videoDownloader.getVideo().getBestQuality());
```
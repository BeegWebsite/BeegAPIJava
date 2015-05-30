# BeegAPIJava
Beeg API for Java

## Why ?

Because Beeg is a fucking awesome website.

## How to use ?

```java
//Declare new BeegVideo
BeegVideo video = new BeegVideo("7241201");
		
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
		
//Show the best quality available (240/360/720)
System.out.println(video.getBestQualityInPixels());

//Show the best quality available (fast/good/best)
System.out.println(video.getBestQuality());
<<<<<<< HEAD
```
=======
```
>>>>>>> 76e78366b3ac47c4e7cf1fc83538c552f7336108

package com.beeg.api;

public abstract class DownloadEventListener {

	protected abstract void ProgressChanged(int percentage);
	protected abstract void Completed();
	
}

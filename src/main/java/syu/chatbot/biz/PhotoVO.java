package syu.chatbot.biz;

public class PhotoVO
{
	private String url;
	private int width;
	private int height;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "PhotoVO [url=" + url + ", width=" + width + ", height=" + height + "]";
	}
	
	
}

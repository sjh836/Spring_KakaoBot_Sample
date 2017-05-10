package syu.chatbot.biz;

public class MessageButtonVO
{
	private String label;
	private String url;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "MessageButtonVO [label=" + label + ", url=" + url + "]";
	}
}

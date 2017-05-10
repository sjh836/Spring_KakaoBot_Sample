package syu.chatbot.biz;

public class MessageVO
{
	private String text;
	private PhotoVO photo;
	private MessageButtonVO message_button;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public PhotoVO getPhoto() {
		return photo;
	}
	public void setPhoto(PhotoVO photo) {
		this.photo = photo;
	}
	public MessageButtonVO getMessage_button() {
		return message_button;
	}
	public void setMessage_button(MessageButtonVO message_button) {
		this.message_button = message_button;
	}
	@Override
	public String toString() {
		return "MessageVO [text=" + text + ", photo=" + photo + ", message_button=" + message_button + "]";
	} 
}

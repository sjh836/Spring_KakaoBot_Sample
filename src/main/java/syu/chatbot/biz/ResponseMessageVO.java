package syu.chatbot.biz;

public class ResponseMessageVO
{
	private MessageVO message;
	private KeyboardVO keyboard;
	
	public MessageVO getMessage() {
		return message;
	}
	public void setMessage(MessageVO message) {
		this.message = message;
	}
	public KeyboardVO getKeyboard() {
		return keyboard;
	}
	public void setKeyboard(KeyboardVO keyboard) {
		this.keyboard = keyboard;
	}
	
	@Override
	public String toString() {
		return "ResponseMessageVO [message=" + message + ", keyboard=" + keyboard + "]";
	}
}

package com.scmd.socialmedia.entity;
 
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name ="Messages")
public class Messages 
{
	@Id
    @Column(name = "messageID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageID;
 
    @ManyToOne
    @JoinColumn(name = "senderID")
    private Users sender;
 
    @ManyToOne
    @JoinColumn(name = "receiverID")
    private Users receiver;
    @Column(name="message_text")
    private String messageText;
    @CreationTimestamp
    @Column(name="timestamp",columnDefinition = "TIMESTAMP")
    private Timestamp timestamp;
	private boolean is_deleted = false; // Flag for soft deletion
    public boolean isIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public Messages() {}
    public Messages(int messageID, String messageText, Timestamp timestamp,boolean is_deleted) 
    {
		this.messageID = messageID;
		this.messageText = messageText;
		this.timestamp = timestamp;
		this.is_deleted = is_deleted;
	}
	public int getMessageID() 
	{
		return messageID;
	}
	public void setMessageID(int messageID) 
	{
		this.messageID = messageID;
	}
	public Users getSender() 
	{
		return sender;
	}
	public void setSender(Users sender) 
	{
		this.sender = sender;
	}
	public Users getReceiver() 
	{
		return receiver;
	}
	public void setReceiver(Users receiver) 
	{
		this.receiver = receiver;
	}
	public String getMessageText() 
	{
		return messageText;
	}
	public void setMessageText(String messageText) 
	{
		this.messageText = messageText;
	}
	public Timestamp getTimestamp() 
	{
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) 
	{
		this.timestamp = timestamp;
	}
}
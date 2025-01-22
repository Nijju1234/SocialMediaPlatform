package com.scmd.socialmedia.services;
 
import com.scmd.socialmedia.dto.MessagesDTO;
import java.util.List;
 
public interface MessagesService {
 
    List<MessagesDTO> getAllMessages(); // Retrieve a list of all messages.
 
    MessagesDTO getMessageById(int messageID); // Retrieve a message by its ID.
 
    MessagesDTO saveMessage(MessagesDTO msg); // Create a new message.
 
    MessagesDTO updateMessageById(int messageID, MessagesDTO updatedMessage); // Update an existing message by its ID.
 
    void deleteMessageById(int messageID); // Delete a message by its ID (soft delete).
}
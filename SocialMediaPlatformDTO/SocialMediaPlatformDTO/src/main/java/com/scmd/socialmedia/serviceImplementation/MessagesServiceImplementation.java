package com.scmd.socialmedia.serviceImplementation;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.entity.Messages;
import com.scmd.socialmedia.entity.Users;
 
import com.scmd.socialmedia.exception.MessageIdNotFoundException;
import com.scmd.socialmedia.exception.MessageIdAlreadyExistException;
import com.scmd.socialmedia.exception.MessageNotFoundException;
import com.scmd.socialmedia.exception.UserNotFoundException;
import com.scmd.socialmedia.repositories.MessagesRepository;
import com.scmd.socialmedia.repositories.UsersRepository;
import com.scmd.socialmedia.services.MessagesService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MessagesServiceImplementation implements MessagesService {
    @Autowired
    private MessagesRepository messageRepo;
    @Autowired
    private UsersRepository userRepo;  // Ensure this is autowired
    // Convert an entity to DTO
    private MessagesDTO convertToDTO(Messages message) {
        return new MessagesDTO(
                message.getMessageID(),
                message.getSender().getUserID(),
                message.getReceiver().getUserID(),
                message.getMessageText(),
                message.getTimestamp(),
                message.isIs_deleted()
        );
    }
    private Messages convertToEntity(MessagesDTO messageDTO) {
        Users sender = userRepo.findById(messageDTO.getSenderID())
                .orElseThrow(() -> new UserNotFoundException("Sender with ID " + messageDTO.getSenderID() + " not found"));
        Users receiver = userRepo.findById(messageDTO.getReceiverID())
                .orElseThrow(() -> new UserNotFoundException("Receiver with ID " + messageDTO.getReceiverID() + " not found"));
        Messages message = new Messages();
        message.setMessageID(messageDTO.getMessageID());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageText(messageDTO.getMessageText());
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return message;
    }
    @Override
    public List<MessagesDTO> getAllMessages() {
        List<Messages> messages = messageRepo.findAll();
        if (messages.isEmpty()) {
            throw new MessageNotFoundException("No messages found.");
        }
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Override
    public MessagesDTO getMessageById(int messageID) {
        Messages message = messageRepo.findById(messageID)
                .orElseThrow(() -> new MessageIdNotFoundException("Message not found"));
        return convertToDTO(message);
    }
    @Override
    public MessagesDTO saveMessage(MessagesDTO msgDTO) {
        if (messageRepo.existsById(msgDTO.getMessageID())) {
            throw new MessageIdAlreadyExistException("Message already exists");
        }
        Messages message = convertToEntity(msgDTO);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Messages savedMessage = messageRepo.save(message);
        return convertToDTO(savedMessage);
    }
    @Override
    public MessagesDTO updateMessageById(int messageID, MessagesDTO updatedMessageDTO) {
        Messages existingMessage = messageRepo.findById(messageID)
                .orElseThrow(() -> new MessageIdNotFoundException("Message not found"));
        existingMessage.setMessageText(updatedMessageDTO.getMessageText());
        existingMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Messages updatedMessage = messageRepo.save(existingMessage);
        return convertToDTO(updatedMessage);
    }
    @Override
    public void deleteMessageById(int messageID) {
        Messages message = messageRepo.findById(messageID)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));
        message.setIs_deleted(true);
        messageRepo.save(message);
    }
}

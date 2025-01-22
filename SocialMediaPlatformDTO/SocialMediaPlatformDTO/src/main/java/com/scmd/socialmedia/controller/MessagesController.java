package com.scmd.socialmedia.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmd.socialmedia.dto.ApiResponse;
import com.scmd.socialmedia.dto.MessagesDTO;
import com.scmd.socialmedia.services.MessagesService;

import jakarta.validation.Valid;
@RestController

@CrossOrigin(origins = "http://localhost:8089")
@RequestMapping("api/messages")
public class MessagesController {
 
    @Autowired
    private MessagesService service;
 
    // Retrieve a list of all messages.
    @GetMapping
    public ResponseEntity<List<MessagesDTO>> getAllMessages() {
        List<MessagesDTO> messages = service.getAllMessages();
        return ResponseEntity.ok(messages); // Return only the list of messages
    }

    
 // Retrieve a message by its ID.
    @GetMapping("/{messageId}")
    public ResponseEntity<MessagesDTO> getMessageById(@PathVariable("messageId") int messageId) {
        MessagesDTO message = service.getMessageById(messageId);
        return ResponseEntity.ok(message);  // Return only the data (MessagesDTO)
    }


    
 // Create a new message.
    @PostMapping
    public ResponseEntity<MessagesDTO> createMessage(@Valid @RequestBody MessagesDTO messageDTO) {
        MessagesDTO savedMessage = service.saveMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);  // Return only the saved message data
    }

    // Update an existing message by its ID.
    @PutMapping("/{messageId}")
    public ResponseEntity<MessagesDTO> updateMessageById(@PathVariable("messageId") int messageId,
                                                         @Valid @RequestBody MessagesDTO updatedMessageDTO) {
        MessagesDTO updatedMessage = service.updateMessageById(messageId, updatedMessageDTO);
        return ResponseEntity.ok(updatedMessage);  // Return only the updated message data
    }

    // Delete a message by its ID (soft delete).
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable("messageId") int messageId) {
        service.deleteMessageById(messageId);
        return ResponseEntity.noContent().build();  // Return a no content response indicating successful deletion
    }

}
 
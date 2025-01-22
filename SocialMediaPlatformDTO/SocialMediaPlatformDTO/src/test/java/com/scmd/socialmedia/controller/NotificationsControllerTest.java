package com.scmd.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmd.socialmedia.dto.NotificationsDTO;
import com.scmd.socialmedia.services.NotificationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NotificationsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationsService notificationsService;

    @InjectMocks
    private NotificationsController notificationsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationsController).build();
    }

    @Test
    public void testGetNotification() throws Exception {
        NotificationsDTO mockNotification = new NotificationsDTO();
        mockNotification.setUserID(1);
        mockNotification.setContent("Test Notification");

        when(notificationsService.notificationByUser(anyInt(), anyInt())).thenReturn(mockNotification);

        mockMvc.perform(get("/api/users/1/notifications/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.userID").value(1))
                .andExpect(jsonPath("$.data.content").value("Test Notification"));
    }

    @Test
    public void testMarkNotificationAsRead() throws Exception {
        NotificationsDTO mockNotification = new NotificationsDTO();
        mockNotification.setContent("Updated Content");

        when(notificationsService.updateNotification(any(), anyInt())).thenReturn(mockNotification);

        mockMvc.perform(put("/api/users/1/notifications/mark-read/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockNotification)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Notification marked as read successfully"));
    }

    @Test
    public void testGetNotifications() throws Exception {
        NotificationsDTO notification1 = new NotificationsDTO();
        notification1.setContent("Notification 1");

        NotificationsDTO notification2 = new NotificationsDTO();
        notification2.setContent("Notification 2");

        List<NotificationsDTO> notificationsList = List.of(notification1, notification2);

        when(notificationsService.getNotificationsByUser(1)).thenReturn(notificationsList);

        mockMvc.perform(get("/api/users/1/notifications"))  // Corrected URL
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Notification of specific user retrieved successfully"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].content").value("Notification 1"))
                .andExpect(jsonPath("$.data[1].content").value("Notification 2"));
    }

    @Test
    public void testDeleteNotification() throws Exception {
        doNothing().when(notificationsService).deleteNotification(1);

        mockMvc.perform(delete("/api/users/1/notifications/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Notification deleted successfully"));

        verify(notificationsService).deleteNotification(1);
    }
}

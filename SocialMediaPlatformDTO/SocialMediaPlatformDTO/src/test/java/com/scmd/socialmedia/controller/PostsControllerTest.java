//package com.scmd.socialmedia.controller;
// 
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
// 
//import com.scmd.socialmedia.controller.PostsController;
//import com.scmd.socialmedia.entity.Likes;
//import com.scmd.socialmedia.entity.Posts;
//import com.scmd.socialmedia.services.LikesService;
//import com.scmd.socialmedia.services.PostsService;
//
// 
//class PostsControllerTest {
// 
//    @InjectMocks
//    private PostsController postsController;
// 
//    @Mock
//    private PostsService postsService;
// 
//    @Mock
//    private LikesService likesService;
// 
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
// 
//    @Test
//    public void testAddPost() {
//        // Arrange
//        Posts expectedPost = new Posts(); // Create a mock Posts object
//        expectedPost.setPostID(1);
//        expectedPost.setContent("Sample content");
// 
//        Map<String, String> expectedResponse = new LinkedHashMap<>();
//        expectedResponse.put("status", "success");
//        expectedResponse.put("message", "Post created successfully");
// 
//        when(postsService.savepost(any(Posts.class))).thenReturn(expectedPost); // Mock behavior
// 
//        // Act
//        ResponseEntity<Object> response = postsController.addPost(expectedPost);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertTrue(response.getBody() instanceof Map);
// 
//        Map<String, String> actualResponse = (Map<String, String>) response.getBody();
//        assertEquals(expectedResponse.get("status"), actualResponse.get("status"));
//        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
//    }
// 
// 
//    @Test
//    public void testGetPosts() {
//        // Arrange
//        int postId = 1;
//        Posts expectedPost = new Posts();
//        expectedPost.setPostID(postId);
//        expectedPost.setContent("Sample post content");
// 
//        Map<String, Object> expectedResponse = new LinkedHashMap<>();
//        expectedResponse.put("status", "success");
//        expectedResponse.put("data", expectedPost);
// 
//        when(postsService.getByPostID(postId)).thenReturn(expectedPost); // Mock behavior
// 
//        // Act
//        ResponseEntity<Object> response = postsController.getPosts(postId);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody() instanceof Map, "Response body is not an instance of Map");
// 
//        Map<String, Object> actualResponse = (Map<String, Object>) response.getBody();
//        assertEquals(expectedResponse.get("status"), actualResponse.get("status"));
//        assertEquals(expectedResponse.get("data"), actualResponse.get("data"));
//    }
// 
// 
//    @Test
//    public void testUpdatePostDetails() {
//        // Arrange
//        int postId = 1;
//        Posts updatedPost = new Posts(); // Create a mock updated Posts object
//        updatedPost.setPostID(postId);
//        updatedPost.setContent("Updated content");
// 
//        Map<String, String> expectedResponse = new LinkedHashMap<>();
//        expectedResponse.put("status", "success");
//        expectedResponse.put("message", "Post updated successfully");
// 
//        when(postsService.updatePosts(eq(postId), any(Posts.class))).thenReturn(updatedPost); // Mock behavior
// 
//        // Act
//        ResponseEntity<Object> response = postsController.updatePostDetails(postId, updatedPost);
// 
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody() instanceof Map);
// 
//        Map<String, String> actualResponse = (Map<String, String>) response.getBody();
//        assertEquals(expectedResponse.get("status"), actualResponse.get("status"));
//        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
//    }
// 
// 
//    @Test
//    void testDeletePosts() {
//        Posts post = new Posts();
//        post.setPostID(1);
// 
//        when(postsService.getByPostID(1)).thenReturn(post);
//        doNothing().when(postsService).deletePostById(1);
// 
//        ResponseEntity<Object> response = postsController.deletePosts(1);
// 
//        assertEquals(200, response.getStatusCodeValue());
//        assertTrue(response.getBody().toString().contains("Post deleted successfully"));
//    }
// 
//    @Test
//    void testAddLike() {
//        Likes like = new Likes();
//        like.setLikeID(1);
// 
//        when(likesService.savelike(1, 1, 1)).thenReturn(like);
// 
//        ResponseEntity<Object> response = postsController.addLike(1, 1, like);
// 
//        assertEquals(201, response.getStatusCodeValue());
//        assertFalse(response.getBody().toString().contains("likes posted successfully"));
//    }
// 
//    @Test
//    void testGetLikesByPostId() {
//        Likes like1 = new Likes();
//        like1.setLikeID(1);
//        Likes like2 = new Likes();
//        like2.setLikeID(2);
// 
//        List<Likes> likes = Arrays.asList(like1, like2);
// 
//        when(likesService.getLikeByPostID(1)).thenReturn(likes);
// 
//        ResponseEntity<Object> response = postsController.getLikesByPostId(1);
// 
//        assertEquals(200, response.getStatusCodeValue());
//        assertTrue(response.getBody().toString().contains("success"));
//    }
// 
//    @Test
//    void testDeletelike() {
//        Likes like = new Likes();
//        like.setLikeID(1);
// 
//        Posts post = new Posts();
//        post.setPostID(1);
// 
//        when(postsService.getByPostID(1)).thenReturn(post);
//        when(likesService.getByLikeID(1)).thenReturn(like);
//        doNothing().when(likesService).deleteLikes(1);
// 
//        ResponseEntity<Object> response = postsController.deletelike(1, 1);
// 
//        assertEquals(200, response.getStatusCodeValue());
//        assertTrue(response.getBody().toString().contains("Like removed from the post successfully"));
//    }
// 
//    @Test
//    void testGetAllLikesOnPostsByUserId() {
//        Likes like1 = new Likes();
//        like1.setLikeID(1);
//        Likes like2 = new Likes();
//        like2.setLikeID(2);
// 
//        List<Likes> likes = Arrays.asList(like1, like2);
// 
//        when(likesService.getLikeBySpecfUser(1)).thenReturn(likes);
// 
//        ResponseEntity<Object> response = postsController.getAllLikesOnPostsByUserId(1);
// 
//        assertEquals(200, response.getStatusCodeValue());
//        assertTrue(response.getBody().toString().contains("success"));
//    }
// 
//}
//
//has context menu
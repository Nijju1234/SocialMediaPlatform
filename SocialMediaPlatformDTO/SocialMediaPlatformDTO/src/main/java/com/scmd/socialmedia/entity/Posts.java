package com.scmd.socialmedia.entity;
 
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
 
@Entity
@Table(name = "Posts")
public class Posts 
{
 
	@Id
    @Column(name = "postID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "posts_seq", allocationSize = 1)
    private int postID;
 
    @ManyToOne
    @JoinColumn(name = "userID") // This specifies the foreign key column that is used to link this post to the corresponding user in the Users table. 
    @NotNull(message = "User cannot be null")
    private Users user;
 
    @Column(name = "content")
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 500, message = "Content must be less than or equal to 500 characters")
    private String content;
 
    @CreationTimestamp
    @Column(name = "timestamp", columnDefinition = "TIMESTAMP")
    @NotNull(message = "Timestamp cannot be null")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Timestamp timestamp;
 
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comments> comments;
 
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Likes> likes;
    private boolean is_deleted = false;
 
	public Posts() {}
 
    public Posts(int postID, String content, Timestamp timestamp,boolean is_deleted) 
    {
        this.postID = postID;
        this.content = content;
        this.timestamp = timestamp;
        this.is_deleted = is_deleted;
    }
    public boolean isIs_deleted() {
		return is_deleted;
	}
 
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
 
    public int getPostID() 
    {
        return postID;
    }
 
    public void setPostID(int postID) 
    {
        this.postID = postID;
    }
 
    public Users getUser() 
    {
        return user;
    }
 
    public void setUser(Users user) 
    {
        this.user = user;
    }
 
    public String getContent() 
    {
        return content;
    }
 
    public void setContent(String content) 
    {
        this.content = content;
    }
 
    public Timestamp getTimestamp()
    {
        return timestamp;
    }
 
    public void setTimestamp(Timestamp timestamp) 
    {
        this.timestamp = timestamp;
    }
 
    public List<Comments> getComments() 
    {
        return comments;
    }
 
    public void setComments(List<Comments> comments) 
    {
        this.comments = comments;
    }
 
    public List<Likes> getLikes() 
    {
        return likes;
    }
 
    public void setLikes(List<Likes> likes) 
    {
        this.likes = likes;
    }
 
}
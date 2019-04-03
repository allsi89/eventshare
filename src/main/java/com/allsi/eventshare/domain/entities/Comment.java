package com.allsi.eventshare.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "comments")
public class Comment extends BaseEntity {
  private String content;
  private LocalDateTime postedOn;
  private LocalDateTime lastEditedOn;
  private User user;
  private Event event;

  public Comment() {
  }

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name = "posted_on", nullable = false)
  public LocalDateTime getPostedOn() {
    return postedOn;
  }

  public void setPostedOn(LocalDateTime postedOn) {
    this.postedOn = postedOn;
  }

  @Column(name = "last_edited_on")
  public LocalDateTime getLastEditedOn() {
    return lastEditedOn;
  }

  public void setLastEditedOn(LocalDateTime lastEditedOn) {
    this.lastEditedOn = lastEditedOn;
  }

  //TODO - DONE
  @ManyToOne(targetEntity = User.class)
  @JoinTable(name = "users_comments",
      joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  //TODO - DONE
  @ManyToOne(targetEntity = Event.class)
  @JoinTable(name = "events_comments",
      joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}

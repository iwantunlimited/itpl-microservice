package io.itpl.microservice.common;

public class Reaction {
	
	 private String id;
	 private String userId;
	 private String reaction;
	 private UserObject userObject;
	 private String createdOn;
	 
	public String getId() {	return id;}
	public void setId(String id) {this.id = id;}
	public String getUserId() {return userId;}
	public void setUserId(String userId) {this.userId = userId;}
	public String getReaction() {return reaction;}
	public void setReaction(String reaction) {this.reaction = reaction;}
	public UserObject getUserObject() {return userObject;}
	public void setUserObject(UserObject userObject) {this.userObject = userObject;}
	public String getCreatedOn() {return createdOn;}
	public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}	
	
}

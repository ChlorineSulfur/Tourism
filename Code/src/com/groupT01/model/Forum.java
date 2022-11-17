package com.groupT01.model;

public class Forum {
	public int fid;
	public String identity;
	public String fname;
	public String forumdate;
	public String comment;

	public int getFid() {
		return fid;
	}

	public String getIdentity() {
		return identity;
	}

	public String getFname() {
		return fname;
	}

	public String getForumdate() {
		return forumdate;
	}

	public String getComment() {
		return comment;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setForumdate(String forumdate) {
		this.forumdate = forumdate;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

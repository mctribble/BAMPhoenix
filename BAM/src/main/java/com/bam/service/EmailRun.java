package com.bam.service;

import com.bam.bean.BamUser;

public class EmailRun implements Runnable{
	private BamUser user;

	public EmailRun() {}
	
	public BamUser getUser() {
		return user;
	}

	public void setUser(BamUser user2) {
		this.user = user2;
	}

	@Override
	public void run() {
		MailService.sendMail(user.getEmail(), user.getPwd());
		
	}


}
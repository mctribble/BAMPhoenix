package com.bam.service;


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
<<<<<<< HEAD
		//Empty Because of no args run method
=======
		MailService.sendMail(user.getEmail(), user.getPwd());
		
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50
	}


}
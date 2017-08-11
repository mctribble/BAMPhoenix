package com.bam.service;

public class PasswordGenerator {

	public String makePassword() {
		String pass = "";

		for (int i = 0; i < 8; i++) {
			char x = (char) (Math.random() * 62);

			if (x < 26) {
				pass += (char) (x + 65);
			} else if (x > 25 && x < 52) {
				pass += (char) (x + 71);
			} else {
				pass += (char) (x - 4);
			}

		}

		return pass;
	}

}

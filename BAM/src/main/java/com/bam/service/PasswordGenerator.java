package com.bam.service;

public class PasswordGenerator {
	

	
	public static String makePassword() {
		StringBuilder pass = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			char x = (char) (Math.random() * 62);

			if (x < 26) {
				char v = (char) (x + 65);
				pass.append(v); 
			} else if (x > 25 && x < 52) {
				 char m = (char) (x + 71);
				 pass.append(m);
			} else {
				char k= (char) (x - 4);
				pass.append(k);
			}

		}

		return pass.toString();
	}

}

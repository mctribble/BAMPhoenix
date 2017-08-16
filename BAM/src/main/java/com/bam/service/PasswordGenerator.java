package com.bam.service;

class PasswordGenerator {
	

	
	public static String makePassword() {
		String pass = "";

		for (int i = 0; i < 8; i++) {
			char x = (char) (Math.random() * 62);

			if (x < 26) {
				char v = (char) (x + 65);
				pass += Character.toString(v);
			} else if (x > 25 && x < 52) {
				 char m = (char) (x + 71);
				 pass += Character.toString(m);
			} else {
				char k= (char) (x - 4);
				pass += Character.toString(k);
			}

		}

		return pass;
	}

}

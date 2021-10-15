class user {
	static int score = 0;
	static String name;
	public user(String name, int score) {
		this.name = name;
		this.score = score;
	}
	public static void addScore(int in) {
		score += in; // adding score
	}
	

	public static int showScore() {
		return score;
	}
	public static void det() {
		System.out.println(name + ": " + score + " points"); // print line of score and user
	}
	public static void remScore() {
		score--; // dock a point
	}
}


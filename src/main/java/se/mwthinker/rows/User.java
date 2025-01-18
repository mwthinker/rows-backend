package se.mwthinker.rows;

public class User {
	private final String username;
	private final String id;

	public User(String username) {
		this.username = username;
		// Generate random id
		this.id = String.valueOf((int) (Math.random() * 1000));
	}

	public String getUsername() {
		return username;
	}

	public String getId() {
		return id;
	}
}

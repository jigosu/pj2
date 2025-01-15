package com.copperbrass.practice.purchase;

public class UserDTO {
	private Long id;
	private String username;

    // 생성자
    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;

    }

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
  
}

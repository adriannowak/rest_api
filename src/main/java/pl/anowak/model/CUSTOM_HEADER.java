package pl.anowak.model;

public enum CUSTOM_HEADER {
	AUTH_TOKEN("authorization");
	
	private String header;
	
	CUSTOM_HEADER(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
}

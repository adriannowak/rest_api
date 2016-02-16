package pl.anowak.model;

import javax.ws.rs.container.ContainerRequestContext;

public enum CUSTOM_HEADER {
	AUTH_TOKEN("authorization");
	
	private String header;
	
	CUSTOM_HEADER(String header) {
		this.header = header;
	}
	
	public String getHeader(ContainerRequestContext ctx) {
		return ctx.getHeaderString(this.header);
	}
	
}

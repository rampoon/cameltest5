package se.rampoon.example5.beans;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileNameBean {

	public String getUniqueFileName() {
		return UUID.randomUUID().toString();
	}
}

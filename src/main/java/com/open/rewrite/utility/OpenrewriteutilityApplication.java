package com.open.rewrite.utility;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.open.rewrite.utility.helper.FileReader;

@SpringBootApplication
public class OpenrewriteutilityApplication implements CommandLineRunner {

	@Autowired
	private FileReader fileReader;
	
	@Value( "${git.repo.path}" )
	private String gitRepoPath;

	public static void main(String[] args) {
		SpringApplication.run(OpenrewriteutilityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		File path = new File(gitRepoPath);
		fileReader.executeReceipe(path.listFiles());
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	public String getGitRepoPath() {
		return gitRepoPath;
	}

	public void setGitRepoPath(String gitRepoPath) {
		this.gitRepoPath = gitRepoPath;
	}
}

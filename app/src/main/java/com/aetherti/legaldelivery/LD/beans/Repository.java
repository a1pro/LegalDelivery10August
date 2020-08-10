package LD.beans;

public class Repository {

	private String repoFullname;

	public Repository(){
		super();
	}
	public Repository(String fullname)
	{
		this.repoFullname=fullname;
	}
	public String getRepoFullname() {
		return repoFullname;
	}
	public void setRepoFullname(String repoFullname) {
		this.repoFullname = repoFullname;
	}
}

package dto;

public class genre {
	private String genreID;
	private String genreName;
	
	public genre(String genreID, String genreName) {
		this.genreID=(genreID);
		this.genreName=(genreName);
	}
	
	public String getGenreID() {
		return genreID;
	}
	
	public String getGenreName() {
		return genreName;
	}

}

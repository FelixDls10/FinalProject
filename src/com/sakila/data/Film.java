package com.sakila.data;

public class Film extends Entity {

    private int filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private int languageId;
    private Integer length;
    private String rating;

    public Film() {
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLastUpdate() {
    }

    @Override
    public String toString() {
        return String.format(
                "%d | %s | Año: %s | Duración: %s | Rating: %s",
                filmId,
                title,
                releaseYear != null ? releaseYear.toString() : "N/A",
                length != null ? length.toString() : "N/A",
                rating
        );
    }
}

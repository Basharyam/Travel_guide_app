package com.app.tourguide.model;

import java.util.List;

public class Tour {
    private int id;
    private String title;
    private String category;
    private String region;
    private String theme;
    private String season;
    private String duration;
    private String description;
    private String country;
    private String flag;
    private List<String> schedule; // List instead of a comma-separated string
    private List<String> cities; // List of cities
    private String famousLocations; // JSON string of famous locations

    public Tour(int id, String title, String category, String region, String theme, String season,
                String duration, String description, String country, String flag, List<String> schedule,
                List<String> cities, String famousLocations) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.region = region;
        this.theme = theme;
        this.season = season;
        this.duration = duration;
        this.description = description;
        this.country = country;
        this.flag = flag;
        this.schedule = schedule;
        this.cities = cities;
        this.famousLocations = famousLocations;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getRegion() { return region; }
    public String getTheme() { return theme; }
    public String getSeason() { return season; }
    public String getDuration() { return duration; }
    public String getDescription() { return description; }
    public String getCountry() { return country; }
    public String getFlag() { return flag; }
    public List<String> getSchedule() { return schedule; }
    public List<String> getCities() { return cities; }
    public String getFamousLocations() { return famousLocations; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setRegion(String region) { this.region = region; }
    public void setTheme(String theme) { this.theme = theme; }
    public void setSeason(String season) { this.season = season; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setDescription(String description) { this.description = description; }
    public void setCountry(String country) { this.country = country; }
    public void setFlag(String flag) { this.flag = flag; }
    public void setSchedule(List<String> schedule) { this.schedule = schedule; }
    public void setCities(List<String> cities) { this.cities = cities; }
    public void setFamousLocations(String famousLocations) { this.famousLocations = famousLocations; }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", region='" + region + '\'' +
                ", theme='" + theme + '\'' +
                ", season='" + season + '\'' +
                ", duration='" + duration + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", flag='" + flag + '\'' +
                ", schedule=" + schedule +
                ", cities=" + cities +
                ", famousLocations='" + famousLocations + '\'' +
                '}';
    }
}

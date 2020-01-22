package pl.edu.agh.eis.wsswaml.models;

public class Cuisine {
    private String entityID;
    private String image;
    private String name;
    private String description;

    public Cuisine(String entityID, String name, String image) {
        this.entityID = entityID;
        this.image = image;
        this.name = name;
    }

    public Cuisine(String entityID, String name, String image, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }
}

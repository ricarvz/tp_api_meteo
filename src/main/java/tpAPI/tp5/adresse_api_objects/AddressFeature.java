package tpAPI.tp5.adresse_api_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AddressFeature {
    AddressGeometry geometry;
    AddressProperties properties;


    public AddressFeature() {
    }

    public AddressGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(AddressGeometry geometry) {
        this.geometry = geometry;
    }

    public AddressProperties getProperties() {
        return properties;
    }

    public void setProperties(AddressProperties properties) {
        this.properties = properties;
    }
}

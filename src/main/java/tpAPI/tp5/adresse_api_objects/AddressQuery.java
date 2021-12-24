package tpAPI.tp5.adresse_api_objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class AddressQuery {
    @JsonProperty
    private AddressFeature [] features;

    public AddressQuery() {
    }

    public AddressFeature [] getFeatures() {
        return features;
    }

    public void setFeatures(AddressFeature []features) {
        this.features = features;
    }
}

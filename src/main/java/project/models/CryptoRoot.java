package project.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoRoot {

    @JsonProperty("Response")
    String response;

    @JsonProperty("Type")
    int type;

    @JsonProperty("Data")
    Data [] data;

    @JsonProperty("TimeTo")
    int timeTo;

    @JsonProperty("TimeFrom")
    int timeFrom;

    @JsonProperty("FirstValueInArray")
    boolean firstValueInArray;

    @JsonProperty("ConversionType")
    ConversionType conversionType;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(int timeTo) {
        this.timeTo = timeTo;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(int timeFrom) {
        this.timeFrom = timeFrom;
    }

    public boolean isFirstValueInArray() {
        return firstValueInArray;
    }

    public void setFirstValueInArray(boolean firstValueInArray) {
        this.firstValueInArray = firstValueInArray;
    }

    public ConversionType getConversionType() {
        return conversionType;
    }

    public void setConversionType(ConversionType conversionType) {
        this.conversionType = conversionType;
    }
}

package model;
/**
 * Extracting properties file to object model - BusinessData
 * It is the mapping between properties file parameters and Java model.
 * Properties which aren't mapped are not supported
 */
public class BusinessData {
    private String trnName;
    private String trnVer;
    private String timestamp;
    private String uuid;
    private String wrId;
    private String clientId;
    private String iso2CtryName;
    private String requestId;
    private String routeId;


    private BusinessData() {
    }

    String getTrnName() {
        return trnName;
    }

    String getTrnVer() {
        return trnVer;
    }

    String getTimestamp() {
        return timestamp;
    }

    String getUuid() {
        return uuid;
    }

    String getWrId() {
        return wrId;
    }

    String getClientId() {
        return clientId;
    }

    String getIso2CtryName() {
        return iso2CtryName;
    }

    String getRequestId() {
        return requestId;
    }

    String getRouteId() {
        return routeId;
    }

    @Override
    public String toString() {
        return "\"" + PropertyTypeEnum.TRNNAM + "\"=" + this.trnName + "\n"
                + "\"" + PropertyTypeEnum.VERSION + "\"=" + trnVer + "\n"
                + "\"" + PropertyTypeEnum.CURRENT_DATETIME + "\"=" + timestamp + "\n"
                + "\"" + PropertyTypeEnum.UUID + "\"=" + uuid + "\n"
                + "\"" + PropertyTypeEnum.WAREHOUSE + "\"=" + wrId + "\n"
                + "\"" + PropertyTypeEnum.CLIENT + "\"=" + clientId + "\n"
                + "\"" + PropertyTypeEnum.COUNTRY + "\"=" + this.iso2CtryName + "\n"
                + "\"" + PropertyTypeEnum.REQUEST_ID + "\"=" + this.requestId + "\n"
                + "\"" + PropertyTypeEnum.ROUTE + "\"=" + this.routeId + "\n";
    }

    /**
     * Builder allows to fill the model in only one way.
     * Prevent issues with data input for model.
     */
    public static Builder newBuilder() {
        return new BusinessData().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        Builder setTrnName(String trnName) {
            BusinessData.this.trnName = trnName;
            return this;
        }

        Builder setTrnVer(String trnVer) {
            BusinessData.this.trnVer = trnVer;
            return this;
        }

        Builder setTimestamp(String timestamp) {
            BusinessData.this.timestamp = timestamp;
            return this;
        }

        Builder setUuid(String uuid) {
            BusinessData.this.uuid = uuid;
            return this;
        }

        Builder setWrId(String wrId) {
            BusinessData.this.wrId = wrId;
            return this;
        }

        Builder setClientId(String clientId) {
            BusinessData.this.clientId = clientId;
            return this;
        }

        Builder setIso2CtryName(String iso2CtryName) {
            BusinessData.this.iso2CtryName = iso2CtryName;
            return this;
        }

        Builder setRequestId(String requestId) {
            BusinessData.this.requestId = requestId;
            return this;
        }

        Builder setRouteId(String routeId) {
            BusinessData.this.routeId = routeId;
            return this;
        }

        BusinessData build() {
            return BusinessData.this;
        }

    }

}

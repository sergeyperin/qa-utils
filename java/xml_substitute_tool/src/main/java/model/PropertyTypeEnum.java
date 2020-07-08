package model;

import helper.PropertyHelper;
import model.interfaces.IBusinessDataOperations;

/**
 * PropertyTypeEnum holds the supported properties from properties file, for better validation.
 * IBusinessDataOperations allows to set/get relevant BusinessData field for relevant property type from supported ones
 */
public enum PropertyTypeEnum implements IBusinessDataOperations {
    TRNNAM {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setTrnName(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getTrnName();
        }
    },
    CURRENT_DATETIME {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            if (PropertyHelper.isDynamicValue(value)) {
                value = new BusinessDataHandler.DateTimeValueImpl().generateDateTimeValue();
            }
            return builder.setTimestamp(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getTimestamp();
        }
    },
    UUID {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            if (PropertyHelper.isDynamicValue(value)) {
                value = new BusinessDataHandler.UUIDValueImpl().generateUUIDValue();
            }
            return builder.setUuid(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getUuid();
        }
    },
    ROUTE {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setRouteId(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getRouteId();
        }
    },
    WAREHOUSE {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setWrId(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getWrId();
        }
    },
    CLIENT {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setClientId(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getClientId();
        }
    },
    COUNTRY {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setIso2CtryName(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getIso2CtryName();
        }
    },
    REQUEST_ID {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            if (PropertyHelper.isDynamicValue(value)) {
                value = new BusinessDataHandler.RequestIdValueImpl().generateRequestIdValue();
            }
            return builder.setRequestId(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getRequestId();
        }
    },
    VERSION {
        @Override
        public BusinessData.Builder setData(BusinessData.Builder builder, String value) {
            return builder.setTrnVer(value);
        }

        @Override
        public String getData(BusinessData businessData) {
            return businessData.getTrnVer();
        }

    };

    PropertyTypeEnum() {
    }

}

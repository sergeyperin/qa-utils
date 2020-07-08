package model;

import helper.PropertyHelper;
import model.interfaces.IDateTimeValue;
import model.interfaces.IRequestIdValue;
import model.interfaces.IUUIDValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BusinessDataHandler {
    private BusinessData.Builder builder;


    BusinessDataHandler(BusinessData.Builder builder) {
        this.builder = builder;
    }
    /**
     * Maps supported properties to BusinessData
     */
    void mapPropertyToModel(String propName, String propValue) {
        for (PropertyTypeEnum p : PropertyTypeEnum.values()) {
            if (PropertyHelper.isValidProperty(p.name(), propName)) {
                p.setData(builder, propValue);
            }
        }
    }

    BusinessData build() {
        return this.builder.build();
    }

    public static class UUIDValueImpl implements IUUIDValue {

        @Override
        public String generateUUIDValue() {
            return "\"" + UUID.randomUUID().toString() + "\"";
        }

    }

    public static class DateTimeValueImpl implements IDateTimeValue {

        @Override
        public String generateDateTimeValue() {
            return "\"" + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()) + "\"";
        }

    }

    public static class RequestIdValueImpl implements IRequestIdValue {

        @Override
        public String generateRequestIdValue() {
            return "\"" + String.valueOf(Math.abs(new Random().nextInt())) + "\"";
        }

    }

}

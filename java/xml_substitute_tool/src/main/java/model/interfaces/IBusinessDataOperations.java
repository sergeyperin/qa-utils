package model.interfaces;

import model.BusinessData;

public interface IBusinessDataOperations {

    BusinessData.Builder setData(BusinessData.Builder builder, String value);
    String getData(BusinessData businessData);

}

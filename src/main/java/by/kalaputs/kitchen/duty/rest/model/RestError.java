package by.kalaputs.kitchen.duty.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestError {
    public static final String errorText403 = "Permission Denied (insufficient rights).";

    @XmlElement(name = "status-code")
    private Integer statusCode;

    @XmlElement(name = "sub-code")
    private Integer subCode;

    @XmlElement
    private String message;

    public RestError() {
    }

    public RestError(String message, Integer subCode, Integer statusCode) {
        this.setMessage(message);
        this.setStatusCode(statusCode);
        this.setSubCode(subCode);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSubCode() {
        return subCode;
    }

    public void setSubCode(Integer subCode) {
        this.subCode = subCode;
    }
}

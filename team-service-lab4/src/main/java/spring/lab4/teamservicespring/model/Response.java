package spring.lab4.teamservicespring.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement
@Data
public class Response {
    private int code;
    private String content;

    public Response() {
    }

    public Response(int code, String content) {
        this.code = code;
        this.content = content;
    }
}

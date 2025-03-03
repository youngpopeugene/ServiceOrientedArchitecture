package humanbeing.ejb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Column(name = "car_id")
    private int id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;
    private Boolean cool;
}

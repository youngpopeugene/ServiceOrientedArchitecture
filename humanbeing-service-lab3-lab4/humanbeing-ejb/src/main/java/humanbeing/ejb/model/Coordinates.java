package humanbeing.ejb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@Entity
@Table(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Column(name = "coordinates_id")
    private int id;
    private long x;
    private int y;
}

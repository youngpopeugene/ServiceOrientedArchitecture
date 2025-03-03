package humanbeing.ejb.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@Entity
@Table(name = "humanbeings")
@XmlRootElement()
@XmlType(propOrder = {"id", "name", "coordinates", "creationDate", "realHero", "hasToothpick", "impactSpeed", "soundtrackName", "minutesOfWaiting", "weaponType", "moodType", "car", "teamID"})
public class HumanBeing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Column(nullable = false)
    private java.util.Date creationDate = Date.from(Instant.now());;

    @Column(nullable = false)
    private Boolean realHero;

    @Column(nullable = false)
    private Boolean hasToothpick;

    @Column(nullable = false)
    private Double impactSpeed;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String soundtrackName;

    @Column(nullable = false)
    private Float minutesOfWaiting;

    @Column(columnDefinition = "TEXT")
    private String weaponType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String moodType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private Integer teamID;
}

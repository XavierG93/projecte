package gerisoft.apirest.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author Gurrea
 * Classe per anidar atributs de activitats, residents i usuaris
 */
@Entity
@Table(name = "activities_residents")
public class ActivitiesResidents {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activities_id", referencedColumnName = "id", nullable = false)
    private Activities activitie;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id", referencedColumnName = "id", nullable = false)
    private Residents resident;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private String observacion;
    private Date dataActiviti;

    public Long getId() {
        return id;
    }

    public Activities getActivitie() {
        return activitie;
    }

    public Residents getResident() {
        return resident;
    }

    public User getUser() {
        return user;
    }

    public String getObservacion() {
        return observacion;
    }

    public Date getDataActiviti() {
        return dataActiviti;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActivitie(Activities activitie) {
        this.activitie = activitie;
    }

    public void setResident(Residents resident) {
        this.resident = resident;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setDataActiviti(Date dataActiviti) {
        this.dataActiviti = dataActiviti;
    }

}

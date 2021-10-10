package gerisoft.apirest.entity;


import javax.persistence.*;
import java.util.Date;
/**
 *
 * @author Gurrea
 * Classe per anidar atributs de atencions, residents i usuaris
 */
@Entity
@Table(name = "attentions_residents")
public class AttentionsResidents {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "attention_id", nullable = false)
    private Attentions attention;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "resident_id", nullable = false)
    private Residents resident;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String observacion;
    private Date dataAtencio;
    private boolean comprovat = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAttention(Attentions attention) {
        this.attention = attention;
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

    public void setDataAtencio(Date dataAtencio) {
        this.dataAtencio = dataAtencio;
    }

    public void setComprovat(boolean comprovat) {
        this.comprovat = comprovat;
    }
    public Long getId() {
        return id;
    }

    public Attentions getAttention() {
        return attention;
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

    public Date getDataAtencio() {
        return dataAtencio;
    }

    public boolean isComprovat() {
        return comprovat;
    }
}

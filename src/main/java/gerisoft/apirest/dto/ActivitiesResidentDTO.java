package gerisoft.apirest.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ActivitiesResidentDTO {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long activitieId;
    private Long residentId;
    private String observacion;
    private Date dataActiviti;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setActivitieId(Long activitieId) {
        this.activitieId = activitieId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setDataActiviti(Date dataActiviti) {
        this.dataActiviti = dataActiviti;
    }

    public Long getActivitieId() {
        return activitieId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public String getObservacion() {
        return observacion;
    }

    public Date getDataActiviti() {
        return dataActiviti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

package gerisoft.apirest.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class AttentionsResidentDTO {
    @NotNull
    private Long attentionId;
    @NotNull
    private Long userId;
    private Long residentId;

    public void setAttentionId(Long attentionId) {
        this.attentionId = attentionId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComprovat(boolean comprovat) {
        this.comprovat = comprovat;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setDataAtencio(Date dataAtencio) {
        this.dataAtencio = dataAtencio;
    }

    public Long getAttentionId() {
        return attentionId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getResidentId() {
        return residentId;
    }

    public Long getId() {
        return id;
    }

    public boolean isComprovat() {
        return comprovat;
    }

    public String getObservacion() {
        return observacion;
    }

    public Date getDataAtencio() {
        return dataAtencio;
    }

    private Long id;
    private boolean comprovat;
    private String observacion;
    private Date dataAtencio;

}

package br.com.ventisol.hub2b.model.auxiliar;

import java.util.List;

public class Erro {

    private String error_description;
    private String error;
    private List<String> errors;
    private String data;
    private String status;
    private String active;

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public void setError(String erro) {
        this.error = erro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Erro{" + "error_description=" + error_description + ", error=" + error + ", errors=" + errors + ", data=" + data + ", status=" + status + ", active=" + active + '}';
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}

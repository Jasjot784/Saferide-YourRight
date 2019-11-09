package com.jasjotsingh.saferide_yourright;


public class CombuyLocal {
    private int id;
    private String nombrenegocio;
    private String ruc;
    private double latitud;
    private double longitud;
    private String descripcion;
    private String telefono;
    private String hora_inicio;
    private String hora_fin;
    private String idtiponegocio;
    private float distancia;//Agregado para obtener la distancia entre este negocio y la posicion actual del usuario

    @Override
    public String toString() {
        return "CombuyLocal{" +
                "id=" + id +
                ", nombrenegocio='" + nombrenegocio + '\'' +
                ", ruc='" + ruc + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", descripcion='" + descripcion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", hora_fin='" + hora_fin + '\'' +
                ", idtiponegocio='" + idtiponegocio + '\'' +
                ", distancia=" + distancia +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrenegocio() {
        return nombrenegocio;
    }

    public void setNombrenegocio(String nombrenegocio) {
        this.nombrenegocio = nombrenegocio;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getIdtiponegocio() {
        return idtiponegocio;
    }

    public void setIdtiponegocio(String idtiponegocio) {
        this.idtiponegocio = idtiponegocio;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public CombuyLocal(int id, String nombrenegocio, String ruc, double latitud, double longitud, String descripcion, String telefono, String hora_inicio, String hora_fin, String idtiponegocio) {
        this.id = id;
        this.nombrenegocio = nombrenegocio;
        this.ruc = ruc;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.idtiponegocio = idtiponegocio;
        this.distancia = 0;
    }
}

package org.example.labo1umbrella.model;

public class Especimen {

    private String nombre;
    private VirusBase virusBase;
    private int nivelPeligro;
    private String puntoDebilFisico;
    private EstadoActual estadoActual;
    private Ubicacion ultimaUbicacion;
    private String ubicacionOrigenUniversidad;

    // Constructor vacío
    public Especimen() {}

    // Constructor completo
    public Especimen(String nombre, VirusBase virusBase, int nivelPeligro,
                     String puntoDebilFisico, EstadoActual estadoActual,
                     Ubicacion ultimaUbicacion, String ubicacionOrigenUniversidad) {
        this.nombre = nombre;
        this.virusBase = virusBase;
        this.nivelPeligro = nivelPeligro;
        this.puntoDebilFisico = puntoDebilFisico;
        this.estadoActual = estadoActual;
        this.ultimaUbicacion = ultimaUbicacion;
        this.ubicacionOrigenUniversidad = ubicacionOrigenUniversidad;
    }

    // --- GETTERS ---
    public String getNombre() { return nombre; }
    public VirusBase getVirusBase() { return virusBase; }
    public int getNivelPeligro() { return nivelPeligro; }
    public String getPuntoDebilFisico() { return puntoDebilFisico; }
    public EstadoActual getEstadoActual() { return estadoActual; }
    public Ubicacion getUltimaUbicacion() { return ultimaUbicacion; }
    public String getUbicacionOrigenUniversidad() { return ubicacionOrigenUniversidad; }

    // --- SETTERS ---
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setVirusBase(VirusBase virusBase) { this.virusBase = virusBase; }
    public void setNivelPeligro(int nivelPeligro) { this.nivelPeligro = nivelPeligro; }
    public void setPuntoDebilFisico(String puntoDebilFisico) { this.puntoDebilFisico = puntoDebilFisico; }
    public void setEstadoActual(EstadoActual estadoActual) { this.estadoActual = estadoActual; }
    public void setUltimaUbicacion(Ubicacion ultimaUbicacion) { this.ultimaUbicacion = ultimaUbicacion; }
    public void setUbicacionOrigenUniversidad(String ubicacionOrigenUniversidad) { this.ubicacionOrigenUniversidad = ubicacionOrigenUniversidad; }
}

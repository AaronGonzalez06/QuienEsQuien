/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegoquien;

/**
 *
 * @author Aaron
 */
public class Personaje {
    
    //estado nos indica si ha sido pulsada o no. 
    /*
    0 -> se puede pulsar
    1 -> no se puede pulsar
    2 -> pulsa el boton empezar
    */
    
    private String nombre;
    private String ruta;
    private String rutaAux;
    private int estado;
    
    private String pregunta_1;
    private String pregunta_2;
    private String pregunta_3;
    private String pregunta_4;
    private String pregunta_5;
    private String pregunta_6;
    private String pregunta_7;
    private String pregunta_8;
    private String pregunta_9;
    private String pregunta_10;
    private String pregunta_11;
    private String pregunta_12;
    private String pregunta_13;

    public Personaje(String nombre, String ruta, String rutaAux, int estado, String pregunta_1, String pregunta_2, String pregunta_3, String pregunta_4, String pregunta_5, String pregunta_6, String pregunta_7, String pregunta_8, String pregunta_9, String pregunta_10, String pregunta_11, String pregunta_12, String pregunta_13) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.rutaAux = rutaAux;
        this.estado = estado;
        this.pregunta_1 = pregunta_1;
        this.pregunta_2 = pregunta_2;
        this.pregunta_3 = pregunta_3;
        this.pregunta_4 = pregunta_4;
        this.pregunta_5 = pregunta_5;
        this.pregunta_6 = pregunta_6;
        this.pregunta_7 = pregunta_7;
        this.pregunta_8 = pregunta_8;
        this.pregunta_9 = pregunta_9;
        this.pregunta_10 = pregunta_10;
        this.pregunta_11 = pregunta_11;
        this.pregunta_12 = pregunta_12;
        this.pregunta_13 = pregunta_13;
    }

    public String getRutaAux() {
        return rutaAux;
    }

    public void setRutaAux(String rutaAux) {
        this.rutaAux = rutaAux;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getPregunta_1() {
        return pregunta_1;
    }

    public void setPregunta_1(String pregunta_1) {
        this.pregunta_1 = pregunta_1;
    }

    public String getPregunta_2() {
        return pregunta_2;
    }

    public void setPregunta_2(String pregunta_2) {
        this.pregunta_2 = pregunta_2;
    }

    public String getPregunta_3() {
        return pregunta_3;
    }

    public void setPregunta_3(String pregunta_3) {
        this.pregunta_3 = pregunta_3;
    }

    public String getPregunta_4() {
        return pregunta_4;
    }

    public void setPregunta_4(String pregunta_4) {
        this.pregunta_4 = pregunta_4;
    }

    public String getPregunta_5() {
        return pregunta_5;
    }

    public void setPregunta_5(String pregunta_5) {
        this.pregunta_5 = pregunta_5;
    }

    public String getPregunta_6() {
        return pregunta_6;
    }

    public void setPregunta_6(String pregunta_6) {
        this.pregunta_6 = pregunta_6;
    }

    public String getPregunta_7() {
        return pregunta_7;
    }

    public void setPregunta_7(String pregunta_7) {
        this.pregunta_7 = pregunta_7;
    }

    public String getPregunta_8() {
        return pregunta_8;
    }

    public void setPregunta_8(String pregunta_8) {
        this.pregunta_8 = pregunta_8;
    }

    public String getPregunta_9() {
        return pregunta_9;
    }

    public void setPregunta_9(String pregunta_9) {
        this.pregunta_9 = pregunta_9;
    }

    public String getPregunta_10() {
        return pregunta_10;
    }

    public void setPregunta_10(String pregunta_10) {
        this.pregunta_10 = pregunta_10;
    }

    public String getPregunta_11() {
        return pregunta_11;
    }

    public void setPregunta_11(String pregunta_11) {
        this.pregunta_11 = pregunta_11;
    }

    public String getPregunta_12() {
        return pregunta_12;
    }

    public void setPregunta_12(String pregunta_12) {
        this.pregunta_12 = pregunta_12;
    }

    public String getPregunta_13() {
        return pregunta_13;
    }

    public void setPregunta_13(String pregunta_13) {
        this.pregunta_13 = pregunta_13;
    }


    
}
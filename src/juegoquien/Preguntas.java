/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegoquien;

/**
 *
 * @author Aaron
 */
public class Preguntas {
    
    /*
    estado 0 - 1 . 0 cuando no se ha preguntado y 1 ya se ha preguntado
    */
    
    private String pregunta;
    private int numero;
    private int estado;

    public Preguntas(String pregunta, int numero, int estado) {
        this.pregunta = pregunta;
        this.numero = numero;
        this.estado = estado;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package juegoquien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Aaron
 */
public class JuegoQuien extends JFrame {

    public JPanel juegoPanel, panelImg, panelBotones, panelItro, vidas, panelPreguntas, panelRespuesta, panelMetal;
    public JButton boton, boton2;
    public Clip clip;
    JLabel[][] img = new JLabel[3][3];
    JLabel[] preguntas = new JLabel[13];
    JLabel vida1, vida2, vida3, respuesta, fondo;

    public Personaje clasePersonaje;

    public String reinicio = "";

    public Preguntas pregunta[];

    public Personaje personajes[];
    public Vidas vidasActivas;

    public String personajeMaquina;

    public int empezar = 0;
    public int empezarAux = 0;
    public int ganar = 0;

    //para modo facil
    public JPanel panelPuntuacion;
    public JLabel etiquetaPuntuacion;
    public int preguntaActual;
    public String respuestaActual;
    public int verificar = 0;
    public int clickPersona = 0;
    public int endGame;
    public boolean gameOver = false;
    public int puntuacion = 0;
    //fin modo facil

    //modo normal
    JButton boton1;
    boolean pulsarBoton = true;
    //fin normal

    //modo de juego, con vidas o sin vida
    /*
    0 = normal
    1=dificil
     */
    //public int modo = 1;
    public int modo;
    public String modoJuego;

    public JuegoQuien() {
        crearVentana();
        crearPaneles();
        //juego();

    }

    //crear la ventana
    public void crearVentana() {

        //Image icon = new ImageIcon(getClass().getResource("icono/prueba.jpeg")).getImage();
        //setIconImage(icon);
        Image icono;
        icono = Toolkit.getDefaultToolkit().getImage("icono/signo5.png");
        setIconImage(icono);
        //this.setSize(800, 800);
        this.setSize(1000, 1000);
        //pone titulo a la ventana
        setTitle("¿Quien es quien?, versión Resident evil");
        //nos hace el cierre de la ventana 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1000, 1000));
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        //quita el layout
        this.setLayout(null);

    }

    //crea los paneles
    public void crearPaneles() {

        panelItro = new JPanel();
        panelImg = new JPanel();
        panelBotones = new JPanel();
        juegoPanel = new JPanel();
        panelPreguntas = new JPanel();
        panelRespuesta = new JPanel();
        panelMetal = new JPanel();

        //panelPreguntas.setBackground(Color.GREEN);
        panelPreguntas.setOpaque(false);
        //panelPuntuacion.setOpaque(false);

        panelItro.setBackground(Color.black);
        panelBotones.setBackground(Color.black);
        panelRespuesta.setOpaque(false);

        panelImg.setBackground(Color.black);
        panelImg.setOpaque(false);
        //juegoPanel.setBackground(Color.pink);
        juegoPanel.setBounds(0, 0, 1000, 1000);

        panelBotones.setBounds(355, 450, 300, 100);
        panelRespuesta.setBounds(200, 750, 550, 25);
        //panel de las fotos
        panelImg.setBounds(15, 50, 500, 650);
        //panelItro.setBounds(0, 0, 500, 600);
        panelItro.setBounds(0, 0, 1000, 1000);
        panelPreguntas.setBounds(580, 150, 450, 500);
        panelMetal.setBounds(600, 125, 350, 500);
        //juegoPanel.add(panelMetal);
        juegoPanel.add(panelRespuesta);
        juegoPanel.add(panelImg);

        juegoPanel.add(panelPreguntas);
        //quitar el layout
        //panelFoto.setLayout(null);
        this.getContentPane().add(panelItro);
        panelItro.add(panelBotones);
        //panelItro.setLayout(new BorderLayout());
        panelItro.setLayout(null);

        crearImagenInicial();
        botonesInicio();

        String sonido = "audio/intro.wav";
        sonidoIntro(sonido);
    }

    //agregar puntuacion
    public void puntuacion() {
        panelPuntuacion = new JPanel();
        panelPuntuacion.setOpaque(false);
        //panelPuntuacion.setBackground(Color.red);
        panelPuntuacion.setBounds(700, 75, 150, 25);
        juegoPanel.add(panelPuntuacion);
        etiquetaPuntuacion = new JLabel();
        etiquetaPuntuacion.setForeground(Color.GRAY);
        String textPuntuacion = "puntuacion: " + puntuacion;
        etiquetaPuntuacion.setText(textPuntuacion);
        panelPuntuacion.add(etiquetaPuntuacion);
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(20f);
            etiquetaPuntuacion.setFont(sizedFont);

        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }
    }

    //añade las fotos a la aplicacion, tanto las inciales como las del juego
    // y JLabel a al panel puntuacion modo facil
    public void crearImagenInicial() {
        // imagen de la pelicula
        JLabel imagen = new JLabel();
        //añadimos la imagen
        String nombre = "img/juegointro.jpg";
        ImageIcon imageicon = new ImageIcon(nombre);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT));
        //se añade la imagen a la etiqueta
        imagen.setIcon(icon);
        imagen.setBounds(210, 190, 600, 400);
        //se añade la etiqueta al panel
        //panelItro.add(imagen, BorderLayout.EAST);
        panelItro.add(imagen);

        //fondo del juego
        fondo = new JLabel();
        //añadimos la imagen
        String ruta = "img/fondo-2.jpg";
        ImageIcon imageiconfondo = new ImageIcon(ruta);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconfondo = new ImageIcon(imageiconfondo.getImage().getScaledInstance(1000, 1000, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        fondo.setIcon(iconfondo);
        fondo.setBounds(0, 0, 1000, 1000);
        //se añade la etiqueta al panel
        //panelItro.add(imagen, BorderLayout.EAST);

        //titulo arriba
        JLabel titulo = new JLabel();
        //añadimos la imagen
        String rutaTitulo = "img/titulo.png";
        ImageIcon imageiconTitulo = new ImageIcon(rutaTitulo);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconTitulo = new ImageIcon(imageiconTitulo.getImage().getScaledInstance(400, 65, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        titulo.setIcon(iconTitulo);
        titulo.setBounds(250, 5, 400, 65);

        juegoPanel.add(titulo);

        //img metal
        JLabel metal = new JLabel();
        //añadimos la imagen
        String rutametal = "img/nemesis.png";
        ImageIcon imageiconMetal = new ImageIcon(rutametal);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconMetal = new ImageIcon(imageiconMetal.getImage().getScaledInstance(350, 500, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        metal.setIcon(iconMetal);
        metal.setBounds(125, 125, 350, 500);

        juegoPanel.add(metal);

        //img logo
        JLabel logo = new JLabel();
        //añadimos la imagen
        String rutalogo = "img/logo.png";
        ImageIcon imageiconLogo = new ImageIcon(rutalogo);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconLogo = new ImageIcon(imageiconLogo.getImage().getScaledInstance(250, 250, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        logo.setIcon(iconLogo);
        logo.setBounds(50, 680, 250, 250);

        juegoPanel.add(logo);

        //juegoPanel.add(fondo);
//quitar para que salga el fondo de pantall de zombies        
    }

    //añade el sonido
    public void sonidoIntro(String nombreSonido) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido.");
        }
    }

    //añade los botones del menu principal
    public void botonesInicio() {
        boton = new JButton("jugar");
        boton.setForeground(Color.blue);
        boton.setForeground(Color.black);
        panelBotones.add(boton);

        boton2 = new JButton("salir");
        boton2.setForeground(Color.blue);
        boton2.setForeground(Color.black);
        panelBotones.add(boton2);
        cambiarFuente();

        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] modo = {
                    "Facil",
                    "Normal",
                    "Dificil"
                };
                modoJuego = (String) JOptionPane.showInputDialog(null, "Seleccione el modo de juego.", "Dificultad", JOptionPane.DEFAULT_OPTION, null, modo, modo[0]);
                clip.close();
                String voz = "audio/voz.wav";
                sonidoIntro(voz);
                //System.err.println(clip.isRunning());
                clip.loop(0);
                while (clip.isRunning()) {
                    panelBotones.removeAll();
                    panelItro.removeAll();
                    panelItro.add(juegoPanel);
//juegoPanel.add(panelImg, BorderLayout.BEFORE_LINE_BEGINS);
                    juegoPanel.setLayout(null);
                    panelBotones.repaint();
                    panelBotones.revalidate();
                    panelItro.repaint();
                    panelItro.revalidate();

                    barramenu();
                }
                System.out.println(modoJuego);
                juego();
            }

        });

    }

    //fuente
    public void cambiarFuente() {
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(15f);
            boton.setFont(sizedFont);
            boton2.setFont(sizedFont);
        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }
    }

    //public void cambiarPanel(JPanel panel){}
    public void juego() {
// panelImg.setBounds(0, 0, 450, 450);
        crearpersonajes();
        crearpreguntas();
        listarpreguntas();
        panelImg.setLayout(new GridLayout(3, 3, 3, 0));
        int cont = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                //String img = "imagen" + x;
                //System.out.println();
                img[x][y] = new JLabel();
                //String ruta = "img/"+x+y+".jpg";
                //ImageIcon imageicon = new ImageIcon(ruta);
                ImageIcon imageicon = new ImageIcon(personajes[cont].getRuta());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                img[x][y].setIcon(icon);

                //imagen.setBounds(250, 250, 300, 200); 
                //System.out.println(img[0]);
                panelImg.add(img[x][y]);
                cont++;
            }
        }

        /*JLabel imagen = new JLabel();
        String nombre = "img/5.jpg";
        ImageIcon imageicon = new ImageIcon(nombre);
        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(95, 95, Image.SCALE_DEFAULT));
        imagen.setIcon(icon);
        //imagen.setBounds(250, 250, 300, 200);       
        panelImg.add(imagen); */
        //personaje de la maquina
        if (reinicio == "") {
            int numMaquina = (int) Math.floor(Math.random() * 8 + 1);
            clasePersonaje = personajes[numMaquina];
            personajeMaquina = personajes[numMaquina].getNombre();
            System.out.println("inicio con:" + personajeMaquina);
        }

        //modo vida
        previdas();

        if (modoJuego == "Facil") {
            modo = 0;
        } else if (modoJuego == "Dificil") {
            modo = 1;
        } else if (modoJuego == "Normal") {
            modo = 2;
        }
        cambioJuego(modo);

        //foto fondo para que no tape el resto de las que esta encima
        juegoPanel.add(fondo);
    }

    //solo lista las preguntas
    public void listarpreguntas() {
        //
        File fuente = new File("fuente/fuente.ttf");
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        for (int x = 0; x < preguntas.length; x++) {
            String frase = pregunta[x].getPregunta();
            preguntas[x] = new JLabel(frase);
            try {
                //crea la fuente
                Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
                //dar tanaño fuente
                Font sizedFont = font.deriveFont(15f);
                preguntas[x].setFont(sizedFont);
                preguntas[x].setForeground(Color.GRAY);
            } catch (FontFormatException ex) {
                System.err.println("error en font format");
            } catch (IOException ex) {
                System.err.println("error de entrada/salida");
            }
            panelPreguntas.add(preguntas[x]);
        }

    }

    public void previdas() {
        vidas = new JPanel();
        //vidas.setBackground(Color.black);
        vidas.setBounds(750, 15, 150, 75);
        vidas.setOpaque(false);
        juegoPanel.add(vidas);

        /*
        String ruta = "icono/signo.png";
        ImageIcon imageiconvida1 = new ImageIcon(ruta);
        Icon iconfondovida1 = new ImageIcon(imageiconvida1.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        vida1.setIcon(iconfondovida1);
        ImageIcon imageiconvida2 = new ImageIcon(ruta);
        Icon iconfondovida2 = new ImageIcon(imageiconvida2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        vida2.setIcon(iconfondovida2);
        ImageIcon imageiconvida3 = new ImageIcon(ruta);
        Icon iconfondovida3 = new ImageIcon(imageiconvida3.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        vida3.setIcon(iconfondovida3);
        vidasActivas.setVidas(3);
         */
        vida1 = new JLabel();
        //añadimos la imagen

        vida1.setBounds(0, 0, 50, 50);
        vidas.add(vida1);

        vida2 = new JLabel();
        //añadimos la imagen

        vida2.setBounds(0, 0, 50, 50);
        vidas.add(vida2);

        vida3 = new JLabel();
        //añadimos la imagen
        vida3.setBounds(0, 0, 50, 50);
        vidas.add(vida3);

        vidasActivas = new Vidas(3);

    }

    /*
    en funcion del modo, se ejecutara unos procedimientos u otros, que estan determinados por un 
    condicional.
     */
    public void cambioJuego(int cambio) {

        if (cambio == 0) {
            System.out.println("emtro 0");
            puntuacion();
            questionFacil();
            pingFacil();

        } else if (cambio == 1) {
            System.out.println("emtro 1");
            //vidas

            vidas();
            //elegir pregunta y su respuesta
            question();
            //pulsador
            ping();
        } else if (cambio == 2) {
            System.out.println("Normal");
            //setLayout(null);
            puntuacion();

            boton1 = new JButton("preguntar");
            boton1.setBounds(600, 500, 100, 30);
            this.add(boton1);

            respuesta = new JLabel();
            respuesta.setText("bienvenido  al juego.");
            respuesta.setForeground(Color.GRAY);
            File fuente = new File("fuente/fuente.ttf");
            try {
                //crea la fuente
                Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
                //dar tanaño fuente
                Font sizedFont = font.deriveFont(20f);
                respuesta.setFont(sizedFont);

            } catch (FontFormatException ex) {
                System.err.println("error en font format");
            } catch (IOException ex) {
                System.err.println("error de entrada/salida");
            }

            boton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    empezar = 1;
                    int numeroPregunta;
                    do {
                        numeroPregunta = (int) Math.floor(Math.random() * 13 + 1);
                    } while (pregunta[numeroPregunta - 1].getEstado() == 1);

                    if (pulsarBoton != true) {
                        numeroPregunta = 14;
                    } else {
                        pulsarBoton = false;
                        puntuacion++;
                        String textPuntuacion = "puntuacion :" + puntuacion;
                        etiquetaPuntuacion.setText(textPuntuacion);
                    }

                    System.out.println("numero que sale: " + numeroPregunta);
                    Preguntas estado;
                    int tiene;
                    switch (numeroPregunta) {
                        case 1:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_1());
                            preguntas[0].setForeground(Color.red);
                            preguntas[0].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_1());
                            estado = pregunta[0];
                            estado.setEstado(1);
                            preguntaActual = 1;

                            respuestaActual = clasePersonaje.getPregunta_1();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_1() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_1());
                            break;
                        case 2:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_2());
                            preguntas[1].setForeground(Color.red);
                            preguntas[1].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_2());
                            estado = pregunta[1];
                            estado.setEstado(1);
                            preguntaActual = 2;

                            respuestaActual = clasePersonaje.getPregunta_2();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_2() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_2());
                            break;
                        case 3:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_3());
                            preguntas[2].setForeground(Color.red);
                            preguntas[2].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_3());
                            estado = pregunta[2];
                            estado.setEstado(1);
                            preguntaActual = 3;

                            respuestaActual = clasePersonaje.getPregunta_3();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_3() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_3());
                            break;
                        case 4:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_4());
                            preguntas[3].setForeground(Color.red);
                            preguntas[3].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_4());
                            estado = pregunta[3];
                            estado.setEstado(1);
                            preguntaActual = 4;

                            respuestaActual = clasePersonaje.getPregunta_4();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_4() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_4());
                            break;
                        case 5:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_5());
                            preguntas[4].setForeground(Color.red);
                            preguntas[4].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_5());
                            estado = pregunta[4];
                            estado.setEstado(1);
                            preguntaActual = 5;

                            respuestaActual = clasePersonaje.getPregunta_5();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_5() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_5());
                            break;
                        case 6:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_6());
                            preguntas[5].setForeground(Color.red);
                            preguntas[5].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_6());
                            estado = pregunta[5];
                            estado.setEstado(1);
                            preguntaActual = 6;

                            respuestaActual = clasePersonaje.getPregunta_6();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_6() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_6());
                            break;
                        case 7:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_7());
                            preguntas[6].setForeground(Color.red);
                            preguntas[6].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_7());
                            estado = pregunta[6];
                            estado.setEstado(1);
                            preguntaActual = 7;

                            respuestaActual = clasePersonaje.getPregunta_7();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_7() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_7());
                            break;
                        case 8:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_8());
                            preguntas[7].setForeground(Color.red);
                            preguntas[7].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_8());
                            estado = pregunta[7];
                            estado.setEstado(1);
                            preguntaActual = 8;

                            respuestaActual = clasePersonaje.getPregunta_8();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_8() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_8());
                            break;
                        case 9:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_9());
                            preguntas[8].setForeground(Color.red);
                            preguntas[8].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_9());
                            estado = pregunta[8];
                            estado.setEstado(1);
                            preguntaActual = 9;

                            respuestaActual = clasePersonaje.getPregunta_9();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_9() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_9());
                            break;
                        case 10:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_10());
                            preguntas[9].setForeground(Color.red);
                            preguntas[9].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_10());
                            estado = pregunta[9];
                            estado.setEstado(1);
                            preguntaActual = 10;

                            respuestaActual = clasePersonaje.getPregunta_10();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_10() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_10());
                            break;
                        case 11:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_11());
                            preguntas[10].setForeground(Color.red);
                            preguntas[10].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_11());
                            estado = pregunta[10];
                            estado.setEstado(1);
                            preguntaActual = 11;

                            respuestaActual = clasePersonaje.getPregunta_11();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_11() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_11());
                            break;
                        case 12:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_12());
                            preguntas[11].setForeground(Color.red);
                            preguntas[11].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_12());
                            estado = pregunta[11];
                            estado.setEstado(1);
                            preguntaActual = 12;

                            respuestaActual = clasePersonaje.getPregunta_12();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_12() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_12());
                            break;
                        case 13:
                            respuesta.setText(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_13());
                            preguntas[12].setForeground(Color.red);
                            preguntas[12].setText(pregunta[numeroPregunta - 1].getPregunta() + " -- " + clasePersonaje.getPregunta_13());
                            estado = pregunta[12];
                            estado.setEstado(1);
                            preguntaActual = 13;

                            respuestaActual = clasePersonaje.getPregunta_13();
                            tiene = 0;
                            for (int z = 0; z < personajes.length; z++) {
                                if ((personajes[z].getPregunta_13() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                    tiene++;
                                }
                            }
                            if (tiene == 0) {
                                JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                pulsarBoton = true;
                            }

                            System.out.println(pregunta[numeroPregunta - 1].getPregunta() + ": " + clasePersonaje.getPregunta_13());
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Seleciona todas las opciones para poder seguir preguntando.");
                            break;
                    }
                }

            });
            panelRespuesta.add(respuesta);
            pingFacil();
        }

    }

    //añade solo las vidas
    //no tocar
    public void vidas() {

        String ruta = "icono/signo.png";
        ImageIcon imageiconvida1 = new ImageIcon(ruta);

        Icon iconfondovida1 = new ImageIcon(imageiconvida1.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));

        vida1.setIcon(iconfondovida1);

        ImageIcon imageiconvida2 = new ImageIcon(ruta);

        Icon iconfondovida2 = new ImageIcon(imageiconvida2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));

        vida2.setIcon(iconfondovida2);

        ImageIcon imageiconvida3 = new ImageIcon(ruta);

        Icon iconfondovida3 = new ImageIcon(imageiconvida3.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));

        vida3.setIcon(iconfondovida3);

    }

    //no tocar
    public void question() {

        respuesta = new JLabel();
        respuesta.setText("bienvenido  al juego.");
        respuesta.setForeground(Color.GRAY);
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(20f);
            respuesta.setFont(sizedFont);

        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }

        for (int x = 0; x < pregunta.length; x++) {

            String pre = pregunta[x].getPregunta();
            Preguntas estado = pregunta[x];

            int numero = pregunta[x].getNumero();

            preguntas[x].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println(numero);

                    if (empezarAux == 2) {
                        JOptionPane.showMessageDialog(null, "Ahora te toca elegir el personaje");
                    } else {

                        switch (numero) {
                            case 1:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_1());
                                    preguntas[0].setForeground(Color.red);
                                    preguntas[0].setText(pre + " -- " + clasePersonaje.getPregunta_1());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 2:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_2());
                                    preguntas[1].setForeground(Color.red);
                                    preguntas[1].setText(pre + " -- " + clasePersonaje.getPregunta_2());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 3:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_3());
                                    preguntas[2].setForeground(Color.red);
                                    preguntas[2].setText(pre + " -- " + clasePersonaje.getPregunta_3());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 4:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_4());
                                    preguntas[3].setForeground(Color.red);
                                    preguntas[3].setText(pre + " -- " + clasePersonaje.getPregunta_4());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 5:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_5());
                                    preguntas[4].setForeground(Color.red);
                                    preguntas[4].setText(pre + " -- " + clasePersonaje.getPregunta_5());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 6:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_6());
                                    preguntas[5].setForeground(Color.red);
                                    preguntas[5].setText(pre + " -- " + clasePersonaje.getPregunta_6());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 7:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_7());
                                    preguntas[6].setForeground(Color.red);
                                    preguntas[6].setText(pre + " -- " + clasePersonaje.getPregunta_7());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 8:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_8());
                                    preguntas[7].setForeground(Color.red);
                                    preguntas[7].setText(pre + " -- " + clasePersonaje.getPregunta_8());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 9:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_9());
                                    preguntas[8].setForeground(Color.red);
                                    preguntas[8].setText(pre + " -- " + clasePersonaje.getPregunta_9());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 10:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_10());
                                    preguntas[9].setForeground(Color.red);
                                    preguntas[9].setText(pre + " -- " + clasePersonaje.getPregunta_10());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 11:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_11());
                                    preguntas[10].setForeground(Color.red);
                                    preguntas[10].setText(pre + " -- " + clasePersonaje.getPregunta_11());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 12:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_12());
                                    preguntas[11].setForeground(Color.red);
                                    preguntas[11].setText(pre + " -- " + clasePersonaje.getPregunta_12());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 13:
                                empezar = 1;
                                empezarAux = 2;
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_13());
                                    preguntas[12].setForeground(Color.red);
                                    preguntas[12].setText(pre + " -- " + clasePersonaje.getPregunta_13());
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            default:
                                System.out.println("algo sale mal, no deberias estar mirando esto");
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            panelRespuesta.add(respuesta);
        }

    }

    public void questionFacil() {

        respuesta = new JLabel();
        respuesta.setText("bienvenido  al juego.");
        respuesta.setForeground(Color.GRAY);
        File fuente = new File("fuente/fuente.ttf");
        try {
            //crea la fuente
            Font font = Font.createFont(Font.TRUETYPE_FONT, fuente);
            //dar tanaño fuente
            Font sizedFont = font.deriveFont(20f);
            respuesta.setFont(sizedFont);

        } catch (FontFormatException ex) {
            System.err.println("error en font format");
        } catch (IOException ex) {
            System.err.println("error de entrada/salida");
        }

        for (int x = 0; x < pregunta.length; x++) {

            String pre = pregunta[x].getPregunta();
            Preguntas estado = pregunta[x];

            int numero = pregunta[x].getNumero();

            preguntas[x].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println(numero);

                    if (empezarAux == 2) {
                        JOptionPane.showMessageDialog(null, "Ahora te toca elegir el personaje,y recuerda que debes de tachar todos personajes contrarios");
                    } else {

                        switch (numero) {
                            case 1:
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_1());
                                    preguntas[0].setForeground(Color.red);
                                    preguntas[0].setText(pre + " -- " + clasePersonaje.getPregunta_1());

                                    preguntaActual = 1;
                                    respuestaActual = clasePersonaje.getPregunta_1();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_1() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }

                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 2:
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_2());
                                    preguntas[1].setForeground(Color.red);
                                    preguntas[1].setText(pre + " -- " + clasePersonaje.getPregunta_2());

                                    preguntaActual = 2;
                                    respuestaActual = clasePersonaje.getPregunta_2();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_2() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }

                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 3:
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_3());
                                    preguntas[2].setForeground(Color.red);
                                    preguntas[2].setText(pre + " -- " + clasePersonaje.getPregunta_3());

                                    preguntaActual = 3;
                                    respuestaActual = clasePersonaje.getPregunta_3();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_3() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }

                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 4:
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_4());
                                    preguntas[3].setForeground(Color.red);
                                    preguntas[3].setText(pre + " -- " + clasePersonaje.getPregunta_4());

                                    preguntaActual = 4;
                                    respuestaActual = clasePersonaje.getPregunta_4();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_4() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }

                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 5:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_5());
                                    preguntas[4].setForeground(Color.red);
                                    preguntas[4].setText(pre + " -- " + clasePersonaje.getPregunta_5());

                                    preguntaActual = 5;
                                    respuestaActual = clasePersonaje.getPregunta_5();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_5() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 6:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_6());
                                    preguntas[5].setForeground(Color.red);
                                    preguntas[5].setText(pre + " -- " + clasePersonaje.getPregunta_6());

                                    preguntaActual = 6;
                                    respuestaActual = clasePersonaje.getPregunta_6();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_6() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 7:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_7());
                                    preguntas[6].setForeground(Color.red);
                                    preguntas[6].setText(pre + " -- " + clasePersonaje.getPregunta_7());

                                    preguntaActual = 7;
                                    respuestaActual = clasePersonaje.getPregunta_7();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_7() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 8:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_8());
                                    preguntas[7].setForeground(Color.red);
                                    preguntas[7].setText(pre + " -- " + clasePersonaje.getPregunta_8());

                                    preguntaActual = 8;
                                    respuestaActual = clasePersonaje.getPregunta_8();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_8() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 9:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_9());
                                    preguntas[8].setForeground(Color.red);
                                    preguntas[8].setText(pre + " -- " + clasePersonaje.getPregunta_9());

                                    preguntaActual = 9;
                                    respuestaActual = clasePersonaje.getPregunta_9();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_9() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 10:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_10());
                                    preguntas[9].setForeground(Color.red);
                                    preguntas[9].setText(pre + " -- " + clasePersonaje.getPregunta_10());

                                    preguntaActual = 10;
                                    respuestaActual = clasePersonaje.getPregunta_10();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_10() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 11:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_11());
                                    preguntas[10].setForeground(Color.red);
                                    preguntas[10].setText(pre + " -- " + clasePersonaje.getPregunta_11());

                                    preguntaActual = 11;
                                    respuestaActual = clasePersonaje.getPregunta_11();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_11() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 12:
                                if (estado.getEstado() == 0) {

                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_12());
                                    preguntas[11].setForeground(Color.red);
                                    preguntas[11].setText(pre + " -- " + clasePersonaje.getPregunta_12());

                                    preguntaActual = 12;
                                    respuestaActual = clasePersonaje.getPregunta_12();

                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_12() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            case 13:
                                if (estado.getEstado() == 0) {
                                    estado.setEstado(1);
                                    respuesta.setText(pre + ": " + clasePersonaje.getPregunta_13());
                                    preguntas[12].setForeground(Color.red);
                                    preguntas[12].setText(pre + " -- " + clasePersonaje.getPregunta_13());

                                    preguntaActual = 13;
                                    respuestaActual = clasePersonaje.getPregunta_13();
                                    int tiene = 0;
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_13() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            tiene++;
                                        }
                                    }
                                    if (tiene == 0) {
                                        JOptionPane.showMessageDialog(null, "Esta pregunta no tiene ningun personaje asociado ya");
                                    } else {
                                        puntuacion++;
                                        String textPuntuacion = "puntuacion :" + puntuacion;
                                        etiquetaPuntuacion.setText(textPuntuacion);
                                        empezar = 1;
                                        empezarAux = 2;

                                    }
                                } else {
                                    respuesta.setText("esta pregunta ya ha sido respondidia");
                                }
                                break;
                            default:
                                System.out.println("algo sale mal, no deberias estar mirando esto");
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            panelRespuesta.add(respuesta);
        }

    }

    //no tocar
    public void ping() {
        int cont = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {

                String persona = personajes[cont].getNombre();
                System.out.println(persona);
                //guardo todo el obejto en la variable personajeOb
                Personaje personajeOb = personajes[cont];

                img[x][y].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(persona);
                        System.out.println(personajeOb.getEstado());

                        if (ganar == 1) {
                            JOptionPane.showMessageDialog(null, "ya has ganado, deja de jugar");
                        } else if (empezar == 0) {
                            JOptionPane.showMessageDialog(null, "Aun no has seleccionado la pregunta");
                        } else if ((persona == personajeMaquina) && (vidasActivas.getVidas() >= 1)) {

                            int contwin = 0;
                            for (int x = 0; x < 3; x++) {
                                for (int y = 0; y < 3; y++) {
                                    if (personajes[contwin].getNombre() == personajeMaquina) {
                                        personajeOb.setRuta("img/stars.png");
                                        ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                        img[x][y].setIcon(icon);
                                        panelImg.add(img[x][y]);
                                        personajeOb.setEstado(1);

                                    } else {
                                        System.out.println("a");
                                        panelImg.add(img[x][y]);

                                    }
                                    contwin++;
                                }
                            }

                            ganar = 1;
                            String conjunto;
                            String nombre = JOptionPane.showInputDialog("Has ganado a Umbrella.escribre tu nombre como ganador.");
                            conjunto = "\n" + nombre + " ---- " + " 100 " + " ---- " + " " + personajeMaquina;
                            //Scanner sc = new Scanner(System.in);
                            //PrintWriter salida = null;
                            BufferedWriter bw = null;
                            FileWriter fw = null;
                            try {
                                String data;
                                data = conjunto;
                                File file = new File("datos/puntuacion.txt");
                                // Si el archivo no existe, se crea
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                // flag true, indica adjuntar información al archivo.
                                fw = new FileWriter(file.getAbsoluteFile(), true);
                                bw = new BufferedWriter(fw);
                                bw.write(data);
                            } catch (IOException u) {
                                u.printStackTrace();
                            } finally {
                                try {
                                    //Cierra instancias de FileWriter y BufferedWriter
                                    if (bw != null) {
                                        bw.close();
                                    }
                                    if (fw != null) {
                                        fw.close();
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } else if (personajeOb.getEstado() == 1 && (vidasActivas.getVidas() >= 1)) {
                            JOptionPane.showMessageDialog(null, personajeOb.getNombre() + " ya ha sido seleccionado.");
                        } else {
                            int vidas = vidasActivas.getVidas();
                            empezar = 0;
                            switch (vidas) {
                                case 3:
                                    empezarAux = 1;
                                    personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    int cont = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {

                                            if (personajeOb.getRuta() == personajes[cont].getRuta()) {
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                            } else {
                                                panelImg.add(img[q][z]);
                                            }
                                            cont++;
                                        }
                                    }
                                    personajeOb.setEstado(1);
                                    vida3.setIcon(null);
                                    vidasActivas.setVidas(2);
                                    break;
                                case 2:
                                    empezarAux = 1;
                                    personajeOb.setRuta("img/00.png");
                                    int cont2 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            if (personajeOb.getRuta() == personajes[cont2].getRuta()) {
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                            } else {
                                                panelImg.add(img[q][z]);
                                            }
                                            cont2++;
                                        }
                                    }
                                    personajeOb.setEstado(1);
                                    vida2.setIcon(null);
                                    vidasActivas.setVidas(1);
                                    break;
                                case 1:
                                    empezarAux = 1;
                                    personajeOb.setRuta("img/00.png");
                                    int cont3 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            if (personajeOb.getRuta() == personajes[cont3].getRuta()) {
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                            } else {
                                                panelImg.add(img[q][z]);
                                            }
                                            cont3++;
                                        }
                                    }
                                    personajeOb.setEstado(1);
                                    vida1.setIcon(null);
                                    vidasActivas.setVidas(0);
                                    sonidoIntro("audio/nemesis.wav");
                                    clip.loop(0);
                                    JOptionPane.showMessageDialog(null, "Game over");
                                    break;
                                case 0:
                                    personajeOb.setEstado(1);
                                    JOptionPane.showMessageDialog(null, "Estas muerto, no puedes jugar.");
                                    break;
                                default:
                                    vidasActivas.setVidas(0);
                            }
                        }

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                });
                cont++;
            }
        }
    }

    public void pingFacil() {
        System.out.println("modo facil");
        int cont = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {

                String persona = personajes[cont].getNombre();
                //System.out.println(persona);
                //guardo todo el obejto en la variable personajeOb
                Personaje personajeOb = personajes[cont];

                img[x][y].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("selecciono a: " + persona);
                        //System.out.println(personajeOb.getEstado());

                        //sacar game over
                        endGame = 0;
                        for (int q = 0; q < personajes.length; q++) {
                            if (personajes[q].getEstado() == 1) {
                                endGame++;
                            }
                        }
                        System.out.println("hay tachadas: " + endGame);
                        int sumafin = endGame + 1;

                        if (ganar == 1) {
                            JOptionPane.showMessageDialog(null, "ya has ganado, deja de jugar");
                        } else if (gameOver) {
                            JOptionPane.showMessageDialog(null, "estas muerto");
                        } else if (empezar == 0) {
                            JOptionPane.showMessageDialog(null, "Aun no has seleccionado la pregunta");
                        } else if (personajeOb.getEstado() == 1) {
                            JOptionPane.showMessageDialog(null, "este personaje ya ha sido seleccionado");
                        } else if ((sumafin == 8) && persona != personajeMaquina) {

                            int cont = 0;
                            for (int q = 0; q < 3; q++) {
                                for (int z = 0; z < 3; z++) {
                                    if (personajeOb.getRuta() == personajes[cont].getRuta()) {
                                        personajeOb.setRuta("img/00.png");
                                        ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                        img[q][z].setIcon(icon);
                                        panelImg.add(img[q][z]);
                                        personajeOb.setEstado(1);
                                    } else {
                                        panelImg.add(img[q][z]);
                                    }
                                    cont++;
                                }
                            }

                            sonidoIntro("audio/nemesis.wav");
                            clip.loop(0);

                            JOptionPane.showMessageDialog(null, "Game OVer");
                            gameOver = true;
                        } else if ((persona == personajeMaquina)) {
                            puntuacion++;
                            String textPuntuacion = "puntuacion :" + puntuacion;
                            etiquetaPuntuacion.setText(textPuntuacion);
                            int contwin = 0;
                            for (int x = 0; x < 3; x++) {
                                for (int y = 0; y < 3; y++) {
                                    if (personajes[contwin].getNombre() == personajeMaquina) {
                                        personajeOb.setRuta("img/stars.png");
                                        ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                        Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                        img[x][y].setIcon(icon);
                                        panelImg.add(img[x][y]);
                                        personajeOb.setEstado(1);

                                    } else {
                                        System.out.println("a");
                                        panelImg.add(img[x][y]);

                                    }
                                    contwin++;
                                }
                            }

                            ganar = 1;
                            String conjunto;
                            String nombre = JOptionPane.showInputDialog("Has ganado a Umbrella.escribre tu nombre como ganador.");
                            conjunto = "\n" + nombre + " ---- " + puntuacion + " ---- " + " " + personajeMaquina;
                            //Scanner sc = new Scanner(System.in);
                            //PrintWriter salida = null;
                            BufferedWriter bw = null;
                            FileWriter fw = null;
                            try {
                                String data;
                                data = conjunto;
                                File file = new File("datos/puntuacion.txt");
                                // Si el archivo no existe, se crea
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                // flag true, indica adjuntar información al archivo.
                                fw = new FileWriter(file.getAbsoluteFile(), true);
                                bw = new BufferedWriter(fw);
                                bw.write(data);
                            } catch (IOException u) {
                                u.printStackTrace();
                            } finally {
                                try {
                                    //Cierra instancias de FileWriter y BufferedWriter
                                    if (bw != null) {
                                        bw.close();
                                    }
                                    if (fw != null) {
                                        fw.close();
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } else if (personajeOb.getEstado() == 1 && (vidasActivas.getVidas() >= 1)) {
                            JOptionPane.showMessageDialog(null, personajeOb.getNombre() + " ya ha sido seleccionado.");
                        } else {
                            //aqui va la logica
                            //System.out.println(preguntaActual);
                            //System.out.println(respuestaActual);
                            //int verificar = 0;
                            //int clickPersona = 0;
                            /*for(int x = 0; x<personajes.length;x++){                                    
                                    if(personajes[x].getPregunta_1()  == respuestaActual){
                                        //System.out.println("nombre: "+personajes[x].getNombre() + "estado: "+ personajes[x].getEstado());
                                    }                                
                                }*/

                            switch (preguntaActual) {
                                case 1:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_1() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_1() != respuestaActual) && (personajeOb.getRuta() == personajes[cont].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 2:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_2() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont2 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_2() != respuestaActual) && (personajeOb.getRuta() == personajes[cont2].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont2++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 3:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_3() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont3 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_3() != respuestaActual) && (personajeOb.getRuta() == personajes[cont3].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont3++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 4:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_4() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont4 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_4() != respuestaActual) && (personajeOb.getRuta() == personajes[cont4].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont4++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 5:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_5() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont5 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_5() != respuestaActual) && (personajeOb.getRuta() == personajes[cont5].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont5++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 6:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_6() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont6 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_6() != respuestaActual) && (personajeOb.getRuta() == personajes[cont6].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont6++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 7:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_7() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont7 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_7() != respuestaActual) && (personajeOb.getRuta() == personajes[cont7].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont7++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 8:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_8() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont8 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_8() != respuestaActual) && (personajeOb.getRuta() == personajes[cont8].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont8++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 9:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_9() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont9 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_9() != respuestaActual) && (personajeOb.getRuta() == personajes[cont9].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont9++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 10:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_10() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont10 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_10() != respuestaActual) && (personajeOb.getRuta() == personajes[cont10].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont10++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 11:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_11() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont11 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_11() != respuestaActual) && (personajeOb.getRuta() == personajes[cont11].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont11++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 12:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_12() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont12 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_12() != respuestaActual) && (personajeOb.getRuta() == personajes[cont12].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont12++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;
                                case 13:

                                    //personajeOb.setRuta("img/00.png");
                                    //String rutaCambio = personajeOb.getRuta();
                                    for (int z = 0; z < personajes.length; z++) {
                                        if ((personajes[z].getPregunta_13() != respuestaActual) && (personajes[z].getEstado() != 1)) {
                                            verificar++;
                                        }
                                    }

                                    int cont13 = 0;
                                    for (int q = 0; q < 3; q++) {
                                        for (int z = 0; z < 3; z++) {
                                            //total de personajes a quitar que cumplen la regla

                                            //poner foto
                                            if ((personajeOb.getPregunta_13() != respuestaActual) && (personajeOb.getRuta() == personajes[cont13].getRuta())) {
                                                personajeOb.setRuta("img/00.png");
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[q][z].setIcon(icon);
                                                panelImg.add(img[q][z]);
                                                personajeOb.setEstado(1);
                                                clickPersona++;
                                                //System.out.println("veces clicadas al correcto:"+clickPersona);
                                                if (verificar == 1) {
                                                    empezarAux = 1;
                                                    verificar = 0;
                                                    clickPersona = 0;
                                                    empezar = 0;
                                                    JOptionPane.showMessageDialog(null, "ya puedes volver a seleccionar otra pregunta");
                                                    pulsarBoton = true;
                                                }
                                            } else {
                                                panelImg.add(img[q][z]);

                                            }
                                            cont13++;
                                        }
                                    }
                                    //personajeOb.setEstado(1);  
                                    System.out.println("hay que señalar: " + verificar);
                                    verificar = 0;
                                    break;

                                default:
                                    System.out.println("no deberias estar aqui,fuera");
                            }
                            puntuacion++;
                            String textPuntuacion = "puntuacion :" + puntuacion;
                            etiquetaPuntuacion.setText(textPuntuacion);

                        }

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                });
                cont++;
            }
        }
    }

    //reiniciar el juego
    public void reiniciar() {
        puntuacion = 0;
        empezar = 0;
        ganar = 0;
        empezarAux = 0;
        clickPersona = 0;
        verificar = 0;
        gameOver = false;
        if (modo == 0) {
            String textPuntuacion = "puntuacion :" + puntuacion;
            etiquetaPuntuacion.setText(textPuntuacion);
        }

        for (int x = 0; x < pregunta.length; x++) {
            String rescribir = pregunta[x].getPregunta();
            //pregunta[x].setPregunta(reinicio);
            preguntas[x].setText(rescribir);

        }

        int numMaquina = (int) Math.floor(Math.random() * 8 + 1);
        clasePersonaje = personajes[numMaquina];
        personajeMaquina = personajes[numMaquina].getNombre();
        for (int x = 0; x < personajes.length; x++) {
            personajes[x].setEstado(0);
        }

        //crea un atributo aux para guardar la ruta origen y no modificarla
        int cont = 0;
        for (int q = 0; q < 3; q++) {
            for (int z = 0; z < 3; z++) {
                System.out.println(personajes[cont].getRutaAux());
                String resetearRuta = personajes[cont].getRutaAux();
                personajes[cont].setRuta(resetearRuta);
                ImageIcon imageicon = new ImageIcon(personajes[cont].getRutaAux());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                img[q][z].setIcon(icon);
                panelImg.add(img[q][z]);
                cont++;
            }
        }

        for (int y = 0; y < pregunta.length; y++) {
            pregunta[y].setEstado(0);
        }

        for (int y = 0; y < preguntas.length; y++) {
            //volver el color original de las preguntas
            preguntas[y].setForeground(Color.GRAY);
        }

        respuesta.setText("bienvenido  al juego.");

        if (modo == 1) {
            String ruta = "icono/signo.png";
            ImageIcon imageiconvida1 = new ImageIcon(ruta);
            Icon iconfondovida1 = new ImageIcon(imageiconvida1.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
            vida1.setIcon(iconfondovida1);
            ImageIcon imageiconvida2 = new ImageIcon(ruta);
            Icon iconfondovida2 = new ImageIcon(imageiconvida2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
            vida2.setIcon(iconfondovida2);
            ImageIcon imageiconvida3 = new ImageIcon(ruta);
            Icon iconfondovida3 = new ImageIcon(imageiconvida3.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
            vida3.setIcon(iconfondovida3);
            vidasActivas.setVidas(3);
        }
        System.out.println("reinicio con:" + personajeMaquina);

    }

    //barra de menú del juego
    public void barramenu() {
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu opciones = new JMenu("Opciones");
        JMenuItem reiniciar = new JMenuItem("Reiniciar");
        JMenuItem puntuacion = new JMenuItem("Puntuacion");
        JMenuItem salir = new JMenuItem("salir");
        menubar.add(opciones);
        opciones.add(reiniciar);
        opciones.add(puntuacion);
        opciones.add(salir);

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        reiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciar();
            }

        });

        puntuacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FileReader fr = null;
                String contenido = "";

                try {
                    fr = new FileReader("datos/puntuacion.txt");
                    BufferedReader entrada = new BufferedReader(fr);
                    String cadena = entrada.readLine();    //se lee la primera línea del fichero
                    while (cadena != null) {               //mientras no se llegue al final del fichero                   
                        //System.out.println(cadena);        //se nuestra por pantalla
                        contenido += cadena;
                        contenido += "\n";
                        cadena = entrada.readLine();       //se lee la siguiente línea del fichero                        
                    }
                } catch (FileNotFoundException x) {
                    System.out.println(x.getMessage());
                } catch (IOException i) {
                    System.out.println(i.getMessage());
                } finally {
                    try {
                        if (fr != null) {
                            fr.close();
                        }
                    } catch (IOException z) {
                        System.out.println(z.getMessage());
                    }
                }
                JOptionPane.showMessageDialog(null, contenido);

            }

        });

    }

    ;
            //creación de los personajes
        private void crearpersonajes() {
        personajes = new Personaje[9];
        personajes[0] = new Personaje("Ada Wong", "img/00.jpg", "img/00.jpg", 0, "si", "no", "no", "si", "no", "no", "no", "no", "no", "si", "si", "si", "no");
        personajes[1] = new Personaje("Albert Wesker", "img/01.jpg", "img/01.jpg", 0, "si", "si", "no", "si", "si", "si", "no", "no", "si", "no", "si", "no", "si");
        personajes[2] = new Personaje("Chris Redfield", "img/02.jpg", "img/02.jpg", 0, "si", "no", "no", "no", "no", "si", "si", "no", "si", "no", "no", "no", "no");

        personajes[3] = new Personaje("Hunk", "img/10.jpg", "img/10.jpg", 0, "si", "no", "si", "si", "no", "no", "no", "no", "no", "no", "no", "no", "no");
        personajes[4] = new Personaje("Jill Valentine", "img/11.jpg", "img/11.jpg", 0, "si", "no", "no", "si", "si", "si", "si", "no", "si", "no", "no", "si", "no");
        personajes[5] = new Personaje("Leon Kennedy", "img/12.jpg", "img/12.jpg", 0, "si", "no", "no", "si", "si", "si", "no", "no", "no", "si", "no", "no", "no");

        personajes[6] = new Personaje("Nemesis", "img/20.jpg", "img/20.jpg", 0, "no", "no", "no", "si", "si", "si", "no", "si", "no", "no", "no", "no", "no");
        personajes[7] = new Personaje("Zombie", "img/21.jpg", "img/21.jpg", 0, "no", "no", "no", "no", "si", "si", "no", "no", "si", "no", "no", "no", "no");
        personajes[8] = new Personaje("William Birkin", "img/22.jpg", "img/22.jpg", 0, "si", "si", "no", "si", "si", "no", "no", "no", "si", "no", "no", "no", "no");
    }

    //creación de las preguntas
    private void crearpreguntas() {
        pregunta = new Preguntas[13];
        pregunta[0] = new Preguntas("Es un humano", 1, 0);
        pregunta[1] = new Preguntas("Ha creado algun virus", 2, 0);
        pregunta[2] = new Preguntas("Pertenece a las fuerzas especiales de Umbrella Corps", 3, 0);
        pregunta[3] = new Preguntas("Ha estado en el incidente de Racoon City", 4, 0);
        pregunta[4] = new Preguntas("Ha sido infectado con un virus", 5, 0);
        pregunta[5] = new Preguntas("Ha estado en la RPD", 6, 0);
        pregunta[6] = new Preguntas("A combatido las plagas en Africa", 7, 0);
        pregunta[7] = new Preguntas("Da caza a los STARS", 8, 0);
        pregunta[8] = new Preguntas("A estado en la mansion Spencer", 9, 0);
        pregunta[9] = new Preguntas("A estado por Espana en busca de la hija del presidente", 10, 0);
        pregunta[10] = new Preguntas("Es agente doble", 11, 0);
        pregunta[11] = new Preguntas("Fue declarada muerta", 12, 0);
        pregunta[12] = new Preguntas("Muere en un volcan", 13, 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        JuegoQuien juego = new JuegoQuien();
        juego.setVisible(true);
    }

}

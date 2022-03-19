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
import java.io.File;
import java.io.IOException;
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

    public JPanel juegoPanel, panelImg, panelBotones, panelItro, vidas, panelPreguntas, panelRespuesta;
    public JButton boton, boton2;
    public Clip clip;
    JLabel[] img = new JLabel[9];
    JLabel[] preguntas = new JLabel[13];
    JLabel vida1, vida2, vida3, respuesta, fondo;

    public Personaje clasePersonaje;

    public String reinicio = "";

    public Preguntas pregunta[];

    public Personaje personajes[];
    public Vidas vidasActivas;

    public String personajeMaquina;

    public JuegoQuien() {
        crearVentana();
        crearPaneles();
        juego();

    }

    public void crearVentana() {

        //this.setSize(800, 800);
        this.setSize(1000, 1000);
        //pone titulo a la ventana
        setTitle("¿Quien es quien?");
        //nos hace el cierre de la ventana 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1000, 1000));
        this.setResizable(false);
        this.getContentPane().setBackground(Color.BLACK);
        //quita el layout
        this.setLayout(null);

    }

    public void crearPaneles() {

        panelItro = new JPanel();
        panelImg = new JPanel();
        panelBotones = new JPanel();
        juegoPanel = new JPanel();
        panelPreguntas = new JPanel();
        panelRespuesta = new JPanel();

        panelPreguntas.setBackground(Color.MAGENTA);
        panelItro.setBackground(Color.black);
        panelBotones.setBackground(Color.black);
        panelRespuesta.setBackground(Color.red);

        panelImg.setBackground(Color.black);
        panelImg.setOpaque(false);
        //juegoPanel.setBackground(Color.pink);
        juegoPanel.setBounds(0, 0, 1000, 1000);
        panelBotones.setBounds(355, 450, 300, 100);
        panelRespuesta.setBounds(200, 750, 450, 25);
        //panel de las fotos
        panelImg.setBounds(15, 50, 500, 650);
        //panelItro.setBounds(0, 0, 500, 600);
        panelItro.setBounds(0, 0, 1000, 1000);
        panelPreguntas.setBounds(600, 73, 350, 500);
        juegoPanel.add(panelImg);
        juegoPanel.add(panelRespuesta);
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
        //juegoPanel.add(fondo);

//quitar para que salga el fondo de pantall de zombies        
    }

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
                clip.close();
                String voz = "audio/voz.wav";
                sonidoIntro(voz);
                System.err.println(clip.isRunning());
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

            }

        });

    }

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
                img[cont] = new JLabel();
                //String ruta = "img/"+x+y+".jpg";
                //ImageIcon imageicon = new ImageIcon(ruta);
                ImageIcon imageicon = new ImageIcon(personajes[cont].getRuta());
                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                img[cont].setIcon(icon);

                //imagen.setBounds(250, 250, 300, 200); 
                //System.out.println(img[0]);
                panelImg.add(img[cont]);
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

        //vidas
        vidas();

        //elegir pregunta y su respuesta
        question();

        //pulsador
        ping();
        juegoPanel.add(fondo);
    }

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
            } catch (FontFormatException ex) {
                System.err.println("error en font format");
            } catch (IOException ex) {
                System.err.println("error de entrada/salida");
            }
            panelPreguntas.add(preguntas[x]);
        }

    }

    public void vidas() {
        vidas = new JPanel();
        //vidas.setBackground(Color.black);
        vidas.setBounds(750, 15, 150, 75);
        vidas.setOpaque(false);
        juegoPanel.add(vidas);

        vida1 = new JLabel();
        //añadimos la imagen
        String ruta = "icono/signo.png";
        ImageIcon imageiconvida1 = new ImageIcon(ruta);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconfondovida1 = new ImageIcon(imageiconvida1.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        vida1.setIcon(iconfondovida1);
        vida1.setBounds(0, 0, 50, 50);
        vidas.add(vida1);

        vida2 = new JLabel();
        //añadimos la imagen
        ImageIcon imageiconvida2 = new ImageIcon(ruta);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconfondovida2 = new ImageIcon(imageiconvida2.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        vida2.setIcon(iconfondovida2);
        vida2.setBounds(0, 0, 50, 50);
        vidas.add(vida2);

        vida3 = new JLabel();
        //añadimos la imagen
        ImageIcon imageiconvida3 = new ImageIcon(ruta);
        //se crea la imagen y se escala a lo que le hemos dicho con Image.SCALE_DEFAULT
        Icon iconfondovida3 = new ImageIcon(imageiconvida3.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        //se añade la imagen a la etiqueta
        vida3.setIcon(iconfondovida3);
        vida3.setBounds(0, 0, 50, 50);
        vidas.add(vida3);

        vidasActivas = new Vidas(3);

    }

    public void question() {

        respuesta = new JLabel();
        //respuesta.setText("A");
        respuesta.setText("bienvenido  al juego.");

        for (int x = 0; x < pregunta.length; x++) {

            String pre = pregunta[x].getPregunta();
            Preguntas estado = pregunta[x];

            int numero = pregunta[x].getNumero();

            preguntas[x].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println(numero);
                    switch (numero) {
                        case 1:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_1());
                                preguntas[0].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 2:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_2());
                                preguntas[1].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 3:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_3());
                                preguntas[2].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 4:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_4());
                                preguntas[3].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 5:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_5());
                                preguntas[4].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 6:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_6());
                                preguntas[5].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 7:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_7());
                                preguntas[6].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 8:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_8());
                                preguntas[7].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 9:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_9());
                                preguntas[8].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 10:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_10());
                                preguntas[9].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 11:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_11());
                                preguntas[10].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 12:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_12());
                                preguntas[11].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        case 13:
                            if (estado.getEstado() == 0) {
                                estado.setEstado(1);
                                respuesta.setText(pre + ": " + clasePersonaje.getPregunta_13());
                                preguntas[12].setForeground(Color.red);
                            } else {
                                respuesta.setText("esta pregunta ya ha sido respondidia");
                            }
                            break;
                        default:
                            System.out.println("algo sale mal, no deberias estar mirando esto");
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

    public void ping() {

        for (int y = 0; y < img.length; y++) {

            String persona = personajes[y].getNombre();
            //guardo todo el obejto en la variable personajeOb
            Personaje personajeOb = personajes[y];

            img[y].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(persona);
                    System.out.println(personajeOb.getRuta());
                    int estado = 0;
                    for (int x = 0; x < img.length; x++) {
                        if (persona == personajes[x].getNombre()) {
                            estado = personajes[x].getEstado();
                        }
                    }

                    if ((persona == personajeMaquina) && (vidasActivas.getVidas() >= 1)) {
                        JOptionPane.showMessageDialog(null, "Ganas");
                    } else if (estado == 1) {
                        JOptionPane.showMessageDialog(null, "Este personaje ya ha sido seleccionado.");
                    } else {
                        int vidas = vidasActivas.getVidas();
                        switch (vidas) {
                            case 3:
                                for (int x = 0; x < img.length; x++) {
                                    if (persona == personajes[x].getNombre()) {
                                        personajeOb.setRuta("img/umbrella.png");

                                        int cont = 0;
                                        for (int q = 0; q < 3; q++) {
                                            for (int y = 0; y < 3; y++) {
                                                if(personajeOb.getRuta() == personajes[cont].getRuta()){
                                                ImageIcon imageicon = new ImageIcon(personajeOb.getRuta());
                                                Icon icon = new ImageIcon(imageicon.getImage().getScaledInstance(170, 170, Image.SCALE_DEFAULT));
                                                img[cont].setIcon(icon);
                                                panelImg.add(img[cont]);
                                                }
                                                cont++;
                                            }
                                        }

                                        personajes[x].setEstado(1);
                                    }
                                }

                                vida3.setIcon(null);
                                vidasActivas.setVidas(2);
                                break;
                            case 2:
                                for (int x = 0; x < img.length; x++) {
                                    if (persona == personajes[x].getNombre()) {
                                        personajes[x].setEstado(1);
                                    }
                                }
                                vida2.setIcon(null);
                                vidasActivas.setVidas(1);
                                break;
                            case 1:
                                for (int x = 0; x < img.length; x++) {
                                    if (persona == personajes[x].getNombre()) {
                                        personajes[x].setEstado(1);
                                    }
                                }
                                vida1.setIcon(null);
                                vidasActivas.setVidas(0);
                                sonidoIntro("audio/nemesis.wav");
                                clip.loop(0);
                                JOptionPane.showMessageDialog(null, "Game over");
                                break;
                            case 0:
                                for (int x = 0; x < img.length; x++) {
                                    if (persona == personajes[x].getNombre()) {
                                        personajes[x].setEstado(1);
                                    }
                                }
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

        }
    }

    public void barramenu() {
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu opciones = new JMenu("Opciones");
        JMenuItem reiniciar = new JMenuItem("Reiniciar");
        JMenuItem puntuacion = new JMenuItem("Puntiacion");
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
                int numMaquina = (int) Math.floor(Math.random() * 8 + 1);
                clasePersonaje = personajes[numMaquina];
                personajeMaquina = personajes[numMaquina].getNombre();
                for (int x = 0; x < personajes.length; x++) {
                    personajes[x].setEstado(0);
                }

                for (int y = 0; y < personajes.length; y++) {
                    pregunta[y].setEstado(0);
                }

                for (int y = 0; y < preguntas.length; y++) {
                    //volver el color original de las preguntas
                    preguntas[y].setForeground(Color.black);
                }

                respuesta.setText("bienvenido  al juego.");

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
                System.out.println("reinicio con:" + personajeMaquina);

            }

        });

    }

    ;
    
    
    
        private void crearpersonajes() {
        personajes = new Personaje[9];
        personajes[0] = new Personaje("Ada Wong", "img/00.jpg", 0, "si", "no", "no", "si", "no", "no", "no", "no", "no", "si", "si", "si", "no");
        personajes[1] = new Personaje("Albert Wesker", "img/01.jpg", 0, "si", "si", "no", "si", "si", "si", "no", "no", "si", "no", "si", "no", "si");
        personajes[2] = new Personaje("Chris Redfield", "img/02.jpg", 0, "si", "no", "no", "no", "no", "si", "si", "no", "si", "no", "no", "no", "no");

        personajes[3] = new Personaje("Hunk", "img/10.jpg", 0, "si", "no", "si", "si", "no", "no", "no", "no", "no", "no", "no", "no", "no");
        personajes[4] = new Personaje("Jill Valentine", "img/11.jpg", 0, "si", "no", "no", "si", "si", "si", "si", "no", "si", "no", "no", "si", "no");
        personajes[5] = new Personaje("Leon Kennedy", "img/12.jpg", 0, "si", "no", "no", "si", "si", "si", "no", "no", "no", "si", "no", "no", "no");

        personajes[6] = new Personaje("Nemesis", "img/20.jpg", 0, "no", "no", "no", "si", "si", "si", "no", "si", "no", "no", "no", "no", "no");
        personajes[7] = new Personaje("Zombie", "img/21.jpg", 0, "no", "no", "no", "no", "si", "si", "no", "no", "si", "no", "no", "no", "no");
        personajes[8] = new Personaje("William Birkin", "img/22.jpg", 0, "si", "si", "no", "si", "si", "no", "no", "no", "si", "no", "no", "no", "no");
    }

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

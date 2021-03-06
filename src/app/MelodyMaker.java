package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.jfugue.player.Player;


@SuppressWarnings("serial")
public class MelodyMaker extends JFrame {
	
	private JTextField field;
	private Figuras figuraActual = Figuras.Redonda;
	private Melodia melodia;
	
    public MelodyMaker() {

        initUI();
    }

    public final void initUI() {
    	
    	/* MENU */
    	iniMenu();

    	/* PANEL CONTAINER */
        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
        add(basic);
        
        /* TOP */
        basic.add(topPanel());
        
        /* MIDDLE */
        basic.add(midPanel());
                
        basic.add(Box.createVerticalGlue());
        
        
        /* BOTTOM */
        basic.add(bottomPanel());


        setTitle("Melody Maker");
        setSize(new Dimension(1024, 460));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon webIcon = new ImageIcon(getClass().getResource("/icons/turntable.png"));;
        setIconImage(webIcon.getImage());
    }

    
    private void iniMenu() {

        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/exit24.png"));

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem unMenuItem = new JMenuItem("Exit", icon);
        unMenuItem.setMnemonic(KeyEvent.VK_E);
        unMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                ActionEvent.CTRL_MASK));
        unMenuItem.setToolTipText("Exit application");
        unMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			    System.exit(0);
			}
		});

        file.add(unMenuItem);

        menubar.add(file);

        setJMenuBar(menubar);
    }

	
	private JPanel topPanel() {
		JPanel topPanel = new JPanel(new BorderLayout(0, 0));
        topPanel.setMaximumSize(new Dimension(1000, 100));
        
        JLabel hint = new JLabel("Melody Maker");
        hint.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        topPanel.add(hint);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/altavoces.png"));
        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(label, BorderLayout.EAST);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);

        topPanel.add(separator, BorderLayout.SOUTH);
		return topPanel;
	}

	private JPanel midPanel() {
		JPanel medio = new JPanel(new BorderLayout());
        medio.setMaximumSize(new Dimension(950, 300));
        
        JPanel claves = new JPanel();
        claves.setLayout(new GridLayout(7, 2));
        claves.setMaximumSize(new Dimension(250,200));
        
        for (Figuras f : Figuras.values()) {
        	JLabel labelName = new JLabel(f.getName());
        	JButton labelIcon = new JButton(new ImageIcon(new ImageIcon(getClass().getResource(f.getFile())).getImage()));
        	labelIcon.addActionListener(new FiguraListener(f));
        	claves.add(labelName);
        	claves.add(labelIcon);
        	
        }
        medio.add(claves, BorderLayout.LINE_START);
        
        JPanel dibu = new JPanel(new BorderLayout());
        dibu.setSize(new Dimension(0, 400));
        dibu.add(new DrawPanel(), BorderLayout.CENTER);
        
        medio.add(dibu);
        
		return medio;
	}

	private JPanel bottomPanel() {
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton play = new JButton("Play");
        play.setMnemonic(KeyEvent.VK_P);
        JButton close = new JButton("Clean");
        close.setMnemonic(KeyEvent.VK_C);
        close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				melodia.clear();
				field.setText("");
				repaint();
			}
        });
        
        play.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		Player player = new Player();
        		player.play(field.getText());
        	}
        });

        this.field = new JTextField(32);
        JLabel lbl = new JLabel("Melodia");
        bottom.add(lbl);
        bottom.add(field);
        
        
        bottom.add(play);
        bottom.add(close);
        bottom.setMaximumSize(new Dimension(600,0));
		return bottom;
	}

	class FiguraListener implements ActionListener{
		private Figuras figura;
		
		public FiguraListener(Figuras figura){
			this.figura = figura;
		}
		public void actionPerformed(ActionEvent arg0) {
			figuraActual = figura;
		}
		
	}
	class DrawPanel extends JPanel  implements MouseListener{
		
		
		private Image bufferImage;
		
		public DrawPanel(){
			super();
	        this.addMouseListener(this);
	        melodia = new Melodia();
		}
		
	    private void doDrawing(Graphics g) {
	        
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.drawLine(20, 60, 900, 60);
	        g2d.drawLine(20, 90, 900, 90);
	        g2d.drawLine(20, 120, 900, 120);
	        g2d.drawLine(20, 150, 900, 150);
	        g2d.drawLine(20, 180, 900, 180);
	        g2d.drawRect(762, 60, 7, 120);
	        g2d.drawRect(761, 60, 7, 120);
	        g2d.drawRect(20, 60, 1, 120);
	                
	        //Toolkit t = Toolkit.getDefaultToolkit();
	        //Image imagen = t.getImage("icons/clavesol-180.png");
	        //g2d.drawImage(imagen, -45, 39, this);
	        
	        Image im = new ImageIcon(getClass().getResource("/icons/clavesol-180.png")).getImage();
	        g2d.drawImage(im, -45, 39, this);
	        /* prueba de agregar figuras*/      
	        drawingFiguras(g2d);
	        /* fin prueba */
	        
	   
	    }
	    
	    public void drawingFiguras(Graphics2D g2d){
    	
        	for (Nota n : melodia.getNotas()){
        		bufferImage = new ImageIcon(getClass().getResource(n.getFigura().getFile())).getImage();
        		g2d.drawImage(bufferImage, n.getX(), n.getY(), this);
        	}
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        
	        super.paintComponent(g);
	        doDrawing(g);
	    }

		@Override
		public void mouseClicked(MouseEvent e) {
	        int y = e.getY();
	        for (Notas nota : Notas.values()) {
				if(nota.is(y)){ 
					melodia.addNota(new Nota(nota, figuraActual, e.getX(), e.getY()));
					field.setText(melodia.toString());
					repaint();
					return;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                MelodyMaker ex = new MelodyMaker();
                ex.setVisible(true);
            }
        });
    }
}
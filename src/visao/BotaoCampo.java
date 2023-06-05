package visao;

import java.awt.Color;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton 
	implements CampoObservador,MouseListener{
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);
	private Campo campo;
	
	public BotaoCampo(Campo campo) throws IOException {
		this.campo = campo;
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		setOpaque(true);
		addMouseListener(this);
		campo.registrarObservador(this);	
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			this.setIcon(null);
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExpodir();
			break;
		default:
			this.setIcon(null);
			aplicarEstiloPadrao();
			break;
		}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
			
		});
		
	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}

	private void aplicarEstiloExpodir() {
		
		try {
			
			setIcon(new ImageIcon(retornaUmaImagem("bomb.png")));
			
		}catch(Exception e) {
			setBackground(BG_EXPLODIR);
			setForeground(Color.WHITE);
			setText("X");
		}
		
	}

	private void aplicarEstiloMarcar() {
		
		try {
			
			setIcon(new ImageIcon(retornaUmaImagem("red-flag.png")));

		}catch(Exception e) {
			setBackground(BG_MARCAR);
			setForeground(Color.BLACK);
			setText("M");
		}
		
	}
	
	private Image retornaUmaImagem(String png) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(png));
			img = img.getScaledInstance(15,15,15);
		} catch (IOException e) {
			img = null;
		}
		
		return img;
	}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			aplicarEstiloExpodir();
			return;
		}
		

		setBackground(BG_PADRAO);
		
		switch(campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
			break;
		}
		
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1) {
			campo.abrir();
		}else {
			campo.alternarMarcacao();
		}
		
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

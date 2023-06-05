package visao;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLinhas(),tabuleiro.getColunas()));
		
		tabuleiro.paraCadaCampo(c -> {
			try {
				add(new BotaoCampo(c));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		tabuleiro.registrarObservador(e -> {
			SwingUtilities.invokeLater(() -> {
				if(e.FALSE) { 
					JOptionPane.showMessageDialog(this, "Ganhou");
				}else {
					JOptionPane.showMessageDialog(this, "Perdeu");
				}
				tabuleiro.reiniciar();
			});	
		});
	}
}

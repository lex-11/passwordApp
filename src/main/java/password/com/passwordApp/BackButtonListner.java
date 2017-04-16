package password.com.passwordApp;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class BackButtonListner implements ActionListener {
	
	JPanel cards;

	public BackButtonListner(JPanel cards) {
		this.cards = cards;
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, App.LOGINPANEL);
		
	}

}

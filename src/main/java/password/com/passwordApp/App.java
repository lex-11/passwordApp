package password.com.passwordApp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import password.com.passwordApp.config.datasource.DataSourceConfig;
import password.com.passwordApp.dao.Password;
import password.com.passwordApp.dao.PasswordDao;
import password.com.passwordApp.dao.User;
import password.com.passwordApp.dao.UserDao;

/**
 * Hello world!
 * 
 */
public class App {
	final public static String LOGINPANEL = "Card with login JButtons";
	final static String SEARCHPANEL = "Card with search JTextField";
	private static final String SAVEPANEL = "save";
	private String USER_ID = "User ID : ";
	private String PASSWORD = "Password : ";

	static JFrame frame;

	PasswordDao passwordDao = null;
	UserDao userDao = null;

	private String SEARCHKEY = "Search Key : ";

	public static void main(String[] args) {

		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}

		});

	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Password App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		App app = new App();
		app.buildUI(frame.getContentPane());

		// Display the window.

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setSize(screenSize.width / 2, screenSize.height / 6);
		frame.setVisible(true);
	}

	private void buildUI(Container container) {

		ApplicationContext context = new AnnotationConfigApplicationContext(
				DataSourceConfig.class);
		passwordDao = context.getBean(PasswordDao.class);
		userDao = context.getBean(UserDao.class);

		JPanel cards = new JPanel(new CardLayout());

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());

		JPanel loginInputPanel = new JPanel();
		loginInputPanel.setLayout(new GridLayout(2, 2));
		JLabel userNameLabel = new JLabel(USER_ID);
		loginInputPanel.add(userNameLabel);
		JTextField userid = new JTextField();
		loginInputPanel.add(userid);
		JLabel passwordLabel = new JLabel(PASSWORD);
		loginInputPanel.add(passwordLabel);
		JPasswordField password = new JPasswordField();
		loginInputPanel.add(password);

		loginPanel.add(loginInputPanel, BorderLayout.CENTER);

		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new GridLayout(1, 2));
		JLabel submitLabel = new JLabel();
		submitPanel.add(submitLabel);
		JButton submitButton = new JButton("Login");
		submitButton.addActionListener(new LoginActionLisner(password, userid,
				cards));

		submitPanel.add(submitButton);

		loginPanel.add(submitPanel, BorderLayout.SOUTH);

		cards.add(loginPanel, LOGINPANEL);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());

		JPanel searchInputPanel = new JPanel();
		searchInputPanel.setLayout(new GridLayout(2, 2));
		JLabel searchKey = new JLabel(SEARCHKEY);
		searchInputPanel.add(searchKey);
		JTextField inputSearchKey = new JTextField();
		searchInputPanel.add(inputSearchKey);
		JLabel result = new JLabel(PASSWORD);
		searchInputPanel.add(result);
		JTextArea searchResult = new JTextArea();
		searchResult.setEditable(false);
		searchInputPanel.add(searchResult);

		searchPanel.add(searchInputPanel, BorderLayout.CENTER);

		JPanel searchSubmit = new JPanel();
		searchSubmit.setLayout(new GridLayout(1, 4));

		JButton backButton = new JButton("Logout");
		backButton.addActionListener(new BackButtonListner(cards));
		searchSubmit.add(backButton);

		JLabel backLabel = new JLabel();
		searchSubmit.add(backLabel);

		JLabel seearchLabel = new JLabel();
		searchSubmit.add(seearchLabel);

		JButton seearchSubmitButton = new JButton("Search");
		seearchSubmitButton.addActionListener(new SearchInputActionLisner(
				inputSearchKey, searchResult));
		searchSubmit.add(seearchSubmitButton);

		searchPanel.add(searchSubmit, BorderLayout.SOUTH);

		cards.add(searchPanel, SEARCHPANEL);

		JPanel savePanel = new JPanel();
		savePanel.setLayout(new BorderLayout());

		JPanel saveNewPanel = new JPanel();
		saveNewPanel.setLayout(new GridLayout(2, 2));
		JLabel _searchKey = new JLabel(SEARCHKEY);
		saveNewPanel.add(_searchKey);
		JTextField _searchKeyText = new JTextField();
		saveNewPanel.add(_searchKeyText);
		JLabel _result = new JLabel(PASSWORD);
		saveNewPanel.add(_result);
		JTextField _password = new JTextField();

		saveNewPanel.add(_password);

		savePanel.add(saveNewPanel, BorderLayout.CENTER);

		JPanel saveSubmit = new JPanel();
		saveSubmit.setLayout(new GridLayout(1, 4));

		JButton _backButton = new JButton("Logout");
		_backButton.addActionListener(new BackButtonListner(cards));
		saveSubmit.add(_backButton);

		JLabel _backLabel = new JLabel();
		saveSubmit.add(_backLabel);

		JLabel _seearchLabel = new JLabel();
		saveSubmit.add(_seearchLabel);

		JButton saveSubmitButton = new JButton("Save");
		saveSubmitButton.addActionListener(new SaveActionLisner(_searchKeyText,
				_password));
		saveSubmit.add(saveSubmitButton);

		savePanel.add(saveSubmit, BorderLayout.SOUTH);

		cards.add(savePanel, SAVEPANEL);

		container.add(cards);

	}

	class SearchInputActionLisner implements ActionListener {

		JTextField inputSearchKey;
		JTextArea searchResult;

		public SearchInputActionLisner(JTextField inputSearchKey,
				JTextArea searchResult2) {
			this.inputSearchKey = inputSearchKey;
			this.searchResult = searchResult2;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			Password password = passwordDao.getPasswordContainingKey(inputSearchKey.getText());
			if(password == null){
				searchResult.setText("Sorry can not found password !");
			}else{
				searchResult.setText(password.getPassword());
			}

		}

	}

	class SaveActionLisner implements ActionListener {

		JTextField inputSearchKey;
		JTextField _password;

		public SaveActionLisner(JTextField inputSearchKey,
				JTextField password) {
			this.inputSearchKey = inputSearchKey;
			this._password = password;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			    Password password = new Password();
			    password.setPassword(_password.getText());
			    password.setPassword_description(inputSearchKey.getText());
                passwordDao.savePassword(password);
		}
	}

	class LoginActionLisner implements ActionListener {

		JTextField userid;
		JPasswordField password;

		JPanel cards;

		public LoginActionLisner(JPasswordField password, JTextField userid,
				JPanel cards) {
			super();
			this.password = password;
			this.userid = userid;
			this.cards = cards;
		}

		public void actionPerformed(ActionEvent e) {
			if (isLogginSuccessful()) {
				Object[] options = { "Add New", "Search" };
				int n = JOptionPane
						.showOptionDialog(
								frame,
								"Would you like to search password or add new?",
								"Question", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (n == JOptionPane.YES_OPTION) {
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, SAVEPANEL);
				} else if (n == JOptionPane.NO_OPTION) {
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, SEARCHPANEL);
				}

			} else {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, LOGINPANEL);
			}

		}

		private boolean isLogginSuccessful() {
			if (userid.getText() == null) {
				return false;
			}
			User user = userDao.getUser(userid.getText());
			if (user != null
					&& user.getPassword().equals(
							new String(password.getPassword())))

				return true;
			else
				return false;
		}
	}

	public void actionPerformed(ActionEvent e) {

	}
}

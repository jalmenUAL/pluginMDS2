package cd2v.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;

public class CustomDialogHandler implements IDialogHandler {
	private IDialog _dialog;
	private Component _component;
	private JTextField _inputField1, _inputField2, _answerField;

	public Component getComponent() {
		this._inputField1 = new JTextField(10);
		this._inputField2 = new JTextField(10);
		this._answerField = new JTextField(10);
		JLabel addLabel = new JLabel(" + ");
		JLabel equalLabel = new JLabel(" = ");
		JButton okButton = new JButton("Apply");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ok();
			}
		});
		JPanel pane = new JPanel();
		pane.add(this._inputField1);
		pane.add(addLabel);
		pane.add(this._inputField2);
		pane.add(equalLabel);
		pane.add(this._answerField);
		pane.add(okButton);
		this._component = pane;
		return pane;
	}

	public void prepare(IDialog dialog) {
		this._dialog = dialog;
		dialog.setModal(true);
		dialog.setTitle("Maths Test");
		dialog.setResizable(false);
		dialog.pack();
		this._inputField1.setText(String.valueOf((int) (Math.random() * 10000)));
		this._inputField2.setText(String.valueOf((int) (Math.random() * 10000)));
	}

	public void shown() {
		ApplicationManager.instance().getViewManager().showMessageDialog(this._component,
				"Maths Test is started, you have an half hour to finish this test.", "Maths Test",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean canClosed() {
		if (this.checkAnswer()) {
			return true;
		} else {
			ApplicationManager.instance().getViewManager().showMessageDialog(this._component, "Incorrect", "Maths Test",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void ok() {
		if (this.checkAnswer()) {
			this._dialog.close();
		} else {
			ApplicationManager.instance().getViewManager().showMessageDialog(this._component, "Incorrect", "Maths Test",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean checkAnswer() {
		try {
			int a = Integer.parseInt(this._inputField1.getText());
			int b = Integer.parseInt(this._inputField2.getText());
			int c = Integer.parseInt(this._answerField.getText());
			return (a + b == c);
		} catch (Exception ex) {
			return false;
		}
	}
}

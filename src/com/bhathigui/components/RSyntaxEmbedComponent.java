package com.bhathigui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.*;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;


/**
 * 
 * modified by Bhathiya
 * 
 * The root pane used by the demos.  This allows both the applet and the
 * stand-alone application to share the same UI. 
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class RSyntaxEmbedComponent extends JPanel implements HyperlinkListener,SyntaxConstants {

	private RTextScrollPane scrollPane;
	private RSyntaxTextArea textArea;


	public RSyntaxEmbedComponent() {
                setLayout(new BorderLayout());
                add(createMenuBar(),BorderLayout.NORTH);
		textArea = createTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		scrollPane = new RTextScrollPane(textArea, true);
		Gutter gutter = scrollPane.getGutter();
		gutter.setBookmarkingEnabled(true);
		URL url = getClass().getClassLoader().getResource("com/bhathigui/res/bookmark.png");
		gutter.setBookmarkIcon(new ImageIcon(url));
		add(scrollPane,BorderLayout.CENTER);
                
	}


	private void addSyntaxItem(String name, String style,
			ButtonGroup bg, JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(
				new ChangeSyntaxStyleAction(name, style));
		bg.add(item);
		menu.add(item);
	}


	private void addThemeItem(String name, String themeXml, ButtonGroup bg,
			JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(
				new ThemeAction(name, themeXml));
		bg.add(item);
		menu.add(item);
	}


	private JMenuBar createMenuBar() {

		JMenuBar mb = new JMenuBar();

		JMenu menu = new JMenu("Language");
		ButtonGroup bg = new ButtonGroup();
		addSyntaxItem("C",  SyntaxConstants.SYNTAX_STYLE_C, bg, menu);
		addSyntaxItem("Java",  SyntaxConstants.SYNTAX_STYLE_JAVA, bg, menu);
		addSyntaxItem("Perl",  SyntaxConstants.SYNTAX_STYLE_PERL, bg, menu);
		addSyntaxItem("Ruby",  SYNTAX_STYLE_RUBY, bg, menu);
		addSyntaxItem("SQL",  SYNTAX_STYLE_SQL, bg, menu);
		addSyntaxItem("XML",SYNTAX_STYLE_XML, bg, menu);
		menu.getItem(1).setSelected(true);
		mb.add(menu);

		menu = new JMenu("View");
		JCheckBoxMenuItem cbItem = new JCheckBoxMenuItem(new CodeFoldingAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new ViewLineHighlightAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new ViewLineNumbersAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new AnimateBracketMatchingAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new BookmarksAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new WordWrapAction());
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new ToggleAntiAliasingAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new MarkOccurrencesAction());
		cbItem.setSelected(true);
		menu.add(cbItem);
		cbItem = new JCheckBoxMenuItem(new TabLinesAction());
		menu.add(cbItem);
		mb.add(menu);

		bg = new ButtonGroup();
		menu = new JMenu("Themes");
		addThemeItem("Default", "/com/bhathigui/res/themes/default.xml", bg, menu);
		addThemeItem("Default (Alternate)", "/com/bhathigui/res/themes/default-alt.xml", bg, menu);
		addThemeItem("Dark", "/com/bhathigui/res/themes/dark.xml", bg, menu);
		addThemeItem("Eclipse", "/com/bhathigui/res/themes/eclipse.xml", bg, menu);
		addThemeItem("IDEA", "/com/bhathigui/res/themes/idea.xml", bg, menu);
		addThemeItem("Visual Studio", "/com/bhathigui/res/themes/vs.xml", bg, menu);
		mb.add(menu);


		return mb;

	}


	/**
	 * Creates the text area for this application.
	 *
	 * @return The text area.
	 */
	private RSyntaxTextArea createTextArea() {
		RSyntaxTextArea zRsyntax = new RSyntaxTextArea(25, 70);
		zRsyntax.setTabSize(3);
		zRsyntax.setCaretPosition(0);
		zRsyntax.addHyperlinkListener(this);
		zRsyntax.requestFocusInWindow();
		zRsyntax.setMarkOccurrences(true);
		zRsyntax.setCodeFoldingEnabled(true);
		zRsyntax.setClearWhitespaceLinesEnabled(false);
		//textArea.setWhitespaceVisible(true);
		//textArea.setPaintMatchedBracketPair(true);
		return zRsyntax;
	}


	/**
	 * Focuses the text area.
	 */
	void focusTextArea() {
		textArea.requestFocusInWindow();
	}
        
        
        /**
	 * Sets the content in the text area to that in the specified resource.
	 *
	 * @param resource The resource to load.
	 */
        
	public void setText(InputStream iread) {
		BufferedReader r;
		try {
			r = new BufferedReader(new InputStreamReader(iread));
			textArea.read(r, null);
			r.close();
			textArea.setCaretPosition(0);
			textArea.discardAllEdits();
		} catch (RuntimeException re) {
			//throw re; // FindBugs
		} catch (Exception e) { // Never happens
			//textArea.setText("Type here to see syntax highlighting");
		}
	}

	/**
	 * Called when a hyperlink is clicked in the text area.
	 *
	 * @param e The event.
	 */
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType()==HyperlinkEvent.EventType.ACTIVATED) {
			URL url = e.getURL();
			if (url==null) {
				UIManager.getLookAndFeel().provideErrorFeedback(null);
			}
			else {
				JOptionPane.showMessageDialog(this,
									"URL clicked:\n" + url.toString());
			}
		}
	}
        
	private class AnimateBracketMatchingAction extends AbstractAction {

		public AnimateBracketMatchingAction() {
			putValue(NAME, "Animate Bracket Matching");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setAnimateBracketMatching(
						!textArea.getAnimateBracketMatching());
		}

	}


	private class BookmarksAction extends AbstractAction {

		public BookmarksAction() {
			putValue(NAME, "Bookmarks");
		}

		public void actionPerformed(ActionEvent e) {
			scrollPane.setIconRowHeaderEnabled(
							!scrollPane.isIconRowHeaderEnabled());
		}

	}


	private class ChangeSyntaxStyleAction extends AbstractAction {

		private String style;

		public ChangeSyntaxStyleAction(String name, String style) {
			putValue(NAME, name);
			this.style = style;
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setCaretPosition(0);
			textArea.setSyntaxEditingStyle(style);
		}

	}


	private class CodeFoldingAction extends AbstractAction {

		public CodeFoldingAction() {
			putValue(NAME, "Code Folding");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setCodeFoldingEnabled(!textArea.isCodeFoldingEnabled());
		}

	}


	private class MarkOccurrencesAction extends AbstractAction {

		public MarkOccurrencesAction() {
			putValue(NAME, "Mark Occurrences");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setMarkOccurrences(!textArea.getMarkOccurrences());
		}

	}


	private class TabLinesAction extends AbstractAction {

		private boolean selected;

		public TabLinesAction() {
			putValue(NAME, "Tab Lines");
		}

		public void actionPerformed(ActionEvent e) {
			selected = !selected;
			textArea.setPaintTabLines(selected);
		}

	}


	private class ThemeAction extends AbstractAction {

		private String xml;

		public ThemeAction(String name, String xml) {
			putValue(NAME, name);
			this.xml = xml;
		}

		public void actionPerformed(ActionEvent e) {
			InputStream in = getClass().getResourceAsStream(xml);
			try {
				Theme theme = Theme.load(in);
				theme.apply(textArea);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

	}


	private class ToggleAntiAliasingAction extends AbstractAction {

		public ToggleAntiAliasingAction() {
			putValue(NAME, "Anti-Aliasing");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setAntiAliasingEnabled(!textArea.getAntiAliasingEnabled());
		}

	}


	private class ViewLineHighlightAction extends AbstractAction {

		public ViewLineHighlightAction() {
			putValue(NAME, "Current Line Highlight");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setHighlightCurrentLine(
					!textArea.getHighlightCurrentLine());
		}

	}


	private class ViewLineNumbersAction extends AbstractAction {

		public ViewLineNumbersAction() {
			putValue(NAME, "Line Numbers");
		}

		public void actionPerformed(ActionEvent e) {
			scrollPane.setLineNumbersEnabled(
					!scrollPane.getLineNumbersEnabled());
		}

	}


	private class WordWrapAction extends AbstractAction {

		public WordWrapAction() {
			putValue(NAME, "Word Wrap");
		}

		public void actionPerformed(ActionEvent e) {
			textArea.setLineWrap(!textArea.getLineWrap());
		}

	}


}
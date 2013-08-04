package com.zipebook.gui;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import com.zipebook.util.StandardUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * a pdf viewer panel
 * @author http://seanshou.blogspot.com/2012/10/java-swing-pdf-viewer.html
 */
public class PdfViewer extends JPanel {
    private static enum Navigation {GO_FIRST_PAGE, FORWARD, BACKWARD, GO_LAST_PAGE, GO_N_PAGE}
 
    //private static final CharMatcher POSITIVE_DIGITAL = CharMatcher.anyOf("0123456789");
    private static final String GO_PAGE_TEMPLATE = "%s of %s";
    private static final int FIRST_PAGE = 1;
    private int currentPage = FIRST_PAGE;
    private JButton btnFirstPage;
    private JButton btnPreviousPage;
    private JTextField txtGoPage;
    private JButton btnNextPage;
    private JButton btnLastPage;
    private PagePanel pagePanel;
    private PDFFile pdfFile;

    
    public PdfViewer() {
        initial();
    }
 
    private void initial() {
        setLayout(new BorderLayout(0, 0));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(topPanel, BorderLayout.NORTH);
        btnFirstPage = createButton("|<<");
        topPanel.add(btnFirstPage);
        btnPreviousPage = createButton("<<");
        topPanel.add(btnPreviousPage);
        txtGoPage = new JTextField(10);
        txtGoPage.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(txtGoPage);
        btnNextPage = createButton(">>");
        topPanel.add(btnNextPage);
        btnLastPage = createButton(">>|");
        topPanel.add(btnLastPage);
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        JPanel viewPanel = new JPanel(new BorderLayout(0, 0));
        scrollPane.setViewportView(viewPanel);
 
        pagePanel = new PagePanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pagePanel.setPreferredSize(screenSize);
        viewPanel.add(pagePanel, BorderLayout.CENTER);
 
        disableAllNavigationButton();
 
        btnFirstPage.addActionListener(new PageNavigationListener(Navigation.GO_FIRST_PAGE));
        btnPreviousPage.addActionListener(new PageNavigationListener(Navigation.BACKWARD));
        btnNextPage.addActionListener(new PageNavigationListener(Navigation.FORWARD));
        btnLastPage.addActionListener(new PageNavigationListener(Navigation.GO_LAST_PAGE));
        txtGoPage.addActionListener(new PageNavigationListener(Navigation.GO_N_PAGE));
    }
 
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(55, 20));
 
        return button;
    }
 
    private void disableAllNavigationButton() {
        btnFirstPage.setEnabled(false);
        btnPreviousPage.setEnabled(false);
        btnNextPage.setEnabled(false);
        btnLastPage.setEnabled(false);
    }
 
    private boolean isMoreThanOnePage(PDFFile pdfFile) {
        return pdfFile.getNumPages() > 1;
    }
 
    private class PageNavigationListener implements ActionListener {
        private final Navigation navigation;
 
        private PageNavigationListener(Navigation navigation) {
            this.navigation = navigation;
        }
 
        public void actionPerformed(ActionEvent e) {
            if (pdfFile == null) {
                return;
            }
 
            int numPages = pdfFile.getNumPages();
            if (numPages <= 1) {
                disableAllNavigationButton();
            } else {
                if (navigation == Navigation.FORWARD && hasNextPage(numPages)) {
                    goPage(currentPage, numPages);
                }
 
                if (navigation == Navigation.GO_LAST_PAGE) {
                    goPage(numPages, numPages);
                }
 
                if (navigation == Navigation.BACKWARD && hasPreviousPage()) {
                    goPage(currentPage, numPages);
                }
 
                if (navigation == Navigation.GO_FIRST_PAGE) {
                    goPage(FIRST_PAGE, numPages);
                }
 
                if (navigation == Navigation.GO_N_PAGE) {
                    String text = txtGoPage.getText();
                    boolean isValid = false;
                    if (!StandardUtils.isNullOrEmpty(text)) {
                        boolean isNumber = StandardUtils.isPositiveNumber(text);
                        if (isNumber) {
                            int pageNumber = Integer.valueOf(text);
                            if (pageNumber >= 1 && pageNumber <= numPages) {
                                goPage(Integer.valueOf(text), numPages);
                                isValid = true;
                            }
                        }
                    }
 
                    if (!isValid) {
                        JOptionPane.showMessageDialog(PdfViewer.this, StandardUtils.format("Invalid page number '%s' in this document", text));
                        txtGoPage.setText(StandardUtils.format(GO_PAGE_TEMPLATE, currentPage, numPages));
                    }
                }
            }
        }
 
        private void goPage(int pageNumber, int numPages) {
            currentPage = pageNumber;
            PDFPage page = pdfFile.getPage(currentPage);
            pagePanel.showPage(page);
            boolean notFirstPage = isNotFirstPage();
            btnFirstPage.setEnabled(notFirstPage);
            btnPreviousPage.setEnabled(notFirstPage);
            txtGoPage.setText(StandardUtils.format(GO_PAGE_TEMPLATE, currentPage, numPages));
            boolean notLastPage = isNotLastPage(numPages);
            btnNextPage.setEnabled(notLastPage);
            btnLastPage.setEnabled(notLastPage);
        }
 
        private boolean hasNextPage(int numPages) {
            return (++currentPage) <= numPages;
        }
 
        private boolean hasPreviousPage() {
            return (--currentPage) >= FIRST_PAGE;
        }
 
        private boolean isNotLastPage(int numPages) {
            return currentPage != numPages;
        }
 
        private boolean isNotFirstPage() {
            return currentPage != FIRST_PAGE;
        }
    }
 
    public PagePanel getPagePanel() {
        return pagePanel;
    }
 
    public void setPDFFile(PDFFile pdfFile) {
        
        this.pdfFile = pdfFile;
        currentPage = FIRST_PAGE;
        disableAllNavigationButton();
        txtGoPage.setText(StandardUtils.format(GO_PAGE_TEMPLATE, FIRST_PAGE, pdfFile.getNumPages()));
        boolean moreThanOnePage = isMoreThanOnePage(pdfFile);
        btnNextPage.setEnabled(moreThanOnePage);
        btnLastPage.setEnabled(moreThanOnePage);
    }
}
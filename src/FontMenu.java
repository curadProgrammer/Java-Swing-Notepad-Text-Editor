import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FontMenu extends JDialog {
    // will need reference to our gui to make changes to the gui from this class
    private NotepadGUI source;

    private JTextField currentFontField, currentFontStyleField, currentFontSizeField;
    private JPanel currentColorBox;

    public FontMenu(NotepadGUI source){
        this.source = source;
        setTitle("Font Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // disposes used resources once closed
        setSize(425, 350);
        setLocationRelativeTo(source); // launches the menu at center of our notepad gui
        setModal(true);

        // removes layout management, giving us more control on the placement of our gui components
        setLayout(null);

        addMenuComponents();
    }

    private void addMenuComponents(){
        addFontChooser();
        addFontStyleChooser();
        addFontSizeChooser();
        addFontColorChooser();

        // action buttons

        // apply the changes to the font
        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(230, 265, 75, 25);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get current font type
                String fontType  = currentFontField.getText();

                // get font style
                int fontStyle;
                switch(currentFontStyleField.getText()){
                    case "Plain":
                        fontStyle = Font.PLAIN;
                        break;
                    case "Bold":
                        fontStyle = Font.BOLD;
                        break;
                    case "Italic":
                        fontStyle = Font.ITALIC;
                        break;
                    default: // bold italic
                        fontStyle = Font.BOLD | Font.ITALIC;
                        break;
                }

                // get font size
                int fontSize = Integer.parseInt(currentFontSizeField.getText());

                // get font color
                Color fontColor = currentColorBox.getBackground();

                // create font
                Font newFont = new Font(fontType, fontStyle, fontSize);

                // update text area font
                source.getTextArea().setFont(newFont);

                // update text area font color
                source.getTextArea().setForeground(fontColor);

                // dispose menu
                FontMenu.this.dispose();
            }
        });
        add(applyButton);

        // cancel button (exits menu)
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(315, 265, 75, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose menu
                FontMenu.this.dispose();
            }
        });
        add(cancelButton);
    }

    private void addFontChooser(){
        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10, 5, 125, 10);
        add(fontLabel);

        // font panel will display the current font and the list of fonts available to choose from
        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        // display current font
        currentFontField = new JTextField(source.getTextArea().getFont().getFontName());
        currentFontField.setPreferredSize(new Dimension(125, 25));
        currentFontField.setEditable(false);
        fontPanel.add(currentFontField);

        // display list of available fonts
        JPanel listOfFontsPanel = new JPanel();

        // changes our layout to only have one column to display each font properly
        listOfFontsPanel.setLayout(new BoxLayout(listOfFontsPanel, BoxLayout.Y_AXIS));

        // change the background color to white
        listOfFontsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listOfFontsPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));

        // retrieve all of the possible fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();

        // for each font name in font names, we are going to display them to our
        // listOfFontsPanel as a JLabel
        for(String fontName : fontNames){
            JLabel fontNameLabel = new JLabel(fontName);

            fontNameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // when clicked set currentFontField to font name
                    currentFontField.setText(fontName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // add highlight over font names when the mouse hovers over them
                    fontNameLabel.setOpaque(true);
                    fontNameLabel.setBackground(Color.BLUE);
                    fontNameLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // remove the highlight once the mouse stops hovering over the font names
                    fontNameLabel.setBackground(null); // reset background color
                    fontNameLabel.setForeground(null); // reset font color;
                }
            });

            // add to panel
            listOfFontsPanel.add(fontNameLabel);
        }
        fontPanel.add(scrollPane);

        add(fontPanel);
    }

    private void addFontStyleChooser(){
        JLabel fontStyleLabel = new JLabel("Font Style:");
        fontStyleLabel.setBounds(145, 5, 125, 10);
        add(fontStyleLabel);

        // will display the current font style and all available font styles
        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145, 15, 125, 160);

        // get current font style
        int currentFontStyle = source.getTextArea().getFont().getStyle();
        String currentFontStyleText;

        switch(currentFontStyle){
            case Font.PLAIN:
                currentFontStyleText = "Plain";
                break;
            case Font.BOLD:
                currentFontStyleText = "Bold";
                break;
            case Font.ITALIC:
                currentFontStyleText = "Italic";
                break;
            default: // bold italic
                currentFontStyleText = "Bold Italic";
                break;
        }

        currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(125, 25));
        currentFontStyleField.setEditable(false);
        fontStylePanel.add(currentFontStyleField);

        // display list of all font style available
        JPanel listOfFontStylesPanel = new JPanel();

        // make the layout have only one column (similar to the font names)
        listOfFontStylesPanel.setLayout(new BoxLayout(listOfFontStylesPanel, BoxLayout.Y_AXIS));
        listOfFontStylesPanel.setBackground(Color.WHITE);

        // list of font styles
        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog", Font.PLAIN, 12));
        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // update the current style field
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // add blue highlight when hovering
                plainStyle.setOpaque(true);
                plainStyle.setBackground(Color.BLUE);
                plainStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // remove highlights
                plainStyle.setBackground(null);
                plainStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog", Font.BOLD, 12));
        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // update the current style field
                currentFontStyleField.setText(boldStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // add blue highlight when hovering
                boldStyle.setOpaque(true);
                boldStyle.setBackground(Color.BLUE);
                boldStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // remove highlights
                boldStyle.setBackground(null);
                boldStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog", Font.ITALIC, 12));
        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // update the current style field
                currentFontStyleField.setText(italicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // add blue highlight when hovering
                italicStyle.setOpaque(true);
                italicStyle.setBackground(Color.BLUE);
                italicStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // remove highlights
                italicStyle.setBackground(null);
                italicStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(italicStyle);

        JLabel boldItalicStyle = new JLabel("Bold Italic");
        boldItalicStyle.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
        boldItalicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // update the current style field
                currentFontStyleField.setText(boldItalicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // add blue highlight when hovering
                boldItalicStyle.setOpaque(true);
                boldItalicStyle.setBackground(Color.BLUE);
                boldItalicStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // remove highlights
                boldItalicStyle.setBackground(null);
                boldItalicStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(boldItalicStyle);

        JScrollPane scrollPane = new JScrollPane(listOfFontStylesPanel);
        scrollPane.setPreferredSize(new Dimension(125,125));
        fontStylePanel.add(scrollPane);

        add(fontStylePanel);
    }

    private void addFontSizeChooser(){
        JLabel fontSizeLabel= new JLabel("Font Size: ");
        fontSizeLabel.setBounds(275, 5, 125, 10);
        add(fontSizeLabel);

        // display the current font size and list of font sizes to choose from
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBounds(275, 15, 125, 160);

        currentFontSizeField = new JTextField(
                Integer.toString(source.getTextArea().getFont().getSize())
        );
        currentFontSizeField.setPreferredSize(new Dimension(125, 25));
        currentFontSizeField.setEditable(false);
        fontSizePanel.add(currentFontSizeField);


        // create list of font sizes to choose from
        JPanel listOfFontSizesPanel = new JPanel();
        listOfFontSizesPanel.setLayout(new BoxLayout(listOfFontSizesPanel, BoxLayout.Y_AXIS));
        listOfFontSizesPanel.setBackground(Color.WHITE);

        // list of available font sizes will be from 8 -> 72 with increments of 2
        for(int i = 8; i <= 72; i+= 2){
            JLabel fontSizeValueLabel = new JLabel(Integer.toString(i));
            fontSizeValueLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // update current font size field
                    currentFontSizeField.setText(fontSizeValueLabel.getText());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // add highlights
                    fontSizeValueLabel.setOpaque(true);
                    fontSizeValueLabel.setBackground(Color.BLUE);
                    fontSizeValueLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // remove highlights
                    fontSizeValueLabel.setBackground(null);
                    fontSizeValueLabel.setForeground(null);
                }
            });

            listOfFontSizesPanel.add(fontSizeValueLabel);
        }
        JScrollPane scrollPane = new JScrollPane(listOfFontSizesPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));
        fontSizePanel.add(scrollPane);

        add(fontSizePanel);
    }

    private void addFontColorChooser(){
        // display to the user the current color of the text
        currentColorBox = new JPanel();
        currentColorBox.setBounds(175, 200, 23, 23);
        currentColorBox.setBackground(source.getTextArea().getForeground());
        currentColorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(currentColorBox);

        JButton chooseColorButton = new JButton("Choose Color");
        chooseColorButton.setBounds(10, 200, 150, 25);
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(FontMenu.this, "Select a color", Color.BLACK);

                // update color to selected color
                currentColorBox.setBackground(c);
            }
        });
        add(chooseColorButton);
    }
}













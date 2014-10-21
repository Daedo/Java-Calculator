package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import calculate.Calculator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	JRadioButton rdbtnRad;
	JRadioButton rdbtnDeg;
	JSpinner spinner;
	private JLabel lblDecimalPlaces;
	private JLabel lblResult;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new MigLayout("", "[424px,grow][]", "[20%:22.52%:20%][][][][grow]"));
		
		this.textField = new JTextField();
		this.contentPane.add(this.textField, "cell 0 0,growx,aligny center");
		this.textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Calculate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String expression = MainGUI.this.textField.getText();
				int decimalPlaces = Integer.parseInt(MainGUI.this.spinner.getValue().toString());
				boolean useDegrees= MainGUI.this.rdbtnDeg.isSelected();
				
				String result = Calculator.calculate(expression, useDegrees, decimalPlaces);
				
				MainGUI.this.lblResult.setText("Result:    "+result);
				
			}
		});
		this.contentPane.add(btnNewButton, "cell 1 0");
		
		this.lblResult = new JLabel("Result:");
		this.contentPane.add(this.lblResult, "cell 0 1");
		
		ButtonGroup radioGroup = new ButtonGroup();
		
		this.scrollPane = new JScrollPane();
		this.contentPane.add(this.scrollPane, "cell 0 2 1 3,grow");
		
		this.table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2 && !arg0.isConsumed()) {
					arg0.consume();
				     //handle double click event.
				     int clickedRow = MainGUI.this.table.getSelectedRow();
				     String expression = MainGUI.this.table.getValueAt(clickedRow, 0).toString();
				     String currentText =MainGUI.this.textField.getText();
				     MainGUI.this.textField.setText(currentText+expression);
				}
			}
		});
		this.table.setFillsViewportHeight(true);
		this.table.setShowVerticalLines(false);
		this.table.setModel(new DefaultTableModel(
			new Object[][] {
				{"x + y", "Addition"},
				{"x - y", "Subtraction"},
				{"x * y", "Multiplication"},
				{"x / y", "Division"},
				{"x ^ y", "Power"},
				{"(x)", "Bracket"},
				{"sin(x)", "Sine of x"},
				{"cos(x)", "Cosine of x"},
				{"tan(x)", "Tangent of x"},
				{"cot(x)", "Cotangent of x"},
				{"csc(x)", "Cosecant of x"},
				{"versin(x)", "Versine/versed sine of x"},
				{"coversin(x)", "Coversine/ coversed sine of x"},
				{"sem(x)", "Semiversine of x"},
				{"exsec(x)", "Exsecant of x"},
				{"excsc(x)", "Excosecant of x"},
				{"asin(x)", "Arcus/ Inversed Sine of x"},
				{"acos(x)", "Arcus/ Inversed Cosine of x"},
				{"atan(x)", "Arcus/ Inversed Tangent of x"},
				{"acot(x)", "Arcus/ Inversed Cotangent of x"},
				{"acsc(x)", "Arcus/ Inversed Cosecant of x"},
				{"aversin(x)", "Arcus/ Inversed Versine/versed sine of x"},
				{"acoversin(x)", "Arcus/ Inversed Coversine/ coversed sine of x"},
				{"asem(x)", "Arcus/ Inversed Semiversine of x"},
				{"aexsec(x)", "Arcus/ Inversed Exsecant of x"},
				{"aexcsc(x)", "Arcus/ Inversed Excosecant of x"},
				{"log10(x)", "Log of the base 10"},
				{"ln(x)", "Log of the base e (Natural Log)"},
				{"floor(x)", "Round a number down"},
				{"ceil(x)", "Round a number up"},
				{"round(x)", "Round a number to the closest integer"},
				{"pi", "3.1415..."},
				{"tau", "2*pi"},
				{"e", "Eurlers number"},
				{"phi", "Golden ratio"},
			},
			new String[] {
				"Function", "Description"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return this.columnEditables[column];
			}
		});
	
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPane.setViewportView(this.table);
		
		this.rdbtnDeg = new JRadioButton("Deg");
		this.contentPane.add(this.rdbtnDeg, "cell 1 2");
		radioGroup.add(this.rdbtnDeg);
		this.rdbtnDeg.setSelected(true);
		
		this.rdbtnRad = new JRadioButton("Rad");
		this.contentPane.add(this.rdbtnRad, "flowx,cell 1 3");
		radioGroup.add(this.rdbtnRad);
		this.rdbtnRad.setSelected(false);
		
		this.spinner = new JSpinner();
		this.spinner.setModel(new SpinnerNumberModel(4, 0, 6, 1));
		this.contentPane.add(this.spinner, "flowx,cell 1 4");
		
		this.lblDecimalPlaces = new JLabel("Decimal Places");
		this.contentPane.add(this.lblDecimalPlaces, "cell 1 4");
	}

}

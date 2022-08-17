package coffee;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class Japangi_admin extends Frame implements ActionListener {
	//상품 테이블 읽어와서 저장하는 2차원배열
	String coffee[][] = new String[6][4];
	
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	private Image img1, img2, img3, img4, img5, img6, imgPick;
	
	Font fontP15 = new Font("SansSerif", Font.PLAIN, 15);
	Font fontP20 = new Font("SansSerif", Font.PLAIN, 20);
	Font fontP25 = new Font("SansSerif", Font.PLAIN, 25);
	Font fontP30 = new Font("SansSerif", Font.PLAIN, 30);
	
	Font fontB15 = new Font("SansSerif", Font.BOLD, 15);
	Font fontB20 = new Font("SansSerif", Font.BOLD, 20);
	Font fontB25 = new Font("SansSerif", Font.BOLD, 25);
	Font fontB30 = new Font("SansSerif", Font.BOLD, 30);

	Font fontB40 = new Font("SansSerif", Font.BOLD, 40);

	Label lbTitle = new Label("커피 자판기 - 관리자 화면");
	
	Label lbName1 = new Label("상품1   이름 :");
	Label lbName2 = new Label("상품2   이름 :");
	Label lbName3 = new Label("상품3   이름 :");
	Label lbName4 = new Label("상품4   이름 :");
	Label lbName5 = new Label("상품5   이름 :");
	Label lbName6 = new Label("상품6   이름 :");
	
	Label lbCount1 = new Label("수량 :");
	Label lbCount2 = new Label("수량 :");
	Label lbCount3 = new Label("수량 :");
	Label lbCount4 = new Label("수량 :");
	Label lbCount5 = new Label("수량 :");
	Label lbCount6 = new Label("수량 :");
	
	Label lbPrice1 = new Label("가격 :");
	Label lbPrice2 = new Label("가격 :");
	Label lbPrice3 = new Label("가격 :");
	Label lbPrice4 = new Label("가격 :");
	Label lbPrice5 = new Label("가격 :");
	Label lbPrice6 = new Label("가격 :");
	
	TextField tfName1 = new TextField("");
	TextField tfName2 = new TextField("");
	TextField tfName3 = new TextField("");
	TextField tfName4 = new TextField("");
	TextField tfName5 = new TextField("");
	TextField tfName6 = new TextField("");
	
	TextField tfCount1 = new TextField("");
	TextField tfCount2 = new TextField("");
	TextField tfCount3 = new TextField("");
	TextField tfCount4 = new TextField("");
	TextField tfCount5 = new TextField("");
	TextField tfCount6 = new TextField("");

	TextField tfPrice1 = new TextField("");
	TextField tfPrice2 = new TextField("");
	TextField tfPrice3 = new TextField("");
	TextField tfPrice4 = new TextField("");
	TextField tfPrice5 = new TextField("");
	TextField tfPrice6 = new TextField("");
	
	Button btn1 = new Button("적용");
	Button btn2 = new Button("적용");
	Button btn3 = new Button("적용");
	Button btn4 = new Button("적용");
	Button btn5 = new Button("적용");
	Button btn6 = new Button("적용");
	
	
	Japangi_admin() {
		super("자판기 - 관리자화면");
		this.setSize(1020,480);
		this.getData();
		this.center();
		this.init();
		this.start();
		this.setVisible(true); //이 부분이 있어야 창이 보이게 생성 됨
//		for(int t=0 ; t>=0 ; t++)
//		{
//			try { Thread.sleep(1000); } catch(InterruptedException ie) {}
//			System.out.println("가동 시간 : "+t+"초");
//		}
	}
	void init() { //initialize 줄임말로 쓴 init 	
		
		this.setLayout(null); //absolute 방식으로 레이어를 배치할수 있게 해줌
		
		img1 = Toolkit.getDefaultToolkit().getImage("coffee_img/Espresso.jpg");
		img2 = Toolkit.getDefaultToolkit().getImage("coffee_img/Americano.jpg");
		img3 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeLatte.jpg");
		img4 = Toolkit.getDefaultToolkit().getImage("coffee_img/Cappuccino.jpg");
		img5 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaramelMacchiato.jpg");
		img6 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeMocha.jpg");
		imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/HoroloCoffeeLogo.png");
//		imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/HoroloCoffeeLogo.jpg? jpeg?");
		
		this.add(lbTitle);
		lbTitle.setFont(fontB40);
		lbTitle.setBounds(260, 50, 500, 50);
				
		this.add(lbName1);
		lbName1.setFont(fontB20);
		lbName1.setBounds(60, 140, 130, 30);
		this.add(lbName2);
		lbName2.setFont(fontB20);
		lbName2.setBounds(60, 190, 130, 30);
		this.add(lbName3);
		lbName3.setFont(fontB20);
		lbName3.setBounds(60, 240, 130, 30);
		this.add(lbName4);
		lbName4.setFont(fontB20);
		lbName4.setBounds(60, 290, 130, 30);
		this.add(lbName5);
		lbName5.setFont(fontB20);
		lbName5.setBounds(60, 340, 130, 30);
		this.add(lbName6);
		lbName6.setFont(fontB20);
		lbName6.setBounds(60, 390, 130, 30);
		
		this.add(tfName1);
		tfName1.setFont(fontP20);
		tfName1.setBounds(200, 140, 280, 30);
		this.add(tfName2);
		tfName2.setFont(fontP20);
		tfName2.setBounds(200, 190, 280, 30);
		this.add(tfName3);
		tfName3.setFont(fontP20);
		tfName3.setBounds(200, 240, 280, 30);
		this.add(tfName4);
		tfName4.setFont(fontP20);
		tfName4.setBounds(200, 290, 280, 30);
		this.add(tfName5);
		tfName5.setFont(fontP20);
		tfName5.setBounds(200, 340, 280, 30);
		this.add(tfName6);
		tfName6.setFont(fontP20);
		tfName6.setBounds(200, 390, 280, 30);
		
		this.add(lbCount1);
		lbCount1.setFont(fontB20);
		lbCount1.setBounds(490, 140, 60, 30);
		this.add(lbCount2);
		lbCount2.setFont(fontB20);
		lbCount2.setBounds(490, 190, 60, 30);
		this.add(lbCount3);
		lbCount3.setFont(fontB20);
		lbCount3.setBounds(490, 240, 60, 30);
		this.add(lbCount4);
		lbCount4.setFont(fontB20);
		lbCount4.setBounds(490, 290, 60, 30);
		this.add(lbCount5);
		lbCount5.setFont(fontB20);
		lbCount5.setBounds(490, 340, 60, 30);
		this.add(lbCount6);
		lbCount6.setFont(fontB20);
		lbCount6.setBounds(490, 390, 60, 30);
		
		this.add(tfCount1);
		tfCount1.setFont(fontP20);
		tfCount1.setBounds(560, 140, 100, 30);
		this.add(tfCount2);
		tfCount2.setFont(fontP20);
		tfCount2.setBounds(560, 190, 100, 30);
		this.add(tfCount3);
		tfCount3.setFont(fontP20);
		tfCount3.setBounds(560, 240, 100, 30);
		this.add(tfCount4);
		tfCount4.setFont(fontP20);
		tfCount4.setBounds(560, 290, 100, 30);
		this.add(tfCount5);
		tfCount5.setFont(fontP20);
		tfCount5.setBounds(560, 340, 100, 30);
		this.add(tfCount6);
		tfCount6.setFont(fontP20);
		tfCount6.setBounds(560, 390, 100, 30);
		
		this.add(lbPrice1);
		lbPrice1.setFont(fontB20);
		lbPrice1.setBounds(670, 140, 60, 30);
		this.add(lbPrice2);
		lbPrice2.setFont(fontB20);
		lbPrice2.setBounds(670, 190, 60, 30);
		this.add(lbPrice3);
		lbPrice3.setFont(fontB20);
		lbPrice3.setBounds(670, 240, 60, 30);
		this.add(lbPrice4);
		lbPrice4.setFont(fontB20);
		lbPrice4.setBounds(670, 290, 60, 30);
		this.add(lbPrice5);
		lbPrice5.setFont(fontB20);
		lbPrice5.setBounds(670, 340, 60, 30);
		this.add(lbPrice6);
		lbPrice6.setFont(fontB20);
		lbPrice6.setBounds(670, 390, 60, 30);
		
		this.add(tfPrice1);
		tfPrice1.setFont(fontP20);
		tfPrice1.setBounds(740, 140, 100, 30);
		this.add(tfPrice2);
		tfPrice2.setFont(fontP20);
		tfPrice2.setBounds(740, 190, 100, 30);
		this.add(tfPrice3);
		tfPrice3.setFont(fontP20);
		tfPrice3.setBounds(740, 240, 100, 30);
		this.add(tfPrice4);
		tfPrice4.setFont(fontP20);
		tfPrice4.setBounds(740, 290, 100, 30);
		this.add(tfPrice5);
		tfPrice5.setFont(fontP20);
		tfPrice5.setBounds(740, 340, 100, 30);
		this.add(tfPrice6);
		tfPrice6.setFont(fontP20);
		tfPrice6.setBounds(740, 390, 100, 30);
		
		this.add(btn1);
		btn1.setFont(fontB20);
		btn1.setBounds(860, 140, 100, 30);
		this.add(btn2);
		btn2.setFont(fontB20);
		btn2.setBounds(860, 190, 100, 30);
		this.add(btn3);
		btn3.setFont(fontB20);
		btn3.setBounds(860, 240, 100, 30);
		this.add(btn4);
		btn4.setFont(fontB20);
		btn4.setBounds(860, 290, 100, 30);
		this.add(btn5);
		btn5.setFont(fontB20);
		btn5.setBounds(860, 340, 100, 30);
		this.add(btn6);
		btn6.setFont(fontB20);
		btn6.setBounds(860, 390, 100, 30);
		
		
		
		
	}
	public void paint(Graphics g) {
//		g.drawImage(img1, 80, 110, 150, 150, this);
//		g.drawImage(img2, 260, 110, 150, 150, this);
//		g.drawImage(img3, 440, 110, 150, 150, this);
//		g.drawImage(img4, 80, 390, 150, 150, this);
//		g.drawImage(img5, 260, 390, 150, 150, this);
//		g.drawImage(img6, 440, 390, 150, 150, this);
//		g.drawImage(imgPick, 670, 200, 200, 200, this);
		g.drawRoundRect(40, 120, 940, 320, 10, 10); // 선택창
	}
	void start() {
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
//				System.exit(0);
			}
		});
	}
	void center() {
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);
		this.setLocation(xpos, ypos);
	}
	public void actionPerformed(ActionEvent e) {
		
		String coffeeName1 = tfName1.getText();
		String coffeeName2 = tfName2.getText();
		String coffeeName3 = tfName3.getText();
		String coffeeName4 = tfName4.getText();
		String coffeeName5 = tfName5.getText();
		String coffeeName6 = tfName6.getText();
		
		String coffeeCount1 = tfCount1.getText();
		String coffeeCount2 = tfCount2.getText();
		String coffeeCount3 = tfCount3.getText();
		String coffeeCount4 = tfCount4.getText();
		String coffeeCount5 = tfCount5.getText();
		String coffeeCount6 = tfCount6.getText();
		
		String coffeePrice1 = tfPrice1.getText();
		String coffeePrice2 = tfPrice2.getText();
		String coffeePrice3 = tfPrice3.getText();
		String coffeePrice4 = tfPrice4.getText();
		String coffeePrice5 = tfPrice5.getText();
		String coffeePrice6 = tfPrice6.getText();
		
		if(e.getSource() == btn1) {
			if(spaceCheck(coffeeName1, coffeeCount1, coffeePrice1)) {
				return;
			} else {
				update(1, coffeeName1, coffeeCount1, coffeePrice1);
			}
		}
		if(e.getSource() == btn2) {
			if(spaceCheck(coffeeName2, coffeeCount2, coffeePrice2)) {
				return;
			} else {
				update(2, coffeeName2, coffeeCount2, coffeePrice2);
			}
		}
		if(e.getSource() == btn3) {
			if(spaceCheck(coffeeName3, coffeeCount3, coffeePrice3)) {
				return;
			} else {
				update(3, coffeeName3, coffeeCount3, coffeePrice3);
			}
		}
		if(e.getSource() == btn4) {
			if(spaceCheck(coffeeName4, coffeeCount4, coffeePrice4)) {
				return;
			} else {
				update(4, coffeeName4, coffeeCount4, coffeePrice4);
			}
		}
		if(e.getSource() == btn5) {
			if(spaceCheck(coffeeName5, coffeeCount5, coffeePrice5)) {
				return;
			} else {
				update(5, coffeeName5, coffeeCount5, coffeePrice5);
			}
		}
		if(e.getSource() == btn6) {
			if(spaceCheck(coffeeName6, coffeeCount6, coffeePrice6)) {
				return;
			} else {
				update(6, coffeeName6, coffeeCount6, coffeePrice6);
			}
		}

		
	}
	boolean spaceCheck(String name, String count, String price) {
		if(name.equals("")) {msg("커피명을 적어주세요."); return true;}
		if(count.equals("")) {msg("수량을 적어주세요."); return true;}
		if(price.equals("")) {msg("가격을 적어주세요."); return true;}
		return false;
	}
	void getData() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?useUnicode=true&characterEncoding=utf8"; // 나를 지정할 때 127.0.0.1 을 씀
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from coffee_admin";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			int count=0;
			while (rs.next()) {
				coffee[count][0] = rs.getInt("idx")+"";
				coffee[count][1] = rs.getString("name");
				coffee[count][2] = rs.getString("count");
				coffee[count][3] = rs.getString("price");				
				count++;
			}
			
			tfName1.setText(coffee[0][1]);
			tfName2.setText(coffee[1][1]);
			tfName3.setText(coffee[2][1]);
			tfName4.setText(coffee[3][1]);
			tfName5.setText(coffee[4][1]);
			tfName6.setText(coffee[5][1]);
			
			tfCount1.setText(coffee[0][2]);
			tfCount2.setText(coffee[1][2]);
			tfCount3.setText(coffee[2][2]);
			tfCount4.setText(coffee[3][2]);
			tfCount5.setText(coffee[4][2]);
			tfCount6.setText(coffee[5][2]);
			
			tfPrice1.setText(coffee[0][3]);
			tfPrice2.setText(coffee[1][3]);
			tfPrice3.setText(coffee[2][3]);
			tfPrice4.setText(coffee[3][3]);
			tfPrice5.setText(coffee[4][3]);
			tfPrice6.setText(coffee[5][3]);
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ee) {
			System.err.println("error = " + ee.toString());
		}
	}
	boolean update(int idx, String name, String count, String price) {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?useUnicode=true&characterEncoding=utf8"; // 나를 지정할 때 127.0.0.1 을 씀
		String id = "root";
		String pass = "qwer";
		String query = "update coffee_admin set name = ?, count = ?, price = ? where idx = ?";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, count);
			pstmt.setString(3, price);
			pstmt.setInt(4, idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			System.out.println("수정성공");
			msg("상품정보가 변경 되었습니다");
		} catch (SQLException ee) {
			System.err.println("상품정보 변경 실패!!"+ee);
			return false;
		}
		return true;
	}
	void msg(String msg) {
		final Dialog dlg = new Dialog(this, "알림 메세지창", true);
		dlg.setLayout(null);
		Label lbMsg = new Label(msg);
		
		dlg.add(lbMsg);
		lbMsg.setFont(fontB20);
		lbMsg.setBounds(60, 100, 300, 30);
		
		dlg.setSize(400, 200);
		dlg.setLocation(800, 400);
		
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlg.setVisible(false);
			}
		});
		dlg.setVisible(true);
	}
}
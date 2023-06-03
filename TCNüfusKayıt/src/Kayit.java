import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.Blob;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.beans.JavaBean;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Window;

public class Kayit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		JFrame j = new JFrame("Nüfus Kayıt");
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton kayitBtn = new JButton("Kaydet");
		kayitBtn.setBounds(110, 450, 110, 30);
		
		JButton GncBtn = new JButton("Güncelle");
		GncBtn.setBounds(110, 485, 110, 30);
		
		JButton temizleBtn = new JButton("Temizle");
		temizleBtn.setBounds(340, 30, 80, 20);
		
		JLabel adLbl = new JLabel("Adı : ");
		adLbl.setBounds(60, 30, 30, 20);
		
		JTextField adtf = new JTextField();
		adtf.setBounds(90 , 30 , 150 , 20);
		
		
		JLabel SoyadLbl = new JLabel("Soyadı : ");
		SoyadLbl.setBounds(40, 65, 50, 20);
		
		JTextField Soyadtf = new JTextField();
		Soyadtf.setBounds(90 , 65 , 150 , 20);
		
		
		JLabel kimlikResim = new JLabel();
		kimlikResim.setBounds(470, 30, 150, 190);
		
		JLabel imzaResim = new JLabel();
		imzaResim.setBounds(470, 345, 150, 55);
	
		
		JButton kimlikEkleBtn = new JButton("Ekle");
		kimlikEkleBtn.setBounds(500, 255, 90, 30);
		
		
		JButton imzaEkleBtn = new JButton("Ekle");
		imzaEkleBtn.setBounds(500, 435, 90, 30);
		
		
		JButton yazdırBtn = new JButton("Yazdır");
		yazdırBtn.setBounds(500, 650, 90, 20);
		
		
		JLabel CinsiyetLbl = new JLabel("Cinsiyet : ");
		

		String[] cinsiyet = {"Erkek" , "Kadın"};
		
		JComboBox<String> cinsiyetCbox = new JComboBox<>(cinsiyet);
		cinsiyetCbox.setBounds(90, 100, 150, 20);
		CinsiyetLbl.setBounds(cinsiyetCbox.getX() - 57, 100, 60, 20);
		cinsiyetCbox.setSelectedItem(null);
		
		JLabel TcLbl = new JLabel("T.C. No : ");
		TcLbl.setBounds(38, 135 , 50 , 20);
		
		JTextField Tctf = new JTextField();
		Tctf.setBounds(90 , 135 , 150 , 20);
		Tctf.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
		            e.consume();
		        }
			}
		});
		
		JButton tcOluşturBtn = new JButton("Oluştur");
		tcOluşturBtn.setBounds(250, 135, 80, 20);
		
		
		
		JButton tcGetirBtn = new JButton("Getir");
		tcGetirBtn.setBounds(340, 135, 80, 20);
		

		
		JLabel DogumLbl = new JLabel("Doğum Tarihi : ");
		DogumLbl.setBounds(2, 170 , 100 , 20);
		
		JDateChooser dogumTarihiPicker = new JDateChooser();
		Date today = new Date();
		dogumTarihiPicker.setBounds(90, 170, 150, 20);
		dogumTarihiPicker.setDate(today);
		
		JLabel anneLbl = new JLabel("Anne Adı : ");
		anneLbl.setBounds(27, 205, 80, 20);
		
		JTextField annetf = new JTextField();
		annetf.setBounds(90, 205, 150, 20);
		
		JLabel babaLbl = new JLabel("Baba Adı : ");
		babaLbl.setBounds(27, 240, 80, 20);
		
		JTextField babatf = new JTextField();
		babatf.setBounds(90, 240, 150, 20);
		
		JLabel seriNolbl = new JLabel("Seri No : ");
		seriNolbl.setBounds(35, 275, 80, 20);
		
		JTextField seriNoTf = new JTextField();
		seriNoTf.setBounds(90, 275, 150, 20);
		
		JButton seriOluşturBtn = new JButton("Oluştur");
		seriOluşturBtn.setBounds(250, 275, 80, 20);
		
		
		
		
		JLabel ulkeLbl = new JLabel("Ülke : ");
		ulkeLbl.setBounds(50, 310, 80, 20);
		
		JComboBox<String> UlkeTf = new JComboBox<>();
		UlkeTf.setBounds(90, 310, 150, 20);
		UlkeTf.addItem("Türkiye Cumhuriyeti");
		
		JLabel ilLbl = new JLabel("İl : ");
		ilLbl.setBounds(70, 345, 80, 20);
		
		JComboBox<String> IlTf = new JComboBox<>();
		IlTf.setBounds(90, 345, 150, 20);
		
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/il ilce mah";
		String name = "root";
		String password = "1234";
		
		
		try {
			
			conn = DriverManager.getConnection(url, name, password);
			
			String query = "SELECT SehirAdi FROM `il ilce mah`.`dbo.sehirler` order by PlakaNo asc;";
			
			PreparedStatement stm = conn.prepareStatement(query);
			ResultSet rset = stm.executeQuery(query);
			
			while (rset.next()) {
				String isim = rset.getString("SehirAdi");
				IlTf.addItem(isim);	

			}
			conn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		IlTf.setSelectedItem(null);
		
		
		JLabel ilceLbl = new JLabel("İlçe : ");
		ilceLbl.setBounds(55, 380, 80, 20);
		
		JComboBox<String> IlceTf = new JComboBox<>();
		IlceTf.setBounds(90, 380, 150, 20);
		
		
		JLabel mahalleLbl = new JLabel("Mahalle : ");
		mahalleLbl.setBounds(33, 415, 80, 20);
		
		JComboBox<String> MahalleTf = new JComboBox<>();
		MahalleTf.setBounds(90, 415, 150, 20);
		
		

		IlTf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IlceTf.removeAllItems();
				MahalleTf.removeAllItems();
				// TODO Auto-generated method stub
				Connection conn = null;
				String url = "jdbc:mysql://localhost:3306/il ilce mah";
				String name = "root";
				String password = "1234";
				
				try {
					
					conn = DriverManager.getConnection(url, name, password);
					
					String query = "SELECT IlceAdi FROM `il ilce mah`.`dbo.ilceler` where SehirAdi = '"+ IlTf.getSelectedItem().toString() + "' order by IlceAdi asc;";
					
					PreparedStatement stm = conn.prepareStatement(query);
					ResultSet rset = stm.executeQuery(query);
					 
					while (rset.next()) {
						String isim = rset.getString("IlceAdi");
						IlceTf.addItem(isim);	
						IlceTf.setSelectedItem(null);
					}
					conn.close();
					
				} catch (Exception e1) {
					// TODO: handle exception
				}		
			}
		});
		
		

		IlceTf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MahalleTf.removeAllItems();
				// TODO Auto-generated method stub
				Connection conn = null;
				String url = "jdbc:mysql://localhost:3306/il ilce mah";
				String name = "root";
				String password = "1234";
				
				try {
					
					conn = DriverManager.getConnection(url, name, password);
					
					String query = "SELECT MahalleAdi FROM `il ilce mah`.`dbo.semtmah` where SemtAdi = '"+ IlceTf.getSelectedItem().toString() + "' order by MahalleAdi asc;";
					
					PreparedStatement stm = conn.prepareStatement(query);
					ResultSet rset = stm.executeQuery(query);
					 
					while (rset.next()) {
						String isim = rset.getString("MahalleAdi");
						MahalleTf.addItem(isim);	
						MahalleTf.setSelectedItem(null);
					}
					conn.close();
					
				} catch (Exception e1) {
					// TODO: handle exception
				}		
			}
		});
		
tcGetirBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				tcOluşturBtn.setEnabled(false);
				seriOluşturBtn.setEnabled(false);
				kayitBtn.setEnabled(false);
				Tctf.setEnabled(false);
				seriNoTf.setEnabled(false);
				yazdırBtn.setEnabled(true);
				
			}
		});
		
	
		
		tcOluşturBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Tctf.setText(TcKimlik().toString());
				
				tcOluşturBtn.setEnabled(false);
				GncBtn.setEnabled(false);
				tcGetirBtn.setEnabled(false);
				Tctf.setEnabled(false);
			}
		});
		
		seriOluşturBtn.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		seriNoTf.setText(seriNo());
		
		seriOluşturBtn.setEnabled(false);
		GncBtn.setEnabled(false);
		tcGetirBtn.setEnabled(false);
		seriNoTf.setEnabled(false);
		
	}
});
		
	kayitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				Connection con = null;
				 String url = "jdbc:mysql://localhost:3306/il ilce mah"; 
			        String user = "root"; 
			        String password = "1234"; 
			        	
			        
			        try {
						
			        	con = DriverManager.getConnection(url, user, password);
			        	
			        	String query = "insert into `il ilce mah`.kayıtlınüfus(ad,soyad,cinsiyet,tckimlik,dogum,anneAd,babaAd,seriNo,ülke,şehir,ilçe,mahalle,kimlikresim,imzaresim) values(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? );";
			        	
			        	PreparedStatement stmt = con.prepareStatement(query);
			        	
			        	stmt.setString(1, adtf.getText());
			        	stmt.setString(2, Soyadtf.getText());
			        	stmt.setString(3, cinsiyetCbox.getSelectedItem().toString());
			        	stmt.setString(4, Tctf.getText());
			        	
			        	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			        	String datetime = format.format(dogumTarihiPicker.getDate());

			        	stmt.setString(5, datetime);
			        	
			        	
			        	
			        	stmt.setString(6, annetf.getText());
			        	stmt.setString(7, babatf.getText());
			        	stmt.setString(8, seriNoTf.getText());
			        	stmt.setString(9, UlkeTf.getSelectedItem().toString());
			        	stmt.setString(10, IlTf.getSelectedItem().toString());
			        	stmt.setString(11, IlceTf.getSelectedItem().toString());
			        	
			        	if (MahalleTf.getSelectedItem() == null) {
							
			        		stmt.setString(12, null);
			        		
						}else {
							stmt.setString(12, MahalleTf.getSelectedItem().toString());
						}
							
			        	
			        	
			        	ImageIcon kimlikicon = (ImageIcon)kimlikResim.getIcon();
			        	ImageIcon imzaicon = (ImageIcon)imzaResim.getIcon();
			        	
			        	BufferedImage kimlikimage = new BufferedImage(kimlikicon.getIconWidth(), kimlikicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			        	BufferedImage imzaimage = new BufferedImage(imzaicon.getIconWidth(), imzaicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			        	
			        	Graphics kimlikGraph = kimlikimage.createGraphics();
			        	Graphics imzaGraphics = imzaimage.createGraphics();
			        	
			        	kimlikicon.paintIcon(null,kimlikGraph, 0, 0);
			        	imzaicon.paintIcon(null,imzaGraphics,0, 0);
			        	
			        	kimlikGraph.dispose();
			        	imzaGraphics.dispose();
			        	
			        	
			        	ByteArrayOutputStream baoskimlik = new ByteArrayOutputStream();
			        	ImageIO.write(kimlikimage, "jpg", baoskimlik);
			        	byte[] imageByteskimlik = baoskimlik.toByteArray();
			        	
			        	
			        	ByteArrayOutputStream baosimza = new ByteArrayOutputStream();
			        	ImageIO.write(imzaimage, "jpg", baosimza);
			        	byte[] imageBytesimza = baosimza.toByteArray();
			        	
			        	stmt.setBytes(13, imageByteskimlik);
			        	stmt.setBytes(14, imageBytesimza);
			        	
			        	stmt.executeUpdate();
			        	
			        	JOptionPane.showMessageDialog(null, "Kişi Başarıyla Kaydedildi");
			        	
			        	con.close();
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Kayıt İşleminde Bir Hata Oluştu " + "" + e2);
						
					}
				
				tcGetirBtn.setEnabled(true);
				GncBtn.setEnabled(true);
				tcOluşturBtn.setEnabled(true);
				seriOluşturBtn.setEnabled(true);
				
				adtf.setText(null);
				Soyadtf.setText(null);
				cinsiyetCbox.setSelectedItem(null);
				Tctf.setText(null);
				dogumTarihiPicker.setDate(today);
				annetf.setText(null);
				babatf.setText(null);
				seriNoTf.setText(null);
				IlTf.setSelectedItem(null);
				IlceTf.setSelectedItem(null);
				MahalleTf.setSelectedItem(null);
				Tctf.setEnabled(true);
				seriNoTf.setEnabled(true);
				
			}
		});
		
		GncBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				
				

				Connection con = null;
				 String url = "jdbc:mysql://localhost:3306/il ilce mah"; 
			        String user = "root"; 
			        String password = "1234"; 
			        	
			        
			        try {
						
			        	con = DriverManager.getConnection(url, user, password);
			        	
			        	String query = "UPDATE `il ilce mah`.kayıtlınüfus SET ad = '"+adtf.getText() +"' , soyad = '"+ Soyadtf.getText()+"',cinsiyet = '"+ cinsiyetCbox.getSelectedItem() + "',dogum = ? ,anneAd = '"+annetf.getText()+"',babaAd ='"+babatf.getText()+"',şehir = '"+IlTf.getSelectedItem()+"',ilçe = '"+IlceTf.getSelectedItem()+"' ,mahalle = ?,kimlikresim = ?,imzaresim = ? where tckimlik = '"+Tctf.getText()+"';";
			        	
			        	PreparedStatement stmt = con.prepareStatement(query);
			        	
			        	
			        	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			        	String datetime = format.format(dogumTarihiPicker.getDate());

			        	stmt.setString(1, datetime);
			        	
			        	
			        	
			        
			        	
			        	if (MahalleTf.getSelectedItem() == null) {
							
			        		stmt.setString(2, null);
			        		
						}else {
							stmt.setString(2, MahalleTf.getSelectedItem().toString());
						}
							
			        	
			        	
			        	ImageIcon kimlikicon = (ImageIcon)kimlikResim.getIcon();
			        	ImageIcon imzaicon = (ImageIcon)imzaResim.getIcon();
			        	
			        	BufferedImage kimlikimage = new BufferedImage(kimlikicon.getIconWidth(), kimlikicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			        	BufferedImage imzaimage = new BufferedImage(imzaicon.getIconWidth(), imzaicon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			        	
			        	Graphics kimlikGraph = kimlikimage.createGraphics();
			        	Graphics imzaGraphics = imzaimage.createGraphics();
			        	
			        	kimlikicon.paintIcon(null,kimlikGraph, 0, 0);
			        	imzaicon.paintIcon(null,imzaGraphics,0, 0);
			        	
			        	kimlikGraph.dispose();
			        	imzaGraphics.dispose();
			        	
			        	
			        	ByteArrayOutputStream baoskimlik = new ByteArrayOutputStream();
			        	ImageIO.write(kimlikimage, "jpg", baoskimlik);
			        	byte[] imageByteskimlik = baoskimlik.toByteArray();
			        	
			        	
			        	ByteArrayOutputStream baosimza = new ByteArrayOutputStream();
			        	ImageIO.write(imzaimage, "jpg", baosimza);
			        	byte[] imageBytesimza = baosimza.toByteArray();
			        	
			        	stmt.setBytes(3, imageByteskimlik);
			        	stmt.setBytes(4, imageBytesimza);
			        	
			        	int rowCount = stmt.executeUpdate();
			        	
			        	if (rowCount >0) {
			        		JOptionPane.showMessageDialog(null, "Kişi Başarıyla Güncellendi");
						}
			        	
			        	
			        	
			        	con.close();
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Kayıt İşleminde Bir Hata Oluştu " + "" + e2);
						
					}
				
				
				
				
				
				
				
				tcOluşturBtn.setEnabled(true);
				seriOluşturBtn.setEnabled(true);
				kayitBtn.setEnabled(true);
				
				
				adtf.setText(null);
				Soyadtf.setText(null);
				cinsiyetCbox.setSelectedItem(null);
				Tctf.setText(null);
				dogumTarihiPicker.setDate(today);
				annetf.setText(null);
				babatf.setText(null);
				seriNoTf.setText(null);
				IlTf.setSelectedItem(null);
				IlceTf.setSelectedItem(null);
				MahalleTf.setSelectedItem(null);
				Tctf.setEnabled(true);
				seriNoTf.setEnabled(true);
				yazdırBtn.setEnabled(false);
				Connection connn = null;
				String urll = "jdbc:mysql://localhost:3306/il ilce mah";
				String namee = "root";
				String passwordd = "1234";
				
				
				try {
					
					
					
					connn = DriverManager.getConnection(urll, namee, passwordd);
					
					String queryy = "SELECT defaultimzaResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
					
					PreparedStatement stmm = connn.prepareStatement(queryy);
					ResultSet rsett = stmm.executeQuery(queryy);
					
					if (rsett.next()) {
						
						  Blob blob = (Blob) rsett.getBlob("defaultimzaResim");
						  
						  InputStream inputStream = blob.getBinaryStream();
						  
						  BufferedImage image = ImageIO.read(inputStream);
						  
						  ImageIcon icon = new ImageIcon(image);

						  Image img = icon.getImage();
						  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIcon = new ImageIcon(newImg);
						  
						  imzaResim.setIcon(newIcon);
						  
					}
					
					connn.close();
					
				} catch (Exception e3) {
					// TODO: handle exception
				}
				
				

				try {
					
					
					
					connn = DriverManager.getConnection(urll, namee, passwordd);
					
					String queryy = "SELECT defaultkimlikResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
					
					PreparedStatement stmm = connn.prepareStatement(queryy);
					ResultSet rsett = stmm.executeQuery(queryy);
					
					if (rsett.next()) {
						
						  Blob blob = (Blob) rsett.getBlob("defaultkimlikResim");
						  
						  InputStream inputStream = blob.getBinaryStream();
						  
						  BufferedImage image = ImageIO.read(inputStream);
						  
						  ImageIcon icon = new ImageIcon(image);

						  Image img = icon.getImage();
						  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIcon = new ImageIcon(newImg);
						  
						  kimlikResim.setIcon(newIcon);
						  
					}
					
					connn.close();
					
				} catch (Exception e3) {
					// TODO: handle exception
				}
				
			}
		});

		temizleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				adtf.setText(null);
				Soyadtf.setText(null);
				cinsiyetCbox.setSelectedItem(null);
				Tctf.setText(null);
				dogumTarihiPicker.setDate(today);
				annetf.setText(null);
				babatf.setText(null);
				seriNoTf.setText(null);
				IlTf.setSelectedItem(null);
				IlceTf.setSelectedItem(null);
				MahalleTf.setSelectedItem(null);
				
				tcOluşturBtn.setEnabled(true);
				seriOluşturBtn.setEnabled(true);
				kayitBtn.setEnabled(true);
				tcGetirBtn.setEnabled(true);
				GncBtn.setEnabled(true);
				Tctf.setEnabled(true);
				seriNoTf.setEnabled(true);
				yazdırBtn.setEnabled(false);
				
				Connection connn = null;
				String urll = "jdbc:mysql://localhost:3306/il ilce mah";
				String namee = "root";
				String passwordd = "1234";
				
				
				try {
					
					
					
					connn = DriverManager.getConnection(urll, namee, passwordd);
					
					String queryy = "SELECT defaultimzaResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
					
					PreparedStatement stmm = connn.prepareStatement(queryy);
					ResultSet rsett = stmm.executeQuery(queryy);
					
					if (rsett.next()) {
						
						  Blob blob = (Blob) rsett.getBlob("defaultimzaResim");
						  
						  InputStream inputStream = blob.getBinaryStream();
						  
						  BufferedImage image = ImageIO.read(inputStream);
						  
						  ImageIcon icon = new ImageIcon(image);

						  Image img = icon.getImage();
						  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIcon = new ImageIcon(newImg);
						  
						  imzaResim.setIcon(newIcon);
						  
					}
					
					connn.close();
					
				} catch (Exception e3) {
					// TODO: handle exception
				}
				
				

				try {
					
					
					
					connn = DriverManager.getConnection(urll, namee, passwordd);
					
					String queryy = "SELECT defaultkimlikResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
					
					PreparedStatement stmm = connn.prepareStatement(queryy);
					ResultSet rsett = stmm.executeQuery(queryy);
					
					if (rsett.next()) {
						
						  Blob blob = (Blob) rsett.getBlob("defaultkimlikResim");
						  
						  InputStream inputStream = blob.getBinaryStream();
						  
						  BufferedImage image = ImageIO.read(inputStream);
						  
						  ImageIcon icon = new ImageIcon(image);

						  Image img = icon.getImage();
						  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIcon = new ImageIcon(newImg);
						  
						  kimlikResim.setIcon(newIcon);
						  
					}
					
					connn.close();
					
				} catch (Exception e3) {
					// TODO: handle exception
				}
				
				
			}
		});
		

		Connection connn = null;
		String urll = "jdbc:mysql://localhost:3306/il ilce mah";
		String namee = "root";
		String passwordd = "1234";
		
		
		try {
			
			
			
			connn = DriverManager.getConnection(urll, namee, passwordd);
			
			String queryy = "SELECT defaultimzaResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
			
			PreparedStatement stmm = connn.prepareStatement(queryy);
			ResultSet rsett = stmm.executeQuery(queryy);
			
			if (rsett.next()) {
				
				  Blob blob = (Blob) rsett.getBlob("defaultimzaResim");
				  
				  InputStream inputStream = blob.getBinaryStream();
				  
				  BufferedImage image = ImageIO.read(inputStream);
				  
				  ImageIcon icon = new ImageIcon(image);

				  Image img = icon.getImage();
				  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
				  ImageIcon newIcon = new ImageIcon(newImg);
				  
				  imzaResim.setIcon(newIcon);
				  
			}
			
			connn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		

		try {
			
			
			
			connn = DriverManager.getConnection(urll, namee, passwordd);
			
			String queryy = "SELECT defaultkimlikResim FROM `il ilce mah`.defaultkimlik where iddefaultKimlik = 1;";
			
			PreparedStatement stmm = connn.prepareStatement(queryy);
			ResultSet rsett = stmm.executeQuery(queryy);
			
			if (rsett.next()) {
				
				  Blob blob = (Blob) rsett.getBlob("defaultkimlikResim");
				  
				  InputStream inputStream = blob.getBinaryStream();
				  
				  BufferedImage image = ImageIO.read(inputStream);
				  
				  ImageIcon icon = new ImageIcon(image);

				  Image img = icon.getImage();
				  Image newImg = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
				  ImageIcon newIcon = new ImageIcon(newImg);
				  
				  kimlikResim.setIcon(newIcon);
				  
			}
			
			connn.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		kimlikEkleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Jpg Dosyaları", "jpg");
				chooser.setFileFilter(filter);
				int result = chooser.showOpenDialog(null);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					
					File seçilidosya = chooser.getSelectedFile();
					
					try {
						
						BufferedImage kimlikimage = ImageIO.read(seçilidosya);
						ImageIcon iconkim = new ImageIcon(kimlikimage);
						
						Image img = iconkim.getImage();
						  Image newImgkim = img.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIconkim = new ImageIcon(newImgkim);
						
						kimlikResim.setIcon(newIconkim);
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
				}
				
				
			}
		});
		
		
		imzaEkleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JFileChooser choose = new JFileChooser();
				FileNameExtensionFilter filtre = new FileNameExtensionFilter("Jpg Dosyaları", "jpg");
				choose.setFileFilter(filtre);
				int sonuc = choose.showOpenDialog(null);
				if (sonuc == JFileChooser.APPROVE_OPTION) {
					
					File seçilendosya = choose.getSelectedFile();
					
					try {
						BufferedImage imzaImage = ImageIO.read(seçilendosya);
						ImageIcon imzaIcon = new ImageIcon(imzaImage);
						
						Image img = imzaIcon.getImage();
						  Image newImgim = img.getScaledInstance(imzaResim.getWidth(), imzaResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIconimz = new ImageIcon(newImgim);
						
						imzaResim.setIcon(newIconimz);
						
						
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
				}
				
			}
		});
		
		tcGetirBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				

				Connection connn = null;
				String urll = "jdbc:mysql://localhost:3306/il ilce mah";
				String namee = "root";
				String passwordd = "1234";
				
				
				try {
					
					
					
					connn = DriverManager.getConnection(urll, namee, passwordd);
					
					String queryy = "SELECT ad,soyad,cinsiyet,tckimlik,dogum,anneAd,babaAd,seriNo,ülke,şehir,ilçe,mahalle,kimlikresim,imzaresim  FROM `il ilce mah`.kayıtlınüfus where tckimlik = '"+Tctf.getText()+ "';";
					
					PreparedStatement stmm = connn.prepareStatement(queryy);
					ResultSet rsett = stmm.executeQuery(queryy);
					
					if (rsett.next()) {
						
						
						adtf.setText(rsett.getString("ad"));
						Soyadtf.setText(rsett.getString("soyad"));
						cinsiyetCbox.setSelectedItem(rsett.getString("cinsiyet"));
						Tctf.setText(rsett.getString("tckimlik"));
						dogumTarihiPicker.setDate(rsett.getDate("dogum"));
						annetf.setText(rsett.getString("anneAd"));
						babatf.setText(rsett.getString("babaAd"));
						seriNoTf.setText(rsett.getString("seriNo"));
						UlkeTf.setSelectedItem(rsett.getString("ülke"));
						IlTf.setSelectedItem(rsett.getString("şehir"));
						IlceTf.setSelectedItem(rsett.getString("ilçe"));
						MahalleTf.setSelectedItem(rsett.getString("mahalle"));
						
						  Blob blob = (Blob) rsett.getBlob("imzaresim");
						  
						  InputStream inputStream = blob.getBinaryStream();
						  
						  BufferedImage image = ImageIO.read(inputStream);
						  
						  ImageIcon icon = new ImageIcon(image);

						  Image img = icon.getImage();
						  Image newImg = img.getScaledInstance(imzaResim.getWidth(), imzaResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIcon = new ImageIcon(newImg);
						  
						  imzaResim.setIcon(newIcon);
						  
						  Blob blobkim = (Blob) rsett.getBlob("kimlikresim");
						  
						  InputStream inputStreamkim = blobkim.getBinaryStream();
						  
						  BufferedImage imagekim = ImageIO.read(inputStreamkim);
						  
						  ImageIcon iconkim = new ImageIcon(imagekim);

						  Image imgkim = iconkim.getImage();
						  Image newImgkim = imgkim.getScaledInstance(kimlikResim.getWidth(), kimlikResim.getHeight(), Image.SCALE_SMOOTH);
						  ImageIcon newIconkim = new ImageIcon(newImgkim);
						  
						  kimlikResim.setIcon(newIconkim);
					}
					
					connn.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		
		
		yazdırBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				
				kisi k1 = new kisi();
				k1.ad = adtf.getText();
				k1.soyad = Soyadtf.getText();
				k1.anneAd = annetf.getText();
				k1.babaAd = babatf.getText();
				k1.cinsiyet = cinsiyetCbox.getSelectedItem().toString();
				k1.tc = Tctf.getText();
				k1.seri = seriNoTf.getText();
				 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				k1.doğum = formatter.format(dogumTarihiPicker.getDate());
				
				Icon kimlikicn = kimlikResim.getIcon();
				ImageIcon imageicnkimlik = (ImageIcon) kimlikicn;
				
				Icon imzaicn = imzaResim.getIcon();
				ImageIcon imageicnimza = (ImageIcon) imzaicn;
				
				k1.kimlikResim = imageicnkimlik.getImage();
				k1.imzaResim = imageicnimza.getImage();
				
				
				kimlikcikti(k1.ad , k1.soyad ,k1.tc , k1.seri , k1.anneAd , k1.babaAd , k1.cinsiyet , k1.doğum , k1.kimlikResim , k1.imzaResim );
				
				
				
				
			}
		});
		
		
		yazdırBtn.setEnabled(false);
		
		
		j.add(adLbl);
		j.add(adtf);
		
		j.add(SoyadLbl);
		j.add(Soyadtf);
		
		j.add(CinsiyetLbl);
		j.add(cinsiyetCbox);
		
		j.add(TcLbl);
		j.add(Tctf);
		j.add(tcOluşturBtn);
		
		j.add(DogumLbl);
		j.add(dogumTarihiPicker);
		
		j.add(anneLbl);
		j.add(annetf);
		
		j.add(babaLbl);
		j.add(babatf);
	
		j.add(seriNolbl);
		j.add(seriNoTf);
		j.add(seriOluşturBtn);
		
		j.add(ulkeLbl);
		j.add(UlkeTf);
		
		j.add(ilLbl);
		j.add(IlTf);
		
		j.add(ilceLbl);
		j.add(IlceTf);
		
		j.add(mahalleLbl);
		j.add(MahalleTf);
		
		j.add(kayitBtn);
		j.add(GncBtn);
		
		j.add(tcGetirBtn);
		j.add(temizleBtn);
		
		j.add(kimlikResim);
		j.add(kimlikEkleBtn);
		
		j.add(imzaResim);
		j.add(imzaEkleBtn);
		
		j.add(yazdırBtn);
		
		
		
		j.setSize(650, 720);
		j.setLayout(null);
		j.setVisible(true);
	}
	
	
private static String TcKimlik() {
		int tek = 0 , cift = 0 , toplam = 0;
		String tcNo = "";
		int[] sayi = new int[11];
	
		Random rdm = new Random();
		
		sayi[0] = rdm.nextInt(1,10);
		for (int i = 1; i < 9; i++) {
			
			sayi[i] = rdm.nextInt(0,10);
		}
		
		for (int i = 0; i < 9; i+=2) {
			
			tek = tek+sayi[i];
		}
		
		for (int i = 1; i < 9; i+=2) {
			
			cift = cift+ sayi[i];
		}
		
		sayi[9] = (((tek* 7) - cift)%10);
		
		for (int i = 0; i < 10; i++) {
			
			toplam = toplam + sayi[i];
		}
		
		sayi[10] = (toplam%10);
		
		for (int tc : sayi) {
			
			tcNo += tc;
		}
		return tcNo;
	}
		
private static String seriNo() {
	
	Random rand = new Random();
	int rakam;
	String sNo = "";
	String[] seri = new String[9];
	
	seri[0] = "A";
	
	for (int i = 1; i < 3; i++) {
		
		seri[i] = Integer.toString(rand.nextInt(0,10));
		 
	}
	
	char randomChar = (char) (rand.nextInt(26) + 'A');
	
	seri[3] = Character.toString(randomChar);
	
for (int i = 4; i < 9; i++) {
		
		seri[i] = Integer.toString(rand.nextInt(0,10));
		 
	}
	
for (String string : seri) {
	
	sNo += string;
}
	
return sNo;
}

private static void kimlikcikti(String isim,String soyisim, String tck,String serin , String anne , String baba ,String cins , String dogum , Image kimlikres , Image imza) {
	
	JFrame kimlik = new JFrame("Kimlik Çıktı");
	
	
	JLabel KimlikCikti = new JLabel();
	KimlikCikti.setBounds(0, 0, 700, 900);
	
	JLabel tc = new JLabel(tck);
	Font tcfont = tc.getFont();
	float boyut = tcfont.getSize() + 5.0f;
	Font tcyeniFont = tcfont.deriveFont(boyut);
	
	tc.setFont(tcyeniFont);
	tc.setBounds(30, 140, 100, 20);
	
	
	JLabel sad = new JLabel(soyisim.toUpperCase());
	sad.setFont(tcyeniFont);
	sad.setBounds(250, 140, 100, 20);

	JLabel ad = new JLabel(isim.toUpperCase());
	ad.setFont(tcyeniFont);
	ad.setBounds(250, 205, 100, 20);
	
	JLabel dog = new JLabel(dogum);
	dog.setFont(tcyeniFont);
	dog.setBounds(250, 270, 100, 20);
	
	JLabel serno = new JLabel(serin.toUpperCase());
	serno.setFont(tcyeniFont);
	serno.setBounds(250, 335, 100, 20);
	
	JLabel cinsiyet = new JLabel();
	cinsiyet.setFont(tcyeniFont);
	cinsiyet.setBounds(425, 270, 100, 20);
	
	if (cins == "Erkek") {
		
		cinsiyet.setText("E / M");
	}else {
		cinsiyet.setText("K / F");
	}
	
	JLabel kimlikresim = new JLabel();
	kimlikresim.setBounds(58, 191, 153, 175);

	
	ImageIcon imageicokimlik = new ImageIcon(kimlikres);
	
	  Image imgkim = imageicokimlik.getImage();
	  Image grikim = toGrayscale(imgkim);
	  Image newImgkim = grikim.getScaledInstance(kimlikresim.getWidth(), kimlikresim.getHeight(), Image.SCALE_SMOOTH);
	  ImageIcon newIconkim = new ImageIcon(newImgkim);
	
	kimlikresim.setIcon(newIconkim);
	
	JLabel imzaresim = new JLabel();
	imzaresim.setBounds(500, 370, 180, 50);
	
	
	ImageIcon imageiconimza = new ImageIcon(imza);
	
	Image imgimz = imageiconimza.getImage();
	Image griimz = toGrayscale(imgimz);
	Image newImgimz = griimz.getScaledInstance(imzaresim.getWidth(), imzaresim.getHeight(), Image.SCALE_SMOOTH);
	ImageIcon newimzaicon = new ImageIcon(newImgimz);
	
	imzaresim.setIcon(newimzaicon);
	
	JLabel anneAd = new JLabel(anne.toUpperCase());
	anneAd.setFont(tcyeniFont);
	
	anneAd.setBounds(200, 565, 100, 20);
	
	
	JLabel babaAd = new JLabel(baba.toUpperCase());
	babaAd.setFont(tcyeniFont);
	babaAd.setBounds(200, 620, 100, 20);
	
	JLabel arkaSeri = new JLabel(serin.toUpperCase() + "< < <");
	Font Arkafont = tc.getFont();
	float arkaboyut = Arkafont.getSize() + 15.0f;
	Font arkayeniFont = tcfont.deriveFont(arkaboyut);
	
	arkaSeri.setFont(arkayeniFont);
	arkaSeri.setBounds(135, 745, 300, 30);
	
	JLabel arkaTc = new JLabel(tck);
	arkaTc.setFont(arkayeniFont);
	arkaTc.setBounds(400, 745, 350, 30);
	
	JLabel arkaRand = new JLabel((random() + "< < ").toUpperCase());
	arkaRand.setFont(arkayeniFont);
	arkaRand.setBounds(20,785,400,30);
	
	JLabel arkaSoyad = new JLabel((soyisim + " < < " + isim).toUpperCase());
	arkaSoyad.setFont(arkayeniFont);
	arkaSoyad.setBounds(20, 830, 800, 30);
	
	
	
	Connection connn = null;
	String urll = "jdbc:mysql://localhost:3306/il ilce mah";
	String namee = "root";
	String passwordd = "1234";
	
	try {
		
		
		
		connn = DriverManager.getConnection(urll, namee, passwordd);
		
		String queryy = "SELECT kimlik FROM `il ilce mah`.kimlikciktitablo where id = 1;";
		
		PreparedStatement stmm = connn.prepareStatement(queryy);
		ResultSet rsett = stmm.executeQuery(queryy);
		
		if (rsett.next()) {
			
			  Blob blob = (Blob) rsett.getBlob("kimlik");
			  
			  InputStream inputStream = blob.getBinaryStream();
			  
			  BufferedImage image = ImageIO.read(inputStream);
			  
			  ImageIcon icon = new ImageIcon(image);

			  Image img = icon.getImage();
			  Image newImg = img.getScaledInstance(KimlikCikti.getWidth(), KimlikCikti.getHeight(), Image.SCALE_SMOOTH);
			  ImageIcon newIcon = new ImageIcon(newImg);
			  
			  KimlikCikti.setIcon(newIcon);
			  
		}
		
		connn.close();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	
	
	
	kimlik.setContentPane(KimlikCikti);
	kimlik.add(tc);
	kimlik.add(sad);
	kimlik.add(ad);
	kimlik.add(dog);
	kimlik.add(serno);
	kimlik.add(cinsiyet);
	kimlik.add(kimlikresim);
	kimlik.add(imzaresim);
	kimlik.add(anneAd);
	kimlik.add(babaAd);
	kimlik.add(arkaSeri);
	kimlik.add(arkaTc);
	kimlik.add(arkaRand);
	kimlik.add(arkaSoyad);
	
	kimlik.setResizable(false);
	kimlik.setSize(KimlikCikti.getWidth()+20,KimlikCikti.getHeight()+40);
	kimlik.setLayout(null);
	kimlik.setVisible(true);
	
	BufferedImage image = new BufferedImage(kimlik.getWidth(),kimlik.getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics2D graphics2D = image.createGraphics();
	kimlik.paint(graphics2D);
	
	Document document = new Document();
	
	try {
	    String filePath = "C:\\Users\\kaan5\\Desktop\\kimlik.pdf";

	    PdfWriter.getInstance(document, new FileOutputStream(filePath));
	    document.open();

	    com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image, null);
	    pdfImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight()); // Çıktıyı sayfaya sığdır
	    pdfImage.setAbsolutePosition(0, 0);
	    document.add(pdfImage);

	    document.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
}

public static Image toGrayscale(Image image) {
    BufferedImage grayscaleImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g2d = grayscaleImage.createGraphics();
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();
    return grayscaleImage;
}

public static String random() {
	

	Random rand = new Random();
	String sNo = "";
	String[] random = new String[15];
	
	
	for (int i = 0; i < 15; i++) {
		
		if (i == 7) {
			
			continue;
		}
		
		random[i] = Integer.toString(rand.nextInt(0,10));
		 
	}
	
	char randomChar = (char) (rand.nextInt(26) + 'A');
	
	random[7] = Character.toString(randomChar);
		
for (String string : random) {
	
	sNo += string;
}
	
return sNo;
}
	
}



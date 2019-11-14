package mes_sfis.client.model.service;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MailService {
	private static String myEmailAccount = "KJ_J_MIS_SFIS@ch.corpnet";
	// private static String myEmailPassword = "pro#7890";
	private static String myEmailPassword = "kjmes$1234";
	// ?件人?箱的 SMTP 服?器地址
	private static String myEmailSMTPHost = "mail.ch.casetekcorp.com";

	public static void testSendEmail(String[] receiveMailAccount, String[] receiveMailAccountBCC, String subject,
			String content, String[] attachment) throws Exception {

		Properties props = new Properties(); // ??配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的??（JavaMail?范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // ?件人的?箱的 SMTP
																// 服?器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要?求??

		// 根据配置?建???象, 用于和?件服?器交互
		Session session = Session.getDefaultInstance(props);
		// ?置?debug模式, 可以查看??的?送 log
		session.setDebug(true);
		// 1. ?建一封?件
		MimeMessage message = createEmail(session, receiveMailAccount, receiveMailAccountBCC, subject, content,
				attachment);
		// 根据 Session ?取?件???象
		Transport transport = session.getTransport();

		transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);

		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);

		// ?送?件, ?到所有的收件地址, message.getAllRecipients() ?取到的是在?建?件?象?添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());
		// ???接
		transport.close();
		System.out.println("郵件發送成功____" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

	}

	public static MimeMessage createEmail(Session session, String[] receiveMailAccount, String[] receiveMailAccountBCC,
			String subject, String content, String[] attachment) throws Exception {

		try {
			// 1. ?建一封?件
			MimeMessage msg = new MimeMessage(session);

			// 2. From: ?件人
			msg.setFrom(new InternetAddress(myEmailAccount, "EFGP", "UTF-8"));
			// InternetAddress fromAddress = new InternetAddress(ACCOUT,
			// "kimi", "utf-8");

			// 3. To: 收件人（可以增加多?收件人、抄送、密送）
			if (receiveMailAccount != null)
				for (int i = 0; i < receiveMailAccount.length; i++) {
					if (i == 0)
						msg.setRecipient(MimeMessage.RecipientType.TO,
								new InternetAddress(receiveMailAccount[i], "UTF-8"));
					else
						msg.addRecipient(MimeMessage.RecipientType.TO,
								new InternetAddress(receiveMailAccount[i], "UTF-8"));
				}
			if (receiveMailAccountBCC != null)
				for (int i = 0; i < receiveMailAccountBCC.length; i++) {
					if (i == 0)
						msg.setRecipient(MimeMessage.RecipientType.BCC,
								new InternetAddress(receiveMailAccountBCC[i], "UTF-8"));
					else
						msg.addRecipient(MimeMessage.RecipientType.BCC,
								new InternetAddress(receiveMailAccountBCC[i], "UTF-8"));
				}

			// 4. Subject: ?件主旨
			msg.setSubject(subject, "UTF-8");
			// 5. Content: ?件正文（可以使用html??）
			if (attachment != null) {
				MimeBodyPart text = new MimeBodyPart();
				text.setContent(content, "text/html;charset=UTF-8");

				MimeMultipart mm = new MimeMultipart();
				mm.addBodyPart(text);

				for (int i = 0; i < attachment.length; i++) {
					MimeBodyPart attachment5 = new MimeBodyPart();
					DataHandler dh5 = new DataHandler(new FileDataSource(attachment[i]));
					attachment5.setDataHandler(dh5);
					attachment5.setFileName(MimeUtility.encodeText(dh5.getName()));
					mm.addBodyPart(attachment5);
				}

				mm.setSubType("mixed");
				msg.setContent(mm);

			} else {
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource("D://mes_data/pdf/ShipMent.pdf"));
				attach.setDataHandler(dh);
				attach.setFileName(dh.getName());
				MimeBodyPart attach1 = new MimeBodyPart();
				DataHandler dh1 = new DataHandler(new FileDataSource("D://mes_data/pdf/ShipMent.xls"));
				attach1.setDataHandler(dh1);
				attach1.setFileName(dh1.getName());
				MimeMultipart mp = new MimeMultipart();
				mp.addBodyPart(attach);
				mp.addBodyPart(attach1);
				mp.setSubType("mixed");
				msg.setContent(mp);
			}

			// 6. ?置?件??
			msg.setSentDate(new Date());

			// 7. 保存?置
			msg.saveChanges();

			return msg;

		} catch (Exception e) {
			System.out.println("郵件發送失敗____" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}

		return null;
	}

}

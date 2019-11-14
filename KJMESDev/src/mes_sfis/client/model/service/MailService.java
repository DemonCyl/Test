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
	// ?��H?�c�� SMTP �A?���a�}
	private static String myEmailSMTPHost = "mail.ch.casetekcorp.com";

	public static void testSendEmail(String[] receiveMailAccount, String[] receiveMailAccountBCC, String subject,
			String content, String[] attachment) throws Exception {

		Properties props = new Properties(); // ??�t�m
		props.setProperty("mail.transport.protocol", "smtp"); // �ϥΪ�??�]JavaMail?�S�n�D�^
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // ?��H��?�c�� SMTP
																// �A?���a�}
		props.setProperty("mail.smtp.auth", "true"); // �ݭn?�D??

		// ���u�t�m?��???�H, �Τ_�M?��A?���椬
		Session session = Session.getDefaultInstance(props);
		// ?�m?debug�Ҧ�, �i�H�d��??��?�e log
		session.setDebug(true);
		// 1. ?�ؤ@��?��
		MimeMessage message = createEmail(session, receiveMailAccount, receiveMailAccountBCC, subject, content,
				attachment);
		// ���u Session ?��?��???�H
		Transport transport = session.getTransport();

		transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);

		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);

		// ?�e?��, ?��Ҧ�������a�}, message.getAllRecipients() ?���쪺�O�b?��?��?�H?�K�[���Ҧ�����H,
		// �۰e�H, �K�e�H
		transport.sendMessage(message, message.getAllRecipients());
		// ???��
		transport.close();
		System.out.println("�l��o�e���\____" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

	}

	public static MimeMessage createEmail(Session session, String[] receiveMailAccount, String[] receiveMailAccountBCC,
			String subject, String content, String[] attachment) throws Exception {

		try {
			// 1. ?�ؤ@��?��
			MimeMessage msg = new MimeMessage(session);

			// 2. From: ?��H
			msg.setFrom(new InternetAddress(myEmailAccount, "EFGP", "UTF-8"));
			// InternetAddress fromAddress = new InternetAddress(ACCOUT,
			// "kimi", "utf-8");

			// 3. To: ����H�]�i�H�W�[�h?����H�B�۰e�B�K�e�^
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

			// 4. Subject: ?��D��
			msg.setSubject(subject, "UTF-8");
			// 5. Content: ?�󥿤�]�i�H�ϥ�html??�^
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

			// 6. ?�m?��??
			msg.setSentDate(new Date());

			// 7. �O�s?�m
			msg.saveChanges();

			return msg;

		} catch (Exception e) {
			System.out.println("�l��o�e����____" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}

		return null;
	}

}

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import com.sun.messaging.jmq.jmsserver.core.Session;

public class MessageReceiver {

	@Resource(mappedName = "jms/GlassFishConnectionFactory")
	private static ConnectionFactory connectionFactory;

	// jndi queue name
	@Resource(mappedName = "jms/GlassFishQueue")
	private static Queue queue;

	public void getMessages() {
		try {
			Connection connection = connectionFactory.createConnection();
			javax.jms.Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			TextMessage textMessage;

			boolean condition = true;

			while (condition) {

				System.out.println("Waiting For Messages");
				textMessage = (TextMessage) messageConsumer.receive();
				if (textMessage != null) {
					System.out.println("Received : " + textMessage.getText());
				}

				if (textMessage != null && textMessage.equals("Second Message")) {
					condition = false;
				}

			}
			
			messageConsumer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MessageReceiver().getMessages();
		
	}

}

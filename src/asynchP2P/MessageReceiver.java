package asynchP2P;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageReceiver {

	@Resource(mappedName = "jms/GlassFishConnectionFactory")
	private static ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "jms/GlassFishQueue")
	private static Queue queue;
	
	public void getMessages(){
		try {
			Connection connection  = connectionFactory.createConnection();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			MessageConsumer messageConsumer = session.createConsumer(queue);
			messageConsumer.setMessageListener(new ExampleMessageListner());
			connection.start();
			TextMessage textMessage;
			
			boolean condition = true;
			while (condition) {

				System.out.println("Waiting For Messages");
				textMessage = (TextMessage) messageConsumer.receive();
				System.out.println("Performing other task while waiting");
				if (textMessage != null) {
					System.out.println("Received : " + textMessage.getText());
				}

				if (textMessage != null && textMessage.equals("Second Message")) {
					condition = false;
				}

			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

	}

}

package asynchP2P;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessageSender {

	@Resource(mappedName = "jms/GlassFishConnectionFactory")
	private static ConnectionFactory connectionFactory;
	
	//jndi queue name
	@Resource(mappedName = "jms/GlassFishQueue")
	private static Queue queue;
	
public void produceMessages(){
		
		MessageProducer messageProducer;
		TextMessage textMessage;
		
		try {
			Connection connection  = connectionFactory.createConnection();
			
			//First Parameter : if true means session is transacted
			//Second Parameter : Auto - acknw by server , Client - reciever will call the acknowledge() method
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			
			messageProducer = session.createProducer(queue);
			textMessage = session.createTextMessage();
			
			textMessage.setText("First Message");
			System.out.println("Sending Message" + textMessage.getText());
			messageProducer.send(textMessage);
			
			textMessage.setText("Second Message");
			System.out.println("Sending Second Message" + textMessage.getText());
			messageProducer.send(textMessage);
			
			messageProducer.close();
			session.close();
			connection.close();
			
			
		} catch (JMSException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		
	}


	public static void main(String[] args) {
		MessageSender messageSender = new MessageSender();
		messageSender.produceMessages();
	}

}

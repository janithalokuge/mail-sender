package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer
{
	public static Logger logger = Logger.getLogger( "SocketServerLog" );
	private int port;
	private boolean running;
	private int noOfEmailDispatures;
	private int noOfSendEmailReqProducers;
	private EmailDispatchQueue emailDispatchQueue;
	private MailServerConnection mailServerConnection;
	private ExecutorService emailReqProducerExecutorService;
	private ExecutorService emailDispatcherExecutorService;

	public SocketServer( int port, boolean listening, int noOfMailSenders, int noOfRequestHandlers, EmailDispatchQueue emailDispatchQueue, MailServerConnection mailServerConnection )
	{
		this.port = port;
		this.running = listening;
		this.noOfEmailDispatures = noOfMailSenders;
		this.noOfSendEmailReqProducers = noOfRequestHandlers;
		this.emailDispatchQueue = emailDispatchQueue;
		this.mailServerConnection = mailServerConnection;
	}

	public SocketServer()
	{
		// There parameters can be load from configs as well
		this.port = 6666;
		this.running = true;
		this.noOfEmailDispatures = 2;
		this.noOfSendEmailReqProducers = 2;

		// Mail serve created with default configs. Customize mail server can be created by using the constructor which requires Properties as an argument.
		this.mailServerConnection = new MailServerConnection();
		this.emailDispatchQueue = new EmailDispatchQueue( new LinkedBlockingQueue<>( 1000 ) );
		emailDispatcherExecutorService = Executors.newFixedThreadPool( noOfEmailDispatures );
		emailReqProducerExecutorService = Executors.newFixedThreadPool( noOfSendEmailReqProducers );
	}

	public void start()
	{
		for ( int i = 0; i < noOfEmailDispatures; i++ )
		{
			emailDispatcherExecutorService.execute( new EmailDispatcher( emailDispatchQueue, mailServerConnection, running ) );
		}

		try (ServerSocket serverSocket = new ServerSocket( port ))
		{
			logger.log( Level.INFO, "The Email Server is running" );
			while ( running )
			{
				emailReqProducerExecutorService.execute( new EmailReqProducer( serverSocket.accept(), emailDispatchQueue ) );
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			logger.log( Level.SEVERE, "Error Starting Server : " + e.toString() );
		}

	}

	public void shutDown()
	{
		logger.info( "shutting down socket server" );
		running = false;
		if ( emailDispatcherExecutorService != null )
		{
			emailDispatcherExecutorService.shutdown();

		}

		if ( emailReqProducerExecutorService != null )
		{
			emailReqProducerExecutorService.shutdown();
		}
	}
}

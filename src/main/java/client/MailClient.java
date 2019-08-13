package client;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailClient
{
	public static void main( String[] args )
	{
		int requests = 100;
		int threads = 10;

		if ( args.length == 2 )
		{
			requests = Integer.parseInt( args[0] );
			threads = Integer.parseInt( args[1] );
		}
		ExecutorService executorService = Executors.newFixedThreadPool( threads );

		for ( int i = 0; i < requests; i++ )
		{
			String requestId = RandomStringUtils.random( 10, true, true );
			executorService.execute( new ClientMailSender( ClientMailSender.getEmailReq( requestId ) ) );
		}

		executorService.shutdown();
		System.out.println( "Email sending task completed" );
	}
}



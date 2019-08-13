package server;

public class SocketServerRunner
{
	public static void main( String[] args )
	{
		System.out.println( "Server Started" );
		SocketServer socketServer = new SocketServer();
		socketServer.start();
		System.out.println( "Server Ends" );
	}
}

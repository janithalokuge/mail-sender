package model;

import java.io.Serializable;

public class SendEmailReq implements Serializable
{
	private String requestId;
	private String senderName;
	private String receipentEmail;
	private String subject;
	private String body;

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId( String requestId )
	{
		this.requestId = requestId;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public void setSenderName( String senderName )
	{
		this.senderName = senderName;
	}

	public String getReceipentEmail()
	{
		return receipentEmail;
	}

	public void setReceipentEmail( String receipentEmail )
	{
		this.receipentEmail = receipentEmail;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject( String subject )
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody( String body )
	{
		this.body = body;
	}

	@Override
	public String toString()
	{
		return "SendEmailReq{" +
				"requestId='" + requestId + '\'' +
				", senderName='" + senderName + '\'' +
				", receipentEmail='" + receipentEmail + '\'' +
				", subject='" + subject + '\'' +
				", body='" + body + '\'' +
				'}';
	}
}

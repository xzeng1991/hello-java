package base.entity;

public class FtpServerConfig
{
	public static int DEFAULT_PORT=21;
	public static int DEFAULT_SFTP_PORT=22;
	private String server;
	private int port=DEFAULT_PORT;
	private String username;
	private String password;
	/**Initial working directory*/
	private String location;
	private boolean sftp = false;
	private String protocol ="SSL";
	
//	private int mode = FTP.
	
	
	public String getServer()
	{
		return server;
	}
	public void setServer(String server)
	{
		this.server = server;
	}
	public int getPort()
	{
		return port;
	}
	public void setPort(int port)
	{
		this.port = port;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public FtpServerConfig(String server, int port, String username,
			String password, String location)
	{
		super();
		this.server = server;
		this.port = port;
		this.username = username;
		this.password = password;
		this.location = location;
	}
	public FtpServerConfig(String server, String username, String password,
			String location)
	{
		this(server, DEFAULT_PORT, username, password, location);
	}
	
	
	public FtpServerConfig(String server, String username, String password)
	{
		super();
		this.server = server;
		this.username = username;
		this.password = password;
	}
	
	
	public boolean isSftp() {
		return sftp;
	}
	public void setSftp(boolean sftp) {
		this.sftp = sftp;
	}
	public String toString()
	{
		return "FtpServerConfig [host=" + server + ", port=" + port
				+ ", username=" + username + ", password=" + password
				+ ", location=" + location + "]";
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}

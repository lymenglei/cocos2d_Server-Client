package network;

public class ZipProto {
	public ZipProto(int index, String req, String res)
	{
		this.index = index;
		this.req = req;
		this.res = res;
	}
	
	public int getIndex()
	{
		return index;
	}
	public String getReq()
	{
		return req;
	}
	public String getRes()
	{
		return res;
	}
	
	private int index;
	private String req;
	private String res;
}

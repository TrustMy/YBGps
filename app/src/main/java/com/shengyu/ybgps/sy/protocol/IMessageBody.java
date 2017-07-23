package com.shengyu.ybgps.sy.protocol;

public interface IMessageBody {
	
	public byte[] writeToBytes();
	
	public void readFromBytes(byte[] messageBodyBytes);
}
package org.litespring.service.v2;

public class PetStoreService {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private String test;
	private int version;
	private boolean power_switch;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	public ItemDao getItemDao() {
		return itemDao;
	}
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isPower_switch() {
		return power_switch;
	}
	public void setPower_switch(boolean power_switch) {
		this.power_switch = power_switch;
	}
	
	
	
	
}

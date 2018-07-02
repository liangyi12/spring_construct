package org.litespring.service.v3;

public class PetStoreService {
	private AccountDao accountDao;
	private ItemDao itemDao;
	private int version;
	
	public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = -1;
	}
	
	public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
		this.accountDao = accountDao;
		this.itemDao = itemDao;
		this.version = version;
	}
	
	
	
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}

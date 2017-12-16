package dto;


public class Manager extends Staff {

	public Manager(String peopleName, String peopleAddress, String governmentId,
			String staffId, String username, boolean isManager,String password) {
		super(peopleName, peopleAddress, governmentId, staffId, username, isManager, password);
	}
	
	public Manager(Manager newValue) {
		this(newValue.getPeopleName().get(),newValue.getPeopleAddress().get(),newValue.getGovernmentId().get(),
				newValue.getStaffId().get(), newValue.getUsername().get(), newValue.getIsManager().get(),newValue.getPassword().get());
	}
	
	public Manager() {
		this(null,null,null,null,null,true,null);
	}
	
	/**
	 * update room info
	 * @param roomId
	 * 		current room need to be updated
	 * @param room
	 * 		new info
	 */
	
	public void updateRoomInfo(String roomId, Room room) {
		
	}
	
	/**
	 * add new room
	 * @param newRoom
	 * 		info of new room
	 */
	public void addRoom(Room newRoom) {
		
	}
	
	
	/**
	 * delete room
	 * @param roomId
	 * 		
	 */
	public void deleteRoom(String roomId) {
		
	}
	
	/**
	 * add new room's type
	 * @param newRoomType
	 */
	
	public void addRoomType(RoomType newRoomType) {
		
	}
	
	/**
	 * delete room type
	 * @param roomTypeId
	 */
	
	public void deleteRoomType(String roomTypeId) {
		
	}
	
	/**
	 * update price of 1 room type
	 * @param roomTypeId
	 */
	public void updatePrice(String roomTypeId) {
		
	}
	
	/**
	 * add new staff
	 * @param newStaff
	 */
	public void addStaff(Staff newStaff) {
		
	}
	
	/**
	 * delete old staff
	 * @param staffId
	 */
	
	public void deletedStaff(String staffId) {
		
	}
	
	/**
	 * change customer type rate
	 * @param customerTypeId
	 * @param newValue
	 */
	public void changeCustomerTypeRate(String customerTypeId, float newValue) {
		
	}
	
	/**
	 * change surcharge
	 * @param newValue
	 */
	
	public void changeSurcharge(float newValue) {
		
	}
	
	/**
	 * change maximum customers per room
	 * @param newValue
	 */
	public void changeMaximumCustomerS(int newValue) {
		
	}
}

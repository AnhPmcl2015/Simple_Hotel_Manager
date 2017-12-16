package dto;

public class Receptionist extends Staff{


	public Receptionist(String peopleName, String peopleAddress,
			String governmentId, String staffId, String username,
			boolean isManager,String password) {
		super(peopleName, peopleAddress, governmentId, staffId, username, isManager, password);
	}
	
	public Receptionist() {
		this(null,null,null,null,null,false,null);
	}
	
	public Receptionist(Receptionist newValue) {
		this(newValue.getPeopleName().get(), newValue.getPeopleAddress().get(), newValue.getGovernmentId().get(), newValue.getStaffId().get(), newValue.getUsername().get(), newValue.getIsManager().get(), newValue.getPassword().get());
	}
}

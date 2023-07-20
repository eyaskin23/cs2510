import tester.Tester;


interface ILoPerson {
	boolean contains(String name);
	int findPhoneNum(String name);
	void changePhone(String name, int newNum);
	void removePerson(String name);
}

class MtLoPerson implements ILoPerson {
	MtLoPerson() {}
	public boolean contains(String name) {
		return false;
	}
	public int findPhoneNum(String name) {
		return -1;
	}
	public void changePhone(String name, int newName) {
		return;
	}
	public void removePerson(String name) {
		return;
	}
}

class ConsLoPerson implements ILoPerson {
	Person first;
	ILoPerson rest;
	ConsLoPerson(Person first, ILoPerson rest) {
		this.first = first;
		this.rest = rest;
	}
	
}
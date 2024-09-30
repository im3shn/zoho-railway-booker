package org.example;

/**
 * Passenger
 */
public class Passenger {

    private static int idProvider = 0;
    private int id;
    
	private String name;
    private int age;
    private int birthPreference;
    private String alloted;

	public Passenger(int id, String name, int age){
        setId(id);
        setName(name);
        setAge(age);
    }
	public Passenger(String name, int age, int birthPreference){
        setId(++idProvider);
        setName(name);
        setAge(age);
        setBirthPreference(birthPreference);
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getBirthPreference() {
		return birthPreference;
	}
	public void setBirthPreference(int birthPreference) {
		this.birthPreference = birthPreference;
	}
    public String getAlloted() {
		return alloted;
	}
	public void setAlloted(String alloted) {
		this.alloted = alloted;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + age;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (age != other.age)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", name=" + name + ", age=" + age + ", alloted=" + alloted + "]";
	}
}


package me.leckie.robotstudy;

/**
 * 
 * @author 011910
 *
 */
public class Pet {

	private String name;

	public Pet(String name) {
		this.name = name;
	}

	public Pet() {
		name = "pet";
	}

	private void eat(Food food) {
		System.out.println(name + " is eatting " + food.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

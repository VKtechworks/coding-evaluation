package com.aa.act.interview.org;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Organization {

	private Position root;
	private List<Position> titleList; 
	private int identifier;
	
	public Organization() {
		root = createOrganization();
		 titleList = new ArrayList<>();
		 titleList.add(root);
		 titleList.addAll(root.getDirectReports().stream().flatMap(p->Stream.concat(Stream.of(p), p.getDirectReports()
				  .stream().flatMap(x->Stream.concat(Stream.of(x), x.getDirectReports().stream())))).collect(Collectors.toList()));
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {	 
		
	
		//your code here	
		Optional<Name> optionalName = Optional.ofNullable(person);
    	Optional<String> optionalTitel = Optional.ofNullable(title);
    	if(optionalName.isPresent() && optionalTitel.isPresent()) {
    		Position position = findEmpTitle(title);     
    		if(Objects.nonNull(position) && !position.isFilled()) {
    			Optional<Employee> newEmployee = Optional.of(new Employee(++this.identifier, person));
    			position.setEmployee(newEmployee);
    			return Optional.of(position);
    		}
    		
    	}	
		 
		return Optional.empty();
	}
	private Position findEmpTitle(String title) {		  
	    Position position = titleList.stream().filter(x->x.getTitle().equalsIgnoreCase(title)).findAny().orElse(null);
	    return position;
		
	}
  
	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	 
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
